/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class ExpresionArreglo extends Instruccion {
    private LinkedList<Instruccion> contenido;

    public ExpresionArreglo(LinkedList<Instruccion> contenido, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.contenido = contenido;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // Creamos un arreglo de objetos del tamaño de la lista
        Object[] arreglo = new Object[this.contenido.size()];
        
        int i = 0;
        for (Instruccion ins : this.contenido) {
            Object val = ins.interpretar(arbol, tabla);
            if (val instanceof Errores) return val;
            
            arreglo[i] = val;
            i++;
        }
        
        // Retornamos el arreglo (ej: ['A', 'B', 'C'])
        return arreglo;
    }
}
