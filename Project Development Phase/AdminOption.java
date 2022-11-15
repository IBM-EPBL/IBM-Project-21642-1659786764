package inventorymanagement;
import java.awt.Color; 
import java.awt.event.ActionEvent; 
import javax.swing.BorderFactory; 
import javax.swing.JButton; 
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border; 
 
public class AdminOption extends JPanel{ 
    JButton invoicetrackbutton,displaybutton,profit,addstocksButton; 
    Border b = BorderFactory.createLineBorder(Color.black); 
    static JPanel jp_AdminAddStock,jp_AdminMain,jp_AdminDisplay,jp_AdminInvoice;
    public static void chart(Object res)throws Exception{
        BarChartExample example=new BarChartExample("Bar Chart ");
        example.result = res;
        example.setSize(800, 400);  
        example.setLocationRelativeTo(null); 
        example.setVisible(true);  
    }
    public AdminOption() { 
        setLayout(null);
        setSize(100,520); 
        setLocation(0,50); 
        setBorder(b); 
        
        
        addstocksButton = new JButton("STOCKS"); 
        addstocksButton.setBounds(0,50,100, 50); 
        addstocksButton.setOpaque(true); 
        addstocksButton.setBackground(Color.lightGray); 
         
        invoicetrackbutton = new JButton("TRACK"); 
        invoicetrackbutton.setBounds(0,150,100, 50); 
        invoicetrackbutton.setBackground(Color.lightGray); 
        invoicetrackbutton.setOpaque(true); 
         
        displaybutton = new JButton("DISPLAY"); 
        displaybutton.setBounds(0,0,100, 50); 
        displaybutton.setBackground(Color.lightGray); 
        displaybutton.setOpaque(true); 
         
        profit = new JButton("PROFIT"); 
        profit.setBounds(0,100,100, 50); 
        profit.setBackground(Color.lightGray); 
        profit.setOpaque(true); 
         
        add(invoicetrackbutton); 
        add(displaybutton); 
        add(profit); 
        add(addstocksButton); 
        repaint();
        revalidate();
        
        profit.addActionListener((ActionEvent ae)->{
            Object result = JOptionPane.showInputDialog("Time: ");
            if(result == null || (result!= null && ("".equals(result))))   {
                
            }else{
               try{
                    chart(result);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,e);
                } 
            }
        });
        
        invoicetrackbutton.addActionListener((ActionEvent ae)->{ 
             jp_AdminInvoice = new AdminInvoice();
             jp_AdminMain.add(jp_AdminInvoice);
             jp_AdminAddStock.setVisible(false);
             jp_AdminDisplay.setVisible(false);
             jp_AdminInvoice.setVisible(true);
        }); 
        displaybutton.addActionListener((ActionEvent ae) -> {
            jp_AdminDisplay = new AdminDisplay();
            jp_AdminMain.add(jp_AdminDisplay);
            jp_AdminAddStock.setVisible(false);
            jp_AdminDisplay.setVisible(true);
            jp_AdminInvoice.setVisible(false);
        }); 
        addstocksButton.addActionListener((ActionEvent ae)->{
//            System.out.println("ADD clicked");
//            jp_AdminMain.add(jp_AdminAddStock);
            jp_AdminAddStock = new AdminAddStock();
            jp_AdminMain.add(jp_AdminAddStock);
            jp_AdminDisplay.setVisible(false);
            jp_AdminAddStock.setVisible(true);
            jp_AdminInvoice.setVisible(false);
        });
    } 
}