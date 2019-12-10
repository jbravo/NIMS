# stage 1
FROM 10.240.201.50:7890/node:10.15.1-stretch-slim as node
WORKDIR /app
COPY . .
RUN pwd
RUN yarn lint
