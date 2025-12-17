/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import abstracto.Instruccion;
import Simbolo.*;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class Relacionales extends Instruccion {
    
    private Instruccion cond1;
    private Instruccion cond2;
    private OperadoresRelacionales relacional;

    public Relacionales(Instruccion cond1, Instruccion cond2, OperadoresRelacionales relacional, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.cond1 = cond1;
        this.cond2 = cond2;
        this.relacional = relacional;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Interpretar hijo izquierdo
        var izq = this.cond1.interpretar(arbol, tabla);
        if (izq instanceof Errores) return izq;

        // 2. Interpretar hijo derecho
        var der = this.cond2.interpretar(arbol, tabla);
        if (der instanceof Errores) return der;

        var tipoIzq = this.cond1.tipo.getTipo();
        var tipoDer = this.cond2.tipo.getTipo();

        // Establecemos que el resultado siempre será booleano
        this.tipo.setTipo(tipoDato.BOOLEANO);

        // --- CASO 1: COMPARACIÓN NUMÉRICA (Entero, Decimal, Caracter) ---
        // El PDF permite comparar char con int/double. Tratamos al char como número (ASCII).
        if (esNumero(tipoIzq) && esNumero(tipoDer)) {
            double valorIzq = obtenerValorDouble(izq, tipoIzq);
            double valorDer = obtenerValorDouble(der, tipoDer);

            return switch (relacional) {
                case EQUALS      -> valorIzq == valorDer;
                case DIFERENTE   -> valorIzq != valorDer;
                case MENOR       -> valorIzq < valorDer;
                case MENORIGUAL  -> valorIzq <= valorDer;
                case MAYOR       -> valorIzq > valorDer;
                case MAYORIGUAL  -> valorIzq >= valorDer;
                default -> new Errores("SEMANTICO", "Operador relacional inválido", this.linea, this.col);
            };
        }

        // --- CASO 2: COMPARACIÓN DE CADENAS ---
        // Las cadenas solo soportan == y !=
        if (tipoIzq == tipoDato.CADENA && tipoDer == tipoDato.CADENA) {
            String val1 = izq.toString();
            String val2 = der.toString();

            return switch (relacional) {
                case EQUALS     -> val1.equals(val2); // Comparación exacta
                case DIFERENTE  -> !val1.equals(val2);
                default -> new Errores("SEMANTICO", "Cadenas solo soportan Igualdad y Diferencia", this.linea, this.col);
            };
        }
        
        // --- CASO 3: COMPARACIÓN DE BOOLEANOS ---
        // Los booleanos solo soportan == y !=
        if (tipoIzq == tipoDato.BOOLEANO && tipoDer == tipoDato.BOOLEANO) {
            boolean val1 = (boolean) izq;
            boolean val2 = (boolean) der;
            
            return switch (relacional) {
                case EQUALS     -> val1 == val2;
                case DIFERENTE  -> val1 != val2;
                default -> new Errores("SEMANTICO", "Booleanos solo soportan Igualdad y Diferencia", this.linea, this.col);
            };
        }

        return new Errores("SEMANTICO", "Tipos incompatibles para operación relacional", this.linea, this.col);
    }

    // --- MÉTODOS AUXILIARES ---

    // Verifica si el tipo es numérico o caracter (ya que char se comporta como número)
    private boolean esNumero(tipoDato tipo) {
        return tipo == tipoDato.ENTERO || tipo == tipoDato.DECIMAL || tipo == tipoDato.CARACTER;
    }

    // Convierte cualquier tipo numérico (int, double, char) a double para comparar fácil
    private double obtenerValorDouble(Object valor, tipoDato tipo) {
        if (tipo == tipoDato.ENTERO) return (int) valor;
        if (tipo == tipoDato.DECIMAL) return (double) valor;
        if (tipo == tipoDato.CARACTER) return (char) valor; // Char a ASCII
        return 0.0;
    }
    
}
