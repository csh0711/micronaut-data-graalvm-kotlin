FROM oracle/graalvm-ce:19.3.1-java8 as graalvm
#FROM oracle/graalvm-ce:19.3.1-java11 as graalvm # For JDK 11
RUN gu install native-image

COPY . /home/app/footballmanager
WORKDIR /home/app/footballmanager

RUN native-image --no-server --static -cp build/libs/footballmanager-*-all.jar

FROM scratch
EXPOSE 8080
COPY --from=graalvm /home/app/footballmanager/footballmanager /app/footballmanager
ENTRYPOINT ["/app/footballmanager", "-Djava.library.path=/app"]
