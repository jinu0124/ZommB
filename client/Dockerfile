# FROM node:lts-alpine
# RUN npm install -g http-server
# WORKDIR /app/frontend
# COPY package*.json ./
# RUN npm install --production
# COPY . .
# RUN npm run build
# CMD ["http-server", "dist", "-p", "8000"]

FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package.json package-lock.json /app/
RUN yarn install

COPY . /app
RUN yarn run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY  ./nginx/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/dist /usr/share/nginx/html
# EXPOSE 8000
CMD ["nginx", "-g", "daemon off;"]
