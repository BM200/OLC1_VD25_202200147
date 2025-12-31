/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class Return extends Instruccion {
    private Instruccion expresion; // La expresión a evaluar (ej: return a + b;)
    public Object valorRetorno;    // Donde guardaremos el resultado final (ej: 15)

    // Constructor para funciones con valor (return 10;)
    public Return(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.valorRetorno = null;
    }
    
    // Constructor para métodos void (return;)
    public Return(int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = null;
        this.valorRetorno = null;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // Si hay una expresión, la interpretamos antes de salir
        if (this.expresion != null) {
            Object valor = this.expresion.interpretar(arbol, tabla);
            
            if (valor instanceof Errores) {
                return valor;
            }
            
            // Guardamos el valor calculado y el tipo
            this.valorRetorno = valor;
            this.tipo.setTipo(this.expresion.tipo.getTipo());
        } else {
            this.valorRetorno = null;
        }
        
        // IMPORTANTE: Retornamos 'this' (el objeto Return) 
        // para que la clase Llamada sepa que debe detener la ejecución.
        return this;
    }

    
    
    
    
    
}
