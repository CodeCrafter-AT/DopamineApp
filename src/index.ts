import { buildApp } from './app.js';
import { loadConfig } from './config.js';

async function start() {
  const config = loadConfig();
  const app = await buildApp(config);

  try {
    await app.listen({ port: config.PORT, host: config.HOST });
    app.log.info(`DropZero Backend server listening on http://${config.HOST}:${config.PORT}`);
  } catch (err) {
    app.log.error(err, 'Failed to start server');
    process.exit(1);
  }
}

start();
