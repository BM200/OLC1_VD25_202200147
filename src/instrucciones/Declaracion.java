/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;
import Simbolo.Arbol;
import Simbolo.Simbolo;
import Simbolo.Tipo;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class Declaracion extends Instruccion {
     public String identificador;
    public Instruccion valor;

    public Declaracion(String identificador, Instruccion valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = valor;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        
        Object valorInterpretado = null;

        // --- CASO 1: Si la declaración trae asignación ---
        if (this.valor != null) {
            valorInterpretado = this.valor.interpretar(arbol, tabla);
            
            if (valorInterpretado instanceof Errores){
                return valorInterpretado;
            }

            // Validación de tipos
            if (this.valor.tipo.getTipo() != this.tipo.getTipo()){
                
                // CORRECCIÓN 1: Permitir asignar un Entero a una variable Double
                if (this.tipo.getTipo() == tipoDato.DECIMAL && this.valor.tipo.getTipo() == tipoDato.ENTERO) {
                    // Convertimos el valor entero a double para almacenarlo correctamente
                    // Asumiendo que valorInterpretado viene como Integer o int
                    double val = Double.parseDouble(valorInterpretado.toString());
                    valorInterpretado = val;
                } else {
                    return new Errores("semantico",
                        "Tipos erroneos en la declaración de '" + this.identificador + 
                        "'. Se esperaba " + this.tipo.getTipo() + " y se obtuvo " + this.valor.tipo.getTipo(),
                        this.linea, this.col);
                }
            }
        }

        // --- CASO 2: Si NO trae asignación → asignar valor por defecto ---
        else {
            switch(this.tipo.getTipo()){
                case ENTERO:   valorInterpretado = 0; break;
                case DECIMAL:  valorInterpretado = 0.0; break;
                // CORRECCIÓN 2: El PDF Pág 7 dice que el default es TRUE
                case BOOLEANO: valorInterpretado = true; break; 
                case CADENA:   valorInterpretado = ""; break;
                // CORRECCIÓN 3: Faltaba el tipo Carácter
                case CARACTER: valorInterpretado = '\u0000'; break; 
                default:       valorInterpretado = null; break;
            }
        }

        // Crear Simbolo y guardar
        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado, this.linea, this.col);
        boolean creacion = tabla.setVariables(s);
        
        if(!creacion){
            return new Errores("semantico", "Variable '" + this.identificador + "' ya existente en este entorno", this.linea, this.col);
        }
        
        return null;
    }
    
}
