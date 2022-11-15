package inventorymanagement;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InventoryManagement extends JFrame{
    JPanel jp_panel1 = new Login(this);
    JPanel jp_panel2 = new Register();
    JPanel jp_panel3 ;
    JPanel jp_panle4 = new Admin();
    Container ob_container;
    InventoryManagement(){
        Login.getRegister(jp_panel2);
        Login.getCustomer(jp_panel3);
        Login.getAdmin(jp_panle4);
        Register.getLogin(jp_panel1);
        CustomerTitle.getLogin(jp_panel1);
        AdminTitle.getLogin(jp_panel1);
        setTitle("Inventory Management System");
        setSize(850,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ob_container = getContentPane();
        ob_container.setLayout(null);
        ob_container.add(jp_panel1);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) throws Exception {
        InventoryManagement ob_login = new InventoryManagement();
    }
}
