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
public class Switch extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> casos;
    private LinkedList<Instruccion> bloqueDefault;

    public Switch(Instruccion expresion, LinkedList<Instruccion> casos, LinkedList<Instruccion> bloqueDefault, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.casos = casos;
        this.bloqueDefault = bloqueDefault;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Calcular el valor de la condición principal del Switch
        Object valorSwitch = this.expresion.interpretar(arbol, tabla);
        
        if (valorSwitch instanceof Errores) {
            return valorSwitch;
        }

        boolean casoEjecutado = false;

        // 2. Recorrer todos los CASES
        for (Instruccion inst : casos) {
            if (!(inst instanceof Case)) continue; // Validación de seguridad
            
            Case casoActual = (Case) inst;
            
            // Calculamos el valor de la etiqueta del case (ej: case 1:)
            Object valorCase = casoActual.valor.interpretar(arbol, tabla);
            
            if (valorCase instanceof Errores) {
                return valorCase;
            }

            // Comparar: valorSwitch == valorCase
            // Usamos .equals para comparar objetos (Enteros, Strings, etc.)
            if (valorSwitch.equals(valorCase)) {
                
                // --- EJECUTAR EL CASO ---
                casoEjecutado = true;
                
                // Creamos un entorno nuevo para el case
                tablaSimbolos tablaCase = new tablaSimbolos(tabla);
                tablaCase.setNombre("SWITCH_CASE");

                for (Instruccion i : casoActual.instrucciones) {
                    Object res = i.interpretar(arbol, tablaCase);

                    // Manejo de Errores
                    if (res instanceof Errores) return res;

                    // Manejo de BREAK: Si encontramos un break, terminamos el Switch completo
                    if (res instanceof Break) {
                        return null; // Salimos del Switch
                    }
                    
                    // Manejo de CONTINUE: Si hay un continue, debe propagarse hacia afuera (al ciclo que envuelve el switch)
                    if (res instanceof Continue) {
                        return res;
                    }
                    
                    // (Nota: Si tuvieras RETURN, también deberías propagarlo aquí)
                }
                
                // Si ejecutamos un caso exitosamente, terminamos (Comportamiento sin fall-through simple)
                return null; 
            }
        }

        // 3. Si ningún case se ejecutó, ejecutamos el DEFAULT
        if (!casoEjecutado && bloqueDefault != null) {
            tablaSimbolos tablaDefault = new tablaSimbolos(tabla);
            tablaDefault.setNombre("SWITCH_DEFAULT");

            for (Instruccion i : bloqueDefault) {
                Object res = i.interpretar(arbol, tablaDefault);
                
                if (res instanceof Errores) return res;
                if (res instanceof Break) return null;
                if (res instanceof Continue) return res;
            }
        }

        return null;
    }
    
}
