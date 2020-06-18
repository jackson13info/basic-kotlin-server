FROM openjdk:8

COPY bin/* /bin/
COPY lib/* /lib/

ADD production.yaml production.yaml

CMD /bin/spiritz server production.yaml

EXPOSE 8080
