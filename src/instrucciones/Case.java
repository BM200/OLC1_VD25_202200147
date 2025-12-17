/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import Simbolo.Arbol;
import Simbolo.Tipo;
import Simbolo.tablaSimbolos;
import Simbolo.tipoDato;
import abstracto.Instruccion;
import java.util.LinkedList;


/**
 *
 * @author balam
 */
public class Case extends Instruccion{
        // CAMBIO: Hacerlas públicas para acceder desde Switch
    public Instruccion valor;
    public LinkedList<Instruccion> instrucciones;

    public Case(Instruccion valor, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.valor = valor;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // El Case no se interpreta por sí mismo, lo hace el Switch
        return null;
    }
}