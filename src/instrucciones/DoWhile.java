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
public class DoWhile extends Instruccion {
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public DoWhile(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        boolean condicionCumplida = false;

        do {
            // 1. Crear entorno para esta iteración (Scope del Do-While)
            tablaSimbolos tablaDoWhile = new tablaSimbolos(tabla);
            tablaDoWhile.setNombre("DO_WHILE_SCOPE");

            // 2. Ejecutar instrucciones
            for (Instruccion ins : this.instrucciones) {
                Object resultado = ins.interpretar(arbol, tablaDoWhile);

                if (resultado instanceof Errores) {
                    arbol.errores.add((Errores) resultado);
                }

                // Manejo de Break: Rompe el ciclo Java y retorna null
                if (resultado instanceof Break) {
                    return null; 
                }

                // Manejo de Continue: Salta al paso de evaluación de condición
                if (resultado instanceof Continue) {
                    break; 
                }
            }

            // 3. Evaluar la condición (Al final del ciclo)
            //    Se evalúa en el entorno PADRE ('tabla'), igual que en el While
            Object condResult = this.condicion.interpretar(arbol, tabla);

            if (condResult instanceof Errores) {
                return condResult;
            }

            if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
                return new Errores("Semantico", "La condición del Do-While debe ser Booleana", this.linea, this.col);
            }

            condicionCumplida = (boolean) condResult;

        } while (condicionCumplida);

        return null;
    }
    
}
