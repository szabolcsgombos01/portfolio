package sikidom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    private final ArrayList<Planefigure> planefigures;
    private    Integer RogzitettX = null;
    private Point p = new Point();
    private    Integer RogzitettY = null;

    public Database()
    {
        this.RogzitettX = RogzitettX;
        this.RogzitettX = RogzitettX;
        planefigures = new ArrayList<>();
    }

    public  void read(String filename) throws FileNotFoundException , InvalidInputException , UresFile
    {
        Scanner sc = new Scanner(new BufferedReader((new FileReader(filename))));
        if(!sc.hasNext())
        {
            throw new UresFile();
        }
        else
        {
        if(!sc.hasNextDouble())
        {
            throw new InvalidInputException();
        }
        p.x = sc.nextDouble();
        if(!sc.hasNextDouble())
        {
                throw new InvalidInputException();
        }
        p.y = sc.nextDouble();
        if(!sc.hasNextDouble())
        {
                throw new InvalidInputException();
        }
        int db = sc.nextInt();
        while (sc.hasNext())
        {
            Planefigure planefigure = null;

            switch (sc.next())
            {
                case "C":
                    planefigure = new Circle(sc.nextDouble() , sc.nextDouble() , sc.nextDouble());
                    break;
                case "H":
                    planefigure = new Hexagon(sc.nextDouble() , sc.nextDouble() , sc.nextDouble());
                    break;
                case "S":
                    planefigure = new Square(sc.nextDouble() , sc.nextDouble() , sc.nextDouble());
                    break;
                case "T":
                    planefigure = new Triangle(sc.nextDouble() , sc.nextDouble() , sc.nextDouble());
                    break;

            }
           planefigures.add(planefigure);

        }
        }
        sc.close();
    }

    public void szamolo()
    {
        Integer Counter = 0;
        for(Planefigure v : planefigures)
        {
            boolean boolcounter = false;
            switch (v.getCategory())
            {
                case "C":
                     boolcounter = Circle(v.getX() , v.getY() , v.getLength());
                        if(boolcounter == true)
                        {
                            Counter += 1;
                        }
                    break;
                case "H":
                    if(Hexagon(v.getX() , v.getY() , v.getLength()) == true)
                    {
                        Counter += 1;
                    }
                    break;
                case "S":
                    if(Square(v.getX() , v.getY() , v.getLength()) == true)
                    {
                        Counter += 1;
                    }
                    break;
                case "T":
                    if(Triangle(v.getX() , v.getY() , v.getLength()) == true)
                    {
                        Counter += 1;
                    }

                    break;
            }
        }
        System.out.println(Counter + " db síkidomban van benne az adott pont! :)");
    }


    /** Körben a pont
     *
     * @param X         a kör X koordja
     * @param Y         a kör Y koordja
     * @param Length    a háromszög sugara
     * @return          true, ha benne van a körben a pont
     */
    public boolean Circle(double X , double Y , double Length)
    {
        boolean vane = false;
        double tavolsag = p.distance(X , Y);
        if(tavolsag <= Length)
        {
            vane = true;
        }
        return vane;
    }


    /** Hatszögben a pont
     *
     * @param X         a hatszög X koordja
     * @param Y         a hatszög Y koordja
     * @param Length    a hatszög hossza
     * @return          rue ha a hatszögben van a pont
     */
    public boolean Hexagon(double X , double Y , double Length)
    {
        boolean vane = false;
        double dx = Math.abs(p.x - X)/(Length*2);
        double dy = Math.abs(p.y - Y)/(Length*2);
        double a = 0.25 * Math.sqrt(3.0);
        if( (dy <= a) && (a*dx + 0.25*dy <= 0.5*a))
        {
             vane = true;
        }
        return vane;

    }


    /** Háromszögben a pont
     *
     * @param Xalap     a háromszög X koordja
     * @param Yalap     a háromszög Y koordja
     * @param Length    a háromszög hossza
     * @return          true ha a háromszögben van a pont
     */
    public boolean Triangle(double Xalap , double Yalap , double Length)
    {
        boolean vane = false;
        /**
         * Magasság számolása
         */
        double magassag = Math.sqrt((Length * Length) - ((Length / 2) * (Length / 2)));

        Double X = Xalap;
        Double Y = Yalap;


        /**haromszög terület kiszámolása
         *
         */
        double terulet = ((Length/2) * magassag);

        /**háromszög koord kiszámolása
         *
         */
        Point felsocsucskoord = new Point();
        felsocsucskoord.x = X;
        felsocsucskoord.y = Y;
        Point jobbalso = new Point();
        jobbalso.x = X;
        jobbalso.y = Y;
        Point balalso = new Point();
        balalso.x = X;
        balalso.y = Y;
        felsocsucskoord.move(0, (magassag * 2 /3)) ;
        balalso.move((-(Length/2)) ,(-(magassag/ 3) ));
        jobbalso.move((Length/2) ,((-magassag /3) ));

        /**
         * külsőpont és csúcsok távolsága
         */
        double felsotavolsag = p.distance(felsocsucskoord.x , felsocsucskoord.y);
        double jobbalsotavolsag = p.distance(jobbalso.x , jobbalso.y);
        double balalsotavolsag = p.distance(balalso.x , balalso.y);


        /**
         * Terület szmáolás héron képlettel
         */
        double s1 = (felsotavolsag + jobbalsotavolsag +Length)/2;
        double s2 = (balalsotavolsag + jobbalsotavolsag +Length)/2;
        double s3 = (felsotavolsag + balalsotavolsag +Length)/2;
        double heronterulet1 = Math.sqrt(s1*(s1 - jobbalsotavolsag) * (s1- felsotavolsag) * (s1 -Length));
        heronterulet1 += Math.sqrt(s2*(s2 - jobbalsotavolsag) * (s2- balalsotavolsag) * (s2 - Length));
        heronterulet1 += Math.sqrt(s3*(s3 - felsotavolsag) * (s3- balalsotavolsag) * (s3 - Length));


        if( (int) heronterulet1 <= terulet)
        {
            vane = true;
        }
        return vane;

    }


    /** A négyzetben a pont
     *
     * @param Xalap     a négyzet X koordja
     * @param Yalap     a négyzet X koordja
     * @param Length    a hánégyzet Xhossza
     * @return          true, ha a négyzetben van a pont
     */
    public boolean Square(double Xalap , double Yalap , double Length)
    {
        boolean vane = false;

        Double X = Xalap;
        Double Y = Yalap;

        double terulet = (Length * Length);

        /**
         * A négyzet csúcsai koordjának a számítása
         */
        Point jobbfelsocsucskoord = new Point();
        jobbfelsocsucskoord.x = X;
        jobbfelsocsucskoord.y = Y;
        Point balfelsocsucskoord = new Point();
        balfelsocsucskoord.x = X;
        balfelsocsucskoord.y = Y;
        Point jobbalso = new Point();
        jobbalso.x = X;
        jobbalso.y = Y;
        Point balalso = new Point();
        balalso.x = X;
        balalso.y = Y;
        jobbfelsocsucskoord.move((Length / 2), (Length / 2)) ;
        balalso.move((-(Length/2)) ,(-(Length/2) ));
        jobbalso.move((Length/2) ,(-(Length/2) ));
        balfelsocsucskoord.move((-(Length/2)) , (Length/2));

        double jobbfelsotavolsag = p.distance(jobbfelsocsucskoord.x , jobbfelsocsucskoord.y);
        double balfelsotavolsag = p.distance(balfelsocsucskoord.x , balfelsocsucskoord.y);
        double jobbalsotavolsag = p.distance(jobbalso.x , jobbalso.y);
        double balalsotavolsag = p.distance(balalso.x , balalso.y);


        double s1 = (jobbfelsotavolsag + jobbalsotavolsag +Length)/2;
        double s2 = (jobbalsotavolsag + balalsotavolsag + Length)/2;
        double s3 = (balfelsotavolsag + balalsotavolsag +Length)/2;
        double s4 = (jobbfelsotavolsag + balfelsotavolsag +Length)/2;

        double heronterulet1 = Math.sqrt(s1 * (s1 - jobbalsotavolsag) * (s1- jobbfelsotavolsag) * (s1 - Length));
        heronterulet1 += Math.sqrt(s2*(s2 - jobbalsotavolsag) * (s2- balalsotavolsag) * (s2 - Length));
        heronterulet1 += Math.sqrt(s3*(s3 - balfelsotavolsag) * (s3- balalsotavolsag) * (s3 - Length));
        heronterulet1 += Math.sqrt(s4*(s4 - balfelsotavolsag) * (s4- jobbfelsotavolsag) * (s4 - Length));

        if((int) heronterulet1   <= terulet)
        {
            vane = true;
        }

        return vane;
    }

    public void report()
    {
        System.out.println("X pont koord: " + p.x );
        System.out.println("Y pont koord: " + p.y);
        System.out.println("Plane figure in database:");
        for(Planefigure v: planefigures)
        {
            System.out.println(v);
        }
    }
}
