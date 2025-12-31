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
public class DeclaracionArreglo extends Instruccion {
    private String id;
    private Tipo tipoArreglo; // El tipo de dato base (INT, CHAR...)
    private int dimensiones;  // 1 o 2
    private LinkedList<Instruccion> valores;

    public DeclaracionArreglo(String id, Tipo tipoArreglo, int dimensiones, LinkedList<Instruccion> valores, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.tipoArreglo = tipoArreglo;
        this.dimensiones = dimensiones;
        this.valores = valores;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        // 1. Crear el contenedor (Arreglo de Java)
        Object[] arregloFinal = new Object[this.valores.size()];
        
        int indice = 0;
        
        // 2. Iterar sobre cada expresión
        for (Instruccion instruccion : this.valores) {
            
            Object valorInterpretado = instruccion.interpretar(arbol, tabla);
            
            if (valorInterpretado instanceof Errores) {
                return valorInterpretado;
            }
            
            // --- VALIDACIÓN DIFERENCIADA 1D vs 2D ---
            
            if (this.dimensiones == 2) {
                // CASO MATRIZ: Esperamos que el valor sea otro arreglo (una fila)
                if (valorInterpretado instanceof Object[]) {
                    // Es válido: es una sub-lista. 
                    // (Aquí podrías iterar 'valorInterpretado' para validar que sus elementos sean del tipo base,
                    // pero para efectos de compilación, aceptar el arreglo es suficiente).
                } else {
                    return new Errores("SEMANTICO", 
                        "Error en matriz '" + this.id + "'. Se esperaba una fila [ ] y se encontró un valor simple.", 
                        this.linea, this.col);
                }
            } 
            else {
                // CASO VECTOR 1D: Validamos el tipo directamente
                tipoDato tipoValor = instruccion.tipo.getTipo();
                tipoDato tipoDeclarado = this.tipoArreglo.getTipo();
                
                if (tipoValor != tipoDeclarado) {
                    // Excepción Int -> Double
                    if (tipoDeclarado == tipoDato.DECIMAL && tipoValor == tipoDato.ENTERO) {
                        valorInterpretado = Double.parseDouble(valorInterpretado.toString());
                    } 
                    else {
                        return new Errores("SEMANTICO", 
                            "Tipo incorrecto en arreglo '" + this.id + "'. Esperado: " + tipoDeclarado + ", Encontrado: " + tipoValor, 
                            this.linea, this.col);
                    }
                }
            }
            
            // 4. Guardar valor
            arregloFinal[indice] = valorInterpretado;
            indice++;
        }
        
        // 5. Crear el Símbolo
        Simbolo sim = new Simbolo(this.tipoArreglo, this.id, arregloFinal, this.linea, this.col);
        
        // 6. Guardar en la tabla
        boolean creacion = tabla.setVariables(sim);
        if (!creacion) {
            return new Errores("SEMANTICO", "El arreglo '" + this.id + "' ya existe.", this.linea, this.col);
        }
        
        arbol.simbolosReporte.add(sim);
        
        return null;
    }
}
