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
public class Remove extends Instruccion {
    private String id;
    private Instruccion indice;

    public Remove(String id, Instruccion indice, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col); // El tipo se define al ejecutar
        this.id = id;
        this.indice = indice;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Buscar lista
        Simbolo sim = tabla.getVariable(this.id);
        if (sim == null) return new Errores("SEMANTICO", "Variable no existe", this.linea, this.col);

        if (!(sim.getValor() instanceof LinkedList)) {
            return new Errores("SEMANTICO", "'" + id + "' no es una lista", this.linea, this.col);
        }

        LinkedList<Object> lista = (LinkedList<Object>) sim.getValor();

        // 2. Interpretar índice
        Object valIndex = this.indice.interpretar(arbol, tabla);
        if (valIndex instanceof Errores) return valIndex;

        if (this.indice.tipo.getTipo() != tipoDato.ENTERO) {
            return new Errores("SEMANTICO", "El índice del remove debe ser entero", this.linea, this.col);
        }

        int idx = (int) valIndex;

        // 3. Validar límites
        if (idx < 0 || idx >= lista.size()) {
            return new Errores("SEMANTICO", "Índice remove fuera de rango: " + idx, this.linea, this.col);
        }

        // 4. Eliminar y Retornar
        Object eliminado = lista.remove(idx);
        
        // Actualizamos el tipo de retorno de esta instrucción al tipo de la lista
        this.tipo.setTipo(sim.getTipo().getTipo());
        
        return eliminado;
    }
}
