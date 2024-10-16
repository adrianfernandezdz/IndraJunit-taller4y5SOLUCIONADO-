package Taller5MockitoVerifiers.services;

import java.util.List;

import Taller5MockitoVerifiers.models.Orden;
import Taller5MockitoVerifiers.models.Usuario;
import Taller5MockitoVerifiers.repositories.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final OrdenService ordenService; // Dependencia del segundo servicio

    public UsuarioService(UsuarioRepository usuarioRepository, OrdenService ordenService) {
        this.usuarioRepository = usuarioRepository;
        this.ordenService = ordenService;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioRepository.agregarUsuario(usuario);
        // Crear una orden inicial para el nuevo usuario
        Orden ordenInicial = new Orden("1", usuario.getId(), 0.0); // Orden inicial con total 0
        ordenService.registrarOrden(ordenInicial);
    }

    public Usuario buscarUsuario(String id) {
        return usuarioRepository.obtenerUsuario(id);
    }

    public void eliminarUsuario(String id) {
        usuarioRepository.eliminarUsuario(id);
        // Opcional: eliminar todas las órdenes asociadas al usuario
        // ordenService.eliminarOrdenesPorUsuario(id); // Método a implementar si se desea
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.actualizarUsuario(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.obtenerTodosLosUsuarios();
    }
}
