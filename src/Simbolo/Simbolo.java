/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simbolo;

/**
 *
 * @author balam
 */
public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;
    private String entorno;
    private int linea;
    private int columna;
    

    public Simbolo(Tipo tipo, String id, Object valor, int linea, int columna) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.entorno = "Global";
        
    }
    //--Getter y Setter nuevo
    public String getEntorno() { return (String) entorno; }
    
    public void setEntorno(String entorno) { this.entorno = entorno; }
    
   
    
    

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    public int getLinea() { return linea; }
    
    public int getColumna() { return columna; }
    
    
}
