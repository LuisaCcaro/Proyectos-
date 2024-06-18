package modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MetodosAlumnos {
    
    Conexion con = new Conexion();
    Connection cn = con.getConnection();

    public MetodosAlumnos() {
    }
    
    public MetodosAlumnos(Connection cn) {
       cn = con.getConnection();
    }
    
    //Método para insertar un nuevo alumno en la base datos
    public boolean insertarAlumno (Alumnos alu){
        String sql = "INSERT INTO reg_alu (Nombre, Nota, Genero, Materia) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)){
            
            alu.nombre = alu.nombre.trim();
            alu.genero = alu.genero.trim();
            alu.materia = alu.materia.trim();
           
            ps.setString(1, alu.nombre);
            ps.setDouble(2, alu.nota);
            ps.setString(3, alu.genero);
            ps.setString(4, alu.materia);
            
            //Se ejecuta sentencia de insercion
            ps.executeUpdate();
                return true;
        }catch (SQLException e) {
            //Se manejan excepciones que puedan ocurrir
            System.out.println("UPS, error al insertar alumno: "+ e.getMessage());
        }
        return false;
    }
    
    public List<Alumnos> consultarEstudiantes(String nombre) {
        
        List<Alumnos> estudiantes = new ArrayList<>();
        
        String consulta = "SELECT * FROM reg_alu WHERE Nombre = ?";
        
        try (Connection conn = new Conexion().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Alumnos estudiante = new Alumnos();
                estudiante.setNombre(rs.getString(1));
                estudiante.setNota(rs.getDouble(2));
                estudiante.setGenero(rs.getString(3));
                estudiante.setMateria(rs.getString(4));
                estudiantes.add(estudiante);
            }
            
            if (estudiantes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron estudiantes con el nombre especificado");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar estudiantes: " + e.getMessage());
        }
        
        return estudiantes;
    }
    
    public List<Alumnos> consultarPorMateria(String materia) {
        
        List<Alumnos> estudiantes = new ArrayList<>();
        
        String consulta = "SELECT * FROM reg_alu WHERE Materia = ?";
        
        try (Connection conn = new Conexion().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, materia);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Alumnos estudiante = new Alumnos();
                estudiante.setNombre(rs.getString(1));
                estudiante.setNota(rs.getDouble(2));
                estudiante.setGenero(rs.getString(3));
                estudiante.setMateria(rs.getString(4));
                estudiantes.add(estudiante);
            }
            
            if (estudiantes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron estudiantes con el nombre especificado");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }
    
    public void eliminarPorNombre(String nombre) {
        
        String consulta = "DELETE FROM reg_alu WHERE Nombre = ?";
        
        try (Connection conn = new Conexion().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"EL estudiante con el nombre "+nombre+" fue eliminado correctamente.");
        }
        
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al eliminar el estudiante "+ nombre);
        }
    }
    
        public void eliminarPorMeterias(String Materia) {
            
        String consulta = "DELETE FROM reg_alu WHERE Materia = ?";
        
        try (Connection conn = new Conexion().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, Materia);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Los estudiantes con la masteria "+Materia+" fueron eliminados correctamente.");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al eliminar la materia "+ Materia);
        }
    }  
    
    //Método loadData
    public Alumnos[] loadData() throws SQLException {
        
        Alumnos[] estudiantes = null;
        
        try {
            
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM reg_alu");
            ResultSet rs = ps.executeQuery();
            
            // Creamos una lista para almacenar los estudiantes
            List<Alumnos> estudiantesList = new ArrayList<>();
                   
            while(rs.next()) {
                String nombre = rs.getString("Nombre");
                String genero = rs.getString("Genero");
                String materia = rs.getString("Materia");
                double nota = rs.getDouble("Nota");
                
                Alumnos estudiante = new Alumnos(nombre, nota, genero, materia);
                estudiantesList.add(estudiante);
            }
            
            // Convertimos la lista a un arreglo de Alumnos
            estudiantes = estudiantesList.toArray(new Alumnos[0]);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage(), "¡Error!", JOptionPane.WARNING_MESSAGE);
    
        }
        
        return estudiantes;
    }
    
    public void mostrarDatos(JTextArea txtArea, Alumnos[] estudiantes) {
        // Limpiamos el JTextArea antes de mostrar los datos
        txtArea.setText("");
        
        // Iteramos sobre los estudiantes y los agregamos al JTextArea
        for (Alumnos estudiante : estudiantes) {
            txtArea.append("Nombre: " + estudiante.getNombre() + "\n");
            txtArea.append("Género: " + estudiante.getGenero() + "\n");
            txtArea.append("Materia: " + estudiante.getMateria() + "\n");
            txtArea.append("Nota: " + estudiante.getNota() + "\n");
            txtArea.append("\n"); // Agregamos una línea en blanco entre cada estudiante
        }
    }
    
    public void procesarDatosYMostrar(JTextArea txtAreaa) throws SQLException {
        
        Alumnos[] estudiantes = loadData(); // Llamar al método loadData
        double porcentajeAprobacion = stat1(estudiantes);
        int cantidadRegulares = stat2(estudiantes);
        String mejorMateriaFemenina = stat3(estudiantes);
        String mejorEstudianteBiologia = stat4(estudiantes);
        
        // Mostrar resultados en txtAreaDerecha
        txtAreaa.setText("Porcentaje de aprobación: " + porcentajeAprobacion + "\n\n");
        txtAreaa.append("Cantidad de exámenes con calificación Regular: " + cantidadRegulares + "\n\n");
        txtAreaa.append("Mejor materia para el género femenino: " + mejorMateriaFemenina + "\n\n");
        txtAreaa.append("Mejor estudiante en Biología: " + mejorEstudianteBiologia + "\n\n");
    }
    
    // Método para limpiar los datos de un JTextArea
    public void limpiarTextArea(JTextArea textArea) {
        textArea.setText(""); // Limpiar el JTextArea
    }
    
    /*
    1. ¿Cuál es el porcentaje de aprobación 
    para todos los exámenes presentados por el grupo?
    */
    
    public static double stat1(Alumnos[] estudiantes) {
        
        double promedio = 0;
        
        //Se recorre todos los estudiantes y se suman las notas
        for (Alumnos estudiante : estudiantes) {
            promedio += estudiante.getNota();
        }
        
        //Se calcula el promedio y se convierte a un valor entre 0 y 1
        return promedio / estudiantes.length / 100.0; //Se devuelve el promedio como porcentaje
    }
    
    /*
    2. ¿Cuántos exámenes tienen una calificación Regular?
    */
    
    public static int stat2(Alumnos[] estudiantes) {
        
        int regulares = 0;
        for (Alumnos estudiante : estudiantes) {
            double nota = estudiante.getNota();
            if (nota >= 60 && nota <= 80) {
                regulares++;
            }
        }
        return regulares;
    }
    
    /*
    3. ¿Cuál es la materia con el mejor desempeño 
    promedio para el género femenino?
    */
    public String stat3(Alumnos[] estudiantes) throws SQLException {
        
        String mejorMateria = ""; // Inicialización con una materia válida
        double mejorPromedio = Double.MIN_VALUE; // Inicialización con un valor muy pequeño

        // Consulta a la base de datos para obtener los estudiantes de género femenino
        String consulta = "SELECT Materia, AVG(Nota) AS Promedio FROM reg_alu WHERE Genero = 'Femenino' GROUP BY Materia";
        try (Connection conn = new Conexion().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(consulta)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String materia = rs.getString("Materia");
                double promedio = rs.getDouble("Promedio");

                // Actualizar la mejor materia si este promedio es el mejor hasta ahora
                if (promedio > mejorPromedio) {
                    mejorPromedio = promedio;
                    mejorMateria = materia;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el mejor promedio por materia para el género femenino: " + e.getMessage());
        }

        // Verificar si se encontró una mejor materia
        if ("".equals(mejorMateria)) {
            return "No se encontraron datos para calcular la mejor materia";
        } else {
            return mejorMateria; // Devolver la mejor materia encontrada
        }
    }
    
    /*
    4. ¿Cuál es el estudiante con el mejor desempeño para la materia biología?
    */
    
    public static String stat4(Alumnos[] estudiantes) {
        String estudiante = "";
        double mejorNota = Double.MIN_VALUE;
        for (Alumnos est : estudiantes) {
            if (est.getMateria().equals(Materia(1)) && est.getNota() > mejorNota) {
                mejorNota = est.getNota();
                estudiante = est.getNombre();
            }
        }
        return estudiante;
    }

    //Método para obtener y ponerle identificadores a los nombres
    public static String Nombre(int numero) {
        String[] nombres = {"Armando", "Nicolas", "Daniel", "Maria", "Marcela", "Alexandra"};
        if (numero >= 1 && numero <= nombres.length) {
            return nombres[numero - 1];
        } else {
            return "";
        }
    }
    
    //Método para obtener y ponerle identificadores a los generos
    public static String Genero(int numero) {
        String[] generos = {"M", "F"};
        if (numero >= 0 && numero <= 1) {
            return generos[numero];
        } else {
            return "";
        }
    }
    
    //Método para obtener y ponerle identificadores a las materias
    public static String Materia(int numero) {
        String[] materias = {"", "Biologia", "Geografia", "Matematicas"};
        if (numero >= 1 && numero <= 3) {
            return materias[numero];
        } else {
            return "";
        }
    } 
}

