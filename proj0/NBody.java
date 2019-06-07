public class NBody {

    public static double readRadius(String dir) {
        In in = new In(dir);
        int N = in.readInt();
        double R = in.readDouble();
        return R;

    }
    public static Body[] readBodies(String a) {
    	In in = new In(a);
    	int N = in.readInt();
    	double R = in.readDouble();
    	Body[] bodies = new Body[5];
    	for (int i = 0; i < 5; i++) {
    		double xxPos = in.readDouble();
    		double yyPos = in.readDouble();
    		double xxVel = in.readDouble();
    		double yyVel = in.readDouble();
    		double mass = in.readDouble();
    		String imgFileName = in.readString();
    		Body b = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
    		bodies[i] = b;   		    	}
    	return bodies;
    }
    public static void main(String[] args) {
    	double T = Double.parseDouble(args[0]);
    	double dt = Double.parseDouble(args[1]);
    	String fileName = args[2];
    	double R = readRadius(fileName);
    	Body[] bodies = readBodies(fileName);
    	StdDraw.enableDoubleBuffering();
    	StdDraw.setScale(-R, R);
    	StdDraw.clear();
    	for (double t = 0.0; t <= T; t += dt) {
    		double[] xForces = new double[bodies.length];
    		double[] yForces = new double[bodies.length];
    		for (int i = 0; i < bodies.length; i ++) {
    			xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
    			yForces[i] = bodies[i].calcNetForceExertedByY(bodies);}
    		
    		for(int i = 0; i < bodies.length; i ++) {
    			bodies[i].update(dt, xForces[i], yForces[i]);
    		}    		
    			}
    	StdDraw.picture(0, 0, "images/starfield.jpg");
    	for (Body b: bodies) {
    		b.draw();
    	}
    	StdDraw.show();
    	StdDraw.pause(10);
    	StdOut.printf("%d\n", bodies.length);
    	StdOut.printf("%.2e\n", radius);
    	for (int i = 0; i < bodies.length; i++) {
    	    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
    	                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
    	                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
    	}
    }
}
    	