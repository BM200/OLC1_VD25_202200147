/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.Arbol;
import Simbolo.Tipo;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;
import abstracto.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class Start extends Instruccion {
    private String id;
    private LinkedList<Instruccion> parametros;
   
    public Start(String id, LinkedList<Instruccion> parametros, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.parametros = parametros;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // En lugar de reescribir la lógica, delegamos la ejecución a la clase Llamada.
        // Llamada ya sabe buscar la función, validar parámetros, crear el entorno y ejecutar.
        
        Llamada llamadaStart = new Llamada(this.id, this.parametros, this.linea, this.col);
        
        // Ejecutamos la llamada
        return llamadaStart.interpretar(arbol, tabla);
    }
}