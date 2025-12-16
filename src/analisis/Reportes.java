/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analisis;

import Simbolo.Simbolo;
import excepciones.Errores;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author balam
 */
public class Reportes {
 public static void generarReporteErrores(LinkedList<Errores> listaErrores) {
        try {
            FileWriter file = new FileWriter("ReporteErrores.html");
            PrintWriter write = new PrintWriter(file);
            
            write.println("<html>");
            write.println("<head><title>Reporte de Errores</title>");
            write.println("<style>");
            write.println("table { border-collapse: collapse; width: 100%; }");
            write.println("th, td { text-align: left; padding: 8px; border: 1px solid black; }");
            write.println("th { background-color: #f2f2f2; }");
            write.println("h1 { text-align: center; }");
            write.println("</style></head>");
            write.println("<body>");
            write.println("<h1>Reporte de Errores - JavaUSAC</h1>");
            write.println("<table>");
            write.println("<tr><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr>");

            for (Errores err : listaErrores) {
                write.println("<tr>");
                write.println("<td>" + err.getTipo() + "</td>");
                write.println("<td>" + err.getDesc() + "</td>");
                write.println("<td>" + err.getLinea() + "</td>");
                write.println("<td>" + err.getColumna() + "</td>");
                write.println("</tr>");
            }

            write.println("</table></body></html>");
            write.close();
            System.out.println("Reporte de Errores generado con éxito.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generarReporteSimbolos(LinkedList<Simbolo> listaSimbolos) {
        try {
            FileWriter file = new FileWriter("ReporteTablaSimbolos.html");
            PrintWriter write = new PrintWriter(file);
            
            write.println("<html>");
            write.println("<head><title>Tabla de Símbolos</title>");
            write.println("<style>");
            write.println("table { border-collapse: collapse; width: 100%; }");
            write.println("th, td { text-align: left; padding: 8px; border: 1px solid black; }");
            write.println("th { background-color: #4CAF50; color: white; }");
            write.println("h1 { text-align: center; }");
            write.println("</style></head>");
            write.println("<body>");
            write.println("<h1>Tabla de Símbolos (Variables Globales)</h1>");
            write.println("<table>");
            write.println("<tr><th>Identificador</th><th>Tipo</th><th>Valor Actual</th></tr>");

            for (Simbolo sim : listaSimbolos) {
                write.println("<tr>");
                write.println("<td>" + sim.getId() + "</td>");
                write.println("<td>" + sim.getTipo().getTipo() + "</td>");
                write.println("<td>" + (sim.getValor() != null ? sim.getValor().toString() : "null") + "</td>");
                write.println("</tr>");
            }

            write.println("</table></body></html>");
            write.close();
            System.out.println("Reporte de Símbolos generado con éxito.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

