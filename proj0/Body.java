
public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public Body(double xP, double yP, double xV,
            double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Body(Body b) {
		xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
	}
	public double calcDistance(Body b) {
		double dx = this.xxPos - b.xxPos;
		double dy = this.yyPos - b.yyPos;
		double distanceSquare = Math.pow(dx, 2) + Math.pow(dy, 2);
		double distance = Math.sqrt(distanceSquare);		
		return distance;
	}
	public double calcForceExertedBy(Body b) {
		double distance = this.calcDistance(b);
		double denominator = Math.pow(distance, 2);
		double numerator = 6.67e-11 * this.mass * b.mass;
		double ANS = numerator / denominator;
		return ANS;

	
	}
	public double calcForceExertedByX(Body b) {
		double netForce = this.calcForceExertedBy(b);
		double cosine =  (this.xxPos - b.xxPos) / this.calcDistance(b);
		double ANS = netForce * cosine;
		return -ANS;
	}
	public double calcForceExertedByY(Body b) {
		double netForce = this.calcForceExertedBy(b);
		double sine = (this.yyPos - b.yyPos) / this.calcDistance(b);
		double ANS = netForce * sine;
		return -ANS;
	}
	public double calcNetForceExertedByX(Body[] bodies) {
		double netForce = 0;
		for(Body b: bodies) {
			if (this.equals(b)) {
				continue;
			}else {netForce += this.calcForceExertedByX(b);								
			}
		}return netForce;
	}
	public double calcNetForceExertedByY(Body[] bodies) {
		double netForce = 0;
		for(Body b: bodies) {
			if (this.equals(b)) {
				continue;
			}else {netForce += this.calcForceExertedByY(b);								
			}
		}return netForce;
	}
	public void update(double dt,double fX, double fY) {
		double xAcceleration = fX / this.mass;
		double yAcceleration = fY / this.mass;
		double xVelocity = this.xxVel + xAcceleration * dt;
		double yVelocity = this.yyVel + yAcceleration * dt;
		xxVel = xVelocity;
		yyVel = yVelocity;
		xxPos += xVelocity * dt;
		yyPos += yVelocity * dt;
}
	public void draw() {
		String imgPath = this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + imgPath);
	}
}