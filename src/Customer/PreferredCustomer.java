package Customer;
/**
 *
 * @author James Dunlap
 */
public class PreferredCustomer extends Customer{
    protected double discount;
    
    public PreferredCustomer(){super(); discount = 0;}//default linked with customer class default
    public PreferredCustomer( int i, String f, String l, double t, double d){super(i, f, l, t); discount = d;}//overloaded constructor linking with customer
    
    public double getDiscount(){return discount;}//get the discount
    public void setDiscount(double x){discount = x;}//set the discount
}
