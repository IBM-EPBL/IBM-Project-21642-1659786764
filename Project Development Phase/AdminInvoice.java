package inventorymanagement;

import static inventorymanagement.CustomerDisplay.cart;
import java.awt.Color; 
import java.awt.event.ActionEvent; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*; 
 
public class AdminInvoice extends JPanel{ 
//JPanel invoicepanel; 
JLabel jlb_date,jlb_mobile; 
    JTextField jtf_date,jtf_mobile; 
    JButton jb_submit, jb_invoice,jb_order; 
    JScrollPane orderScrollPane,invoiceScrollPane; 
    public AdminInvoice() { 
        //invoicepanel = new JPanel(); 
        setSize(750,700); 
        setLayout(null); 
        setLocation(150,50); 
        setVisible(true); 
        jlb_date = new JLabel("Date");jlb_date.setBounds(20 - 20,50,80,25); 
        jlb_mobile = new JLabel("Mobile");jlb_mobile.setBounds(320 - 50,50,80,25); 
        jtf_date = new JTextField();jtf_date.setBounds(100 - 50,50,200,25); 
        jtf_mobile = new JTextField();jtf_mobile.setBounds(400 - 50,50,200,25); 
        jb_submit = new JButton("Submit");jb_submit.setBounds(620 - 50,50,80,25); 
         
        add(jlb_date); 
        add(jlb_mobile); 
        add(jtf_date); 
        add(jtf_mobile); 
        add(jb_submit); 
        setVisible(true); 
         
        jb_invoice = new JButton("INVOICE");jb_invoice.setBounds(60+100,100,80,30); 
        jb_order = new JButton("ORDER");jb_order.setBounds(180+100,100,80,30); 
         
         
         
        jb_submit.addActionListener((ActionEvent ae)->{ 
  
        add(jb_invoice); 
        add(jb_order); 
        repaint(); 
        revalidate(); 
         
        }); 
       
         
         
        jb_invoice.addActionListener((ActionEvent a)->{ 
            String date = jtf_date.getText(); 
            String mob = jtf_mobile.getText(); 
             
            String column[] = {"ORDER ID","AMOUNT","CUSTOMER ID"}; 
            String values[][] = null; 
            // GET DETAILS FROM SQL STORE INTO VALUES ARRAY 
            int row = 0;
            try{
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String sql = "select o.orderid,i.totcost,o.custid from order_tab o inner join invoice i on i.orderid = o.orderid where o.orderdt = '" + jtf_date.getText() +"' and o.custid = '" + jtf_mobile.getText() +"' order by orderid";
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    row++;
                }
                rs = pst.executeQuery();
                values = new String[row][3];
                int i=0;
                while(rs.next()){
                    int j=0;
                    values[i][j++]=rs.getString("orderid");
                    values[i][j++]=rs.getString("totcost");
                    values[i][j++]=rs.getString("custid");
                    i++;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            // TABLE CREATION 
            JTable invoiceTable = new JTable(values,column){ 
                @Override 
                public boolean isCellEditable(int row, int column) { 
                    return false;  
                } 
            }; 
            invoiceTable.getTableHeader().setReorderingAllowed(false);
            invoiceScrollPane = new JScrollPane(invoiceTable); 
            invoiceScrollPane.setBounds(0,180,650,300); 
            invoiceScrollPane.setBackground(Color.red); 
            invoiceScrollPane.setVisible(true); 
            if(orderScrollPane != null){ 
                 orderScrollPane.setVisible(false); 
            } 
            add(invoiceScrollPane); 
//            invoicepanel.revalidate(); 
//            invoicepanel.repaint(); 
             
        }); 
          
        jb_order.addActionListener((ActionEvent ae)->{ 
            String date = jtf_date.getText(); 
            String mob = jtf_mobile.getText(); 
             
            String values[][] = {{"7","8","9"}}; 
            String column[] = {"ORDER ID","PRODUCT","QUANTITY"}; 
             
            // GET DETAILS FROM SQL STORE INTO VALUES ARRAY 
            int row = 0;
            try{
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String sql = "select o.orderid,s.stkname,s.quantity from order_tab o inner join order_det s on s.orderid = o.orderid where o.orderdt = '" + jtf_date + "' and o.custid = '" + jtf_mobile + "' order by orderid";
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while(rs.next()){
                    row++;
                }
                rs = pst.executeQuery();
                values = new String[row][3];
                int i=0;
                while(rs.next()){
                    int j=0;
                    values[i][j++]=rs.getString("orderid");
                    values[i][j++]=rs.getString("stkname");
                    values[i][j++]=rs.getString("quantity");
                    i++;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            //TABLE CREATION 
             JTable orderTable = new JTable(values,column){ 
                @Override 
                public boolean isCellEditable(int row, int column) { 
                    return false; //To change body of generated methods, choose Tools | Templates. 
                } 
 
            }; 
              
            orderTable.setVisible(true); 
            orderTable.getTableHeader().setReorderingAllowed(false);
            orderScrollPane = new JScrollPane(orderTable); 
            orderScrollPane.setBounds(0,180,650,300); 
            orderScrollPane.setBackground(Color.blue); 
            if(invoiceScrollPane != null){ 
                invoiceScrollPane.setVisible(false); 
            } 
            add(orderScrollPane); 
            orderScrollPane.setVisible(true); 
//             invoicepanel.revalidate(); 
//             invoicepanel.repaint(); 
        }); 
      
    } 
      
}