/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class exportarregistros {
       public static void exportarRegistros(JTable tablaRegistros) {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(archivo);
                // Escribir los datos de la tabla en el archivo
                for (int i = 0; i < tablaRegistros.getRowCount(); i++) {
                    for (int j = 0; j < tablaRegistros.getColumnCount(); j++) {
                        writer.write(tablaRegistros.getValueAt(i, j).toString() + ",");
                    }
                    writer.write("\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Registros exportados correctamente.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al exportar los registros.");
            }
        }
    }
    
}
