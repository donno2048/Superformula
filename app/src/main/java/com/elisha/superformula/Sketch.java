package com.elisha.superformula;
import processing.core.PApplet;
public class Sketch extends PApplet {
  double accuracy = .001;
  boolean[] flags = new boolean[6];
  float[] t = new float[6];
  int[] speeds = {4, 6, 10, 14, 22, 26};
  float[] Ulimits = {10, 3, 3, 1, 1, 1};
  float[] Blimits = new float[6];
  boolean joined = true;
  public void setup() {
    stroke(255);
    strokeWeight(3);
    if (joined)
      noFill();
  }
  public void draw() {
    background(0);
    translate((float) width / 2, (float) height / 2);
    if (joined) {
      beginShape();
      for (float theta = -PI; theta <= PI; theta += accuracy)
        polar_vertex(min(width, height) * superformula(theta, t[0], t[1], t[2], t[3], t[4], t[5]) / 3, theta);
      endShape();
    }
    else {
      for (float theta = -PI; theta <= PI; theta += accuracy)
        polar_point(min(width, height) * superformula(theta, t[0], t[1], t[2], t[3], t[4], t[5]) / 3, theta);
    }
    for (int i = 0; i < 6; i++) {
      if (t[i] >= Ulimits[i])
        flags[i] = false;
      if (t[i] <= Blimits[i])
        flags[i] = true;
      if (flags[i])
        t[i] += accuracy * speeds[i];
      else
        t[i] -= accuracy * speeds[i];
    }
  }
  public void polar_point(float r, float theta) {
    point(r * cos(theta), r * sin(theta));
  }
  public void polar_vertex(float r, float theta) {
    vertex(r * cos(theta), r * sin(theta));
  }
  public float superformula(float theta, float m, float a, float b, float n1, float n2, float n3) {
    return pow(pow(abs(cos(m * theta) / a), n2) + pow(abs(sin(m * theta) / b), n3), (float) (-1. / n1));
  }
}