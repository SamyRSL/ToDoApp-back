FROM ubuntu:latest
LABEL authors="samyr"

ENTRYPOINT ["top", "-b"]