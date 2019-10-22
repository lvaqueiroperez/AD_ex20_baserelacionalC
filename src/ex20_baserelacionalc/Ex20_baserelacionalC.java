package ex20_baserelacionalc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ex20_baserelacionalC {

    /*
     A ventaxa de usar PreparedStatements é que a base de datos pode subir a cache 
     e reusar as 
     sentenzas preparadas co que se executan mais rapido que as sentenzas normais.

     */
    Connection conn;

    public void Conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

        conn = DriverManager.getConnection(url);

    }

    public void Cerrar() throws SQLException {

        conn.close();

    }

    /*
     1- metodo que insira filas  mediante sentenzas preparadas (insireprep) .
     */
    public void insertarFilaPSt(String cod, String desc, int prezo) {

        try {
            PreparedStatement pst = conn.prepareStatement("insert into produtos values(?,?,?)");

            pst.setString(1, cod);
            pst.setString(2, desc);
            pst.setInt(3, prezo);

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Ex20_baserelacionalC.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR INSERTANDO FILA");
        }

    }

    /*
     2- metodo que actualize unha fila  coñecido o seu codigo , mediante sentenzas preparadas (actuprep) . 
     */
    public void actuFilaPSt(String codigo, int prezo) {

        try {
            PreparedStatement pst = conn.prepareStatement("update produtos set prezo = ? where codigo = ?");

            pst.setInt(1, prezo);
            pst.setString(2, codigo);

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Ex20_baserelacionalC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
     3- realizar unha consulta a totalidade mediante resulset con sentenza preparada.
     */
    public void listarPSt() {

        try {
            PreparedStatement pst = conn.prepareStatement("select produtos.* from produtos");

            ResultSet rs = pst.executeQuery();

            System.out.println("Codigo\tDesc\tPrezo");

            while (rs.next()) {

                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Ex20_baserelacionalC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        Ex20_baserelacionalC obj = new Ex20_baserelacionalC();

        try {
            obj.Conexion();

            //obj.insertarFilaPSt("p80", "adioos", 999);
            obj.actuFilaPSt("p2", 543);
            obj.listarPSt();

            obj.Cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(Ex20_baserelacionalC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
