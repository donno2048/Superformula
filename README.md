# Mandelbrot for android

Compile or run in Android studio, the app will ask you to replace your wallpaper with a **live** Mandelbrot set (close-ups at random places).

1. To build it first create _local.properties_ pointing to Android sdk (install it if you son't have it):

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
