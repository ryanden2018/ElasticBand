import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

class ElasticBandGraphics extends JComponent {
  int WIDTH = 650;
  int HEIGHT = WIDTH;
  double MASS = 1.0;
  int N = 20;
  double DT = 0.0001;
  ElasticBandData ebd;

  ElasticBandGraphics() {
    setPreferredSize(new Dimension(WIDTH,HEIGHT));
    ebd = new ElasticBandData(MASS,N,DT,WIDTH);
  }

  @Override
  public void paintComponent(Graphics g) {
    for(int i = 0; i < (int)(1/DT); i++) {
      ebd.update();
    }

    for(int i = 0; i < ebd.masses.length; i++) {
      g.fillOval((int) (ebd.masses[i].centerX-5), (int) (ebd.masses[i].centerY-5),
        10, 10);
    }

    BasicStroke bs = new BasicStroke(3);
    ((Graphics2D) g).setStroke(bs);

    int[] xPoints = new int[ebd.masses.length];
    int[] yPoints = new int[ebd.masses.length];
    for(int i = 0; i < ebd.masses.length; i++) {
      xPoints[i] = (int) ebd.masses[i].centerX;
      yPoints[i] = (int) ebd.masses[i].centerY;
    }

    g.drawPolygon(xPoints,yPoints,ebd.masses.length);

    super.paintComponent(g);
  }
}
