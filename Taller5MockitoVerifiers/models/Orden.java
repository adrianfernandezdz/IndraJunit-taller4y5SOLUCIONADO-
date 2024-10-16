package Taller5MockitoVerifiers.models;

import java.util.Objects;

public class Orden {
    private String id;
    private String usuarioId;
    private double total;

    // Constructor, getters y setters
    public Orden(String id, String usuarioId, double total) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orden orden = (Orden) o;
        return Double.compare(total, orden.total) == 0 && Objects.equals(id, orden.id) && Objects.equals(
                usuarioId, orden.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, total);
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public double getTotal() {
        return total;
    }
}
