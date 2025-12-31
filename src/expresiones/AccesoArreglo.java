package expresiones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

public class AccesoArreglo extends Instruccion {
    private String id;
    private Instruccion indice1;
    private Instruccion indice2; 

    public AccesoArreglo(String id, Instruccion indice1, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = null;
    }

    public AccesoArreglo(String id, Instruccion indice1, Instruccion indice2, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo sim = tabla.getVariable(this.id);
        if (sim == null) return new Errores("SEMANTICO", "La variable '" + id + "' no existe", this.linea, this.col);

        Object valorSimbolo = sim.getValor();
        
        // Interpretar el índice
        Object valIndex = this.indice1.interpretar(arbol, tabla);
        if (valIndex instanceof Errores) return valIndex;
        int idx = (int) valIndex;

        this.tipo.setTipo(sim.getTipo().getTipo());

        // --- CASO 1: ARREGLO (Vector) ---
        if (valorSimbolo instanceof Object[]) {
            Object[] arreglo = (Object[]) valorSimbolo;
            if (idx < 0 || idx >= arreglo.length) return new Errores("SEMANTICO", "Indice fuera de límites: " + idx, this.linea, this.col);
            
            // Acceso 1D
            if (this.indice2 == null) return arreglo[idx];
            
            // Acceso 2D
            Object filaObj = arreglo[idx];
            if (filaObj instanceof Object[]) {
                Object valIndex2 = this.indice2.interpretar(arbol, tabla);
                if (valIndex2 instanceof Errores) return valIndex2;
                int idx2 = (int) valIndex2;
                return ((Object[]) filaObj)[idx2];
            }
        }
        
        // --- CASO 2: LISTA DINÁMICA (LinkedList) ---
        else if (valorSimbolo instanceof LinkedList) {
            LinkedList<Object> lista = (LinkedList<Object>) valorSimbolo;
            if (idx < 0 || idx >= lista.size()) return new Errores("SEMANTICO", "Indice de lista fuera de límites: " + idx, this.linea, this.col);
            return lista.get(idx);
        }

        return new Errores("SEMANTICO", "La variable '" + id + "' no es un arreglo ni una lista", this.linea, this.col);
    }
}