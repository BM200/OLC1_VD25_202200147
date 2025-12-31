/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.*;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import java.util.HashMap; // <--- ESTA IMPORTACIÓN FALTABA

/**
 *
 * @author balam
 */
public class Funcion extends Instruccion {
    public String nombre;
    // Lista de parámetros. Cada parámetro es un mapa con "tipo" y "id"
    public LinkedList<HashMap<String, Object>> parametros; 
    public LinkedList<Instruccion> instrucciones;

    public Funcion(Tipo tipo, String nombre, LinkedList<HashMap<String, Object>> parametros, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(tipo, linea, col);
        this.nombre = nombre;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // La instrucción de "Definir Función" no ejecuta el cuerpo.
        // Solo se guarda en el árbol para ser llamada después.
        
        // Validación: Que no exista ya una función o método con ese nombre
        if (arbol.getFuncion(this.nombre) != null) {
            return new Errores("SEMANTICO", "Ya existe una función/método con el nombre: " + this.nombre, this.linea, this.col);
        }
        
        // Guardar la función en el entorno global de funciones del árbol
        arbol.addFuncion(this);
        
        return null;
    }
}