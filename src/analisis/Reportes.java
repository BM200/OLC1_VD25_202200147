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
            write.println("<head>");
            write.println("<title>Reporte de Errores</title>");
            // ESTA LÍNEA ARREGLA LOS CARACTERES RAROS (Tildes y Ñ)
            write.println("<meta charset=\"UTF-8\">"); 
            write.println("<style>");
            write.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
            write.println("h1 { text-align: center; color: #333; }");
            write.println("table { border-collapse: collapse; width: 100%; background-color: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            write.println("th, td { text-align: left; padding: 12px; border-bottom: 1px solid #ddd; }");
            write.println("th { background-color: #d9534f; color: white; }"); // Color rojo para errores
            write.println("tr:hover { background-color: #f5f5f5; }");
            write.println("</style>");
            write.println("</head>");
            write.println("<body>");
            write.println("<h1>Reporte de Errores - JavaUSAC</h1>");
            write.println("<table>");
            // Encabezados según el PDF
            write.println("<tr><th>#</th><th>Tipo</th><th>Descripción</th><th>Línea</th><th>Columna</th></tr>");
            

            int contador = 1;
            for (Errores err : listaErrores) {
                write.println("<tr>");
                write.println("<td>" + contador++ + "</td>");
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
            write.println("<head>");
            write.println("<title>Tabla de Símbolos</title>");
            write.println("<meta charset=\"UTF-8\">"); // <--- ARREGLA LAS TILDES
            write.println("<style>");
            write.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }");
            write.println("h1 { text-align: center; color: #333; }");
            write.println("table { border-collapse: collapse; width: 100%; background-color: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            write.println("th, td { text-align: left; padding: 12px; border-bottom: 1px solid #ddd; }");
            write.println("th { background-color: #4CAF50; color: white; }");
            write.println("tr:hover { background-color: #f5f5f5; }");
            write.println("</style>");
            write.println("</head>");
            write.println("<body>");
            write.println("<h1>Tabla de Símbolos (Variables Globales)</h1>");
            write.println("<table>");
            
            // 7 ENCABEZADOS
            write.println("<tr><th>#</th><th>Identificador</th><th>Tipo</th><th>Tipo Dato</th><th>Entorno</th><th>Valor</th><th>Línea</th><th>Columna</th></tr>");

            int contador = 1;
            for (Simbolo sim : listaSimbolos) {
                write.println("<tr>");
                write.println("<td>" + contador++ + "</td>");
                write.println("<td>" + sim.getId() + "</td>");
                write.println("<td>Variable</td>"); 
                write.println("<td>" + sim.getTipo().getTipo() + "</td>");
                write.println("<td>" + sim.getEntorno() + "</td>"); // <--- SOLO UNA VEZ
                write.println("<td>" + (sim.getValor() != null ? sim.getValor().toString() : "null") + "</td>");
                write.println("<td>" + sim.getLinea() + "</td>");
                write.println("<td>" + sim.getColumna() + "</td>");
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

