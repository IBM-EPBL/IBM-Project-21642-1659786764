package inventorymanagement;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminAddStock extends JPanel implements ActionListener{
    JLabel jlb_stockId,jlb_stockname,jlb_stockrate,jlb_date,jlb_expdate,jlb_gst,jlb_category,jlb_purchaseid,jlb_quantity;
    JTextField jtf_stockId,jtf_stockname,jtf_stockrate,jtf_date,jtf_gst,jtf_quantity,jtf_purchaseid;
    JComboBox<String> jcb_category,jcb_seller;
    JButton jb_addtostock,jb_addtopur;
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    String id;
    public AdminAddStock() {
        setLayout(null);
        setSize(740,520);
        setLocation(100,50);
        setBorder(blackline);
        JLabel sellerLabel = new JLabel("SELLER"); 
        jcb_seller  = new JComboBox<>(); //seller
        jlb_purchaseid = new JLabel("PURCHASE ID"); 
        jtf_purchaseid =new JTextField(); 
        jlb_stockId = new JLabel("STOCK ID");
        jlb_stockname = new JLabel("STOCKNAME");
        jlb_stockrate = new JLabel("RATE");
        jlb_date = new JLabel("DATE");
        jlb_expdate = new JLabel("EXP DATE");
        JDateChooser jdc = new JDateChooser();        
        jdc.setDateFormatString("dd/MM/yyyy");
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdc.getDateEditor();
        editor.setEditable(false);
        jlb_gst = new JLabel("GST");
        jlb_category = new JLabel("CATEGORY");
        jlb_quantity = new JLabel("QUANTITY");
        jtf_stockId = new JTextField();
        jtf_stockname = new JTextField();
        jtf_stockrate = new JTextField();
        jtf_date = new JTextField();
        jtf_gst = new JTextField();
        jtf_quantity = new JTextField();
        jcb_category = new JComboBox<>();
        jb_addtostock = new JButton("ADD TO STOCK");
        jb_addtostock.setBackground(Color.lightGray);
        jb_addtopur = new JButton("UPLOAD");
        jb_addtopur.setBackground(Color.lightGray);
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "select sellername from seller";
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            jcb_seller.addItem("-- Select Seller --");
            while(rs.next()){
                jcb_seller.addItem(rs.getString("sellername"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"No seller found");
        }
        try{
            jcb_category.addItem("-- Select Category --");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "select category from category";
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                jcb_category.addItem(rs.getString("category"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"No category found");
        }
        
        sellerLabel.setBounds(100, 30, 100, 25);
        jcb_seller.setBounds(200, 30, 200, 25);
        jlb_purchaseid.setBounds(410, 30, 100, 25);
        jtf_purchaseid.setBounds(490, 30, 200, 25);
        jlb_gst.setBounds(410,280,100,25);
        jlb_date.setBounds(100,80,100,25);
        jlb_stockrate.setBounds(410,330,100,25);
        jlb_stockname.setBounds(410,230,100,25);
        jlb_expdate.setBounds(100,330,100,25);
        jlb_quantity.setBounds(100,380,100,25);
        jlb_category.setBounds(100,280,100,25);
        jlb_stockId.setBounds(100,230,100,25);
        jtf_stockname.setBounds(490,230,200,25);
        jtf_gst.setBounds(490,280,200,25);
        jtf_date.setBounds(200,80,200,25);
        jtf_stockrate.setBounds(490,330,200,25);
        jdc.setBounds(200,330,200,25);
        jtf_quantity.setBounds(200,380,200,25);
        jcb_category.setBounds(200,280,200,25);
        jtf_stockId.setBounds(200,230,200,25);
        jb_addtostock.setBounds(490,420,200,25);
        jb_addtopur.setBounds(490,130,200,25);
        
        jtf_purchaseid.setEditable(false);
        jtf_date.setEditable(false);
        jtf_gst.setEditable(false);
        
        jcb_seller.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                String id = "";
                try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String query = "select sellerid from seller where sellername = '" + jcb_seller.getSelectedItem().toString() +"'";
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    PreparedStatement pst = con.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        id = rs.getString("sellerid");
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }
                StringBuffer s = new StringBuffer(id.toUpperCase());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
                LocalDateTime now = LocalDateTime.now(); 
                String sb = dtf.format(now);
                s.append(sb);
                jtf_purchaseid.setText(s.toString());
                dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                now = LocalDateTime.now();
                jtf_date.setText(dtf.format(now));
            }
        });
        
        jcb_category.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent b){
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "select gst from category where catid = '" + jcb_category.getSelectedItem().toString() +"'";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()){
                        jtf_gst.setText(String.valueOf(rs.getFloat("gst")));
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }
            }
        });
        
        jb_addtopur.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent c){
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "select sellerid from seller where sellername = '" + jcb_seller.getSelectedItem().toString() +"'";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()){
                        id = rs.getString("sellerid");
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "insert into purchase values(?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,jtf_purchaseid.getText());
                    pst.setString(2,jtf_date.getText());
                    pst.setString(3,id);
                    pst.executeUpdate();
                }catch(Exception exe){
                    JOptionPane.showMessageDialog(null,exe);
                }
            }
        });
        
        jb_addtostock.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d){
                String date = ((JTextField)jdc.getDateEditor().getUiComponent()).getText();
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "insert into stocks values(?,?,?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,jtf_stockId.getText());
                    pst.setString(2,jtf_stockname.getText());
                    pst.setInt(3,Integer.parseInt(jtf_quantity.getText()));
                    pst.setString(4,date);
                    pst.setString(5,jcb_category.getSelectedItem().toString());
                    pst.executeUpdate();
                    con.setAutoCommit(true);
                }catch(Exception exe){
                    exe.printStackTrace();
                }
                try{
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String sql = "insert into purchase_det values(?,?,?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,jtf_stockId.getText());
                    pst.setString(2,jtf_stockname.getText());
                    pst.setString(3,jtf_quantity.getText());
                    pst.setString(4,jtf_stockrate.getText());
                    pst.setString(5,jtf_purchaseid.getText());
                    pst.executeUpdate();
                    con.setAutoCommit(true);
                    jtf_stockId.setText("");
                    jtf_stockname.setText("");
                    jtf_quantity.setText("");
                    editor.setText("");
                    jtf_gst.setText("");
                    jtf_stockrate.setText("");
                    jcb_category.setSelectedItem("-- Select Category --");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }
            }
        });
        
        
        
        add(jlb_stockId);
        add(jlb_stockname);
        add(jlb_stockrate);
        add(jlb_date);
        add(jlb_expdate);
        add(jlb_gst);
        add(jlb_category);
        add(jlb_quantity);
        add(jtf_quantity);
        add(jtf_stockId);
        add(jtf_stockname);
        add(jtf_stockrate);
        add(jtf_date);
        add(jdc);
        add(jtf_gst);
        add(jcb_category);
        add(jb_addtostock);
        add(jb_addtopur);
        add(sellerLabel);
        add(jcb_seller);
        add(jlb_purchaseid);
        add(jtf_purchaseid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

//package inventorymanagement; 
// 
//import java.awt.Color; 
//import java.awt.Font; 
//import java.awt.event.ActionEvent; 
//import java.awt.event.ActionListener; 
//import java.awt.event.ItemEvent; 
//import javax.swing.BorderFactory; 
//import javax.swing.JButton; 
//import javax.swing.JComboBox; 
//import javax.swing.JLabel; 
//import javax.swing.JPanel; 
//import javax.swing.JTextField; 
//import javax.swing.border.Border; 
// 
//public class AdminAddStock extends JPanel implements ActionListener{ 
//    JPanel addstocksPanel; 
//    JLabel jlb_stockId,jlb_stockname,jlb_stockrate,jlb_mrp,jlb_description,jlb_indate,jlb_mfgdate,jlb_expdate,jlb_gst,jlb_category,jlb_quantity,vendorLabel,orderidLabel; 
//    JTextField jtf_stockId,jtf_stockname,jtf_stockrate,jtf_mrp,jtf_description,jtf_indate,jtf_mfgdate,jtf_expdate,jtf_gst,jtf_quantity,orderidField; 
//    JComboBox<String> jcb_category,jcb_vendor; 
//    JButton jb_addtostock; 
//    Font f2 = new Font(Font.DIALOG,Font.BOLD,15); 
//    Border blackline = BorderFactory.createRaisedSoftBevelBorder(); 
//    public AdminAddStock() { 
//        addstocksPanel = new JPanel(null); 
//        addstocksPanel.setSize(850-150,700); 
//        addstocksPanel.setLocation(150,50); 
//        addstocksPanel.setBorder(blackline); 
//         
//        int incre_Y = 50; 
//         
//        vendorLabel = new JLabel("VENDOR");vendorLabel.setBounds(100, 30, 100, 25); 
//        jcb_vendor  = new JComboBox<>();jcb_vendor.setBounds(200, 30, 200, 25); 
//        orderidLabel = new JLabel("ORDER ID");orderidLabel.setBounds(410, 30, 100, 25); 
//        orderidField =new JTextField();orderidField.setBounds(480, 30, 200, 25); 
//         
//        jlb_stockId = new JLabel("STOCK ID");jlb_stockId.setBounds(100,30+incre_Y,100,25); 
//        jlb_stockname = new JLabel("STOCK NAME");jlb_stockname.setBounds(100,80+incre_Y,100,25); 
//        jlb_stockrate = new JLabel("RATE/UNIT");jlb_stockrate.setBounds(100,130+incre_Y,100,25); 
//        jlb_mrp = new JLabel("MRP");jlb_mrp.setBounds(100,180+incre_Y,100,25); 
//        jlb_description = new JLabel("DESCRIPTION");jlb_description.setBounds(100,480+incre_Y,100,25); 
//        jlb_indate = new JLabel("DATE");jlb_indate.setBounds(100,330+incre_Y,100,25); 
//        jlb_mfgdate = new JLabel("MFG DATE");jlb_mfgdate.setBounds(100,380+incre_Y,100,25); 
//        jlb_expdate = new JLabel("EXP DATE");jlb_expdate.setBounds(100,430+incre_Y,100,25); 
//        jlb_gst = new JLabel("GST");jlb_gst.setBounds(420,230+incre_Y,100,25); 
//        jlb_category = new JLabel("CATEGORY");jlb_category.setBounds(100,230+incre_Y,100,25); 
//        jtf_stockId = new JTextField();jtf_stockId.setBounds(200,30+incre_Y,200,25); 
//        jtf_stockname = new JTextField();jtf_stockname.setBounds(200,80+incre_Y,200,25); 
//        jtf_stockrate = new JTextField();jtf_stockrate.setBounds(200,130+incre_Y,200,25); 
//        jtf_mrp = new JTextField();jtf_mrp.setBounds(200,180+incre_Y,200,25); 
//        jtf_description = new JTextField();jtf_description.setBounds(200,480+incre_Y,200,25); 
//        jtf_indate = new JTextField();jtf_indate.setBounds(200,330+incre_Y,200,25); 
//        jtf_mfgdate = new JTextField();jtf_mfgdate.setBounds(200,380+incre_Y,200,25); 
//        jtf_expdate = new JTextField();jtf_expdate.setBounds(200,430+incre_Y,200,25); 
//        jtf_gst = new JTextField();jtf_gst.setBounds(480,230+incre_Y,200,25); 
//        jb_addtostock = new JButton("ADD TO STOCK");jb_addtostock.setBounds(500,480+incre_Y,200,25); 
//        jb_addtostock.setBackground(Color.lightGray); 
//        jtf_gst.setEditable(false); 
//         
//        jcb_category = this.setComboBox(); 
//        jcb_category.setBounds(200,230+incre_Y,200,25); 
//        jlb_quantity = new JLabel("Quantity");jlb_quantity.setBounds(100,280+incre_Y,100,25); 
//        jtf_quantity = new JTextField();jtf_quantity.setBounds(200,280+incre_Y,200,25); 
//         
//        addstocksPanel.add(vendorLabel); 
//        addstocksPanel.add(jcb_vendor); 
//        addstocksPanel.add(orderidLabel); 
//        addstocksPanel.add(orderidField); 
//        addstocksPanel.add(jlb_quantity); 
//        addstocksPanel.add(jtf_quantity); 
//        addstocksPanel.add(jlb_stockId);
//        addstocksPanel.add(jlb_stockname); 
//        addstocksPanel.add(jlb_stockrate); 
//        addstocksPanel.add(jlb_mrp); 
//        addstocksPanel.add(jlb_description); 
//        addstocksPanel.add(jlb_indate); 
//        addstocksPanel.add(jlb_mfgdate); 
//        addstocksPanel.add(jlb_expdate); 
//        addstocksPanel.add(jlb_gst); 
//        addstocksPanel.add(jlb_category); 
//        addstocksPanel.add(jtf_stockId); 
//        addstocksPanel.add(jtf_stockname); 
//        addstocksPanel.add(jtf_stockrate); 
//        addstocksPanel.add(jtf_mrp); 
//        addstocksPanel.add(jtf_description); 
//        addstocksPanel.add(jtf_indate); 
//        addstocksPanel.add(jtf_mfgdate); 
//        addstocksPanel.add(jtf_expdate); 
//        addstocksPanel.add(jtf_gst); 
//        addstocksPanel.add(jcb_category); 
//        addstocksPanel.add(jb_addtostock); 
//        //addstocksPanel.setVisible(true); 
//         
//        jcb_category.addItemListener((ItemEvent ie)->{  
//            jtf_gst.setText(ie.paramString()); 
//        }); 
//         
//        jb_addtostock.addActionListener((ActionEvent e) -> { 
//             
//        }); 
//    } 
//     
//    private JComboBox<String> setComboBox(){ 
//        JComboBox<String> categorylist = new JComboBox<>(); 
//        String arr[] = {"Dairy Products","Baked Items","Dry Fruits"}; 
//        for (String string : arr) { 
//            categorylist.addItem(string); 
//        } 
//        return categorylist; 
//    } 
//}
