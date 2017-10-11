#!/bin/bash

# Examples:
# ./scripts/count-lines-per-user.sh # count all code
# ./scripts/count-lines-per-user.sh "./src" # count everything in source

if [ -z "$1" ]; then
  SEARCH_DIR="." # Defaults to current directory
else
  SEARCH_DIR="$1"
fi


USERS=("Chong" "Palado" "Mcghie|mgoo" "Arora" "Diputa")
for user in ${USERS[*]}; do
  echo "$user"
  for file in $(git ls-files "$SEARCH_DIR"); do
    git blame $file | grep --ignore-case -E "$user"
  done
done
