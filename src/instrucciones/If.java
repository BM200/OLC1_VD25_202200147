/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class If extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instruccioneselseif;
    private LinkedList<Instruccion> instruccioneselse;

    // Constructor Simple (IF)
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones,  int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }
    
    // Constructor IF-ELSE
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccioneselse,int linea, int col){
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
        this.instruccioneselse = instruccioneselse;  
    }
    
    // Constructor IF-ELSEIF-ELSE
    public If(Instruccion expresion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccioneselseif,LinkedList<Instruccion> instruccioneselse,int linea, int col){
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
        this.instruccioneselseif = instruccioneselseif;
        this.instruccioneselse = instruccioneselse;  
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        var condicion = this.expresion.interpretar(arbol, tabla);
        
        
        
        if (condicion instanceof Errores){
            return condicion;
        }
        
        // Validar booleano
        if(this.expresion.tipo.getTipo() != tipoDato.BOOLEANO){
            return new Errores("Semantico", "La condición del if debe ser booleana", this.linea, this.col);
        }

        boolean ejecutarIf = (boolean) condicion;

        // --- BLOQUE IF ---
        if(ejecutarIf){
            var nuevaTabla = new tablaSimbolos(tabla);
            nuevaTabla.setNombre("If");
            
            for (var inst: instrucciones){
                var res = inst.interpretar(arbol, nuevaTabla);
                
                // ERROR CRÍTICO CORREGIDO:
                // Si encontramos un Error, Break o Continue, debemos retornarlo inmediatamente
                // para que el ciclo padre (While/For) se entere.
                if (res instanceof Errores || res instanceof Break || res instanceof Continue){
                    return res;
                }
            }
            return true; // Retornamos true para indicar que SÍ se ejecutó un bloque (para los else-if)
        }

        // --- BLOQUE ELSE-IF ---
        if (instruccioneselseif != null){
            for (Instruccion elseif: instruccioneselseif){
                // Interpretamos el If secundario
                var res  = elseif.interpretar(arbol, tabla);
                
                // Si el ElseIf retornó un Break/Continue/Error, lo subimos
                if (res instanceof Errores || res instanceof Break || res instanceof Continue){
                    return res;
                }
                
                // Si retornó true (Booleano), significa que ese ElseIf se ejecutó correctamente.
                // Por lo tanto, ya no evaluamos los siguientes ni el Else.
                if (res instanceof Boolean && (Boolean) res){
                    return true;
                }
            }
        }
        
        // --- BLOQUE ELSE ---
        if (instruccioneselse != null){
            var nuevaTabla = new tablaSimbolos(tabla);
            nuevaTabla.setNombre("Else");
            
            for(var instElse: instruccioneselse){
                var res = instElse.interpretar(arbol, nuevaTabla);
                
                // ERROR CRÍTICO CORREGIDO AQUÍ TAMBIÉN:
                if (res instanceof Errores || res instanceof Break || res instanceof Continue){
                    return res;
                }
            }
            return true;
        }
        
        return false; // Nadie se ejecutó
    }
}
