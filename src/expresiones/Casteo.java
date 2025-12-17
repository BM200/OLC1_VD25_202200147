/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class Casteo extends Instruccion{
    private Tipo tipoDestino;
    private Instruccion expresion;

    public Casteo(Tipo tipoDestino, Instruccion expresion, int linea, int col) {
        super(tipoDestino, linea, col);
        this.tipoDestino = tipoDestino;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Interpretar la expresión que queremos convertir
        Object valor = this.expresion.interpretar(arbol, tabla);
        
        // Si hay error previo, lo propagamos
        if (valor instanceof Errores) {
            return valor;
        }

        var tipoOrigen = this.expresion.tipo.getTipo();
        
        // 2. Realizar el casteo según el Tipo Destino solicitado
        
        // --- DESTINO: ENTERO (int) ---
        if (this.tipoDestino.getTipo() == tipoDato.ENTERO) {
            
            if (tipoOrigen == tipoDato.DECIMAL) {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int) ((double) valor); // Truncar decimales
            }
            
            if (tipoOrigen == tipoDato.CARACTER) {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int) ((char) valor); // Char a ASCII
            }
            
            if (tipoOrigen == tipoDato.ENTERO) {
                return (int) valor; // Redundante pero válido
            }
        }
        
        // --- DESTINO: DECIMAL (double) ---
        else if (this.tipoDestino.getTipo() == tipoDato.DECIMAL) {
            
            if (tipoOrigen == tipoDato.ENTERO) {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) ((int) valor);
            }
            
            if (tipoOrigen == tipoDato.CARACTER) {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) ((char) valor);
            }
            
            if (tipoOrigen == tipoDato.DECIMAL) {
                return (double) valor;
            }
        }
        
        // --- DESTINO: CARACTER (char) ---
        else if (this.tipoDestino.getTipo() == tipoDato.CARACTER) {
            
            if (tipoOrigen == tipoDato.ENTERO) {
                this.tipo.setTipo(tipoDato.CARACTER);
                // Convertir código ASCII a Char
                int val = (int) valor;
                return (char) val;
            }
        }
        
        // --- DESTINO: CADENA (string) ---
        else if (this.tipoDestino.getTipo() == tipoDato.CADENA) {
            
            if (tipoOrigen == tipoDato.ENTERO || tipoOrigen == tipoDato.DECIMAL) {
                this.tipo.setTipo(tipoDato.CADENA);
                return valor.toString();
            }
        }

        // Si llega aquí, es un casteo no permitido
        return new Errores("SEMANTICO", 
                "No es posible castear de " + tipoOrigen + " a " + this.tipoDestino.getTipo(), 
                this.linea, this.col);
    }
}