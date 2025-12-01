#!/bin/bash

# ------------------------------------------------------------------
# Script Name: check_prime.sh
# Description: Checks if a user-entered number is a Prime Number.
# Logic: A prime number is only divisible by 1 and itself.
#        We check if the number is divisible by any integer from 2
#        up to half of the number.
# ------------------------------------------------------------------

echo "---------------------------------------"
echo "      Prime Number Checker"
echo "---------------------------------------"

# 1. Ask the user for input
read -p "Enter a number to check: " number

# 2. Define a flag variable. 
# We assume the number IS prime initially (is_prime=1).
# If we find a divisor, we change this to 0.
is_prime=1

# 3. Handle edge cases (numbers less than 2 are not prime)
if [ $number -lt 2 ]; then
    is_prime=0
fi

# 4. Loop from 2 up to number/2
# We only need to check up to half the number because a 
# divisor cannot be larger than half the number (except the number itself).
for (( i=2; i<=$number/2; i++ ))
do
    # Check if 'number' is divisible by 'i'
    # The % operator gives the remainder. If remainder is 0, it divides evenly.
    if [ $((number % i)) -eq 0 ]; then
        is_prime=0
        break # We found a divisor, so we stop checking.
    fi
done

# 5. Display the result based on the flag
if [ $is_prime -eq 1 ]; then
    echo "Result: $number is a Prime Number."
else
    echo "Result: $number is NOT a Prime Number."
fi