
package project2;

/**
 *
 * @author James Dunlap
 */
public class Customer {
    public Customer(int i, String f, String l, double t){fname = f; lname = l; id = i; total = t;}
    public Customer(){fname = ""; lname = ""; id = 0; total = 0.0;}
    
    protected String fname;
    protected String lname;
    protected int id;
    protected double total;
    
    
    public int getID(){return id;}
    public double getTotal(){return total;}
    public String getLname(){return lname;}
    public String getFname(){return fname;}
    public void setID(int x){id = x;}
    public void setTotal(double x){total = x;}
    public void setLname(String x){lname = x;}
    public void setFname(String x){fname = x;}
}
