FROM oracle/graalvm-ce:21.2.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/footballermanager
WORKDIR /home/app/footballermanager

RUN native-image --no-server -cp build/libs/footballermanager-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/footballermanager/footballermanager /app/footballermanager
ENTRYPOINT ["/app/footballermanager"]
