package Customer;
/**
 *
 * @author James Dunlap
 */
public class Customer {
    public Customer(int i, String f, String l, double t){fname = f; lname = l; id = i; total = t;}//overloaded constructor
    public Customer(){fname = ""; lname = ""; id = 0; total = 0.0;}//default constructor
    
    protected String fname;//variables
    protected String lname;
    protected int id;
    protected double total;
    
    
    public int getID(){return id;}//get the id
    public double getTotal(){return total;}//get the total money spent
    public String getLname(){return lname;}//get the last name
    public String getFname(){return fname;}//get the first name
    public void setID(int x){id = x;}//set the id
    public void setTotal(double x){total = x;}//set total money spent
    public void setLname(String x){lname = x;}//set the last name
    public void setFname(String x){fname = x;}//set the first name
}
