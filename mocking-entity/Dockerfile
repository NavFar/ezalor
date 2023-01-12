FROM python:latest
WORKDIR /usr/app/src
COPY mock.py requirements.txt ./
RUN  pip install -r requirements.txt
ENTRYPOINT ["python3","mock.py"]

