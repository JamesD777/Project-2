import Customer.Customer;
import Customer.PreferredCustomer;
import java.io.*;
import java.util.*;

public class Main{

    public static Customer[] C = new Customer[0];//global customer array

    public static void main(String[] args) throws FileNotFoundException{
        String line;
        File pref = new File("preferred.dat");//open preferred
        PreferredCustomer[] P = null;//create null array for if preferred is found or not
        if(pref.exists() && !pref.isDirectory()){//check if it exists
            P = new PreferredCustomer[0];//if it exists, create an array
                Scanner s = new Scanner(pref);
                while(s.hasNext()){
                    line = s.nextLine(); //get the line and scan through it pciking apart the customer info
                    line=line.substring(0,line.length() -1);
                    Scanner s2 = new Scanner(line);
                    PreferredCustomer p1 = new PreferredCustomer();
                    p1.setID(s2.nextInt());
                    p1.setFname(s2.next());
                    p1.setLname(s2.next());
                    p1.setTotal(s2.nextDouble());
                    p1.setDiscount(s2.nextInt());
                    P = addPCustomer(P,p1);//add prefcustomer to the array
                }
                s.close();
        }
        
        try {
            File c = new File("customer.dat");

            Scanner s = new Scanner(c);
            while(s.hasNext()){
                line = s.nextLine();
                Scanner s2 = new Scanner(line); //get the line and scan through it pciking apart the customer info
                Customer c1 = new Customer();
                c1.setID(s2.nextInt());
                c1.setFname(s2.next());
                c1.setLname(s2.next());
                c1.setTotal(s2.nextDouble());
                C = addCustomer(C,c1);//add customer to array
            }
            s.close();
        }catch (FileNotFoundException f){
            System.out.println("File not found" + f);
        }
        
        File o = new File("orders.dat");
        Scanner s = new Scanner(o);
        int spot;
        int id;
        while(s.hasNextLine()){//for each order
            spot = -1;
            id = s.nextInt();
            for(int i = 0; i < C.length;i++){//chec if ID of order is in customer array
                if(C[i].getID() == id)
                    spot = i;
            }
            if(spot != -1)//if in the customer array, calculate surface price and call payC
                P = payC(spot, surfacePrice(s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble()), P);
            else if(P != null){
                for(int i = 0; i < P.length;i++){//check for ID in Pref array
                    if(P[i].getID() == id)
                        spot = i;
                }
                if(spot != -1)//if in array, calculate surface price and call payC
                    P = payP(spot, surfacePrice(s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble()), P);
            }

        }
        s.close();

        File cOUT = new File("customer.dat");
        PrintWriter cPrint = new PrintWriter(cOUT);
        File pOUT = new File("preferred.dat");
        PrintWriter pPrint = new PrintWriter(pOUT);
        
        for(Customer C1 : C) //for each object, print the info back into the corresponding file
            cPrint.println(C1.getID() + " " + C1.getFname() + " " + C1.getLname() + " " + (Math.round(C1.getTotal() * 100.0) / 100.0));
        if(P.length != 0){
        for(PreferredCustomer P1 : P) //for each object, print the info back into the corresponding file
            pPrint.println(P1.getID() + " " + P1.getFname() + " " + P1.getLname() + " " +(Math.round(P1.getTotal() * 100.0) / 100.0) + " " + (int)(P1.getDiscount()) + "%");
        }
        cPrint.close();
        pPrint.close();
    }
    //calculate the surface price + ounce price of all drinks
    public static double surfacePrice(double radius, double height, double ounce, double price, double sprice, double quantity){
        return (((((2*Math.PI*radius*height) + (2*Math.PI*Math.pow(radius,2.0))) * sprice) + (ounce*price))*(quantity));
    }
    //add payment to customers, upgrade if needed
    public static PreferredCustomer[] payC(int spot, double p, PreferredCustomer[] P){
        C[spot].setTotal(C[spot].getTotal() + p);//update the total spent
        if(C[spot].getTotal() >= 150){//if passed Preferred limit, make a new Pref customer and add it to the Pref array
            PreferredCustomer p1= new PreferredCustomer(C[spot].getID(), C[spot].getFname() , C[spot].getLname(), C[spot].getTotal(), 5);
            P = addPCustomer(P, p1);
            C = removeC(C, spot);//remove upgraded customer from customer array
        }
        if(P!= null){//if p exists
            if(P[P.length - 1].getTotal() >= 200)//check if past the last 2 discount limits
                P[P.length - 1].setDiscount(7);
            if(P[P.length - 1].getTotal() >= 350)
                P[P.length - 1].setDiscount(10);
        }
        return P;
    }
    //upgrade total for preferred customers
    public static PreferredCustomer[] payP(int spot, double p, PreferredCustomer[] P){
        P[spot].setTotal((p - (p * (P[spot].getDiscount()/100.0))) + P[spot].getTotal());//update total spent
        if(P[spot].getTotal() >= 200.0)//check if passed discount limits and update if so
            P[spot].setDiscount(7);
        if(P[spot].getTotal() >= 350)
            P[spot].setDiscount(10);
        return P;
    }
    //remove customer from customer array
    public static Customer[] removeC(Customer[] c, int spot){
        Customer[] c1 = new Customer[c.length - 1];//create new array
        int i = 0, s=0;
        while(s < c.length){//loop through initial array
            if(s != spot){//if not the customer being removed, copy over to new array
                c1[i] = c[s];
                i++;
            }
            s++;
        }
        return c1;
    }
    //add customer to array
    public static Customer[] addCustomer(Customer[] c, Customer c1){
        Customer[] arr = new Customer[c.length + 1];
        if(c.length != 0){//if there are already customers in the array
            for(int i = 0;i < arr.length-1;i++){//loop thru array and copy them over
                arr[i] = c[i];
            }
        }
        arr[arr.length - 1] = c1;//add new customer
        return arr;
    }
    //add pref customer to pref array
    public static PreferredCustomer[] addPCustomer(PreferredCustomer[] p, PreferredCustomer p1){
        if(p == null)//if array not instantiated, create the array
            p = new PreferredCustomer[0];
        PreferredCustomer[] arr = new PreferredCustomer[p.length + 1];//create the copy array
        if(p.length != 0){//if pref customers are in the array already
            for(int i = 0;i < arr.length-1;i++){//copy over to new array
                arr[i] = p[i];
            }
        }
        arr[arr.length - 1] = p1;//add new pref customer
        return arr;
    }
    
        
}
