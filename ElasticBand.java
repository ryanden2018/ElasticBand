import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ElasticBand implements ActionListener {
  JFrame jfrm;
  ElasticBandGraphics ebg;

  ElasticBand() {
    jfrm = new JFrame("ElasticBand");
    ebg = new ElasticBandGraphics();

    jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jfrm.setBackground(Color.white);
    jfrm.add(ebg,BorderLayout.CENTER);
    jfrm.pack();
    jfrm.setResizable(false);
    jfrm.setVisible(true);

    int delay = 50;
    Timer timer = new Timer(delay,this);
    while(true) {
      timer.start();
    }
  }

  
  public void actionPerformed(ActionEvent e) {
    ebg.repaint();
  }
  
  public static void main(String args[]) {
    new ElasticBand();
  }
}
