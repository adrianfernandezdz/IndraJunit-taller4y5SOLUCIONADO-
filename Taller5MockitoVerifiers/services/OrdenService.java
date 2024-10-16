package Taller5MockitoVerifiers.services;

import java.util.List;

import Taller5MockitoVerifiers.models.Orden;
import Taller5MockitoVerifiers.repositories.OrdenRepository;
public class OrdenService {
    private final OrdenRepository ordenRepository;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    public void registrarOrden(Orden orden) {
        ordenRepository.agregarOrden(orden);
    }

    public Orden buscarOrden(String id) {
        return ordenRepository.obtenerOrden(id);
    }

    public void eliminarOrden(String id) {
        ordenRepository.eliminarOrden(id);
    }

    public List<Orden> obtenerOrdenesPorUsuario(String usuarioId) {
        return ordenRepository.obtenerOrdenesPorUsuario(usuarioId);
    }
}