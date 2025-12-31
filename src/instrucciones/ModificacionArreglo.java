package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
/**
 *
 * @author balam
 */
public class ModificacionArreglo extends Instruccion {
    private String id;
    private Instruccion indice1;
    private Instruccion indice2;
    private Instruccion valor;

    public ModificacionArreglo(String id, Instruccion indice1, Instruccion valor, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.valor = valor;
        this.indice2 = null;
    }
    
    public ModificacionArreglo(String id, Instruccion indice1, Instruccion indice2, Instruccion valor, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object nuevoValor = this.valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) return nuevoValor;

        Simbolo sim = tabla.getVariable(this.id);
        if (sim == null) return new Errores("SEMANTICO", "Variable no existe", this.linea, this.col);
        
        Object contenedor = sim.getValor();
        int idx = (int) this.indice1.interpretar(arbol, tabla);

        // --- CASO 1: ARREGLO ---
        if (contenedor instanceof Object[]) {
            Object[] arreglo = (Object[]) contenedor;
            if (indice2 == null) {
                if (idx < 0 || idx >= arreglo.length) return new Errores("SEMANTICO", "Indice fuera de rango", this.linea, this.col);
                arreglo[idx] = nuevoValor;
            } else {
                // Lógica 2D...
            }
        }
        // --- CASO 2: LISTA DINÁMICA ---
        else if (contenedor instanceof LinkedList) {
            LinkedList<Object> lista = (LinkedList<Object>) contenedor;
            if (idx < 0 || idx >= lista.size()) return new Errores("SEMANTICO", "Indice fuera de rango en lista", this.linea, this.col);
            lista.set(idx, nuevoValor);
        } 
        else {
            return new Errores("SEMANTICO", "No es una lista ni un arreglo", this.linea, this.col);
        }

        return null;
    }
}