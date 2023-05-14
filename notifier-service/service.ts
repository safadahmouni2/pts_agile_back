import * as express from 'express';
import * as fs from 'fs';
import * as http from 'http';
import * as https from 'https';
import { Server } from 'socket.io';
import {
  HealthChecker,
  LivenessEndpoint,
  ReadinessEndpoint,
  HealthEndpoint
} from '@cloudnative/health-connect';

const DEFAULT_NODE_ENV = 'DEV';
const DEFAULT_CONNECTION = 'HTTP';
const DEFAULT_PORT = 5000;

const loggedUsers: string[] = [];
let index = -1;

console.log('NODE_ENV:', (process.env.NODE_ENV || `[${DEFAULT_NODE_ENV}]`));
console.log('CONNECTION:', (process.env.CONNECTION || `[${DEFAULT_CONNECTION}]`));
// set environment
const env = environments(process.env.NODE_ENV);
/*------------- express server setup ---------------*/
const app: express.Application = express();
// Set up a HealthChecker
let healthcheck = new HealthChecker();
// Register a separate Liveness endpoint
app.use('/live', LivenessEndpoint(healthcheck));
// Register a separate readiness endpoint
app.use('/ready', ReadinessEndpoint(healthcheck));
// Register a combined health endpoint
app.use('/health', HealthEndpoint(healthcheck));
// check secure connection
let server;
if (process.env.CONNECTION === 'HTTPS') {
  server = https.createServer(env.certOptions, app);
} else {
  server = http.createServer(app);
}

const CORSOptions = {
  allowedHeaders: ['Content-Type', 'Authorization'],
  methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS', 'HEAD'],
  origin: '*',
  credentials: true
};

const io = new Server(server,
  {
    allowEIO3: true, // false by default migration to Socket.IO v3
    cors: CORSOptions
  });
// start listening to port default 5000
server.listen((process.env.PORT || DEFAULT_PORT), () => {
  console.log('start', (process.env.CONNECTION || DEFAULT_CONNECTION), `connection on port ${process.env.PORT || DEFAULT_PORT}`);
});

io.on('connection', (socket: any) => {
  console.log('client socket.id [', socket.id, '] connected successfully');

  socket.on('disconnect', function () {
    console.log('client socket.id [', socket.id, '] disconnected');
  });

  /****************************************************************************************************/

  socket.on('ds-start', (message: string) => {
    console.log('server data :', message);
    io.emit('ds-start', { type: 'new-message', text: message });
  });

  socket.on('ds-finished', (message: string) => {
    console.log('server data :', message);
    io.emit('ds-finished', { type: 'new-message', text: message });
  });

  socket.on('user-joined', (message: string) => {
    console.log('server data :', message);
    io.emit('user-joined', { type: 'new-message', text: message });
  });

  socket.on('user-talking', (message: string) => {
    console.log('server data :', message);
    io.emit('user-talking', { type: 'new-message', text: message });
  });

  socket.on('us-update', (message: string) => {
    console.log('server data :', message);
    io.emit('us-update', { type: 'new-message', text: message });
  });

  socket.on('add-message', (message: string) => {
    io.emit('message', { type: 'new-message', text: message });
  });

  socket.on('add-user', (user: string) => {
    console.log('new user logged in the server side: ', user);
    loggedUsers.push(user);
    console.log('Liste logged in users in the server side: ', loggedUsers);
    io.emit('user', { type: 'new-user', text: { 'user': user, 'loggeduser': loggedUsers } });

  });

  socket.on('delete-user', (user: string) => {
    index = loggedUsers.indexOf(user);
    console.log('index : ', index);
    loggedUsers.splice(index, 1);
    console.log('Liste logged in users in the server side: ', loggedUsers);
    io.emit('loggedout-user', { type: 'loggedout-user', text: { 'user': user, 'loggeduser': loggedUsers } });
  });

  socket.on('onstatuschage-user', (user: string) => {
    io.emit('user-statuschanged', { type: 'user-statuschanged', text: { 'user': user } });
  });


});

/*------------------- helper functions ----------------------*/
function environments(env: string): { certOptions: { key: Buffer, cert: Buffer } } {
  console.log(`Loading certicate for env: ${env || DEFAULT_NODE_ENV} ...`);
  switch (env) {
    case 'PROD':
      return {
        certOptions: {
          key: fs.readFileSync('certificate/env/prod/pts_scrum.key'),
          cert: fs.readFileSync('certificate/env/prod/pts_scrum.crt')
        }
      }
    case 'TEST':
      return {
        certOptions: {
          key: fs.readFileSync('certificate/env/test/pts_scrum.key'),
          cert: fs.readFileSync('certificate/env/test/pts_scrum.crt')
        }
      }
    default:
      return {
        certOptions: {
          key: fs.readFileSync('certificate/env/dev/pts_scrum.key'),
          cert: fs.readFileSync('certificate/env/dev/pts_scrum.crt')
        }
      }
  }

}