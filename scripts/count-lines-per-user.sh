#!/bin/bash

# Examples:
# ./scripts/count-lines-per-user.sh # count all code
# ./scripts/count-lines-per-user.sh "./src" # count everything in source

if [ -z "$1" ]; then
  SEARCH_DIR="." # Defaults to current directory
else
  SEARCH_DIR="$1"
fi


# Copied from https://gist.github.com/amitchhajer/4461043
git ls-files "$SEARCH_DIR" \
  | while read f; do git blame --line-porcelain $f | grep '^author '; done \
  | sort -f \
  | uniq -ic \
  | sort -n
