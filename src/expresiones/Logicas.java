/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import Simbolo.*;


/**
 *
 * @author balam
 */
public class Logicas extends Instruccion{
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresLogicos operacion;
    private Instruccion operandoUnico; // Para el NOT (!)

    // Constructor Binario (AND, OR, XOR)
    public Logicas(Instruccion operando1, Instruccion operando2, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    // Constructor Unario (NOT)
    public Logicas(Instruccion operandoUnico, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.operandoUnico = operandoUnico;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object op1 = null, op2 = null;

        // Interpretación Unaria (NOT)
        if (this.operandoUnico != null) {
            op1 = this.operandoUnico.interpretar(arbol, tabla);
            if (op1 instanceof Errores) return op1;
            
            if (this.operandoUnico.tipo.getTipo() != tipoDato.BOOLEANO) {
                return new Errores("Semantico", "El operador NOT solo aplica a booleanos", this.linea, this.col);
            }
            
            this.tipo.setTipo(tipoDato.BOOLEANO);
            return !((boolean) op1);
        }

        // Interpretación Binaria (AND, OR, XOR)
        op1 = this.operando1.interpretar(arbol, tabla);
        if (op1 instanceof Errores) return op1;

        op2 = this.operando2.interpretar(arbol, tabla);
        if (op2 instanceof Errores) return op2;                                                                                                                                                                                 

        // Validar tipos (Ambos deben ser Booleanos)
        if (this.operando1.tipo.getTipo() != tipoDato.BOOLEANO || this.operando2.tipo.getTipo() != tipoDato.BOOLEANO) {
            return new Errores("Semantico", "Operación lógica inválida. Ambos operandos deben ser booleanos.", this.linea, this.col);
        }

        this.tipo.setTipo(tipoDato.BOOLEANO);
        
        switch (operacion) {
            case AND: return (boolean) op1 && (boolean) op2;
            case OR:  return (boolean) op1 || (boolean) op2;
            case XOR: return (boolean) op1 ^ (boolean) op2;
            default: return new Errores("Semantico", "Operador lógico desconocido", this.linea, this.col);
        }
    }
}
