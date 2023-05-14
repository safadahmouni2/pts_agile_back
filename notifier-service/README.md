
# How to start the service

All commands should be entered in the current folder:

1. "npm install"

2. "npm run build" for compile

3. "npm run start" to start service after compile

# Environment variables

NODE_ENV: string DEV | TEST | PROD default DEV

CONNECTION: string HTTP | HTTPS  default HTTP

PORT: number default 5000

# Running in Kubernetes
## Kubernetes Health checks
notifier-service uses Cloud Health Connect to show the status reported by [Cloud Health](http://github.com/CloudNativeJS/cloud-health).

It exposes the endpoints '/live', '/ready' and '/health' to report the following status:

| Cloud Health Status | Readiness Status Code | Liveness Status Code | Combined Health Status Code |
|---------------------|-----------------------|----------------------|-----------------------------|
| STARTING            | 503 UNAVAILABLE       | 200 OK               | 503 UNAVAILABLE             |
| UP                  | 200 OK                | 200 OK               | 200 OK                      |
| DOWN                | 503 UNAVAILABLE       | 503 UNAVAILABLE      | 503 UNAVAILABLE             |
| STOPPING            | 503 UNAVAILABLE       | 503 UNAVAILABLE      | 503 UNAVAILABLE             |
| STOPPED             | 503 UNAVAILABLE       | 503 UNAVAILABLE      | 503 UNAVAILABLE             |
| -		              | 500 SERVER ERROR      | 500 SERVER ERROR     | 500 SERVER ERROR            |

See also [Cloud Health Connect](https://github.com/CloudNativeJS/cloud-health-connect).
