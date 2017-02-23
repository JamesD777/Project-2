
package project2;
import java.io.*;
import java.util.*;

public class Project2 {

    public static Customer[] C = new Customer[0];

    public static void main(String[] args) throws FileNotFoundException{
        String line;
        File pref = new File("preferred.dat");
        PreferredCustomer[] P = null;
        if(pref.exists() && !pref.isDirectory()){
            P = new PreferredCustomer[0];
                Scanner s = new Scanner(pref);
                while(s.hasNext()){
                    line = s.nextLine();
                    line=line.substring(0,line.length() -1);
                    Scanner s2 = new Scanner(line);
                    PreferredCustomer p1 = new PreferredCustomer();
                    p1.setID(s2.nextInt());
                    p1.setFname(s2.next());
                    p1.setLname(s2.next());
                    p1.setTotal(s2.nextDouble());
                    p1.setDiscount(s2.nextInt());
                    P = addPCustomer(P,p1);
                }
                s.close();
        }
        
        try {
            File c = new File("customer.dat");

            Scanner s = new Scanner(c);
            while(s.hasNext()){
                line = s.nextLine();
                Scanner s2 = new Scanner(line);
                Customer c1 = new Customer();
                c1.setID(s2.nextInt());
                c1.setFname(s2.next());
                c1.setLname(s2.next());
                c1.setTotal(s2.nextDouble());
                C = addCustomer(C,c1);
            }
            s.close();
        }catch (FileNotFoundException f){
            System.out.println("File not found" + f);
        }
        
        File o = new File("orders.dat");
        Scanner s = new Scanner(o);
        int spot;
        int id;
        while(s.hasNextLine()){
            spot = -1;
            id = s.nextInt();
            for(int i = 0; i < C.length;i++){
                if(C[i].getID() == id)
                    spot = i;
            }
            if(spot != -1)
                P = payC(spot, surfacePrice(s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble()), P);
            else{
                for(int i = 0; i < P.length;i++){
                    if(P[i].getID() == id)
                        spot = i;
                }
                if(spot != -1)
                    P = payP(spot, surfacePrice(s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble()), P);
            }

        }
        s.close();

        File cOUT = new File("customer1.dat");
        PrintWriter cPrint = new PrintWriter(cOUT);
        File pOUT = new File("preferred1.dat");
        PrintWriter pPrint = new PrintWriter(pOUT);
        
        for(Customer C1 : C)
            cPrint.println(C1.getID() + " " + C1.getFname() + " " + C1.getLname() + " " + (Math.round(C1.getTotal() * 100.0) / 100.0));
        for(PreferredCustomer P1 : P)
            pPrint.println(P1.getID() + " " + P1.getFname() + " " + P1.getLname() + " " +(Math.round(P1.getTotal() * 100.0) / 100.0) + " " + (int)(P1.getDiscount()) + "%");
        cPrint.close();
        pPrint.close();
    }

    public static double surfacePrice(double radius, double height, double ounce, double price, double sprice, double quantity){
        return (((((2*Math.PI*radius*height) + (2*Math.PI*Math.pow(radius,2.0))) * sprice) + (ounce*price))*(quantity));
    }
    public static PreferredCustomer[] payC(int spot, double p, PreferredCustomer[] P){
        C[spot].setTotal(C[spot].getTotal() + p);
        if(C[spot].getTotal() >= 150){
            PreferredCustomer p1= new PreferredCustomer(C[spot].getID(), C[spot].getFname() , C[spot].getLname(), C[spot].getTotal(), 5);
            P = addPCustomer(P, p1);
            C = removeC(C, spot);
        }
        if(P[P.length - 1].getTotal() >= 200)
            P[P.length - 1].setDiscount(7);
        if(P[P.length - 1].getTotal() >= 350)
            P[P.length - 1].setDiscount(10);
        return P;
    }

    public static PreferredCustomer[] payP(int spot, double p, PreferredCustomer[] P){
        P[spot].setTotal((p - (p * (P[spot].getDiscount()/100.0))) + P[spot].getTotal());
        if(P[spot].getTotal() >= 200.0){
            P[spot].setDiscount(7);
        }
        if(P[spot].getTotal() >= 350){
            P[spot].setDiscount(10);
        }
        return P;
    }

    public static Customer[] removeC(Customer[] c, int spot){
        Customer[] c1 = new Customer[c.length - 1];
        int i = 0, s=0;
        while(s < c.length){
            if(s != spot){
                c1[i] = c[s];
                i++;
            }
            s++;
        }
        return c1;
    }

    public static Customer[] addCustomer(Customer[] c, Customer c1){
        Customer[] arr = new Customer[c.length + 1];
        if(c.length != 0){
            for(int i = 0;i < arr.length-1;i++){
                arr[i] = c[i];
            }
        }
        arr[arr.length - 1] = c1;
        return arr;
    }
    public static PreferredCustomer[] addPCustomer(PreferredCustomer[] p, PreferredCustomer p1){
        PreferredCustomer[] arr = new PreferredCustomer[p.length + 1];
        if(p == null)
            p = new PreferredCustomer[0];
        if(p.length != 0){
            for(int i = 0;i < arr.length-1;i++){
                arr[i] = p[i];
            }
        }
        arr[arr.length - 1] = p1;
        return arr;
    }
    
        
}
