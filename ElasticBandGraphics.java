import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;

class ElasticBandGraphics extends JComponent implements MouseMotionListener, MouseListener {
  int WIDTH = 650;
  int HEIGHT = WIDTH;
  double MASS = 1.0;
  int N = 15;
  double DT = 0.0001;
  ElasticBandData ebd;
  Polygon poly;
  int points;
  boolean handlingClick;

  ElasticBandGraphics() {
    setPreferredSize(new Dimension(WIDTH,HEIGHT));
    ebd = new ElasticBandData(MASS,N,DT,WIDTH);
    addMouseMotionListener(this);
    addMouseListener(this);
    points = 0;
  }

  @Override
  public void paintComponent(Graphics g) {
    for(int i = 0; i < (int)(1/DT); i++) {
      ebd.update();
    }

    paintEbd(g,ebd);


    super.paintComponent(g);
  }

  void paintEbd(Graphics g, ElasticBandData ebd0) {
    ((Graphics2D) g).setStroke(new BasicStroke(4));
    g.setFont(new Font("SansSerif",Font.BOLD,18));

    int[] xPoints = new int[ebd0.masses.length];
    int[] yPoints = new int[ebd0.masses.length];
    for(int i = 0; i < ebd0.masses.length; i++) {
      xPoints[i] = (int) ebd0.masses[i].centerX;
      yPoints[i] = (int) ebd0.masses[i].centerY;
    }
    int[] newXPoints = smoothRefinement(xPoints,3);
    int[] newYPoints = smoothRefinement(yPoints,3);

    g.drawPolygon(newXPoints,newYPoints,newXPoints.length);
    poly = new Polygon(newXPoints,newYPoints,newXPoints.length);

    g.drawString("Click outside the band to score a point.", 10, 30);
    g.drawString("Points: " + points, 10, WIDTH-10); 
  }


  public void mouseMoved(MouseEvent e) {
    ebd.cursorX = e.getX();
    ebd.cursorY = e.getY();
  }
  
  public void mouseDragged(MouseEvent e) { 
    ebd.cursorX = e.getX();
    ebd.cursorY = e.getY();
  }

  public void mouseExited(MouseEvent e) { }

  public void mouseEntered(MouseEvent e) { }

  public void mouseReleased(MouseEvent e) { }

  public void mousePressed(MouseEvent e) { 
    if(!poly.contains(e.getX(),e.getY())) {
      points++;
    }
  }

  public void mouseClicked(MouseEvent e) { }

  int[] smoothRefinement(int[] points,int mult) {
    int[] result = new int[mult*points.length];

    for(int i = 0; i < points.length; i++) {
      for(int j = 0; j < mult; j++) {
        int idx = mult*i + j;
        double lambda = (1.0*j)/mult;
        double new_pt = (1.0-lambda)*points[i] + lambda*points[(i+1)%points.length];
        result[idx] = (int) new_pt;
      }
    }

    for(int i = 0; i < 10; i++) {
      result = smoother(result);
    }

    return result;
  }

  int[] smoother(int[] points) {
    int[] result = new int[points.length];
    for(int i = 0; i < points.length; i++) {
      result[i] =(int) ( 0.25 * (double) ( 
        points[(i+3)%(points.length)]
        + points[(i+2)%(points.length)]
        + points[(i+1)%(points.length)]
        + points[i]));
    }
    return result;
  }


}
