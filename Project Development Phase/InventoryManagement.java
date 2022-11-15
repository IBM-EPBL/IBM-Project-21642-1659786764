package inventorymanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Login extends JPanel implements ActionListener{
    JLabel jlb_userId,jlb_pass,jlb_login,jlb_new_user ;
    JTextField jtf_userId;
    JPasswordField jpf_pass;
    JButton jb_login,jb_clear,jb_register;
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    Font f1 = new Font(Font.SERIF,Font.BOLD,15);
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    static JPanel jp_register;
    static JPanel jp_customer,jp_admin;
    static void getRegister(JPanel jp_2) {
        jp_register =  jp_2;
    }
    static void getCustomer(JPanel jp_panel3) {
        jp_customer = jp_panel3;
    }
    static void getAdmin(JPanel jp_panle4) {
        jp_admin=jp_panle4;
    }
    JFrame jf_main;
    Login(JFrame jf_main){
        this.jf_main=jf_main;
        setLayout(null);
        int x = 20;
        int y = 70;
        jlb_login = new JLabel(" Log in");jlb_login.setBounds(360+x,50+y,60,25);
        jlb_login.setBorder(blackline);
        jlb_login.setForeground(Color.BLACK);
        jlb_login.setFont(f1);
        
        jlb_userId = new JLabel("Username : ");jlb_userId.setBounds(250+x,100+y,100,25);
        jlb_userId.setFont(f2);
        
        jlb_pass = new JLabel("Password : ");jlb_pass.setBounds(250+x,150+y,100,25);
        jlb_pass.setFont(f2);
        
        jtf_userId = new JTextField();jtf_userId.setBounds(370+x,100+y,180,25);
        jtf_userId.addActionListener(this);
        
        jpf_pass = new JPasswordField();jpf_pass.setBounds(370+x,150+y,180,25);
        jpf_pass.addActionListener(this);
        
        jb_login = new JButton("Log in");jb_login.setBounds(370+x,200+y,80,25);
        jb_login.setBackground(Color.lightGray);
        jb_login.addActionListener(this);
        jb_login.setFont(f2);
        
        jb_clear = new JButton("Clear");jb_clear.setBounds(470+x,200+y,80,25);
        jb_clear.setBackground(Color.lightGray);
        jb_clear.addActionListener(this);
        jb_clear.setFont(f2);
        
        jb_register = new JButton("Register");jb_register.setBounds(400+x,250+y,100,25);
        jb_register.setBackground(Color.lightGray);
        jb_register.addActionListener(this);
        jb_register.setFont(f2);
        
        jlb_new_user = new JLabel("New User? ");jlb_new_user.setBounds(80+230+x,250+y,110,25);
        jlb_new_user.setFont(f2);
        
        add(jlb_login);
        add(jlb_userId);
        add(jlb_pass);
        add(jtf_userId);
        add(jpf_pass);
        add(jb_login);
        add(jb_clear);
        add(jlb_new_user);
        add(jb_register);
        
        setSize(850,600);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jtf_userId)){
            jpf_pass.requestFocus();
        }
        if(e.getSource().equals(jpf_pass)){
            
        }
        if(e.getSource().equals(jb_register)){
            this.setVisible(false);
            jp_register = new Register();
            jf_main.add(jp_register);
            jp_register.setVisible(true);
            jtf_userId.setText("");
            jpf_pass.setText("");
        }
        if(e.getSource().equals(jb_clear)){
            jtf_userId.setText("");
            jpf_pass.setText("");
        }
        if(e.getSource().equals(jb_login)){
            try{
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String sql = "select * from users_tab where userid = '" + jtf_userId.getText() +"'and userpass = '" + jpf_pass.getText() + "'";
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                        if(rs.getString("useracs").equals("admin")){
                            try{
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDateTime now = LocalDateTime.now(); 
                                String sb = dtf.format(now);
                                PreparedStatement pstm;
                                Connection conn;
                                ResultSet rst;
                                Class.forName("oracle.jdbc.driver.OracleDriver");
                                String sq = "delete from stocks where expirydt = '"+sb+"'";
                                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                                pstm = con.prepareStatement(sql);
                                rst = pstm.executeQuery();
                                pstm.executeUpdate(sql);
                                con.setAutoCommit(true);
                            }catch(Exception exe){
                                JOptionPane.showMessageDialog(null,exe);
                            }
//                            userInfo ob = new userInfo();
//                            ob.setName(rs.getString("userid"));
//                            ob.setPassword(rs.getString("userpass"));
//                            ob.setAccess(rs.getString("useracs"));
                            jtf_userId.setText("");
                            jpf_pass.setText("");
                            this.setVisible(false);
                            jp_admin = new Admin();
                            jf_main.add(jp_admin);
                            jp_register.setVisible(false);
                            jp_admin.setVisible(true);
                        }
                        else if(rs.getString("useracs").equals("user")){
                            this.setVisible(false);
                            jp_customer = new Customer(jtf_userId.getText());
                            jf_main.add(jp_customer);
                            jp_register.setVisible(false);
                            jp_customer.setVisible(true);
                            jtf_userId.setText("");
                            jpf_pass.setText("");
                        }
                }else{
                    JOptionPane.showMessageDialog(null,"Invalid credentials");
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());
                ex.printStackTrace();
                ex.getMessage();
            }
        }
    }
}