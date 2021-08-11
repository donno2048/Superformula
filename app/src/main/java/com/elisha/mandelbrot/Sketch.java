package com.elisha.mandelbrot;
import java.util.ArrayList;
import processing.core.PApplet;
public class Sketch extends PApplet {
  int scl = 3, rows, cols, dir, mod = 150, counter;
  float off;
  boolean picked, refresh;
  double cx = -1, cy = 0, r = 1.0E-7;
  double[] next_point = new double[3];
  double[][] x, y, next_x, next_y;
  public void setup() {
    colorMode(3);
    background(0);
    loadPixels();
    rows = ceil(((float) height) / ((float) scl));
    cols = ceil(((float) width) / ((float) scl));
    pick();
    reset();
  }
  public void draw() {
    if (refresh) {
      reset();
    }
    int changed = 0;
    double s = ((float) height) / width;
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        boolean was = x[j][i] * x[j][i] + y[j][i] * y[j][i] < 4.0;
        double x0 = cx + m(i, 0, cols, -r, r), y0 = cy + m(j, 0, rows, r, -r) * s, tmp = 2 * x[j][i] * y[j][i] + y0;
        x[j][i] = x[j][i] * x[j][i] - y[j][i] * y[j][i] + x0;
        y[j][i] = tmp;
        if (was && x[j][i] * x[j][i] + y[j][i] * y[j][i] >= 4.0) {
          put(i, j, color(map(off, 0, mod, 255 * (1 - dir), 255 * dir), 255, 255));
          changed++;
        }
      }
    }
    counter++;
    off = (off + 1) % mod;
    updatePixels();
    if (changed < 10 && counter > 150) {
      refresh = true;
    }
  }
  public void reset() {
    if (!picked) {
      return;
    }
    background(0);
    loadPixels();
    cx = next_point[0];
    cy = next_point[1];
    r  = next_point[2];
    x = next_x;
    y = next_y;
    dir = (int) random(2);
    off = random(mod);
    counter = 0;
    refresh = false;
    picked = false;
    Thread picker = new Thread() {
      public void run() {
        try { pick(); } catch(Exception ignored) {}
      }
    };
    picker.start();
  }
  public void put(int i, int j, int c) {
    for (int x_ = 0; x_ < scl; x_++) {
      for (int y_ = 0; y_ < scl; y_++) {
        if (i * scl + x_ >= width || j * scl + y_ >= height) {
          continue;
        }
        pixels[(i * scl + x_) + (j * scl + y_) * width] = c;
      }
    }
  }
  public double m(double value, double istart, double istop, double ostart, double ostop) {
    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
  }
  public void pick() {
    int row = 300, col = 300, num = 100;
    double cx = -0.75, cy = 0, r = 1.25;
    boolean[][] map = new boolean[row][col];
    ArrayList<Pair> boundary = new ArrayList<Pair>();
    for (int depth = 0; depth < (int) random(6, 11); depth++) {
      boundary.clear();
      double s = ((double) height) / width;
      for (int i = 0; i < col; i++) {
        for (int j = 0; j < row; j++) {
          double x0 = cx + m(i, 0, col, -r, r), y0 = cy + m(j, 0, row, r, -r) * s, x = x0, y = y0;
          int n = 0;
          while (x * x + y * y <= 4.0 && n < num) {
            double tmp = 2 * x * y + y0;
            x = x * x - y * y + x0;
            y = tmp;
            n++;
          }
          map[j][i] = n == num;
        }
      }
      for (int i = 0; i < col; i++) {
        for (int j = 0; j < row; j++) {
          if (on_boundary(map, i, j)) {
            boundary.add(new Pair(i, j));
          }
        }
      }
      int rand = (int) random(0, boundary.size());
      Pair p = boundary.get(rand);
      double y0 = cy + m(p.y, 0, row, r, -r) * s;
      cx += m(p.x, 0, col, -r, r);
      cy = y0;
      r /= 4;
      num += 50;
    }
    next_point[0] = cx;
    next_point[1] = cy;
    next_point[2] = r;
    next_x = new double[rows][cols];
    next_y = new double[rows][cols];
    boolean update = true;
    double s = ((float) height) / width;
    double[][] _x = new double[rows][cols], _y = new double[rows][cols];
    while (update) {
      for (int i = 0; i < cols; i++) {
        for (int j = 0; j < rows; j++) {
          double x0 = cx + m(i, 0, cols, -r, r);
          double y0 = cy + m(j, 0, rows, r, -r) * s;
          _x[j][i] = next_x[j][i] * next_x[j][i] - next_y[j][i] * next_y[j][i] + x0;
          _y[j][i] = 2 * next_x[j][i] * next_y[j][i] + y0;
          if (_x[j][i] * _x[j][i] + _y[j][i] * _y[j][i] >= 4.0) {
            update = false;
          }
        }
      }
      if (update) {
        for (int i = 0; i < cols; i++) {
          for (int j = 0; j < rows; j++) {
            next_x[j][i] = _x[j][i];
            next_y[j][i] = _y[j][i];
          }
        }
      }
    }
    picked = true;
  }
  public boolean on_boundary(boolean[][] map, int i, int j) {
    if (!map[j][i]) {
      return false;
    }
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (i < -x || i + x >= map[j].length || j < -y || j + y >= map.length) {
          return false;
        }
        if (!map[j + y][i + x]) {
          return true;
        }
      }
    }
    return false;
  }
  static public class Pair {
    int x, y;
    Pair(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}