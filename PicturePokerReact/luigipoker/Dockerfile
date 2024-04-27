# pull official base image
FROM node:16.13.1-alpine as build

# set working directory
WORKDIR /usr/src/app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# start app
EXPOSE 80
CMD ["serve", "-s", "build", "-l", "80"]