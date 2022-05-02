# RavnCodeChallenge
This is an App to solve that uses the [Star Wars GraphQL Api](https://swapi-graphql.netlify.app/.netlify/functions/index)
to list and detail the Star Wars characters from all movies.

## Building the App

First Clone the repo:
`git clone git@github.com:aherrh02/Ravn-Challenge-V3-AlejandroHerrera.git`

Just build the project the first time and you can start running it

### Android Studio (Recommended)

(These instructions were tested with Android Studio version 2021.1.1 and SDK API 28: Android 9.0 (Pie))

* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of your project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

### APK Build (Recommended)

You can also build the APK file by running the command `gradlew build` in the terminal and located in the app primary directory

## Using the Star Wars App

The app lists the characters in groups of 5 and uses a loader while it do the whole process, each item in the list shows the Character name with the specie and his homeworld.

When you click on any item you can have a more detailed view of the if and the vehicles he have

### Technologies used

Apollo Version 3.2.1
LifeCycle Version 2.4.1
OkHttp Version 4.9.3

The software works with 

## Pictures
![alt text](Screenshot%2022-05-01%230826.png?raw=true)

![alt text](Screenshot%202022-05-01%20230640.png?raw=true)

![alt text](Screenshot%202022-05-01%20230806.png?raw=true)

![alt text](Screenshot%202022-05-01%20230844.png.png?raw=true)

