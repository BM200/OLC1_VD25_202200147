/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simbolo;

import abstracto.Instruccion;
import java.util.LinkedList;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class Arbol {
    private LinkedList<Instruccion> instrucciones;
    private String consolas;
    public LinkedList<Errores> errores;
    
    // --- NUEVA LISTA PARA EL REPORTE ---
    public LinkedList<Simbolo> simbolosReporte;
    // -----------------------------------

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consolas = "";
        this.errores = new LinkedList<>();
        
        // Inicializamos la lista
        this.simbolosReporte = new LinkedList<>();

    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

  
    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }

    public String getConsolas() {
        return consolas;
    }

    public void setConsolas(String consolas) {
        this.consolas = consolas;
    }
    
    public void Print(String valor){
        this.consolas += valor + "\n";
    }
    
}
