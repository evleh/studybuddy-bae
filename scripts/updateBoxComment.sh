#!/bin/bash

# admin changing user comment
curl -X 'PUT' \
  'http://localhost:8080/boxcomments/7' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 7,
  "text": "admin changing user comment",
  "boxId": 0,
  "authorId": 0
}'

# admin changing own comment
curl -X 'PUT' \
  'http://localhost:8080/boxcomments/2' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 2,
  "text": "admin changing own comment",
  "boxId": 0,
  "authorId": 0
}'

# user changing own comment
curl -X 'PUT' \
  'http://localhost:8080/boxcomments/6' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 6,
  "text": "user changing own comment",
  "boxId": 0,
  "authorId": 0
}'

# user changing admin comment
curl -X 'PUT' \
  'http://localhost:8080/boxcomments/1' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 1,
  "text": "user changing admin comment",
  "boxId": 0,
  "authorId": 0
}'