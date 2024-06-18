package modelo;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
public class Tablas {

    
    public static void tablacentrada(JTable tabla){
        centrarContenido(tabla);
        ajustarEspaciado(tabla);
    }

    private static void centrarContenido(JTable tabla) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private static void ajustarEspaciado(JTable tabla){
        tabla.setRowHeight(30);
        tabla.setIntercellSpacing(new Dimension(10,10));
        tabla.setFont(tabla.getFont().deriveFont(16f));
    }
}

    
    
    
    

