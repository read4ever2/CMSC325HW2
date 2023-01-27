/*
 * Filename: CMSC325HW2Driver.java
 * Author: Will Feighner
 * Date: 2023 01 27
 * Purpose: This program is the driver for a simple image transformation program.
 * Several images are created and then modified with simple transformations
 */

import javax.swing.*;

public class CMSC325HW2Driver extends JPanel {

  static int translateX = 0;
  static int translateY = 0;
  static double rotation = 0.0;
  static double scaleX = 1.0;
  static double scaleY = 1.0;
  ImageTemplate myImages = new ImageTemplate();
  BufferedImage tImage = myImages.getImage(ImageTemplate.letterT);
  // A counter that increases by one in each frame.
  private int frameNumber;
  // The time, in milliseconds, since the animation started.
  private long elapsedTimeMillis;
  // This is the measure of a pixel in the coordinate system
  // set up by calling the applyLimits method.  It can be used
  // for setting line widths, for example.
  private float pixelSize;

  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}