#!/bin/bash

echo "===================="
echo " Sum of Digits Calculator "
echo "===================="
echo

echo -n "Enter a number : "
read num

original=$num

sum=0

num=${num#-}

if ! [[ $num =~ ^[0-9]+$ ]];  then
        echo "Error: Please enter a valid number! "
        exit 1
fi

while [ $num -gt 0 ]
do
        digit=$((num % 10))
        sum=$((sum + digit))
        num=$((num / 10))
done

echo
echo "Number Entered : $original"
echo "Sum of Digits : $sum"
echo