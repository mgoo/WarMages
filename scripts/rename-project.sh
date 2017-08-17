#!/bin/bash





            # TODO remove me after name project





# For renaming this template project modules to your new project name

# Tested on Mac only
# Be careful, this script does a find-replace so could break the project

currentProjectName="$1"
newProjectName="$2"

if [ -z $currentProjectName ] || [ -z $newProjectName ]; then
    echo 'ERROR: Requires 2 arguments. Do something like: ./rename-project.sh towers-of-hanoi test-project'
    exit 1
fi

currentDirName=${PWD##*/}

if [ "$currentDirName" == "scripts" ]; then
    echo 'Running "cd .." to run script from project root directory'
    cd ..
fi

declare -a files=`find .idea settings.gradle`

for filename in ${files[@]}; do
    echo 'Found file: ' $filename

    # sed command may have different syntax between OSs
    sed -i '' "s/$currentProjectName/$newProjectName/g" $filename
    rename "s/$currentProjectName/$newProjectName/g" $filename
done

echo
echo 'Done!'
echo "Don't forget to rename the root folder!"

