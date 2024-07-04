
package Modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class UtilidadesTablas {
     
    //Funcion de personalizar tabla
    
    public static void personalizarTabla(JTable tabla){
        centrarContenido(tabla);
        ajustarEspaciado(tabla);
        //italica(tabla);
        negrita(tabla);
    }
    
    //Es lo mismo de arriba pero sin negrita.
    
    public static void tablaNormal(JTable tabla){
        centrarContenido(tabla);
        ajustarEspaciado(tabla);
    }
    
    // Para centrar el contenido :D
    
    private static void centrarContenido(JTable tabla) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    // Tipos de letra, negrita e italico.
    
    private static void negrita(JTable tabla){
        Font font = tabla.getFont();
        tabla.setFont(font.deriveFont(Font.BOLD));
    }
    private static void italica(JTable tabla){
        Font font = tabla.getFont();
        tabla.setFont(font.deriveFont(Font.ITALIC));
    }
    
    // Ajustar espaciado de la tabla
    
    private static void ajustarEspaciado(JTable tabla){
        tabla.setRowHeight(30);
        tabla.setIntercellSpacing(new Dimension(10,10));
        tabla.setFont(tabla.getFont().deriveFont(16f));
    }
}
