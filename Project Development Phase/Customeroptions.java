/*package inventorymanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CustomerProfile extends JPanel implements ActionListener{
    JLabel jlb_name,jlb_username,jlb_password,jlb_contact;
    JTextField jtf_name,jtf_username,jtf_password,jtf_contact;
    JButton jb_update,jb_back;
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    public CustomerProfile() {
        setSize(740,520);
        setLayout(null);
        setLocation(100,50);
        setBorder(blackline);
        jlb_name  = new JLabel("Name");jlb_name.setBounds(200,100,100,25);
        jlb_name.setFont(f2);
        jlb_username = new JLabel("Username");jlb_username.setBounds(200,150,100,25);
        jlb_username.setFont(f2);
        jlb_password = new JLabel("Password");jlb_password.setBounds(200,200,100,25);
        jlb_password.setFont(f2);
        jlb_contact = new JLabel("Contact");jlb_contact.setBounds(200,250,100,25);
        jlb_contact.setFont(f2);
        jtf_name = new JTextField();jtf_name.setBounds(300,100,200,25);
        jtf_username = new JTextField();jtf_username.setBounds(300,150,200,25);
        jtf_password = new JTextField();jtf_password.setBounds(300,200,200,25);
        jtf_contact = new JTextField();jtf_contact.setBounds(300,250,200,25);
        jb_update = new JButton("UPDATE");jb_update.setBounds(200,300,100,25);
        jb_update.setFont(f2);
        jb_update.setBackground(Color.lightGray);
        add(jlb_name);
        add(jlb_username);
        add(jlb_password);
        add(jlb_contact);
        add(jtf_name);
        add(jtf_username);
        add(jtf_password);
        add(jtf_contact);
        add(jb_update);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob_event = e.getSource();
        
    }
}*/
package inventorymanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;

public class CustomerProfile extends JPanel implements ActionListener{
    JLabel jlb_username,jlb_password,jlb_mail;
    JTextField jtf_username,jtf_mail;
    JPasswordField jtf_password;
    JButton jb_update;
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    public CustomerProfile(String username) {
        setSize(740,520);
        setLayout(null);
        setLocation(100,50);
        setBorder(blackline);
        jlb_username = new JLabel("Username");jlb_username.setBounds(200,150,100,25);
        jlb_username.setFont(f2);
        jlb_password = new JLabel("Password");jlb_password.setBounds(200,200,100,25);
        jlb_password.setFont(f2);
        jlb_mail = new JLabel("Mail");jlb_mail.setBounds(200,250,100,25);
        jlb_mail.setFont(f2);
        jtf_username = new JTextField();jtf_username.setBounds(300,150,200,25);
        jtf_username.setEditable(false);
        jtf_password = new JPasswordField();jtf_password.setBounds(300,200,200,25);
        //jtf_password.setEditable(false);
        jtf_mail = new JTextField();jtf_mail.setBounds(300,250,200,25);
        jb_update = new JButton("UPDATE");jb_update.setBounds(200,300,100,25);
        jb_update.setFont(f2);
        jb_update.setBackground(Color.lightGray);
        jb_update.addActionListener(this);
        try {
            Connection co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
            String query  = "select * from users_tab where userid = '"+username+"'";
            PreparedStatement pstmt = co.prepareStatement(query);
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){
                jtf_username.setText(rst.getString("userid"));
                jtf_password.setText(rst.getString("userpass"));
                jtf_mail.setText(rst.getString("gmail"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        add(jlb_username);
        add(jlb_password);
        add(jlb_mail);
        add(jtf_username);
        add(jtf_password);
        add(jtf_mail);
        add(jb_update);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob_event = e.getSource();
        if(ob_event.equals(jb_update)){
            String username = jtf_username.getText();
            String userpass = jtf_password.getText();
            String usermail = jtf_mail.getText();
            try {
                Connection co =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                String query = "update users_tab set userpass = '"+userpass+"',gmail = '"+usermail+"'where userid = '"+username+"'";
                PreparedStatement pstmt = co.prepareStatement(query);
                pstmt.executeUpdate();
                co.setAutoCommit(true);
                System.out.println("success");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}