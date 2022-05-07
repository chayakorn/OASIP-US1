# build stage
FROM node:14-alpine AS build-stage

WORKDIR /oasipfrontend

COPY . .
RUN npm install
RUN npm run build

# production stage
FROM nginx AS production-stage

COPY --from=build-stage /oasipfrontend/dist /usr/share/nginx/html
COPY ./nginxfrontend.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]