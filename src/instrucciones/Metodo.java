/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class Metodo extends Instruccion {
    public String id;
    
    // CORRECCIÓN: Agregar <String, Object> para que coincida con Llamada.java
    public LinkedList<HashMap<String, Object>> parametros; 
    
    public LinkedList<Instruccion> instrucciones;
   
    // Actualizar también el constructor
    public Metodo(String id, LinkedList<HashMap<String, Object>> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        // 1. Validar que no exista ya
        if (arbol.getFuncion(this.id) != null) {
            return new Errores("SEMANTICO", "Ya existe una función/método con el nombre: " + this.id, this.linea, this.col);
        }

        // 2. Guardar el método en el árbol (NO EJECUTARLO AÚN)
        arbol.addFuncion(this);
        
        return null;
    }
}