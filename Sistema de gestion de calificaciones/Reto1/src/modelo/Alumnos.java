package modelo;

public class Alumnos {
    
    //Atributos
    String nombre;
    double nota;
    String genero;
    String materia;

    //Constructor vacío
    public Alumnos() {
    }

    //Constructor alumnos
    public Alumnos(String nombre, double nota, String genero, String materia) {
        this.nombre = nombre;
        this.nota = nota;
        this.genero = genero;
        this.materia = materia;
    }
    
    //Set y getters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    
    
    
    
    
}