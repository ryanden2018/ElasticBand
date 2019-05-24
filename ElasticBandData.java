class ElasticBandData {
  Mass[] masses;
  Spring[] springs;
  double dt;
  int cursorX;
  int cursorY;
  double width;

  ElasticBandData(double mass, int N, double dt, double width) {
    this.dt = dt;
    springs = new Spring[(N*(N-1))/2];
    masses = new Mass[N];
    cursorX = (int) (width/2);
    cursorY = (int) (width/2);
    this.width = width;

    for(int i=0; i<N; i++) {
      double theta = (1.0/N) * i * 2 * Math.PI;
      masses[i] = new Mass((0.5+0.25*Math.sin(theta))*width,
        (0.5+0.25*Math.cos(theta))*width,
        0.0,0.0,1.0);
    }

    int count = 0;
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < N; j++) {
        if(i < j) {
          double dispX = masses[i].centerX - masses[j].centerX;
          double dispY = masses[i].centerY - masses[j].centerY;
          double dist = Math.sqrt(Math.pow(dispX,2)+Math.pow(dispY,2));
          springs[count] = new Spring(
            masses[i],masses[j], dist, 0.1);
          count++;
        }
      }
    }
  }

  void update() {
    // update positions
    for(int i = 0; i < masses.length; i++) {
      masses[i].centerX += masses[i].vx*dt;
      masses[i].centerY += masses[i].vy*dt;
    }

    // update velocities
    for(int i = 0; i < springs.length; i++) {
      double nx = springs[i].oneEnd.centerX-cursorX;
      double ny = springs[i].oneEnd.centerY - cursorY;
      double nabs = Math.sqrt(Math.pow(nx,2)+Math.pow(ny,2));
      nx = nx / nabs;
      ny = ny / nabs;
      springs[i].oneEnd.vx += springs[i].forceX()*dt / springs[i].oneEnd.mass - 5*nx*Math.exp(-nabs/(width*width))*dt - 0.05*springs[i].oneEnd.vx*dt;
      springs[i].oneEnd.vy += springs[i].forceY()*dt / springs[i].oneEnd.mass - 5*ny*Math.exp(-nabs/(width*width))*dt - 0.05*springs[i].oneEnd.vy*dt;
      nx = springs[i].otherEnd.centerX-cursorX;
      ny = springs[i].otherEnd.centerY - cursorY;
      nabs = Math.sqrt(Math.pow(nx,2)+Math.pow(ny,2));
      nx = nx / nabs;
      ny = ny / nabs;
      springs[i].otherEnd.vx -= springs[i].forceX()*dt / springs[i].oneEnd.mass + 5*nx*Math.exp(-nabs/(width*width))*dt + 0.05*springs[i].otherEnd.vx*dt;
      springs[i].otherEnd.vy -= springs[i].forceY()*dt / springs[i].oneEnd.mass + 5*ny*Math.exp(-nabs/(width*width))*dt + 0.05*springs[i].otherEnd.vy*dt;
    }
  }
}
