package inventorymanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;


public class Register extends JPanel implements ActionListener{
    
    JLabel jlb_name,jlb_username,jlb_pass,jlb_contact,jlb_register,jlb_mail;
    JTextField jtf_name,jtf_pass,jtf_mail;
    JButton jb_submit,jb_clear,jb_cancel;
    static JPanel jp_login ;
    static void getLogin(JPanel jp_panel1) {
        jp_login=jp_panel1;
    }
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    Font f1 = new Font(Font.SERIF,Font.BOLD,15);
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    int OTP;
    Register(){
        int x = 20;
        int y = 70;
        setLayout(null);
        jlb_register = new JLabel("  Register");jlb_register.setBounds(120+230+x,50+y,80,25);
        jlb_register.setFont(f1);
        jlb_register.setBorder(blackline);
        jlb_register.setForeground(Color.BLACK);
        
        jlb_name = new JLabel("Name   ");jlb_name.setBounds(20+230+x,100+y,100,25);
        jlb_name.setFont(f2);
        
        jlb_pass = new JLabel("Password  ");jlb_pass.setBounds(20+230+x,150+y,100,25);
        jlb_pass.setFont(f2);
        
        jlb_mail = new JLabel("Mail ID   ");jlb_mail.setBounds(20+230+x,200+y,100,25);
        jlb_mail.setFont(f2);
        
        jtf_name = new JTextField();jtf_name.setBounds(120+230+x,100+y,180,25);
        
        jtf_pass = new JTextField();jtf_pass.setBounds(120+230+x,150+y,180,25);
        
        jtf_mail = new JTextField();jtf_mail.setBounds(120+230+x,200+y,180,25);
        
        jb_submit = new JButton("Submit");jb_submit.setBounds(110+230+x,275+y,100,25);
        jb_submit.addActionListener(this);
        jb_submit.setBackground(Color.lightGray);
        jb_submit.setFont(f2);
        
        jb_clear = new JButton("Clear");jb_clear.setBounds(220+230+x,275+y,100,25);
        jb_clear.addActionListener(this);
        jb_clear.setBackground(Color.lightGray);
        jb_clear.setFont(f2);
        
        jb_cancel = new JButton("Cancel");jb_cancel.setBounds(0+230+x,275+y,100,25);
        jb_cancel.addActionListener(this);
        jb_cancel.setBackground(Color.lightGray);
        jb_cancel.setFont(f2);
        
        add(jlb_register);
        add(jlb_name);
        add(jlb_pass);
        add(jtf_name);
        add(jtf_pass);
        add(jb_submit);
        add(jb_clear);
        add(jb_cancel);
        add(jlb_mail);
        add(jtf_mail);
        setSize(850,600);
        setBorder(blackline);
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jb_submit)){
            String getotp;            
            SendMailOtp newotp = new SendMailOtp();
            newotp.OTP(jtf_mail.getText());
            String otp = String.valueOf(SendMailOtp.ot);
            getotp = (String) JOptionPane.showInputDialog("Enter OTP");
            if(otp.equals(getotp)){
                JOptionPane.showMessageDialog(null, "Verified");
                this.setVisible(false);
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "insert into users_tab values(?,?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,jtf_name.getText());
                    pst.setString(2,jtf_pass.getText());
                    pst.setString(3,"user");
                    pst.setString(4,jtf_mail.getText());
                    pst.executeUpdate();
                    con.setAutoCommit(true);
                    jtf_name.setText("");
                    jtf_pass.setText("");
                    jtf_mail.setText("");
                    JOptionPane.showMessageDialog(null,"Success");
                    jp_login.setVisible(true);
                }catch(Exception exe){
                    exe.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid otp");
            }   
       }
        
       if(e.getSource().equals(jb_clear)) {
            jtf_name.setText("");
            jtf_pass.setText("");
            jtf_mail.setText("");
       }
       if(e.getSource().equals(jb_cancel)){
            this.setVisible(false);
            jp_login.setVisible(true);
            jtf_name.setText("");
            jtf_pass.setText("");
            jtf_mail.setText("");
       }
    }
    
}
