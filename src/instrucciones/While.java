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
public class While extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override 
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        // Usamos un ciclo infinito y controlamos la salida internamente
        while (true) {
            
            // 1. Evaluar la condición (En el entorno PADRE 'tabla', no en el del while)
            Object condResult = this.condicion.interpretar(arbol, tabla);
            
            if (condResult instanceof Errores) {
                return condResult;
            }

            // 2. Validar que sea booleano
            if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
                return new Errores("Semantico", "La condición del While debe ser Booleana", this.linea, this.col);
            }

            // 3. Si es FALSO, terminamos el ciclo
            if (!(boolean) condResult) {
                break; 
            }

            // 4. Crear el entorno para esta iteración
            tablaSimbolos tablaWhile = new tablaSimbolos(tabla);
            tablaWhile.setNombre("While");

            // 5. Ejecutar instrucciones
            for (Instruccion ins : this.instrucciones) {
                
                Object resultado = ins.interpretar(arbol, tablaWhile);

                // Manejo de Errores
                if (resultado instanceof Errores) {
                    arbol.errores.add((Errores) resultado);
                }

                // Manejo de Flujo: BREAK
                if (resultado instanceof Break) {
                    return null; // Detiene el ciclo While completo
                }

                // Manejo de Flujo: CONTINUE
                if (resultado instanceof Continue) {
                    break; // Detiene el 'for' de instrucciones y salta a la siguiente vuelta del 'while(true)'
                }
            }
        }
        
        return null;
    }
}
