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
        // 1. Obtener el valor de la condición principal (ej: opcion)
        Object valorSwitch = this.expresion.interpretar(arbol, tabla);
        
        if (valorSwitch instanceof Errores) {
            return valorSwitch;
        }

        // Bandera para saber si ya encontramos el caso correcto.
        // Una vez se pone en 'true', ejecutará TODAS las instrucciones siguientes (Fall-through)
        // hasta que encuentre un BREAK.
        boolean isMatch = false; 

        // 2. Recorrer todos los CASES
        for (Instruccion inst : casos) {
            if (!(inst instanceof Case)) continue;
            
            Case casoActual = (Case) inst;
            
            // Si AÚN NO hemos encontrado coincidencia, evaluamos la condición del case
            if (!isMatch) {
                Object valorCase = casoActual.valor.interpretar(arbol, tabla);
                if (valorCase instanceof Errores) return valorCase;

                if (valorSwitch.equals(valorCase)) {
                    isMatch = true; // ¡Encontramos la entrada! Activamos la ejecución.
                }
            }

            // Si isMatch es TRUE (ya sea porque acabamos de coincidir O porque venimos en cascada del anterior)
            if (isMatch) {
                
                // Entorno para el caso
                tablaSimbolos tablaCase = new tablaSimbolos(tabla);
                tablaCase.setNombre("SWITCH_CASE");

                for (Instruccion i : casoActual.instrucciones) {
                    Object res = i.interpretar(arbol, tablaCase);

                    // Validar Errores
                    if (res instanceof Errores) return res;

                    // --- MANEJO CRÍTICO DEL BREAK ---
                    // Si encontramos un Break, RETORNAMOS NULL inmediatamente.
                    // Esto detiene el Switch y evita que siga cayendo a los siguientes casos.
                    if (res instanceof Break) {
                        return null; 
                    }
                    
                    // Propagar Continue si estamos dentro de un ciclo
                    if (res instanceof Continue) {
                        return res;
                    }
                }
            }
        }

        // 3. Ejecutar DEFAULT
        // Se ejecuta si:
        // A) Ningún caso coincidió (isMatch nunca se hizo true)
        // B) Hubo coincidencia previa pero NO hubo break (Fall-through hasta el default)
        if (bloqueDefault != null) {
            // Si venimos en cascada (isMatch=true) o si nadie coincidió (isMatch=false),
            // el default siempre se ejecuta si llegamos hasta aquí sin un break previo.
            // (Nota: si isMatch es false, significa que nadie coincidió, así que toca default).
            
            tablaSimbolos tablaDefault = new tablaSimbolos(tabla);
            tablaDefault.setNombre("SWITCH_DEFAULT");

            for (Instruccion i : bloqueDefault) {
                Object res = i.interpretar(arbol, tablaDefault);
                
                if (res instanceof Errores) return res;
                if (res instanceof Break) return null; // Break en default termina el switch
                if (res instanceof Continue) return res;
            }
        }

        return null;
    }
}
