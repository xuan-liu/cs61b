/** class that shows animation of sevaral planets affected by
 * the laws of Newtonian physics */

public class NBody {
    public static double readRadius(String path){
        /** Given a file name, return a double corresponding
         * to the radius of the universe in that file */

        In in = new In(path);
        int N = in.readInt();
        double R = in.readDouble();
        return R;
    }

    public static Planet[] readPlanets(String path){
        /** Given a file name, return an array of Planets
         * corresponding to the planets in the file */

        In in = new In(path);
        int N = in.readInt();
        double R = in.readDouble();

        Planet[] planets = new Planet[N];
        for (int i = 0; i < N; i++){
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    public static void main(String[] args){
        // Collecting All Needed Input
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // Drawing the Background
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);

        // Drawing All of the Planets
        for (Planet element: planets){
            element.draw();
        }
        StdDraw.show();

        // Creating an Animation
        StdDraw.enableDoubleBuffering();

        for (double time = 0; time < T; time += dt){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < xForces.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < xForces.length; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.setScale(-radius, radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
            for (Planet element: planets){
                element.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        // print out the final state of the universe
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}