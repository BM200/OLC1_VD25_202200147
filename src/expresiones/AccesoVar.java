/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import Simbolo.Arbol;
import Simbolo.Tipo;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;
import abstracto.Instruccion;
import excepciones.Errores;

/**
 *
 * @author balam
 */
public class AccesoVar extends Instruccion {
    private String id;

    public AccesoVar(String id, int linea, int col) {
        // Inicializamos como VOID temporalmente
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        // La tabla ya maneja el toLowerCase internamente según tu código anterior
        var variableEncontrada = tabla.getVariable(this.id);
        
        if (variableEncontrada == null){
            return new Errores("Semantico", 
                "La variable '" + this.id + "' no existe en el contexto actual.", 
                this.linea, this.col);
        }

        // CORRECCIÓN: Asignar el objeto Tipo completo es más seguro
        this.tipo = variableEncontrada.getTipo();
        
        return variableEncontrada.getValor();
    }
    
}
