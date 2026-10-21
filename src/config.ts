import { z } from 'zod';
import dotenv from 'dotenv';

dotenv.config();

const configSchema = z.object({
  NODE_ENV: z.enum(['development', 'test', 'production']).default('development'),
  PORT: z.coerce.number().default(3000),
  HOST: z.string().default('0.0.0.0'),
  LOG_LEVEL: z.enum(['fatal', 'error', 'warn', 'info', 'debug', 'trace']).default('info'),
  DATABASE_URL: z.string().default('postgres://dropzero:dropzero@localhost:5432/dropzero'),
  REDIS_URL: z.string().default('redis://localhost:6379'),
  JWT_SECRET: z.string().min(32).default('super-secret-jwt-key-change-in-production-at-least-32-chars'),
  JWT_ACCESS_EXPIRATION: z.string().default('15m'),
  JWT_REFRESH_EXPIRATION: z.string().default('30d'),
  MIN_APP_VERSION: z.string().default('1.0.0'),
  FEATURE_REWARDS_ENABLED: z.coerce.boolean().default(true),
});

export type Config = z.infer<typeof configSchema>;

export function loadConfig(env = process.env): Config {
  const result = configSchema.safeParse(env);
  if (!result.success) {
    console.error('Invalid environment variables:', result.error.format());
    throw new Error('Invalid environment variables');
  }
  return result.data;
}

export const config = loadConfig();
