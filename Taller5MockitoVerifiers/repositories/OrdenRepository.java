package Taller5MockitoVerifiers.repositories;

import java.util.List;

import Taller5MockitoVerifiers.models.Orden;

public interface OrdenRepository {
    void agregarOrden(Orden orden);
    Orden obtenerOrden(String id);
    void eliminarOrden(String id);
    List<Orden> obtenerOrdenesPorUsuario(String usuarioId);
}