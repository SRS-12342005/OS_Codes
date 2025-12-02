#!/bin/bash

echo "---------------------------------------"
echo "      Prime Number Checker"
echo "---------------------------------------"

read -p "Enter a number to check: " number

is_prime=1

if [ $number -lt 2 ]; then
    is_prime=0
fi

for (( i=2; i<=$number/2; i++ ))
do
    if [ $((number % i)) -eq 0 ]; then
        is_prime=0
        break
    fi
done

if [ $is_prime -eq 1 ]; then
    echo "Result: $number is a Prime Number."
else
    echo "Result: $number is NOT a Prime Number."
fi
