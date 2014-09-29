#!/bin/sh

# generates coverage for coveralls
mvn test jacoco:report coveralls:jacoco -P coveralls
