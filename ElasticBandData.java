class ElasticBandData {
  Mass[] masses;
  Spring[] springs;
  double dt;

  ElasticBandData(double mass, int N, double dt, double width) {
    this.dt = dt;
    springs = new Spring[N];
    masses = new Mass[N];

    for(int i=0; i<N; i++) {
      double theta = (1.0/N) * i * 2 * Math.PI;
      masses[i] = new Mass((0.5+0.25*Math.sin(theta))*width,
        (0.5+0.25*Math.cos(theta))*width,
        Math.sin(2*theta),Math.cos(2*theta),1.0);
    }

    for(int i = 0; i < N; i++) {
      springs[i] = new Spring(masses[(i+1)%N],masses[i],
        2.0*Math.PI*0.25*width/N, 1);
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
      springs[i].oneEnd.vx += springs[i].forceX()*dt / springs[i].oneEnd.mass;
      springs[i].oneEnd.vy += springs[i].forceY()*dt / springs[i].oneEnd.mass;
      springs[i].otherEnd.vx -= springs[i].forceX()*dt / springs[i].otherEnd.mass;
      springs[i].otherEnd.vy -= springs[i].forceY()*dt / springs[i].otherEnd.mass;
    }
  }
}
