# War Mages

War Mages is a Real-time strategy game that was created as part of a group project at Uni. 

## Installation

Clone the project then its just a standard Gradle project so running ./gradlew build and ./gradlew run from the project root will build and run the War Mages.
This does requrire java8 to be installed. Other than that Gradle will handle all the dependancies.

### Setup IntelliJ project

- Make sure the Gradle plugin is on:
    - Go to `Settings/Preferences -> Plugins`
    - Then make sure the Gradle plugin is ticked
- In IntelliJ, `Create New Project` (or File > New > Project if inside)
    - Pick Java on the left
    - Click Next, then click Next
    - Select the project location to be inside the repository you have cloned
    - Click Finish
    - At the bottom right, you should get a `Unlinked Gradle project?` message
      (you will probably have to wait a little bit)
        - Click `Import Gradle project`
        - Click OK
- Open the `Gradle` toolbar thing:
    - Should be on an edge of the window
        - If you can't find it, Try pressing `Ctrl+Shift+A` or `Cmd+Shift+A`
          (Mac) then typing `Gradle`. You should see `Gradle`. Pick the one
          that has this [Gradle
          icon](https://lh6.googleusercontent.com/-fvt5jz8KJ9E/AAAAAAAAAAI/AAAAAAAAAAc/-dxpnszHExs/photo.jpg)
    - Click the blue refresh button to make IntelliJ load the Gradle stuff (if
      it isn't loading Gradle stuff already)

## Structure

Put your java code in `src/main/java/` under the `main` package.

Put your test code in `src/main/java/` under the `test` package.

### Importing Jar files / Libraries

The preferred way to add a library is to add to the `./build.gradle` file.

Put any `.jar` libraries in the `./lib`.

## Running stuff

### Run App

- Select the `App` run configuration
    - Open the `Run Configurations` menu (should be in the top right - it
      should look like a simpler version of
      [this](https://i.stack.imgur.com/UkljJ.png))
    - Click `App`
- Click the debug button (or the run button) the top right

### Running JUnit tests

- Select the `Test` run configuration
    - Open the `Run Configurations` menu (should be in the top right - it
      should look like a simpler version of
      [this](https://i.stack.imgur.com/UkljJ.png))
    - Click `Test`
- Click the red debug button (or the run button) the top right

If you want to run stuff from the terminal, run `./gradlew test` (ignore the
`./` if you are on Windows)

## Workflow Instructions

Our current workflow will work like this (this is only a summary):

1. Someone creates a new issue (it appears in a backlog)
1. As a team we decide to move issues from the backlog into the TODO list on
   the issues board page
1. Someone decides to pick up the issue, or gets assigned to someone.
1. They will drag the issue into the In Development list and create a branch
1. When they are done they will create are merge request
1. Someone reviews the code in the merge request, and then merges it
1. The reviewer removes the labels from the issue

### Creating A New Issue

1. Go to the issues page and click `new issue`
1. Enter your description and title - ideally include an checklist of things to
   do if there are multiple things to do

You don't need to assign it to anyone. Also, don't assign a label so that the
issue appears in the backlog

### Starting Development (creating a new branch)

1. Under the Board page (under Issues), drag your ticket from the TODO list
   into the In Development list
1. Click the little arrow next to the 'Create Merge Request' button, and click
   the `Create A Branch` button
1. On your computer, run `git fetch --all`, then `git checkout
   your-branch-name`

You can now commit and push to this branch

### Submitting Your Code For Code Review (Creating A Merge Request)

1. Make sure your code is reliable, and the tests and lint/code-analysis pass
   (by either running the `All` build configuration or running `./gradlew
   build` from the command line - replace `./gradlew` with `gradlew` if on
   Windows)
1. Push to the correct branch
1. On the project page there will be a button to `Create Merge Request`. Click
   it
1. Set the template to the `default_merge_request_template`
1. Create your merge request
1. Go to the issues board and drag your ticket over the Code Review list

If don't you want anyone to merge your merge request at the moment, add `WIP`
to the start of the merge request title.

You don't need to set any labels on the merge request, because there are
already labels on the issue.

### Code Linting

All java code will be done to the Google Java coding style which can be found
[here](https://google.github.io/styleguide/javaguide.html).

Check the linting by running the `Lint` build configuration in intellij, or by
running `./gradlew lint`.

Setting the default intellij styles will make all the default code completion
be styled correctly and allow you to reformat you code to match the guidelines
(ctrl + alt + L or code > Reformat Code). But dont run this on all code as it
make a large amout of changes just do files you are changing for the issue.