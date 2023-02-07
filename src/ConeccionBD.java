import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.sql.*;

public class ConeccionBD {
    PreparedStatement ps;

    public void actualizar(JTextField JTFIID, JTextField JTFINombre, JTextField JTFITELF, JTextField JTFIMAIL) {
        Connection con;
        try {
            con = getConnection();
            ps = con.prepareStatement("Update Persona where PerId = JTDID set PerNombre=, PerCEL, PerMail Values(?,?,?,?)");
            ps.setString(1, JTFIID.getText());
            ps.setString(2, JTFINombre.getText());
            ps.setString(3, JTFITELF.getText());
            ps.setString(4, JTFIMAIL.getText());
            System.out.println(ps); //Imprimo en consola para verificacion
            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Persona Guardada");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar persona");
            }
            con.close();
        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }
    }
    public void buscarNom(JTextField ID){
        Connection con;
        try{
        con=getConnection();
        String query="select * from Persona PerID="+ID+";";
        Statement s= con.createStatement();
        ResultSet rs=s.executeQuery(query);
        con.close();
        }catch (HeadlessException | SQLException f){
            System.err.println(f);
        }
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
