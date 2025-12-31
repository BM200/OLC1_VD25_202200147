package Simbolo;

import abstracto.Instruccion;
import java.util.LinkedList;
import excepciones.Errores;
// Importamos tus clases
import instrucciones.Metodo; 
import instrucciones.Funcion;

/**
 *
 * @author balam
 */
public class Arbol {
    private LinkedList<Instruccion> instrucciones;
    private tablaSimbolos tablaGlobal;
    private String consolas;
    public LinkedList<Errores> errores;
    
    // Lista para el reporte de símbolos
    public LinkedList<Simbolo> simbolosReporte;
    
    // Lista UNIFICADA para guardar Funciones y Métodos
    private LinkedList<Instruccion> funciones; 

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.tablaGlobal = new tablaSimbolos();
        this.consolas = "";
        this.errores = new LinkedList<>();
        this.simbolosReporte = new LinkedList<>();
        this.funciones = new LinkedList<>();
    }

    // --- GETTERS Y SETTERS BÁSICOS ---
    public LinkedList<Instruccion> getInstrucciones() { return instrucciones; }
    public void setInstrucciones(LinkedList<Instruccion> instrucciones) { this.instrucciones = instrucciones; }
    public tablaSimbolos getTablaGlobal(){ return tablaGlobal; }
    public void setTablaGlobal(tablaSimbolos tablaGlobal){ this.tablaGlobal = tablaGlobal; }
    public LinkedList<Errores> getErrores() { return errores; }
    public void setErrores(LinkedList<Errores> errores) { this.errores = errores; }
    public String getConsolas() { return consolas; }
    public void setConsolas(String consolas) { this.consolas = consolas; }
    
    public void Print(String valor){
        this.consolas += valor + "\n";
    }
    
    // --- MANEJO DE FUNCIONES Y MÉTODOS ---

    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void addFuncion(Instruccion funcion){
        this.funciones.add(funcion);
    }
    
    /**
     * Busca una función o método por su nombre.
     * Adapta la búsqueda dependiendo de si es clase Metodo (.id) o Funcion (.nombre)
     */
    public Instruccion getFuncion(String identificador){
        for(Instruccion ins : this.funciones){
            
            // Caso 1: Es un MÉTODO
            if(ins instanceof Metodo){
                Metodo m = (Metodo) ins;
                if(m.id.equalsIgnoreCase(identificador)){
                    return m;
                }
            }
            
            // Caso 2: Es una FUNCIÓN
            else if(ins instanceof Funcion){
                Funcion f = (Funcion) ins;
                if(f.nombre.equalsIgnoreCase(identificador)){
                    return f;
                }
            }
        }
        return null;
    }
}