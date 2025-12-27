/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import Simbolo.Tipo;
import abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;

/**
 *
 * @author balam
 */
public class Nativo extends Instruccion {
  public Object valor;

    public Nativo(Object valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.valor = valor;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        // si es una CADENA, procesamos los escapes antes de retornar. 
        if (this.tipo.getTipo()== tipoDato.CADENA){
            String val= this.valor.toString();
            //Reemplazo de secuencias de escape
            
            val=val.replace("\\n", "\n"); //salto de linea
            val=val.replace("\\t", "\t"); //tabulacion
            val=val.replace("\\r", "\r"); //Retorno de carro
            val=val.replace("\\\"", "\""); //Comilla doble
            val=val.replace("\\'", "\'"); //Comilla Simple
            val=val.replace("\\\\", "\\"); // Barra invertida. 
            
            return val;
        }
        
        
        return this.valor;
    }
    
}
