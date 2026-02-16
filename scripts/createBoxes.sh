#!/bin/bash

curl -X 'POST' \
  'http://localhost:8080/boxes' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "title": "public admin box",
  "description": "string",
  "ownerId": 1,
  "commentIds": [
    0
  ],
  "cardIds": [
    0
  ],
  "public": true
}'

curl -X 'POST' \
  'http://localhost:8080/boxes' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "title": "private admin box",
  "description": "string",
  "ownerId": 1,
  "commentIds": [
    0
  ],
  "cardIds": [
    0
  ],
  "public": false
}'

curl -X 'POST' \
  'http://localhost:8080/boxes' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "title": "private user box",
  "description": "string",
  "ownerId": 2,
  "commentIds": [
    0
  ],
  "cardIds": [
    0
  ],
  "public": false
}'

curl -X 'POST' \
  'http://localhost:8080/boxes' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "title": "public user box",
  "description": "string",
  "ownerId": 2,
  "commentIds": [
    0
  ],
  "cardIds": [
    0
  ],
  "public": true
}'

