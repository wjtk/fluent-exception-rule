#!/bin/bash

# like travis after_success, but without sending to coveralls
./mvnw test jacoco:report -P coveralls
