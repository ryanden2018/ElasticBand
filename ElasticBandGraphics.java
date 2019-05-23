import javax.swing.*;
import java.awt.*;
import java.util.*;

class ElasticBandGraphics extends JComponent {
  int WIDTH = 650;
  int HEIGHT = WIDTH;
  double MASS = 1.0;
  int N = 100;
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

    ///// DEBUG
    double tot = 0.0;
    for(int i = 0; i < ebd.springs.length; i++) {
      tot += Math.abs( ebd.springs[(i+1)%N].angle( ebd.springs[(i+2)%N] ) - ebd.springs[(i+1)%N].angle( ebd.springs[i] ) );
    }
    System.out.println(tot);
    ///// -DEBUG

    for(int i = 0; i < ebd.masses.length; i++) {
      g.fillOval((int) ebd.masses[i].centerX, (int) ebd.masses[i].centerY,
        5, 5);
    }

    super.paintComponent(g);
  }
}
