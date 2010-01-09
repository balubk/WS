#!/bin/bash

for file in *.csv; do week=${file%%.csv}; cat $file | while read line; do echo -e "$week\t$line" >> all.csv; done; done
