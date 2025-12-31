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
public class Append extends Instruccion {
    private String id;
    private Instruccion valor;

    public Append(String id, Instruccion valor, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Buscar la lista
        Simbolo sim = tabla.getVariable(this.id);
        if (sim == null) {
            return new Errores("SEMANTICO", "La lista '" + id + "' no existe", this.linea, this.col);
        }

        // 2. Obtener el valor (LinkedList)
        Object valorSimbolo = sim.getValor();
        if (!(valorSimbolo instanceof LinkedList)) {
            return new Errores("SEMANTICO", "La variable '" + id + "' no es una lista", this.linea, this.col);
        }

        LinkedList<Object> lista = (LinkedList<Object>) valorSimbolo;

        // 3. Interpretar el valor a agregar
        Object valToAdd = this.valor.interpretar(arbol, tabla);
        if (valToAdd instanceof Errores) return valToAdd;

        // 4. Validar Tipos (El valor debe coincidir con el tipo de la lista)
        if (this.valor.tipo.getTipo() != sim.getTipo().getTipo()) {
             // Caso especial int -> double
             if (sim.getTipo().getTipo() == tipoDato.DECIMAL && this.valor.tipo.getTipo() == tipoDato.ENTERO) {
                 valToAdd = Double.parseDouble(valToAdd.toString());
             } else {
                 return new Errores("SEMANTICO", "Tipos incompatibles. No se puede agregar " + this.valor.tipo.getTipo() + " a una lista de " + sim.getTipo().getTipo(), this.linea, this.col);
             }
        }

        // 5. Agregar
        lista.add(valToAdd);
        
        return null;
    }
}
