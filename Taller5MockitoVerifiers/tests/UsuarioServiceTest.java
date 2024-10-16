package Taller5MockitoVerifiers.tests;

import java.util.Arrays;
import java.util.List;

import Taller5MockitoVerifiers.models.Orden;
import Taller5MockitoVerifiers.models.Usuario;
import Taller5MockitoVerifiers.repositories.UsuarioRepository;
import Taller5MockitoVerifiers.services.OrdenService;
import Taller5MockitoVerifiers.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // Mock del repositorio

    @Mock
    private OrdenService ordenService; // Mock del segundo servicio

    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        // Inicializar mocks antes de cada test
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, ordenService);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado a los métodos correspondientes del otro servicio.
    @Test
    void testRegistrarUsuarioConOrden() {
        // Crear un usuario para registrar
        Usuario usuario = new Usuario("1", "Juan");

        // Registrar el usuario
        usuarioService.registrarUsuario(usuario);

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).agregarUsuario(usuario); // Verificación del método

        // Verificar que se haya registrado una orden para el usuario
        Orden ordenInicial = new Orden("1", usuario.getId(), 0.0); // Orden inicial con total 0
        verify(ordenService).registrarOrden(ordenInicial); // Verificación del método
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado a los métodos correspondientes del otro servicio.
    @Test
    void testEliminarUsuario() {
        String userId = "1";

        // Eliminar el usuario
        usuarioService.eliminarUsuario(userId);

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).eliminarUsuario(userId); // Verificación del método

        // Opcional: verificar que se eliminen las órdenes del usuario
        // verify(ordenService).eliminarOrdenesPorUsuario(userId); // Descomentar si se implementa
    }


    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    // Verificar que NO se ha llamado a otros métodos del repositorio.
    // Comprobar el resultado del servicio.
    @Test
    void testBuscarUsuario() {
        String userId = "1";
        Usuario usuario = new Usuario(userId, "Juan");

        // Configurar el comportamiento del mock
        when(usuarioRepository.obtenerUsuario(userId)).thenReturn(usuario);

        // Buscar el usuario
        Usuario result = usuarioService.buscarUsuario(userId);

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).obtenerUsuario(userId); // Verificación del método
        verify(usuarioRepository, times(1)).obtenerUsuario(userId); // Verificación del método
        verify(usuarioRepository, never()).eliminarUsuario(userId); // Verificación del método
        // Aserciones sobre el resultado
        assertEquals(usuario, result);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario
    @Test
    void testActualizarUsuario() {
        // Crear un usuario para actualizar
        Usuario usuario = new Usuario("1", "Juan");

        // Actualizar el usuario
        usuarioService.actualizarUsuario(usuario);

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).actualizarUsuario(usuario); // Verificación del método
        verify(usuarioRepository, times(1)).actualizarUsuario(usuario); // Verificación del método
    }


    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    // Verificar que NO se ha llamado a otros métodos del repositorio.
    // Comprobar el resultado del servicio.
    @Test
    void testObtenerTodosLosUsuarios() {
        // Crear una lista de usuarios
        Usuario usuario1 = new Usuario("1", "Juan");
        Usuario usuario2 = new Usuario("2", "Maria");
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        // Configurar el comportamiento del mock
        when(usuarioRepository.obtenerTodosLosUsuarios()).thenReturn(usuarios);

        // Obtener todos los usuarios
        List<Usuario> result = usuarioService.obtenerTodosLosUsuarios();

        // Verificar que el método del repositorio fue llamado
        verify(usuarioRepository).obtenerTodosLosUsuarios(); // Verificación del método

        // Aserciones sobre el resultado
        assertEquals(2, result.size());
        assertTrue(result.contains(usuario1));
        assertTrue(result.contains(usuario2));
    }

    // Testear un registro, búsqueda, actualización e eliminación.
    // Verificar el orden de las llamadas (Usaremos InOrder de Mockito).
    @Test
    void testInvocationOrder() {
        Usuario usuario = new Usuario("1", "Juan");

        // Registrar el usuario
        usuarioService.registrarUsuario(usuario);
        // Buscar el usuario
        usuarioService.buscarUsuario("1");
        // Actualizar el usuario
        usuarioService.actualizarUsuario(usuario);
        // Eliminar el usuario
        usuarioService.eliminarUsuario("1");

        // Verificar el orden de invocaciones
        InOrder inOrder = inOrder(usuarioRepository, ordenService);
        inOrder.verify(usuarioRepository).agregarUsuario(usuario);
        inOrder.verify(ordenService).registrarOrden(any(Orden.class)); // Verificación del registro de orden
        inOrder.verify(usuarioRepository).obtenerUsuario("1");
        inOrder.verify(usuarioRepository).actualizarUsuario(usuario);
        inOrder.verify(usuarioRepository).eliminarUsuario("1");
    }
}
