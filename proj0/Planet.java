/** class that defines a planet and figure out how it affected by
 * the laws of Newtonian physics */

public class Planet {
    public double xxPos; // Its current x position
    public double yyPos; // Its current y position
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName;
    // The name of the file that corresponds to the image that
    // depicts the planet
    public static final double G = 6.67e-11; // gravitational constant

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        /** calculates the distance between two Planets */

        return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos) +
                (yyPos - p.yyPos)*(yyPos - p.yyPos));
    }

    public double calcForceExertedBy(Planet p){
        /** calculates the force exerted by other planet */

        return (G * mass * p.mass)/(this.calcDistance(p)
                * this.calcDistance(p));
    }

    public double calcForceExertedByX(Planet p){
        /** calculates the force exerted in the X direction by other planet */

        return this.calcForceExertedBy(p) *
                (p.xxPos - xxPos) / this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
        /** calculates the force exerted in the Y direction by other planet */

        return this.calcForceExertedBy(p) *
                (p.yyPos - yyPos) / this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] ps){
        /** calculate the net X force exerted by all planets in
         * that array upon the current Planet */

        double sum = 0.0;
        for (Planet element : ps){
            if (this.equals(element)){
                continue;
            }
            sum += this.calcForceExertedByX(element);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] ps){
        /** calculate the net Y force exerted by all planets in
         * that array upon the current Planet */

        double sum = 0.0;
        for (Planet element : ps){
            if (this.equals(element)){
                continue;
            }
            sum += this.calcForceExertedByY(element);
        }
        return sum;
    }

    public void update(double dt, double fX, double fY){
        /**  update planet's acceleration, velocity and
         * position in a small period of time */

        double aNetX = fX/mass;
        double aNetY = fY/mass;
        xxVel += dt * aNetX;
        yyVel += dt * aNetY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw(){
        /** draw the planet in its position */

        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
