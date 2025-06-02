#!/bin/bash
curl --verbose -X 'POST' \
  'http://localhost:8080/boxcomment' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "ExampleTextForBoxComment"
}'

