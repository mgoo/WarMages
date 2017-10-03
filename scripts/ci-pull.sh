#!/usr/bin/env bash
echo "if you are not ci then your git name and email just go changed"
git config --global user.email "runner@runner.com"
git config --global user.name "Runner"
if git pull origin master | grep -q 'Automatic merge failed'; then
  exit 1
fi