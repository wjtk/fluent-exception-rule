#!/bin/sh

# generates coverage for coveralls
./mvnw test jacoco:report coveralls:jacoco -P coveralls
