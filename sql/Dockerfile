FROM mysql:5.7


ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./container/ruoyi-vue-pro.sql /docker-entrypoint-initdb.d/ruoyi-vue-pro.sql

COPY ./container/quartz.sql /docker-entrypoint-initdb.d/quartz.sql

COPY ./container/bpm-flowable.sql /docker-entrypoint-initdb.d/bpm-flowable.sql


