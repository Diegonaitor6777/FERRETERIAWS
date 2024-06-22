/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ferreteria.servicio;

import com.ferreteria.consultaDB.ConsultasDB;
import com.ferreteria.entidad.FormaPago;
import com.ferreteria.entidad.Producto;
import com.ferreteria.entidad.Proveedores;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Diego
 */
@WebService(serviceName = "WSFerreteria")
public class WSFerreteria {

   /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "ingreso")
    public String ingreso(@WebParam(name = "usuario") String usuario, @WebParam(name = "pass") String pass) {
        ConsultasDB con = new ConsultasDB();
        return con.Consultausuario(usuario, pass);
    }

    @WebMethod(operationName = "listaProductos")
    public List<Producto> listaProductos(@WebParam(name = "categoria") String categoria) {
        ConsultasDB con = new ConsultasDB();
        return con.Listadoproduct(categoria);
    }

    @WebMethod(operationName = "formapagos")
    public List<FormaPago> formapagos() {
        ConsultasDB con = new ConsultasDB();
        return con.listapago();
    }

    @WebMethod(operationName = "Agregaventa")
    public String AgregaVenta(@WebParam(name = "totalprecio") String totalprecio, @WebParam(name = "usuario") String usuario,
            @WebParam(name = "producto") List<Producto> listaproduct, @WebParam(name = "formapago") String formapago) {
        String respuesta = "9999";
        String codlocal = "";
        ConsultasDB consul = new ConsultasDB();
//        Connection con = null;
        try {
//            con = new Conexion().getConexion();
//            con.setAutoCommit(false);

            respuesta = consul.Registraordenventa(totalprecio, usuario);
            System.out.println("registro el usuario es " + respuesta);
            if (respuesta.trim().equals("0000")) {
                codlocal = consul.ConsultaOrdeVenta();
                System.out.println("obtuvo la consulta con exito " + codlocal);
                if (!codlocal.trim().equals("9999")) {
                    for (Producto prod : listaproduct) {
                        respuesta = consul.Registraordenventadetalle(codlocal, prod.getCodigo(), formapago, prod.getCantcompr(), prod.getSubtotl().replace("Q.", ""));
                    }
                    System.out.println("registroooo");
                    if (respuesta.trim().equals("9999")) {
                        respuesta = "9999";
                        //con.rollback();
                    } else {
                        respuesta = "0000";
                    }

                } else {
                    respuesta = "8888";
                }

            } else {
                respuesta = "9999";
                //   con.rollback();
            }

            // con.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return respuesta;
    }

    @WebMethod(operationName = "Productoinventario")
    public List<Producto> Productoinventario() {
        ConsultasDB con = new ConsultasDB();
        return con.Productoinvet();
    }

    @WebMethod(operationName = "Listaproveedor")
    public List<Proveedores> Listaproveedor() {
        ConsultasDB con = new ConsultasDB();
        return con.listadoproveddor();
    }

    @WebMethod(operationName = "Ordencompr")
    public String Ordencompr(@WebParam(name = "codorden") String orden, @WebParam(name = "codusuario") String usuario, @WebParam(name = "fecha") String fechs,
            @WebParam(name = "totpago") String totpago, @WebParam(name = "codproveedor") String codproveedor, @WebParam(name = "codpago") String codpago,
            @WebParam(name = "tipproduc") List<Producto> tipproduc, @WebParam(name = "cantcompr") String cantcompr) {
        String respuesta = "9999";
        ConsultasDB con = new ConsultasDB();
        try {

            respuesta = con.Regisordencompr(orden, usuario, fechs, totpago);

            if (respuesta.trim().equals("0000")) {
                for (Producto procs : tipproduc) {
                    respuesta = con.Regisordencomprdetalle(orden, codproveedor, codpago, procs.getCodigo(), procs.getCantcompr());
                    respuesta = "0000";
                }

            } else {
                respuesta = "9999";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return respuesta ;
    }

}
