package Taller5MockitoVerifiers.models;

public class Usuario {
    private String id;
    private String nombre;

    // Constructor, getters y setters
    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
