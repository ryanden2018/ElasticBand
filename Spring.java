class Spring {
  Mass oneEnd;
  Mass otherEnd;
  double restingLength;
  double springConstant;

  Spring(Mass oneEnd, Mass otherEnd, double restingLength, double springConstant) {
    this.oneEnd = oneEnd;
    this.otherEnd = otherEnd;
    this.restingLength = restingLength;
    this.springConstant = springConstant;
  }

  double length() {
    double dispX = oneEnd.centerX - otherEnd.centerX;
    double dispY = oneEnd.centerY - otherEnd.centerY;
    return Math.sqrt( Math.pow(dispX,2) + Math.pow(dispY,2) );
  }

  // compute the signed magnitude of the force exerted by this spring
  double forceScalar() {
    return springConstant * (restingLength - length());
  }

  double nx() {
    return (oneEnd.centerX-otherEnd.centerX)/
      Math.sqrt(Math.pow(oneEnd.centerX-otherEnd.centerX,2)+Math.pow(oneEnd.centerY-otherEnd.centerY,2));
  }
  double ny() {
    return (oneEnd.centerY-otherEnd.centerY)/
      Math.sqrt(Math.pow(oneEnd.centerX-otherEnd.centerX,2)+Math.pow(oneEnd.centerY-otherEnd.centerY,2));
  }

  double forceX() {
    return forceScalar() * nx();
  }

  double forceY() {
    return forceScalar() * ny();
  }
}
