# Superformula for android

Compile or run in Android studio, the app will ask you to replace your wallpaper with a **live** [Superformula](https://en.wikipedia.org/wiki/Superformula) render.

The render uses these parameters:

 - m: goes up to 10 in speed: 4
 - a: goes up to 3 in speed: 6
 - b: goes up to 3 in speed: 10
 - n1: goes up to 1 in speed: 14
 - n2: goes up to 1 in speed: 22
 - n3: goes up to 1 in speed: 26

1. To build it first create _local.properties_ pointing to Android sdk (install it if you don't have it):

## Windows

```properties
sdk.dir=C:\\Users\\UserName\\AppData\\Local\\Android\\sdk
```

## Linux (Ubuntu)

```properties
sdk.dir = /home/USERNAME/Android/Sdk
```

2. Open the terminal in the folder and run:

## Windows

```bat
gradlew build
```

## Linux (Ubuntu)

```bash
./gradlew build
```
