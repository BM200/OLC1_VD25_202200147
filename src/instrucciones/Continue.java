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

/**
 *
 * @author balam
 */
public class Continue extends Instruccion {
     public Continue(int linea, int col) {
        // El tipo de un continue es irrelevante, usamos VOID
        super(new Tipo(tipoDato.VOID), linea, col);
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // IMPORTANTE: Retornamos 'this' (el objeto mismo) para que
        // el ciclo sepa que se ejecutó un continue.
        return this; 
    }
    
}
