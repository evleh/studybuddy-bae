#!/bin/bash

curl --verbose -X 'POST' \
  'http://localhost:8080/box' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "shor",
  "description": "ExampleDescription",
  "public": false
}'
