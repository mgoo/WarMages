#!/bin/bash

# Script to get Gradle to work on Victoria University ECS computers

EXPORT_PATH="$HOME/.gradle/settings.gradle"

if [ -f "$EXPORT_PATH" ]; then
    echo "You already have a $EXPORT_PATH file."
    echo "If you can run gradlew successfully, you don't need to run this script."
    echo "But if gradlew is not working, then rename out your settings.gradle."
    echo "and run this script again. You can put your settings.gradle back"
    echo "later."
    exit
fi

BASE_FILE="base-settings.gradle"
if [ -d ".git" ]; then
    BASE_FILE="scripts/$BASE_FILE"
fi

cp "$BASE_FILE" "$EXPORT_PATH"
