import pino from 'pino';
import { Config } from './config.js';

export function createLogger(config: Config) {
  return pino({
    level: config.LOG_LEVEL,
    redact: {
      paths: [
        'req.headers.authorization',
        'req.headers.cookie',
        '*.phone',
        '*.phoneNumber',
        '*.otp',
        '*.code',
        '*.token',
        '*.accessToken',
        '*.refreshToken',
        '*.password',
        '*.cardNumber',
        '*.cvv',
        '*.paymentDetails',
        '*.razorpaySecret',
      ],
      censor: '[REDACTED]',
    },
    formatters: {
      level: (label) => ({ level: label }),
    },
    timestamp: pino.stdTimeFunctions.isoTime,
  });
}
