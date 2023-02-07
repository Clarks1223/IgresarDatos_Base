import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Formulario {
    /*crear la sentencia sql*/
    PreparedStatement ps;
    private JPanel panel1;
    private JLabel JBienvenido;
    private JLabel JLMID;
    private JLabel JLMNombre;
    private JLabel JLMCelular;
    private JLabel JLMCorreo;
    private JTextField JTFIID;
    private JTextField JTFINombre;
    private JTextField JTFITELF;
    private JTextField JTFIMAIL;
    private JButton JBIngresar;
    private JButton JBBorrar;

    public Formulario() {
        JBBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTFIID.setText("");
                JTFIMAIL.setText("");
                JTFINombre.setText("");
                JTFITELF.setText("");
            }
        });
        JBIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try{
                    con=getConnection();
                    ps =con.prepareStatement("INSERT INTO Persona (PerID, PerNombre, PerCEL, PerMail) Values(?,?,?,?)");
                    ps.setString(1,JTFIID.getText());
                    ps.setString(2,JTFINombre.getText());
                    ps.setString(3,JTFITELF.getText());
                    ps.setString(4,JTFIMAIL.getText());
                    System.out.println(ps); //Imprimo en consola para verificacion
                    int res = ps.executeUpdate();

                    if (res>0){
                        JOptionPane.showMessageDialog(null,"Persona Guardada");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al guardar persona");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f){
                    System.err.println(f);
                }

            }
        });
    }

    public void MostarVentana(){
        JFrame ventana= new JFrame("Formulario");
        ventana.setContentPane(new Formulario().panel1);
        ventana.setDefaultCloseOperation(ventana.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }
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