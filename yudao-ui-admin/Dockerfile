FROM nginx

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf

RUN rm /etc/nginx/nginx.conf

ADD ./docker/nginx.conf /etc/nginx/nginx.conf

ADD ./docker/ruoyi-vue-pro.conf /etc/nginx/conf.d/
