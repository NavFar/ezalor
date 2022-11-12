#FROM python:latest
FROM alpine:3.1
RUN apk add --update make cmake gcc g++ gfortran
RUN apk add --update python py-pip python-dev
RUN pip install cython
RUN pip install numpy
WORKDIR /usr/app/src
COPY mock.py requirements.txt ./
#RUN  pip install -r requirements.txt
ENTRYPOINT ["python mock.py"]

