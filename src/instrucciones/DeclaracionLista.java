/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import Simbolo.*;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class DeclaracionLista extends Instruccion {
    private String id;
    private Tipo tipoLista; // El tipo de dato que guarda (INT, STRING...)

    public DeclaracionLista(String id, Tipo tipoLista, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.tipoLista = tipoLista;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Crear la Lista Nativa de Java
        // Usamos LinkedList<Object> para guardar los valores
        LinkedList<Object> listaNueva = new LinkedList<>();
        
        // 2. Crear el Símbolo
        // El valor del símbolo será la LinkedList
        Simbolo sim = new Simbolo(this.tipoLista, this.id, listaNueva, this.linea, this.col);
        
        // 3. Guardar en la tabla
        boolean creacion = tabla.setVariables(sim);
        if (!creacion) {
            return new Errores("SEMANTICO", "La lista '" + this.id + "' ya existe.", this.linea, this.col);
        }
        
        // Agregar al reporte
        arbol.simbolosReporte.add(sim);
        
        return null;
    }
}
