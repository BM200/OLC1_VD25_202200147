/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.Arbol;
import Simbolo.Tipo;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;

import instrucciones.Break;
import instrucciones.Continue;

/**
 *
 * @author balam
 */
public class For extends Instruccion {
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Crear un entorno propio para la variable del For (ej: 'i')
        //    Este entorno será padre del entorno de las instrucciones internas.
        var tablaHeader = new tablaSimbolos(tabla);
        tablaHeader.setNombre("FOR_HEADER"); // Opcional, para debug

        // 2. Ejecutar la declaración/asignación inicial (SOLO UNA VEZ)
        //    Ej: var i:int = 0;
        var res1 = this.asignacion.interpretar(arbol, tablaHeader);
        if (res1 instanceof Errores) {
            return res1;
        }

        // 3. Iniciar el Ciclo
        while (true) {
            // A. Evaluar la condición en CADA iteración
            var cond = this.condicion.interpretar(arbol, tablaHeader);
            if (cond instanceof Errores) {
                return cond;
            }

            // B. Validar que la condición sea Booleana
            if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
                return new Errores("Semantico", "La condición del For debe ser Booleana", this.linea, this.col);
            }

            // C. Si es FALSE, terminamos el ciclo
            if (!(boolean) cond) {
                break;
            }

            // D. Ejecutar bloque de instrucciones
            //    Creamos un entorno nuevo para cada iteración (para que variables declaradas dentro mueran al terminar la vuelta)
            var tablaCuerpo = new tablaSimbolos(tablaHeader);
            tablaCuerpo.setNombre("FOR_BODY");

            for (Instruccion i : this.instrucciones) {
                var res = i.interpretar(arbol, tablaCuerpo);

                // Manejo de errores internos
                if (res instanceof Errores) {
                    arbol.errores.add((Errores) res); // Agregamos al reporte pero no detenemos todo el programa (opcional)
                    // o return res; // Si quieres detener todo
                }

                // E. Manejo de Flujo (Break / Continue)
                // Asumiendo que tus clases Break y Continue retornan null o el objeto mismo
                if (res instanceof Break) {
                    return null; // Rompe el ciclo FOR completo
                }
                if (res instanceof Continue) {
                    break; // Rompe el for-each de instrucciones java, saltando directo al paso F (Actualización)
                }
                
                // Nota: Si usas una clase Return (Fase 2), agrégala aquí también.
            }

            // F. Ejecutar la actualización (Incremento/Decremento)
            //    Ej: i++
            var act = this.actualizacion.interpretar(arbol, tablaHeader);
            if (act instanceof Errores) {
                return act;
            }
        }
        
        return null;
    }
}
