
package candidate_database;

import candidate_database.Web_item;
import java.util.ArrayList;


public class ShoppingCartItem {
    private int quantity=1;
    private String web_page_item_pk;
    private Web_item web_item = null;
    public boolean itemOrdered = false;
    
    ShoppingCartItem(String web_page_item_pk, int quantity)
    {
        this.web_page_item_pk = web_page_item_pk;
        this.quantity = quantity;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    public void setWeb_item(Web_item wi)
    {
        web_item = wi;
    }
    public String getWeb_page_item_pk()
    {
        return web_page_item_pk;
    }
    public Web_item getWeb_item()
    {
        return web_item;
    }
    public boolean matches(String web_page_item_pk)
    {
        return this.web_page_item_pk.equals(web_page_item_pk);
    }
    public boolean getOrdered()
    {
        return itemOrdered;
    }
    // this routine protects us from bad user inputs for quantity
    public static int convert2Int(String s)
    {
        if (s==null)return -1;
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch(NumberFormatException e)
        {
            System.out.println("bad user integer input:"+ s);
            return -1;
        }
    }
    // The next 3 methods are used to add, delete, and update items in 
    // the shopping cart.
    public static void add(String quantityStr, String web_page_item_pk , 
            ArrayList<ShoppingCartItem> shoppingCart)
    {
        int quantity = convert2Int(quantityStr);
        if (quantity < 0) quantity = -quantity;
        if (quantity == 0) return ; // do nothing
        
        shoppingCart.add (new ShoppingCartItem(web_page_item_pk, quantity));       
    }
    public static void delete(String web_page_item_pk , 
            ArrayList<ShoppingCartItem> shoppingCart)
    {
        // updating an item to a quantity of zero will remove it 
        // from the shopping cart
        update("0", web_page_item_pk, shoppingCart);
    }
    
    public static void update(String quantityStr, String web_page_item_pk , 
            ArrayList<ShoppingCartItem> shoppingCart)
    {
        int quantity = convert2Int(quantityStr);
        if (quantity < 0) quantity = -quantity; // do nothing
        
        for (int i =0; i <  shoppingCart.size(); i++)
        {
            ShoppingCartItem item = shoppingCart.get(i);
            if (item.matches(web_page_item_pk))
            {
                if (quantity == 0)
                    shoppingCart.remove(i);
                else
                    item.setQuantity(quantity);
                break;
            }
        }
    }
}
