package inventorymanagement;

import static inventorymanagement.CustomerDisplay.cart;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class AdminDisplay extends JPanel implements ActionListener{
    String comumn_array[] = {"ID","ProductName","Rate/unit","Quantity"};
    JTable jt_stocks;
    JLabel jlb_search;
    JTextField jtf_search;
    Hashtable<String,Integer> maxquantity = new Hashtable<>();
    Border blackline = BorderFactory.createRaisedSoftBevelBorder();
    Font f2 = new Font(Font.DIALOG,Font.BOLD,15);
    public AdminDisplay() {
        setLayout(null);
        setSize(740,520);
        setLocation(100,50);
        setBorder(blackline);
        jlb_search = new JLabel("SEARCH");jlb_search.setBounds(240,10,100,25);
        jlb_search.setFont(f2);
        String values[][] = null;
//        ************************************************************

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
            System.out.println(e);
        }

//        ************************************************************
        DefaultTableModel model = new DefaultTableModel(values, comumn_array){
            @Override public boolean isCellEditable(int row, int column) 
            { //all cells false return false;
                return false;
            }  
        };
        jt_stocks = new JTable(model);
        jt_stocks.getTableHeader().setReorderingAllowed(false);
        jt_stocks.setRowHeight(jt_stocks.getRowHeight()+20);
//      to enable scrollpane for table
        JScrollPane jsp_scroll = new JScrollPane(jt_stocks);
        jsp_scroll.setBounds(0,50,730,400);
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
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
