# stage 1
FROM 10.240.201.50:7890/node:10.15.1-stretch-slim as node
WORKDIR /app
COPY . .
RUN pwd
RUN ls -al
RUN mkdir node_modules
RUN cp -r other_modules/node-sass.tar.gz node_modules
RUN ls -al node_modules
WORKDIR /app/node_modules
RUN tar -xzvf node-sass.tar.gz
RUN ls -al
WORKDIR /app
RUN npm --registry http://10.60.129.132/repository/npm/ install --sass-binary-path="/app/other_modules/linux-x64-64_binding.node"
RUN npm run build --sass-binary-path="/app/other_modules/linux-x64-64_binding.node"
# stage 2
FROM 10.240.201.50:7890/nginx:stable-alpine
COPY /docker/nginx-conf/nginx.conf /usr/share/nginx/nginx.conf
COPY --from=node /app/dist/nims-web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]

