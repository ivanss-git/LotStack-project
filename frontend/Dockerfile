# 1. Build stage using Node
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 2. Serve stage using Nginx (Super lightweight production server)
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
# If you are using standard Vite port 5173 or standard web port 80:
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
