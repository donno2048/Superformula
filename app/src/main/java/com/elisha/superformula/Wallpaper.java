package com.elisha.superformula;
import processing.android.PWallpaper;
import processing.core.PApplet;
public class Wallpaper extends PWallpaper {
    @Override
    public PApplet createSketch() {
        return new Sketch();
    }
}