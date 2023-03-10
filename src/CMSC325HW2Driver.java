/*
 * Filename: CMSC325HW2Driver.java
 * Author: Will Feighner
 * Date: 2023 01 27
 * Purpose: This program is the driver for a simple image transformation program.
 * Several images are created and then modified with simple transformations
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class CMSC325HW2Driver extends JPanel {

  static int translateX = 0;
  static int translateY = 0;
  static double rotation = 0.0;
  static double scaleX = 1.0;
  static double scaleY = 1.0;
  ImageTemplate myImages = new ImageTemplate();
  BufferedImage tImage = myImages.getImage(ImageTemplate.letterT);
  BufferedImage circleImage = myImages.getImage(ImageTemplate.circle);
  BufferedImage xImage = myImages.getImage(ImageTemplate.letterX);
  BufferedImage eImage = myImages.getImage(ImageTemplate.letterE);
  BufferedImage yinYangImage = myImages.getImage(ImageTemplate.yinYang);

  // A counter that increases by one in each frame.
  private int frameNumber;
  // The time, in milliseconds, since the animation started.

  public CMSC325HW2Driver() {
    setPreferredSize(new Dimension(800, 600));
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    JFrame window;
    window = new JFrame("Simple Java Animation");
    final CMSC325HW2Driver panel = new CMSC325HW2Driver();
    window.setContentPane(panel);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.pack();
    window.setResizable(false);
    window.setLocationRelativeTo(null);


    Timer animationTimer = new Timer(1000, e -> {
      if (panel.frameNumber > 3) {
        panel.frameNumber = 0;
      } else {
        panel.frameNumber++;
      }
      panel.repaint();
    });

    window.setVisible(true);
    animationTimer.start();

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    /* First, create a Graphics2D drawing context for drawing on the panel.
     * (g.create() makes a copy of g, which will draw to the same place as g,
     * but changes to the returned copy will not affect the original.)
     */
    Graphics2D g2 = (Graphics2D) g.create();

    /* Turn on antialiasing in this graphics context, for better drawing.
     */
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    /* Fill in the entire drawing area with white.
     */
    g2.setPaint(Color.WHITE);
    g2.fillRect(0, 0, getWidth(), getHeight()); // From the old graphics API!

    /* Here, I set up a new coordinate system on the drawing area, by calling
     * the applyLimits() method that is defined below.  Without this call, I
     * would be using regular pixel coordinates.  This function sets the value
     * of the global variable pixelSize, which I need for stroke widths in the
     * transformed coordinate system.
     */
    // Controls your zoom and area you are looking at
    applyWindowToViewportTransformation(g2, -150, 150, -150, 150, true);

    AffineTransform savedTransform = g2.getTransform();
    System.out.println("Frame is: " + frameNumber);
    switch (frameNumber) {
      case 0 -> {
        translateX = 0;
        translateY = 0;
        scaleX = 1.0;
        scaleY = 1.0;
        rotation = 0;
      }
      case 1 -> {
        translateX = -10;
        translateY = 12;
      }
      case 2 -> {
        translateX = -10;
        translateY = 12;
        rotation = 55 * Math.PI / 180.0;
      }
      case 3 -> {
        translateX = -10;
        translateY = 12;
        rotation = (55 - 70) * Math.PI / 180.0;
      }
      case 4 -> {
        translateX = -10;
        translateY = 12;
        rotation = (55 - 70) * Math.PI / 180.0;
        scaleY = 1.5;
        scaleX = 3;
      }
      default -> {
      }
    }

    g2.translate(translateX, translateY);
    g2.translate(-80, 80);
    g2.rotate(rotation);
    g2.scale(scaleX, scaleY);
    g2.drawImage(tImage, 0, 0, this);
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY);
    g2.translate(80, -80);
    g2.rotate(rotation);
    g2.scale(scaleX, scaleY);
    g2.drawImage(circleImage, 0, 0, this);
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY);
    g2.translate(-80, -80);
    g2.rotate(rotation);
    g2.scale(scaleX, scaleY);
    g2.drawImage(xImage, 0, 0, this);
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY);
    g2.translate(80, 80);
    g2.rotate(rotation);
    g2.scale(scaleX, scaleY);
    g2.drawImage(eImage, 0, 0, this);
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY);
    g2.rotate(rotation);
    g2.scale(scaleX, scaleY);
    g2.drawImage(yinYangImage, 0, 0, this);
    g2.setTransform(savedTransform);
  }

  private void applyWindowToViewportTransformation(Graphics2D g2,
                                                   double left, double right, double bottom, double top,
                                                   boolean preserveAspect) {
    int width = getWidth();   // The width of this drawing area, in pixels.
    int height = getHeight(); // The height of this drawing area, in pixels.
    if (preserveAspect) {
      // Adjust the limits to match the aspect ratio of the drawing area.
      double displayAspect = Math.abs((double) height / width);
      double requestedAspect = Math.abs((bottom - top) / (right - left));
      if (displayAspect > requestedAspect) {
        // Expand the viewport vertically.
        double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
        bottom += excess / 2;
        top -= excess / 2;
      } else if (displayAspect < requestedAspect) {
        // Expand the viewport vertically.
        double excess = (right - left) * (requestedAspect / displayAspect - 1);
        right += excess / 2;
        left -= excess / 2;
      }
    }
    g2.scale(width / (right - left), height / (bottom - top));
    g2.translate(-left, -top);
    // This is the measure of a pixel in the coordinate system
    // set up by calling the applyLimits method.  It can be used
    // for setting line widths, for example.
  }
}