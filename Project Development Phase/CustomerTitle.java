package inventorymanagement;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class CustomerViewCart extends JPanel implements ActionListener{ 
    float gst[],total[];
    float gsttotal = (float) 0.0,totalamount = (float) 0.0;
    JTable t_cart;
    JPanel cartview;
    JScrollPane jsp_table;
    JLabel jlb_cartdetails,jlb_total,jlb_gst;
    JTextField jtf_total,jtf_gst;
    JButton jb_buy;
    int row;
    String column_cart[]={"Name","MRP","Quantity","GST-%","GST-Price","Total"};
    Hashtable<String,Integer> selectedproducts = CustomerDisplay.cart;
    Hashtable<String,Integer> update_tb = CustomerDisplay.maxquantity;
    ArrayList<ArrayList<String>> al_values = new ArrayList<>();
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    
    public void PDF(String d,String dt,String name,String mobile){
        int row = t_cart.getRowCount(); 
        int column = t_cart.getColumnCount(); 
        String Tablevalues[][] = new String[row][column]; 
        for(int i = 0;i < row; i++){ 
            for (int j = 0; j < column; j++) { 
                Tablevalues[i][j] = (String)t_cart.getValueAt(i, j); 
            } 
        } 
        for (String[] Tablevalue : Tablevalues) { 
            for (String data : Tablevalue) { 
                System.out.print(data+" "); 
            } 
            System.out.println(""); 
        } 
        Document doc = new Document(); 
        String Filename = "C:\\Users\\Kamalesh AR\\Desktop\\Inventory_Bills\\" + d + ".pdf"; 
        /*try {
            PdfWriter.getInstance(d, new FileOutputStream(Filename)); 
            d.open(); 
            d.addTitle("INVENTORY"); 
            Paragraph p; 
            for(String[] rows:Tablevalues){ 
                System.out.println(0); 
                String data = ""; 
                for (String string : rows) { 
                    data+=string+" "; 
                }
                p = new Paragraph(data.trim()); 
                d.add(p); 
            } 
            d.close(); 
        } catch ( Exception e) { 
            System.out.println(e.getClass()); 
        } */
        try{
            PdfWriter.getInstance(doc, new FileOutputStream(Filename));
            doc.open();
            PdfPTable pdfTable = new PdfPTable(t_cart.getColumnCount());
            //adding table headers
            for (int i = 0; i < t_cart.getColumnCount(); i++) {
                pdfTable.addCell(t_cart.getColumnName(i));
            }
            //extracting data from the JTable and inserting it to PdfPTable
            for (int rows = 0; rows < t_cart.getRowCount(); rows++) {
                for (int cols = 0; cols < t_cart.getColumnCount(); cols++) {
                    pdfTable.addCell(t_cart.getModel().getValueAt(rows, cols).toString());

                }
            }
            String det = "Name : " + name + "\n" + "Contact: " + mobile +"\n";
            String InvID = "Invoice ID: " + d + "                                                  "+ "Date: " + dt +"\n" + "\n";
            Paragraph p1 = new Paragraph(det);
            Paragraph p2 = new Paragraph(InvID);
            doc.add(p1);
            doc.add(p2);
            doc.add(pdfTable);
            doc.close();
        }catch(Exception E){
            E.printStackTrace();
        }
    }
        /*try {
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(Filename));

            d.open();
            PdfContentByte cb = writer.getDirectContent();

            cb.saveState();
            Graphics2D g2 = cb.createGraphics(1000, 1000);

            Shape oldClip = g2.getClip();
            //g2.clipRect(0, 0, 500, 500);

            t_cart.print(g2);
            g2.setClip(oldClip);

            g2.dispose();
            cb.restoreState();
          } catch (Exception e) {
            System.err.println(e.getMessage());
          }
        d.close();
    }*/
    
    public CustomerViewCart()  { 
        cartview = new JPanel(null);
        cartview.setBounds(100,50,740,520);
        jlb_cartdetails = new JLabel("CART DETAILS");jlb_cartdetails.setBounds(310,50,120,25);
        jlb_cartdetails.setBorder(blackline);
        jlb_cartdetails.setFont(f2);
        jlb_total = new JLabel("TOTAL");jlb_total.setBounds(540,410,80,25);
        jlb_total.setFont(f2);
        jlb_gst = new JLabel("GST AMT");jlb_gst.setBounds(390,410,80,25);
        jlb_total.setFont(f2);
        jtf_total = new JTextField();jtf_total.setBounds(600,410,80,25);
        jtf_gst = new JTextField();jtf_gst.setBounds(450,410,80,25);
        jtf_total.setEditable(false);
        jtf_gst.setEditable(false);
        jb_buy = new JButton("BUY");jb_buy.setBounds(630,450,80,25);
        jb_buy.addActionListener(this);
        jb_buy.setFont(f2);
        jb_buy.setBackground(Color.lightGray);
        cartview.add(jb_buy);
        cartview.add(jlb_total);
        cartview.add(jtf_total);
        cartview.add(jlb_gst);
        cartview.add(jtf_gst);
        t_cart = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        t_cart.getTableHeader().setReorderingAllowed(false);
        t_cart.setShowGrid(true);
        jsp_table = new JScrollPane(t_cart);
        jsp_table.setBounds(50,100,630,300);
    }
    void settable(){
        DefaultTableModel model = new DefaultTableModel(this.getdetails(), column_cart);
        t_cart.setModel(model);
        cartview.add(jlb_cartdetails);
        cartview.add(jsp_table);
    }
    String[][] getdetails(){
        row =0;
        String values[][] = null;
        try{ 
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection co =DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "SYS AS SYSDBA", "cosmos2709"); 
//            Using ENUM 
            for(String key:selectedproducts.keySet()) { 
                if(selectedproducts.get(key) > 0){ 
                   row++;  
                }  
            } 
            values = new String[row][6];///column 5 
            total = new float[row];
            gst = new float[row];
            int i=0,j=0; /////(quan * rate * gst * 0.01) + (quan * rate)
            for(String key:selectedproducts.keySet()){ 
                if( selectedproducts.get(key) > 0){ 
                    String Query= "select s.stkname,p.rate,c.gst from stocks s inner join purchase_det p on p.stkid = s.stkid inner join category c on c.catid = s.catid and s.stkid="+"'"+key+"'"; 
                    PreparedStatement pstmt = co.prepareStatement(Query); 
                    ResultSet rst = pstmt.executeQuery(); 
                    if (rst.next()) {
                        gst[i] = (float) (selectedproducts.get(key) * rst.getInt("rate") * rst.getFloat("gst") * 0.01);
                        total[i] = selectedproducts.get(key) * rst.getInt("rate") + gst[i];
                        values[i][j++] = rst.getString("stkname"); 
                        values[i][j++] = "Rs "+String.valueOf(rst.getInt("rate"));
                        values[i][j++] = String.valueOf(selectedproducts.get(key));
                        values[i][j++] = String.valueOf(rst.getFloat("gst"));
                        values[i][j++] = String.valueOf(gst[i]);
                        values[i][j++] = "Rs "+String.valueOf(selectedproducts.get(key) * rst.getInt("rate"));
                        //total[i] = 0;
                        j=0;
                    }
                    
                    //gst[i] = (float) (selectedproducts.get(key) * rst.getInt("rate") * rst.getFloat("gst") * 0.01);
                    //total[i] = selectedproducts.get(key) * rst.getInt("rate") + gst[i];
                    totalamount = 0;
                    gsttotal = 0;
                    i++;
                } 
            }
            for (float t : total)
                totalamount+=t;
            for(float t : gst)
                gsttotal+=t;
            if(totalamount != 0){
                jtf_total.setText("Rs "+String.valueOf(totalamount));
                jtf_gst.setText("RS "+String.valueOf(gsttotal));
            }
            else{
                jtf_total.setText("");
                jtf_gst.setText("");
            }
        } 
        catch(Exception e){ 
            JOptionPane.showMessageDialog(null,"in getdetails"+ e); 
        }
        return values;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob_event = e.getSource();
        
        if(ob_event.equals(jb_buy)){
            String name = (String) JOptionPane.showInputDialog("Enter name: ");
            String mobile = (String) JOptionPane.showInputDialog("Enter mobile: ");
            System.out.println(name.getClass());
            System.out.println(mobile.getClass());
            String[][] cart_info = this.getdetails();
            try{
                String sql;
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                sql = "select * from customer where custname = '" + name +"'and custmob = '" + mobile + "'";
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    name = rs.getString("CUSTNAME");
                    mobile = rs.getString("CUSTMOB");
                }else{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    sql = "insert into customer values(?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,mobile);
                    pst.setString(2,name);
                    pst.setString(3,mobile);
                    pst.executeUpdate();
                    con.setAutoCommit(true);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Verifying Customer" + ex);
            }
            StringBuffer s = new StringBuffer(mobile);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            LocalDateTime now = LocalDateTime.now(); 
            String sb = dtf.format(now);
            s.append(sb);
            String orderID = s.toString();
            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            now = LocalDateTime.now();
            String orderDt = dtf.format(now);
            try{
                String sql;
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                sql = "insert into order_tab values(?,?,?)";
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                pst = con.prepareStatement(sql);
                pst.setString(1,orderID);
                pst.setString(2,orderDt);
                pst.setString(3,mobile);
                pst.executeUpdate();
                con.setAutoCommit(true);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"OrderID Insertion");
            }
            for(String[] x : cart_info){
                float f = Float.parseFloat(x[5].substring(3));
                try{
                    String sql;
                    PreparedStatement pst;
                    Connection con;
                    ResultSet rs;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    sql = "insert into order_det values(?,?,?,?,?)";
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                    pst = con.prepareStatement(sql);
                    pst.setString(1,x[0]);
                    pst.setString(2,x[0]);
                    pst.setInt(3,Integer.parseInt(x[2]));
                    pst.setFloat(4,f);
                    pst.setString(5,orderID);
                    pst.executeUpdate();
                    con.setAutoCommit(true);
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,"Order_det Tab" + a);
                }
                System.out.println(x);
            }
            try{
                String sql;
                PreparedStatement pst;
                Connection con;
                ResultSet rs;
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                sql = "insert into invoice values(?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setString(1,orderID);
                pst.setString(2,orderID);
                pst.setString(3,orderDt);
                pst.setFloat(4,totalamount);
                pst.setFloat(5,gsttotal);
                pst.executeUpdate();
                con.setAutoCommit(true);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Invoice Table");
            }
            totalamount = 0;
            gsttotal = 0;
            jtf_gst.setText("");
            jtf_total.setText("");
            
            PDF(orderID,orderDt,name,mobile);
            DefaultTableModel model =(DefaultTableModel) t_cart.getModel();
            model.setRowCount(0);
            cart_info = null;
            
            
            for(String keys:CustomerDisplay.cart.keySet())
                CustomerDisplay.cart.put(keys,0);
            /*for(String key:update_tb.keySet()){ 
                if(update_tb.get(key) > 0){ 
                    try{
                        String sql;
                        PreparedStatement pst;
                        Connection con;
                        ResultSet rs;
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
                        sql = "update stocks set quantity = '"+ update_tb.get(key) +"' where stkid = '" + String.valueOf(key) +"'";
                        pst = con.prepareStatement(sql);
                        //pst.setString(1,String.valueOf());
                        //pst.setString(2,);
                        rs = pst.executeQuery();
                        pst.executeUpdate(sql);
                        con.setAutoCommit(true);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                } 
            }*/
            
        }
    }
}