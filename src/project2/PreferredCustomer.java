
package project2;

/**
 *
 * @author James Dunlap
 */
public class PreferredCustomer extends Customer{
    protected int discount;
    
    public PreferredCustomer(){discount = 0;}
    public PreferredCustomer(String f, String l, int i, double t, int d){super(f, l, i, t); discount = d;}
    
    public int getDiscount(){return discount;}
    public void setDiscount(int x){discount = x;}
}
