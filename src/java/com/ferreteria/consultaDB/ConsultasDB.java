/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ferreteria.consultaDB;

import com.ferreteria.entidad.FormaPago;
import com.ferreteria.entidad.Producto;
import com.ferreteria.entidad.Proveedores;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Diegu
 */
public class ConsultasDB {
    /*
     * Este metodo se encarga de consultar si el usuario existe o no
     * Devolviendo ya sea el rol o indicando a traves de un codigo que no se pudo consultar a la BD.
     * Diego.
     */

//    public String Consultausuario(String usuario, String password) {
//        String respuesta = "9999";
//        Connection con = null;
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String url = "jdbc:sqlserver://serverdb;databaseName=FERRETERIA;user=sa;password=12345;";
//            con = DriverManager.getConnection(url);
//            st = con.prepareStatement("SELECT Rol_usuario FROM Usuario where Cod_usuario=? and Contrasenia=?;");
//            st.setString(1, quitaNulo(usuario));
//            st.setString(2, quitaNulo(password));
//            rs = st.executeQuery();
//            while (rs.next()) {
//                respuesta = quitaNulo(rs.getString(1));
//            }
//
//        } catch (SQLException ex) {
//            respuesta = "9999";
//            ex.printStackTrace();
//
//        } catch (Exception ex) {
//            respuesta = "9999";
//            ex.printStackTrace();
//        } finally {
//
//            if (st != null) {
//                try {
//                    st.close();
//                } catch (SQLException ex) {
//
//                }
//                st = null;
//            }
//
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException ex) {
//
//                }
//                rs = null;
//            }
//
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException ex) {
//
//                }
//                con = null;
//            }
//
//        }
//
//        return respuesta;
//
//    }
  public String Consultausuario(String usuario, String password) {
        String respuesta = "9999";
         Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement("SELECT Rol_usuario FROM Usuario where Cod_usuario=? and Contrasenia=?");
            stmt.setString(1, quitaNulo(usuario));
            stmt.setString(2, quitaNulo(password));
            rs = stmt.executeQuery();
            while (rs.next()) {
              respuesta = quitaNulo(rs.getString(1));
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return respuesta;
    }
    /*
     *Este metodo devolvera un lista de productos en base a su categoria.
     *Con el objetivo que se adapte al menu.
     *@Diego.
     */
    public List<Producto> Listadoproduct(String categoria) {
        List<Producto> respuesta = new LinkedList<Producto>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT P.Cod_producto,DET.Existencia,DET.Precio_producto,DET.Descripcion \n"
                    + "FROM Producto P INNER JOIN Detalle_producto DET ON DET.Cod_producto=P.Cod_producto WHERE DET.categoria= ? ;");
            st.setString(1, quitaNulo(categoria));
            rs = st.executeQuery();
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setCodigo(quitaNulo(rs.getString(1)));
                prod.setExistencia(quitaNulo(rs.getString(2)));
                prod.setPrecio("Q." + quitaNulo(rs.getString(3)));
                prod.setDescripcion(quitaNulo(rs.getString(4)));
                respuesta.add(prod);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     * En este metodo se van a traer las formas de pago.
     * @Diego
     */
    public List<FormaPago> listapago() {
        List<FormaPago> respuesta = new LinkedList<FormaPago>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("select Cod_pago, Nombre from Forma_pago;");
            rs = st.executeQuery();
            while (rs.next()) {
                FormaPago prod = new FormaPago();
                prod.setCodpago(quitaNulo(rs.getString(1)));
                prod.setNompago(quitaNulo(rs.getString(2)));
                respuesta.add(prod);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     *Metodo utilizado para registrar las ordenes de venta;
     *@Diego
     */
    public String Registraordenventa(String preciototal, String usuario) {
        String respuesta = "9999";
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO Orden_venta (fecha_orden,Cod_usuario,Total_venta) values(GETDATE(),?,?)");
            st.setString(1, quitaNulo(usuario));
            st.setString(2, quitaNulo(preciototal));
            int res = st.executeUpdate();
            if (res > 0) {
                respuesta = "0000";
            }

        } catch (SQLException ex) {
            respuesta = "9999";
            ex.printStackTrace();

        } catch (Exception ex) {
            respuesta = "9999";
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     *Este metodo se encarga de grabar el detalle de la venta.
     *@Diego
     */
    public String Registraordenventadetalle(String codventa, String codproducto,
            String codformapago, String cantproduct, String precpor) {
        String respuesta = "9999";
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO Detalle_venta values (?, ?, ? ,?,?);");
            st.setString(1, quitaNulo(codventa));
            st.setString(2, quitaNulo(codproducto));
            st.setString(3, quitaNulo(codformapago));
            st.setString(4, quitaNulo(cantproduct));
            st.setString(5, quitaNulo(precpor));

            int res = st.executeUpdate();
            if (res > 0) {
                respuesta = "0000";
            }

        } catch (SQLException ex) {
            respuesta = "9999";
            ex.printStackTrace();

        } catch (Exception ex) {
            respuesta = "9999";
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     *Ete metodo va a capturar el codigo de la venta.
     * @Diego
     */
    public String ConsultaOrdeVenta() {
        String respuesta = "9999";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT MAX(Cod_orden_venta) as codigo FROM Orden_venta");
            rs = st.executeQuery();
            while (rs.next()) {
                respuesta = quitaNulo(rs.getString(1));
            }

        } catch (SQLException ex) {
            respuesta = "9999";
            ex.printStackTrace();

        } catch (Exception ex) {
            respuesta = "9999";
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     *Este metodo va a traer el listado de productos globales.
     * @Diego
     */
    public List<Producto> Productoinvet() {
        List<Producto> respuesta = new LinkedList<Producto>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT P.Cod_producto,DET.Existencia,DET.Descripcion,Det.Precio_producto \n"
                    + "FROM Producto P INNER JOIN Detalle_producto DET ON DET.Cod_producto=P.Cod_producto;");
            rs = st.executeQuery();
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setCodigo(quitaNulo(rs.getString(1)));
                prod.setExistencia(quitaNulo(rs.getString(2)));
                prod.setDescripcion(quitaNulo(rs.getString(3)));
                if (Integer.parseInt(rs.getString(2)) <= 5) {
                    prod.setStatus("Bajo");
                } else if (Integer.parseInt(rs.getString(2)) > 5 && Integer.parseInt(rs.getString(2)) <= 60) {
                    prod.setStatus("Medio");
                } else if (Integer.parseInt(rs.getString(2)) > 60) {
                    prod.setStatus("Alto");
                }
                prod.setPrecio(quitaNulo("Q." + rs.getString(4)));
                respuesta.add(prod);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }
    /*
     *Este metodo traera un listado de proveedores.
     *@Diego
     */

    public List<Proveedores> listadoproveddor() {
        List<Proveedores> respuesta = new LinkedList<Proveedores>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("SELECT Cod_proveedor,Nombre,telefono FROM Proveedor;");
            rs = st.executeQuery();
            while (rs.next()) {
                Proveedores prod = new Proveedores();
                prod.setCodigoproveedor(quitaNulo(rs.getString(1)));
                prod.setNombreproveedor(quitaNulo(rs.getString(2)));
                prod.setTelefonoproveedor(quitaNulo(rs.getString(3)));
                respuesta.add(prod);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     * Este metodo va a registrar las ordenes de compra
     *@Diego
     */
    public String Regisordencompr(String ordencom, String usuario, String fecha, String totcompra) {
        String respuesta = "9999";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO Orden_Compra values (?,?,?,?);");
            st.setString(1, quitaNulo(ordencom));
            st.setString(2, quitaNulo(usuario));
            st.setString(3, quitaNulo(fecha));
            st.setString(4, quitaNulo(totcompra));
            int res = st.executeUpdate();
            if (res > 0) {
                respuesta = "0000";
            }

        } catch (SQLException ex) {
            respuesta = "9999";
            ex.printStackTrace();

        } catch (Exception ex) {
            respuesta = "9999";
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }
    /*
     * En este metodo se va a guardar el detalle de la compra por producto.
     * @Diego
     */

    public String Regisordencomprdetalle(String codcompra, String codproveedor, String codpago, String codproduc, String cantproduct) {
        String respuesta = "9999";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("jdbc/pool");
            con = dataSource.getConnection();
            st = con.prepareStatement("INSERT INTO Detalle_compra values(?,?,?,?,?);");
            st.setString(1, quitaNulo(codcompra));
            st.setString(2, quitaNulo(codproveedor));
            st.setString(3, quitaNulo(codpago));
            st.setString(4, quitaNulo(codproduc));
            st.setString(5, quitaNulo(cantproduct));
            int res = st.executeUpdate();
            if (res > 0) {
                respuesta = "0000";
            }

        } catch (SQLException ex) {
            respuesta = "9999";
            ex.printStackTrace();

        } catch (Exception ex) {
            respuesta = "9999";
            ex.printStackTrace();
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {

                }
                st = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }
                rs = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {

                }
                con = null;
            }

        }

        return respuesta;

    }

    /*
     *Este metodo se encarga de tratar los espacios en blanco.
     */
    public String quitaNulo(String var) {
        String res = "";
        if (var != null && var.trim().length() > 0) {
            res = var.trim();
        } else {
            res = "";
        }
        return res;
    }
}
