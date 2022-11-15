package inventorymanagement;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class CustomerDisplay extends JPanel implements ActionListener{
    String comumn_array[] = {"ID","ProductName","Rate/unit","Quantity"};
    JTable jt_stocks;
    JLabel jlb_search;
    JTextField jtf_search;
    JButton jb_addtocart,jb_buy;
    SpinnerModel sp_model;
    JSpinner jsp_quantity;
    static JPanel jp_viewcart,jp_customer_main;
    static Hashtable<String,Integer> cart = new Hashtable<>();
    Hashtable<String, Integer> prev = new Hashtable<>();
    static Hashtable<String,Integer> maxquantity = new Hashtable<>();
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    static void getview_panel(JPanel jp_viewcart_panel) {
        jp_viewcart=jp_viewcart_panel;
    }
    static void getcustomer_main(Customer aThis) {
        jp_customer_main=aThis;
    }
    public CustomerDisplay() {
        setLayout(null);
        setSize(740,520);
        setLocation(100,50);
        setBorder(blackline);
        jlb_search = new JLabel("SEARCH");jlb_search.setBounds(240,10,100,25);
        jlb_search.setFont(f2);
        jb_addtocart = new JButton("ADD TO CART");jb_addtocart.setBounds(450,450,150,25);
        jb_addtocart.addActionListener(this);
        jb_addtocart.setFont(f2);
        jb_addtocart.setBackground(Color.lightGray);
        jb_addtocart.addActionListener((ActionEvent ae)->{ 
             
            // to get the value from listener and send back to Hashtable 
            int row =jt_stocks.getSelectedRow();
            int viewrow = jt_stocks.convertRowIndexToModel(row);
            int user_entered = (Integer)jsp_quantity.getValue(); 
            int previous_cart = cart.get(jt_stocks.getModel().getValueAt(viewrow, 0).toString()); 
            String selected_key =jt_stocks.getModel().getValueAt(viewrow, 0).toString() ; 
             
            cart.replace(selected_key,previous_cart+user_entered); 
             
            maxquantity.replace(selected_key, maxquantity.get(selected_key)-user_entered); 
            System.out.println("action cart :"+cart); 
            jt_stocks.setValueAt(maxquantity.get(selected_key).toString(), viewrow,2); 
            jsp_quantity.setValue(0); 
        });
        String values[][] = null;
        int row = 0;
        try{
            PreparedStatement pst;
            Connection con;
            ResultSet rs;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String sql = "select s.stkid,s.stkname,s.quantity,p.rate from stocks s inner join purchase_det p on p.stkid = s.stkid";
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYS AS SYSDBA","cosmos2709");
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                row++;
            }
            rs = pst.executeQuery();
            values = new String[row][4];
            int i=0;
            while(rs.next()){
                int j=0;
                values[i][j++]=rs.getString("stkid");
                values[i][j++]=rs.getString("stkname");
                values[i][j++]=String.valueOf(rs.getFloat("rate"));
                values[i][j++]=String.valueOf(rs.getInt("quantity"));
                i++;
                cart.put(rs.getString("stkid"),0); 
                maxquantity.put(rs.getString("stkid"),rs.getInt("quantity"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        DefaultTableModel model = new DefaultTableModel(values, comumn_array){
            @Override public boolean isCellEditable(int row, int column) 
            { //all cells false return false;
                return false;
            } 
        };
        jt_stocks = new JTable(model);
        jt_stocks.getTableHeader().setReorderingAllowed(false);
        jt_stocks.setRowHeight(jt_stocks.getRowHeight()+20);
//      to remove column from the table        
        TableColumnModel tcm = jt_stocks.getColumnModel(); 
        tcm.removeColumn(tcm.getColumn(0)); 
        sp_model = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1); 
        jsp_quantity = new JSpinner(sp_model); 
        jsp_quantity.setBounds(670,70,50,25); 
        jsp_quantity.setEnabled(false);
//      to select a cell from table  
        jt_stocks.addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseClicked(MouseEvent e) { 
                super.mouseClicked(e);
                int viewrow = jt_stocks.getSelectedRow();
                int row = jt_stocks.convertRowIndexToModel(viewrow);;
                if(Integer.parseInt((String)jt_stocks.getModel().getValueAt(row, 3))>0){ ////3
                    jsp_quantity.setEnabled(true);
                    String value =jt_stocks.getModel().getValueAt(jt_stocks.getSelectedRow(), 0).toString(); 
                    jsp_quantity.setValue(cart.get(value)); 
                }
                else{
                    JOptionPane.showMessageDialog(null, "No stock");
                }
            } 
        }); 
        jsp_quantity.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                int row =jt_stocks.getSelectedRow();
                int viewrow = jt_stocks.convertRowIndexToModel(row);
                if((Integer)jsp_quantity.getValue()>maxquantity.get(jt_stocks.getModel().getValueAt(viewrow,0).toString())){
                    jsp_quantity.setValue(maxquantity.get(jt_stocks.getModel().getValueAt(viewrow,0).toString()));
                    return;
                }
            } 
        });
//      to enable scrollpane for table
        JScrollPane jsp_scroll = new JScrollPane(jt_stocks);
        jsp_scroll.setBounds(10,50,650,220);
        jtf_search = new JTextField();jtf_search.setBounds(310,10,200,25);
//      to filter table        
        jtf_search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                DefaultTableModel model = (DefaultTableModel)jt_stocks.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                jt_stocks.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)"+jtf_search.getText().trim()));
            }
        });
        add(jsp_scroll);
        add(jlb_search);
        add(jtf_search);
        add(jb_addtocart);
        add(jsp_quantity);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}