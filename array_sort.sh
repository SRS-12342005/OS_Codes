#!/bin/bash

echo "==========================="
echo " Array Sort in Descending Order "
echo "==========================="
echo

echo -n "How many element do you want to Enter ?"
read n

if ! [[ $n =~ ^[0-9]+$ ]] || [ $n -le 0 ]; then
        echo "Error: Please enter valid positive number "
        exit 1
fi

declare -a array

echo
echo "Enter $n elements : "
for ((i=0; i<n; i++))
do
        echo -n "Elements $((i+1)): "
        read arr[$i]
done

echo
echo "Original Array:"
echo "${arr[@]}"

for ((i=0; i<n; i++))
do
        for((j=0; j<n-i-1; j++))
        do
                if [ ${arr[$j]} -lt ${arr[$((j+1))]} ]
                then
                        temp=${arr[$j]}
                        arr[$j]=${arr[$((j+1))]}
                        arr[$((j+1))]=$temp
                fi
        done
done
echo
echo "Sorted Array (Descending Order) :"
echo "${arr[@]}"
echo
echo "============================="