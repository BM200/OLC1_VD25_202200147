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
public class Find  extends Instruccion {
    private String id;
    private Instruccion valorABuscar;

    public Find(String id, Instruccion valorABuscar, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.id = id;
        this.valorABuscar = valorABuscar;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object busqueda = this.valorABuscar.interpretar(arbol, tabla);
        if (busqueda instanceof Errores) return busqueda;

        Simbolo sim = tabla.getVariable(this.id);
        if (sim == null) return new Errores("SEMANTICO", "Variable '" + id + "' no existe", this.linea, this.col);

        Object coleccion = sim.getValor();
        this.tipo.setTipo(tipoDato.BOOLEANO);

        // Buscar en Arreglo
        if (coleccion instanceof Object[]) {
            for (Object obj : (Object[]) coleccion) {
                if (obj.equals(busqueda)) return true;
            }
            return false;
        }
        // Buscar en Lista
        else if (coleccion instanceof LinkedList) {
            return ((LinkedList<?>) coleccion).contains(busqueda);
        }

        return new Errores("SEMANTICO", ".find() solo funciona en Vectores o Listas", this.linea, this.col);
    }
}
