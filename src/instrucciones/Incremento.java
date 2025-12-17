/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
/**
 *
 * @author balam
 */
public class Incremento extends Instruccion {
    private String id;
    private boolean esIncremento; // true = ++, false = --

    public Incremento(String id, boolean esIncremento, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.esIncremento = esIncremento;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // 1. Buscar la variable
        Simbolo variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores("Semantico", "Variable '" + id + "' no existe", this.linea, this.col);
        }

        // 2. Validar que sea numérica
        tipoDato t = variable.getTipo().getTipo();
        if (t != tipoDato.ENTERO && t != tipoDato.DECIMAL) {
            return new Errores("Semantico", "Solo se puede incrementar/decrementar tipos numéricos", this.linea, this.col);
        }

        // 3. Obtener valor y Operar
        double valorActual = Double.parseDouble(variable.getValor().toString());
        double nuevoValor = esIncremento ? valorActual + 1 : valorActual - 1;

        // 4. Guardar manteniendo el tipo original
        if (t == tipoDato.ENTERO) {
            variable.setValor((int) nuevoValor);
        } else {
            variable.setValor(nuevoValor);
        }

        return null;
    }
}
