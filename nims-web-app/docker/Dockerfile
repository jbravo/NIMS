# stage 1
FROM 10.240.201.50:7890/node:10.15.1-stretch-slim as node
WORKDIR /app
COPY . .
RUN pwd
RUN ls -al
RUN npm config set cache /root/.npm --global
RUN npm config ls -l |grep registry
RUN npm --registry http://10.60.129.132/repository/npm install
RUN npm run build --prod
RUN ls -al dist/nims-web
RUN tar -cvzf NimsWeb.tar.gz --strip-components=1 dist/nims-web
# stage 2
#FROM nginx:alpine
#COPY /docker/nginx-conf/nginx.conf /usr/share/nginx/nginx.conf
#COPY --from=node /app/dist/nims-project-app /usr/share/nginx/html
