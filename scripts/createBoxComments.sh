#!/bin/bash
# create:
## admin comment
curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "admin comment 1",
  "boxId": 1,
  "authorId": 1
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "admin comment 2",
  "boxId": 2,
  "authorId": 1
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "admin comment 3",
  "boxId": 3,
  "authorId": 1
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNTc5NzMsInVzZXJJZCI6MSwidXNlck5hbWUiOiJhZG1pbiIsImlzcyI6InN0dWR5YnVkZHktYmFlIiwiaWF0IjoxNzcwNjI5OTczfQ.WO0evQKs2rqt0fGMg8284XCQXxvoMTlJUGJNNKzIflk' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "admin comment 4",
  "boxId": 4,
  "authorId": 1
}'

## user comments
curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "post user comment box 1",
  "boxId": 1,
  "authorId": 2
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "post user comment box 2",
  "boxId": 2,
  "authorId": 2
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "post user comment box 3",
  "boxId": 3,
  "authorId": 2
}'

curl -X 'POST' \
  'http://localhost:8080/boxcomments' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NzIzNjE3NTAsInVzZXJJZCI6MiwidXNlck5hbWUiOiJub3JtYWwiLCJpc3MiOiJzdHVkeWJ1ZGR5LWJhZSIsImlhdCI6MTc3MDYzMzc1MH0.9mHsbX_wIPk761Qw6MYT6SD14i76clM1P-kNzMNRjJ8' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": 0,
  "text": "post user comment box 4",
  "boxId": 4,
  "authorId": 2
}'