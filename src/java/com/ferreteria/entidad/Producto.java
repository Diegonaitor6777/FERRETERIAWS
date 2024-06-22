/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ferreteria.entidad;

/**
 *
 * @author Diegu
 */
public class Producto {

    private String codigo;
    private String existencia;
    private String precio;
    private String descripcion;
    private String productodetalle;
    private String cantcompr;
    private String subtotl;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    

    public String getSubtotl() {
        return subtotl;
    }

    public void setSubtotl(String subtotl) {
        this.subtotl = subtotl;
    }
    public String getCantcompr() {
        return cantcompr;
    }

    public void setCantcompr(String cantcompr) {
        this.cantcompr = cantcompr;
    }

    public String getProductodetalle() {
        return productodetalle;
    }

    public void setProductodetalle(String productodetalle) {
        this.productodetalle = productodetalle;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
