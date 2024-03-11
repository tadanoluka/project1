FROM node:20.10.0-alpine as stage1
WORKDIR /front
COPY package.json ./
RUN npm install

FROM node:20.10.0-alpine as stage2
WORKDIR /front
COPY . .
COPY --from=stage1 /front/node_modules ./node_modules
RUN npm run build

FROM node:20.10.0-alpine as stage3
WORKDIR /front
ENV NODE_ENV production
COPY --from=stage2 /front ./

EXPOSE 3000
CMD ["npm", "start"]