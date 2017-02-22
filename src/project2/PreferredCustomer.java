
package project2;

/**
 *
 * @author James Dunlap
 */
public class PreferredCustomer extends Customer{
    protected double discount;
    
    public PreferredCustomer(){super(); discount = 0;}
    public PreferredCustomer( int i, String f, String l, double t, double d){super(i, f, l, t); discount = d;}
    
    public double getDiscount(){return discount;}
    public void setDiscount(double x){discount = x;}
}
