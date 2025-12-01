#!/bin/bash

# ------------------------------------------------------------------
# Script Name: check_palindrome.sh
# Description: Checks if a user-entered string is a Palindrome.
# Logic: A palindrome reads the same backwards as forwards.
#        We simply reverse the input string and compare it 
#        to the original.
# ------------------------------------------------------------------

echo "---------------------------------------"
echo "      Palindrome Checker"
echo "---------------------------------------"

# 1. Ask the user for input
read -p "Enter a string to check: " original_string

# 2. Reverse the string manually (From Scratch)
# We calculate the length and loop backwards to build the reversed string.
len=${#original_string}
reversed_string=""

# Loop from the last index (len-1) down to 0
for (( i=$len-1; i>=0; i-- ))
do
    # Append the character at index 'i' to our new string
    # ${string:start:length} extracts a substring
    reversed_string="$reversed_string${original_string:$i:1}"
done

# 3. Compare the original with the reversed version
# We use an 'if' statement to check if they are exactly the same.
if [ "$original_string" == "$reversed_string" ]; then
    echo "Result: '$original_string' is a Palindrome."
else
    echo "Result: '$original_string' is NOT a Palindrome."
fi

# Note: This script is case-sensitive. 
# "Dad" would not be a palindrome because 'D' is not equal to 'd'.