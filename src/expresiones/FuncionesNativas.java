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
public class FuncionesNativas extends Instruccion {
    private Instruccion expresion;
    private TipoNativa operacion;

    public FuncionesNativas(Instruccion expresion, TipoNativa operacion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valor = this.expresion.interpretar(arbol, tabla);
        if (valor instanceof Errores) return valor;

        tipoDato t = this.expresion.tipo.getTipo();

        switch (this.operacion) {
            case LENGTH:
                this.tipo.setTipo(tipoDato.ENTERO);
                
                if (t == tipoDato.CADENA) {
                    return valor.toString().length();
                } else if (valor instanceof Object[]) {
                    return ((Object[]) valor).length;
                } else if (valor instanceof LinkedList) {
                    return ((LinkedList<?>) valor).size();
                }
                return new Errores("SEMANTICO", "La función length() solo aplica a Cadenas, Vectores o Listas.", this.linea, this.col);

            case ROUND:
                this.tipo.setTipo(tipoDato.ENTERO);
                if (t == tipoDato.DECIMAL || t == tipoDato.ENTERO) {
                    double valDouble = Double.parseDouble(valor.toString());
                    return (int) Math.round(valDouble);
                }
                return new Errores("SEMANTICO", "La función round() solo aplica a números.", this.linea, this.col);

            case TOSTRING:
                this.tipo.setTipo(tipoDato.CADENA);
                return valor.toString();

            default:
                return null;
        }
    }
}
