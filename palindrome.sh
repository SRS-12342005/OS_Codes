#!/bin/bash

echo "---------------------------------------"
echo "      Palindrome Checker"
echo "---------------------------------------"

read -p "Enter a string to check: " original_string

len=${#original_string}
reversed_string=""

for (( i=$len-1; i>=0; i-- ))
do
    reversed_string="$reversed_string${original_string:$i:1}"
done

if [ "$original_string" == "$reversed_string" ]; then
    echo "Result: '$original_string' is a Palindrome."
else
    echo "Result: '$original_string' is NOT a Palindrome."
fi
