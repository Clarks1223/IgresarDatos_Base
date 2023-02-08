import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class buscar {
    private JPanel panelByA;
    private JButton JBbuscar;
    private JButton JBactualizar;
    private JTextField JTid;
    private JTextField JTnombre;
    private JTextField JTcel;
    private JTextField JTmail;
    private JLabel JLagenda;
    private JLabel JLid;
    private JLabel JLnombre;
    private JLabel JLcelular;
    private JLabel JLemail;
    JFrame venBuscar = new JFrame("buscar");
    ConeccionBD estcon= new ConeccionBD();
    public buscar() {
        JBbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=JTid.getText();
                Connection con;
                try {
                    con = getConnection();
                    String query = "select * from Persona where PerID=" + id + ";";
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    if (rs == null) {
                        JOptionPane.showMessageDialog(null, "La persona no esta en la base");
                    } else {
                    while (rs.next()) {
                        JTnombre.setText(rs.getString(2));
                        JTcel.setText(rs.getString(3));
                        JTmail.setText(rs.getString(4));
                    }
                }con.close();
                }catch (HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
        JBactualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                String id=JTid.getText();
                try{
                    con= getConnection();
                    PreparedStatement pss;
                    pss=con.prepareStatement("update Persona set PerNombre=?, PerCEL=?, PerMail=? where PerID ="+id);
                    pss.setString(1,JTnombre.getText());
                    pss.setString(2,JTcel.getText());
                    pss.setString(3,JTmail.getText());
                    int res = pss.executeUpdate();
                    if(res>0){
                        JOptionPane.showMessageDialog(null, "Persona actulizada");
                    }else{
                        JOptionPane.showMessageDialog(null, "Persona no actualizada");
                    }
                    con.close();
                }catch (HeadlessException |  SQLException f){
                    System.err.println(f);
                }
            }
        });
    }
    public void mostrarVentana(){
        venBuscar.setContentPane(new buscar().panelByA);
        venBuscar.setDefaultCloseOperation(venBuscar.EXIT_ON_CLOSE);
        venBuscar.pack();
        venBuscar.setVisible(true);
        venBuscar.setLocationRelativeTo(null);
        venBuscar.setResizable(false);
    }
    /*Coneccion con la base de datos*/
    public static Connection getConnection(){
        Connection con = null;
        String base = "base1"; /*nombre de la base de datos*/
        String url = "jdbc:mysql://localhost:3306/"+base;//direccion, puerto y nombre de la base
        String user= "root";
        String password = "3264";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException | SQLException e){
            System.err.println(e);
        }
        return con;
    }
}