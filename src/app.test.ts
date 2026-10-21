import { describe, it, expect, beforeAll, afterAll } from 'vitest';
import { AppInstance, buildApp } from './app.js';

describe('Health Endpoints', () => {
  let app: AppInstance;

  beforeAll(async () => {
    app = await buildApp();
    await app.ready();
  });

  afterAll(async () => {
    await app.close();
  });

  it('GET /health returns 200 ok', async () => {
    const response = await app.inject({
      method: 'GET',
      url: '/health',
    });

    expect(response.statusCode).toBe(200);
    const body = JSON.parse(response.body);
    expect(body.status).toBe('ok');
    expect(body.timestamp).toBeDefined();
  });

  it('GET /v1/health returns 200 ok', async () => {
    const response = await app.inject({
      method: 'GET',
      url: '/v1/health',
    });

    expect(response.statusCode).toBe(200);
    const body = JSON.parse(response.body);
    expect(body.status).toBe('ok');
  });

  it('GET /non-existent returns 404 formatted error', async () => {
    const response = await app.inject({
      method: 'GET',
      url: '/v1/non-existent',
    });

    expect(response.statusCode).toBe(404);
    const body = JSON.parse(response.body);
    expect(body).toMatchObject({
      code: 'NOT_FOUND',
      requestId: expect.any(String),
    });
  });
});
