import Fastify, { FastifyReply, FastifyRequest } from 'fastify';
import cors from '@fastify/cors';
import helmet from '@fastify/helmet';
import crypto from 'node:crypto';
import { Config, loadConfig } from './config.js';
import { createLogger } from './logger.js';

export interface AppErrorOptions {
  code: string;
  message: string;
  statusCode: number;
  details?: unknown;
}

export class AppError extends Error {
  public readonly code: string;
  public readonly statusCode: number;
  public readonly details?: unknown;

  constructor(options: AppErrorOptions) {
    super(options.message);
    this.code = options.code;
    this.statusCode = options.statusCode;
    this.details = options.details;
  }
}

export async function buildApp(customConfig?: Config) {
  const cfg = customConfig || loadConfig();
  const logger = createLogger(cfg);

  const app = Fastify({
    loggerInstance: logger,
    genReqId: (req) => (req.headers['x-request-id'] as string) || crypto.randomUUID(),
    requestIdHeader: 'x-request-id',
  });

  await app.register(cors, { origin: true });
  await app.register(helmet, { contentSecurityPolicy: false });

  // Custom Error Handler following DropZero convention
  app.setErrorHandler((error: Error & { statusCode?: number; code?: string; validation?: unknown }, request: FastifyRequest, reply: FastifyReply) => {
    const requestId = request.id;

    if (error instanceof AppError) {
      return reply.status(error.statusCode).send({
        code: error.code,
        message: error.message,
        details: error.details,
        requestId,
      });
    }

    if (error.validation) {
      return reply.status(400).send({
        code: 'BAD_REQUEST',
        message: 'Validation failed',
        details: error.validation,
        requestId,
      });
    }

    const statusCode = error.statusCode || 500;
    const message = statusCode >= 500 ? 'Internal server error' : error.message;
    const code = error.code || (statusCode === 404 ? 'NOT_FOUND' : 'INTERNAL_ERROR');

    request.log.error({ err: error }, 'Unhandled error');

    return reply.status(statusCode).send({
      code,
      message,
      requestId,
    });
  });

  // Not Found Handler
  app.setNotFoundHandler((request, reply) => {
    reply.status(404).send({
      code: 'NOT_FOUND',
      message: `Route ${request.method} ${request.url} not found`,
      requestId: request.id,
    });
  });

  // Health check endpoints
  const healthHandler = async (_req: FastifyRequest, reply: FastifyReply) => {
    return reply.status(200).send({
      status: 'ok',
      timestamp: new Date().toISOString(),
      uptime: process.uptime(),
    });
  };

  app.get('/health', healthHandler);
  app.get('/v1/health', healthHandler);

  return app;
}

export type AppInstance = Awaited<ReturnType<typeof buildApp>>;
