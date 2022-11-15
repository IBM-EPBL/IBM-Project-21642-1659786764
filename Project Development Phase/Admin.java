package inventorymanagement;

import javax.swing.JPanel;

public class Admin extends JPanel{
    JPanel jp_AdminTitle ;
    JPanel jp_AdminOption ;
    JPanel jp_AdminDisplay ;
    JPanel jp_AdminAddStock ; 
    JPanel jp_AdminInvoice ; 
    public Admin(){
        jp_AdminTitle = new AdminTitle(this);
        jp_AdminOption = new AdminOption();
        jp_AdminDisplay = new AdminDisplay();
        jp_AdminInvoice = new AdminInvoice();
        jp_AdminAddStock = new AdminAddStock();
        AdminOption.jp_AdminMain = this;
        AdminOption.jp_AdminDisplay = jp_AdminDisplay;
        AdminOption.jp_AdminAddStock = jp_AdminAddStock;
        AdminOption.jp_AdminInvoice = jp_AdminInvoice;
        setLayout(null);
        setSize(840,600);
        add(jp_AdminTitle);
        add(jp_AdminOption);
        add(jp_AdminDisplay);
        jp_AdminDisplay.setVisible(true);
        repaint();
        revalidate();
    }
}
