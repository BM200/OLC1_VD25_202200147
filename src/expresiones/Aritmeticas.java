/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;
import abstracto.Instruccion;
import Simbolo.*;
import excepciones.Errores;
/**
 *
 * @author balam
 */
public class Aritmeticas extends Instruccion {
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operaciones;
    private Instruccion operandoUnico;
    // negacion
    public Aritmeticas(OperadoresAritmeticos operaciones, Instruccion operandoUnico, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operaciones = operaciones;
        this.operandoUnico = operandoUnico;
    }
    // aritmetica 
    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operaciones, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operaciones = operaciones;
    }
    
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        Object opIzq = null, opDer = null, Unico = null;
        
        if (this.operandoUnico!=null){
            Unico = this.operandoUnico.interpretar(arbol, tabla);
            if (Unico instanceof Errores) {
                return Unico;
            }
        }else{
            opIzq = this.operando1.interpretar(arbol, tabla);
            if (opIzq instanceof Errores){
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if (opDer instanceof Errores){
                return opDer;
            }
        }
        return switch (operaciones){
            case SUMA -> this.suma(opIzq, opDer);
            case RESTA -> this.resta(opIzq, opDer);
            case MULTIPLICACION -> this.multiplicacion(opIzq, opDer);
            case DIVISION -> this.division(opIzq, opDer);
            case POTENCIA -> this.potencia(opIzq, opDer);
            case MODULO -> this.modulo(opIzq, opDer);
            case NEGACION -> this.negacion(Unico);
            default -> new Errores("Semantico", "Operador aritmética inválido", this.linea, this.col);
        };
        
        
    }
    
    public Object suma(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch(tipo1) {
            case BOOLEANO -> {
            if (tipo2 == tipoDato.CADENA) {
                 this.tipo.setTipo(tipoDato.CADENA);
                 return op1.toString() + op2.toString();
            }
             return new Errores("ERROR semantico", "suma erronea", this.linea, this.col);
            }
            case ENTERO -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 + (double) op2;
                    }
                    case CADENA ->{
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                      
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.linea, this.col);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2){
                    case ENTERO ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 + (double) op2;
                    }
                    case CADENA ->{
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                      
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.linea, this.col);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2){
                    case ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)((Character) op1) + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)((Character) op1) + (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    case CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("ERROR semantico", "suma erronea", this.linea, this.col);
                    }
                }
            }
            
            
            case CADENA ->{
                this.tipo.setTipo(tipoDato.CADENA);
                return op1.toString() + op2.toString();
            }
            
            default -> {
                
                return new Errores("ERROR semantico", "suma erronea", this.linea, this.col);
            }
        }
        
        
    }
    
    public Object resta(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        // Lógica simplificada: Si uno es decimal, el resultado es decimal.
        // Si hay strings o booleanos, es error.
        
        if (tipo1 == tipoDato.CADENA || tipo2 == tipoDato.CADENA || 
            tipo1 == tipoDato.BOOLEANO || tipo2 == tipoDato.BOOLEANO) {
            return new Errores("Semantico", "No se puede restar tipos no numéricos", this.linea, this.col);
        }
        
        double val1 = obtenerValorDouble(op1, tipo1);
        double val2 = obtenerValorDouble(op2, tipo2);

        if (tipo1 == tipoDato.DECIMAL || tipo2 == tipoDato.DECIMAL) {
            this.tipo.setTipo(tipoDato.DECIMAL);
            return val1 - val2;
        } else {
            this.tipo.setTipo(tipoDato.ENTERO);
            return (int)val1 - (int)val2;
        }
    }
    
    public Object multiplicacion(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        if (tipo1 == tipoDato.CADENA || tipo2 == tipoDato.CADENA || 
            tipo1 == tipoDato.BOOLEANO || tipo2 == tipoDato.BOOLEANO) {
            return new Errores("Semantico", "Multiplicación inválida con Cadenas/Booleanos", this.linea, this.col);
        }

        double val1 = obtenerValorDouble(op1, tipo1);
        double val2 = obtenerValorDouble(op2, tipo2);

        if (tipo1 == tipoDato.DECIMAL || tipo2 == tipoDato.DECIMAL) {
            this.tipo.setTipo(tipoDato.DECIMAL);
            return val1 * val2;
        } else {
            this.tipo.setTipo(tipoDato.ENTERO);
            return (int)val1 * (int)val2;
        }
    }
    //division
    
    public Object division(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        if (tipo1 == tipoDato.CADENA || tipo2 == tipoDato.CADENA || 
            tipo1 == tipoDato.BOOLEANO || tipo2 == tipoDato.BOOLEANO) {
            return new Errores("Semantico", "División inválida", this.linea, this.col);
        }

        double val1 = obtenerValorDouble(op1, tipo1);
        double val2 = obtenerValorDouble(op2, tipo2);

        if (val2 == 0) {
            return new Errores("Semantico", "División por CERO", this.linea, this.col);
        }

        // Según PDF Pag 10, la división Entero/Entero retorna DECIMAL
        // Revisar tabla 5.3.4: Entero / Entero -> Decimal
        this.tipo.setTipo(tipoDato.DECIMAL); 
        return val1 / val2; 
    }
    
   //potencia 
    public Object potencia(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        if (tipo1 == tipoDato.CADENA || tipo2 == tipoDato.CADENA || 
            tipo1 == tipoDato.BOOLEANO || tipo2 == tipoDato.BOOLEANO) {
            return new Errores("Semantico", "Potencia inválida", this.linea, this.col);
        }
        
        double base = obtenerValorDouble(op1, tipo1);
        double exp = obtenerValorDouble(op2, tipo2);

        // PDF Pag 11: Entero ** Entero = Entero
        if (tipo1 == tipoDato.ENTERO && tipo2 == tipoDato.ENTERO) {
            this.tipo.setTipo(tipoDato.ENTERO);
            return (int) Math.pow(base, exp);
        } else {
            this.tipo.setTipo(tipoDato.DECIMAL);
            return Math.pow(base, exp);
        }
    }
    
    //Modulo %
    
    public Object modulo(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        if (tipo1 == tipoDato.CADENA || tipo2 == tipoDato.CADENA || 
            tipo1 == tipoDato.BOOLEANO || tipo2 == tipoDato.BOOLEANO) {
            return new Errores("Semantico", "Módulo inválido", this.linea, this.col);
        }

        double val1 = obtenerValorDouble(op1, tipo1);
        double val2 = obtenerValorDouble(op2, tipo2);
        
         if (val2 == 0) {
            return new Errores("Semantico", "Módulo por CERO", this.linea, this.col);
        }

        this.tipo.setTipo(tipoDato.DECIMAL); // PDF dice Decimal
        return val1 % val2;
    }
    
    public Object negacion(Object op1){
            var opU = this.operandoUnico.tipo.getTipo();
            switch (opU){
                case ENTERO->{
                    this.tipo.setTipo(tipoDato.ENTERO);
                    return (int) op1 * -1;
                }
                case DECIMAL ->{
                    this.tipo.setTipo(tipoDato.DECIMAL);
                    return (double) op1 * -1;
                }
                default ->{
                   return new Errores("ERROR semantico", "negacion erronea", this.linea, this.col);
                }
            }
        }
    
    //metodo auxiliar 
    private double obtenerValorDouble(Object op, tipoDato tipo) {
        if (tipo == tipoDato.ENTERO) return (int) op;
        if (tipo == tipoDato.DECIMAL) return (double) op;
        if (tipo == tipoDato.CARACTER) return (int)((Character)op); // Char a ASCII
        return 0; // No debería llegar aquí si validas antes
    }
    
}
