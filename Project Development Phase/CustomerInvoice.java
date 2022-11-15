package inventorymanagement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Customeroptions extends JPanel implements ActionListener{   
    JButton jb_profile,jb_display,jb_invoice,jb_cartdetails;
    static JPanel jp_customer_main,jp_display ,jp_profile, jp_invoice,jp_viewpanel;  
    static void getcustomer_main(Customer aThis) {
        jp_customer_main = aThis;
    }
    static void getdisplay_panel(JPanel jp_display_panel) {
        jp_display = jp_display_panel;
    }
    static void getprofile_panel(JPanel jp_profile_panel) {
        jp_profile = jp_profile_panel;
    }
    static void getinvoice_panel(JPanel jp_invoice_panel) {
        jp_invoice = jp_invoice_panel;
    }
    static void getview_panel(JPanel jp_viewcart_panel) {
        jp_viewpanel=jp_viewcart_panel;
    }
    Border b = BorderFactory.createLineBorder(Color.black);
    public Customeroptions() {
        setLayout(null);
        setSize(100,520);
        setLocation(0,50);
        setBorder(b);
        jb_profile = new JButton("PROFILE");jb_profile.setBounds(0,50,100, 50);
        jb_profile.addActionListener(this);
        jb_profile.setBackground(Color.lightGray);
        jb_display = new JButton("DISPLAY");jb_display.setBounds(0,0,100, 50);
        jb_display.addActionListener(this);
        jb_display.setBackground(Color.lightGray);
        jb_invoice = new JButton("INVOICE");jb_invoice.setBounds(0,150,100, 50);
        jb_invoice.addActionListener(this);
        jb_invoice.setBackground(Color.lightGray);
        jb_cartdetails = new JButton("VIEW CART");jb_cartdetails.setBounds(0,100,100,50);
        jb_cartdetails.addActionListener(this);
        jb_cartdetails.setBackground(Color.lightGray);
        add(jb_profile);
        add(jb_display);
        add(jb_invoice);
        add(jb_cartdetails);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jb_display)){
            jp_display.setVisible(true);
            jp_invoice.setVisible(false);
            jp_profile.setVisible(false);
            Customer.jp_viewcart_panel.cartview.setVisible(false);
        }
        if(e.getSource().equals(jb_profile)){
            jp_customer_main.add(jp_profile);
            jp_profile.setVisible(true);
            jp_invoice.setVisible(false);
            jp_display.setVisible(false);
            Customer.jp_viewcart_panel.cartview.setVisible(false);
        }
        if(e.getSource().equals(jb_invoice)){
            jp_customer_main.add(jp_invoice);
            jp_invoice.setVisible(true);
            jp_display.setVisible(false);
            jp_profile.setVisible(false);
            Customer.jp_viewcart_panel.cartview.setVisible(false);
        }
        if(e.getSource().equals(jb_cartdetails)){
            Customer.jp_viewcart_panel.settable();
            jp_customer_main.add(Customer.jp_viewcart_panel.cartview);
            Customer.jp_viewcart_panel.cartview.setVisible(true);
            jp_invoice.setVisible(false);
            jp_display.setVisible(false);
            jp_profile.setVisible(false);
        }
    }
}