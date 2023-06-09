package Library;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
public class AddJournals extends JFrame implements ActionListener{
    JLabel l1,l2,l3,l4,l5,l6,l8,l9,l10;
    JButton bt1,bt2;
    JPanel p1,p2;
    Font f,f1,f2;
    JTextField tf1,tf2,tf3,tf4,tf5,tf7,tf8,tf9;
    JList<String> list;
    
    String []coAuth=new String[1024];
    int i=0;
    AddJournals(){
        super("Add Journals");
        
        setLocation(0,0);
        setSize(700,450);
        
        ConnectionClass obj1=new ConnectionClass();
        String query="select username from faculty";
        try {
            Statement stm=obj1.con.createStatement();
            ResultSet rs=stm.executeQuery(query);
            while(rs.next()){
                String coAuthor=rs.getString("username");
                
                
                coAuth[i++]=coAuthor;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // for(int j=0;j<i;j++)
        //     System.out.println(coAuth[j]);
        list=new JList<>(coAuth);
        list.setVisibleRowCount(1);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane jcp = new JScrollPane(list);
        add(jcp);
        list.setFont(new Font("Helvetica", Font.BOLD,20));
        
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String str = "";
                List<String> SelectedList = list.getSelectedValuesList();
                for(String coAuthor: SelectedList)
                    str = str + coAuthor + ",";
                str = str.substring(0, str.length()-1);
                tf3.setText(str);	
                
            }
        });
        
        f=new Font("Arial",Font.BOLD,25);
        f1=new Font("Helvetica",Font.BOLD,20);
        f2=new Font("Tahoma",Font.PLAIN,20);

        l1=new JLabel("Add Journals");
        l2=new JLabel("Name");
        l3=new JLabel("Corresponding Author");
        l4=new JLabel("Co-authors (Internal)");
        l8=new JLabel("Co-authors (External)");
        l5=new JLabel("Page Number");
        l9=new JLabel("Volume Number");
        l10=new JLabel("Issue Number");
        l6=new JLabel("Date (YYYY-MM-DD)");

        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(Color.WHITE);

        tf1=new JTextField();
        tf2=new JTextField();
        tf3=new JTextField();
        tf4=new JTextField();
        tf5=new JTextField();
        tf7=new JTextField();
        tf8=new JTextField();
        tf9=new JTextField();

        bt1=new JButton("Add Journal");
        bt2=new JButton("Cancel");

        bt1.setFont(f2);
        bt2.setFont(f2);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        
        l1.setFont(f);
        l2.setFont(f1);
        l3.setFont(f1);
        l4.setFont(f1);
        l5.setFont(f1);
        l6.setFont(f1);
        jcp.setFont(f1);
        l8.setFont(f1);
        l9.setFont(f1);
        l10.setFont(f1);

        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);
        tf5.setFont(f1);
        tf7.setFont(f1);
        tf8.setFont(f1);
        tf9.setFont(f1);

        p1=new JPanel();
        p1.setLayout(new GridLayout(1,1,10,10));
        p1.add(l1);
        p1.setBackground(Color.DARK_GRAY);

        p2=new JPanel();
        p2.setLayout(new GridLayout(9,2,10,10));
        p2.add(l2);
        p2.add(tf1);
        p2.add(l3);
        p2.add(tf2);
        p2.add(l4);
        p2.add(jcp);
        p2.add(l8);
        p2.add(tf7);
        p2.add(l5);
        p2.add(tf4);
        p2.add(l9);
        p2.add(tf8);
        p2.add(l10);
        p2.add(tf9);
        p2.add(l6);
        p2.add(tf5);
        p2.add(bt1);
        p2.add(bt2);

        setLayout(new BorderLayout(10,10));
        add(p1,"North");
        add(p2,"Center");

    }
    public void actionPerformed(ActionEvent e) {
        
        Pattern p4=Pattern.compile("^(?=\\S).*$");
        String name=tf1.getText();
        Matcher m4=p4.matcher(name);
        if(!m4.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Name cannot be Empty");
            return;}
        Pattern p5=Pattern.compile("^(?=\\S).*$");
        String corrAuth=tf2.getText();
        Matcher m5=p5.matcher(corrAuth);
        if(!m5.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Corresponding Author field cannot be Empty");
            return;}
        String coAuthIn=tf3.getText();
        String coAuthEx=tf7.getText();
        Pattern p=Pattern.compile("[0-9]+");
        String pgno=tf4.getText();
        Matcher m=p.matcher(pgno);
        if(!m.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Please enter valid Page Number");
            return;}
        Pattern p2=Pattern.compile("[0-9]+");
        String volno=tf8.getText();
        Matcher m2=p2.matcher(volno);
        if(!m2.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Please enter valid Volume Number");
            return;}
        Pattern p3=Pattern.compile("[0-9]+");
        String issno=tf9.getText();
        Matcher m3=p3.matcher(issno);
        if(!m3.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Please enter valid Issue Number");
            return;}
        
        Pattern p1=Pattern.compile("^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$" 
        + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
        + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" 
        + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");    
        String date=tf5.getText();
        Matcher m1=p1.matcher(date);
        if(!m1.matches()&&e.getSource()!=bt2){
            JOptionPane.showMessageDialog(null, "Please enter valid Date");
            return;}

        if(e.getSource()==bt1){
            try {
                ConnectionClass obj=new ConnectionClass();
                String s="insert into journal(Name,CorrespondingAuthor,CoAuthorInternal,CoAuthorExternal,PageNumber,VolumeNumber,IssueNumber,Date) values('"+name+"','"+corrAuth+"','"+coAuthIn+"','"+coAuthEx+"','"+pgno+"','"+volno+"','"+issno+"','"+date+"')";
                int aa=obj.stm.executeUpdate(s);
                
                if(aa==1){
                    JOptionPane.showMessageDialog(null, "Your Data Successfully Inserted");
                    this.setVisible(false);
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please fill all details carefully");
                    this.setVisible(true);
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        if(e.getSource()==bt2){
            this.setVisible(false);
        }
        
    }

    public static void main(String[] args) {
        new AddJournals().setVisible(true);
    }
}
