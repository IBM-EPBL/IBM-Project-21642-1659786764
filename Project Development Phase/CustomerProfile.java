package inventorymanagement;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CustomerTitle extends JPanel implements ActionListener{
    JButton jb_logut;
    JLabel jlb_name,jlb_image;
    static JPanel jp_login,jp_customer;
    static void getLogin(JPanel jp_panel2) {
        jp_login = jp_panel2;
    }
    Border b = BorderFactory.createLineBorder(Color.black);
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    CustomerTitle(JPanel jp_customer,String name){
        this.jp_customer=jp_customer;
        setLayout(null);
        setSize(840,50);
        setBorder(b);
        jlb_name = new JLabel(name);jlb_name.setBounds(50,10,100,25);
        jlb_name.setFont(f2);
        jlb_image = new JLabel(new ImageIcon(""));//paste here
        jlb_image.setBounds(0,0,50,50);
        jb_logut = new JButton("Log out");jb_logut.setBounds(700,10,100,25);
        jb_logut.addActionListener(this);
        jb_logut.setBorderPainted(true);
        jb_logut.setBackground(Color.lightGray);
        jb_logut.setFont(f2);
        add(jb_logut);
        add(jlb_name);
        add(jlb_image);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jb_logut)){
            jp_customer.setVisible(false);
            jp_login.setVisible(true);
        }
    }
}