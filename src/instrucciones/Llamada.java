/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import java.util.HashMap;

/**
 *
 * @author balam
 */
public class Llamada extends Instruccion {
    private String nombre;
    
    private LinkedList<Instruccion> parametrosEntrantes; // Valores enviados: suma(1, 2)

    public Llamada(String nombre, LinkedList<Instruccion> parametrosEntrantes, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.nombre = nombre;
        this.parametrosEntrantes = parametrosEntrantes;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Buscar la función en el árbol
        Instruccion definicion = arbol.getFuncion(this.nombre);
        
        if (definicion == null) {
            return new Errores("SEMANTICO", "La función o método '" + this.nombre + "' no existe.", this.linea, this.col);
        }

        // 2. Extraer datos según si es Metodo o Funcion
        LinkedList<HashMap<String, Object>> parametrosDefinidos;
        LinkedList<Instruccion> instruccionesCuerpo;
        Tipo tipoRetornoFuncion;

        if (definicion instanceof Metodo) {
            Metodo m = (Metodo) definicion;
            parametrosDefinidos = m.parametros;
            instruccionesCuerpo = m.instrucciones;
            tipoRetornoFuncion = new Tipo(tipoDato.VOID); 
        } else {
            Funcion f = (Funcion) definicion;
            parametrosDefinidos = f.parametros;
            instruccionesCuerpo = f.instrucciones;
            tipoRetornoFuncion = f.tipo;
        }

        // 3. Validar cantidad de parámetros
        if (this.parametrosEntrantes.size() != parametrosDefinidos.size()) {
            return new Errores("SEMANTICO", "Cantidad incorrecta de parámetros para '" + this.nombre + "'.", this.linea, this.col);
        }

        // 4. Crear NUEVO ENTORNO (Scope)
        // El padre es la tabla GLOBAL (Scope Estático), no la tabla actual.
        // Esto evita que la función vea variables locales de quien la llamó.
        tablaSimbolos tablaFuncion = new tablaSimbolos(arbol.getTablaGlobal());
        tablaFuncion.setNombre("FUNCION " + this.nombre);

        // 5. Procesar Parámetros (Declararlos en la nueva tabla)
        for (int i = 0; i < this.parametrosEntrantes.size(); i++) {
            // A. Interpretar valor enviado (usando tabla actual/caller)
            Object valor = this.parametrosEntrantes.get(i).interpretar(arbol, tabla);
            if (valor instanceof Errores) return valor;

            // B. Obtener definición del parámetro (Tipo y ID)
            HashMap<String, Object> paramDef = parametrosDefinidos.get(i);
            String nombreParam = (String) paramDef.get("id");
            Tipo tipoParam = (Tipo) paramDef.get("tipo");

            // C. Validar Tipos
            if (this.parametrosEntrantes.get(i).tipo.getTipo() != tipoParam.getTipo()) {
                // Aquí podrías agregar lógica para casteos implícitos (int -> double) si quisieras
                return new Errores("SEMANTICO", "Error en parámetro " + (i+1) + ". Tipos incompatibles.", this.linea, this.col);
            }

            // D. Guardar variable en tabla local
            Simbolo sim = new Simbolo(tipoParam, nombreParam, valor, this.linea, this.col);
            sim.setEntorno("FUNCION " + this.nombre);
            tablaFuncion.setVariables(sim);
            
            // Guardar en reporte histórico
            arbol.simbolosReporte.add(sim);
        }

        // 6. Ejecutar instrucciones de la función
        for (Instruccion ins : instruccionesCuerpo) {
            // Ejecutamos usando la tabla de la función
            Object res = ins.interpretar(arbol, tablaFuncion);

            if (res instanceof Errores) return res;

            // 7. Manejo del RETURN
            if (res instanceof Return) {
                Return ret = (Return) res;
                
                // Si la función debía retornar algo pero no trajo valor (o viceversa), se puede validar aquí.
                
                // Validar si retornó valor en un método void
                if (tipoRetornoFuncion.getTipo() == tipoDato.VOID && ret.valorRetorno != null) {
                    return new Errores("SEMANTICO", "Un método void no puede retornar valores", this.linea, this.col);
                }
                
                // Validar si falta valor en una función no void
                if (tipoRetornoFuncion.getTipo() != tipoDato.VOID && ret.valorRetorno == null) {
                    return new Errores("SEMANTICO", "La función debe retornar un valor", this.linea, this.col);
                }
                
                // Asignar el tipo de retorno a la Llamada actual
                if (ret.valorRetorno != null) {
                    this.tipo.setTipo(tipoRetornoFuncion.getTipo());
                    return ret.valorRetorno; // Devolvemos el valor real (ej: 10)
                }
                
                return null; // Return; (Void)
            }
        }
        
        // Si termina de ejecutar y no hubo return:
        if (tipoRetornoFuncion.getTipo() != tipoDato.VOID) {
             return new Errores("SEMANTICO", "La función '" + this.nombre + "' debe retornar un valor.", this.linea, this.col);
        }

        return null;
    }
}