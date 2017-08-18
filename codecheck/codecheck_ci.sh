#!/usr/bin/env bash
OUTPUT=$(java -jar codecheck/checkstyle-8.1-all.jar -c codecheck/google_checks.xml src/ | grep WARN)
if [ ! -z "$OUTPUT" -a "$OUTPUT" != " " ]; then
    java -jar codecheck/checkstyle-8.1-all.jar -c codecheck/google_checks.xml src/
    false
fi
echo "Code Correctly Styled :D"

