#!/bin/bash

# like travis after_success, but without sending to coveralls
mvn test jacoco:report -P coveralls
