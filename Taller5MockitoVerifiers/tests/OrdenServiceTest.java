package Taller5MockitoVerifiers.tests;

import java.util.Arrays;
import java.util.List;

import Taller5MockitoVerifiers.models.Orden;
import Taller5MockitoVerifiers.repositories.OrdenRepository;
import Taller5MockitoVerifiers.services.OrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository; // Mock del repositorio

    private OrdenService ordenService;

    @BeforeEach
    public void setUp() {
        // Inicializar mocks antes de cada test
        MockitoAnnotations.openMocks(this);
        ordenService = new OrdenService(ordenRepository);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    @Test
    void testRegistrarOrden() {
        // Crear una orden para registrar
        Orden orden = new Orden("1", "1", 100.0);

        // Registrar la orden
        ordenService.registrarOrden(orden);

        // Verificar que el método del repositorio fue llamado
        verify(ordenRepository).agregarOrden(orden);
        verify(ordenRepository, times(1)).agregarOrden(orden);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    @Test
    void testEliminarOrden() {
        String ordenId = "1";

        // Eliminar la orden
        ordenService.eliminarOrden(ordenId);

        // Verificar que el método del repositorio fue llamado
        verify(ordenRepository).eliminarOrden(ordenId); // Verificación del método
        verify(ordenRepository, times(1)).eliminarOrden(ordenId);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    // Verificar que NO se ha llamado a otros métodos del repositorio.
    @Test
    void testBuscarOrden() {
        String ordenId = "1";
        Orden orden = new Orden(ordenId, "1", 100.0);

        // Configurar el comportamiento del mock
        when(ordenRepository.obtenerOrden(ordenId)).thenReturn(orden);

        // Buscar la orden
        Orden result = ordenService.buscarOrden(ordenId);

        // Verificar que el método del repositorio fue llamado
        verify(ordenRepository).obtenerOrden(ordenId); // Verificación del método
        verify(ordenRepository, times(1)).obtenerOrden(ordenId); // Verificación del método
        // Verificar que el método del repositorio no fue llamado
        verify(ordenRepository, never()).eliminarOrden(any()); // Verificación del método

        // Aserciones sobre el resultado
        assertEquals(orden, result);
    }

    // Verificar que se ha llamado a los métodos correspondientes del repositorio.
    // Verificar que se ha llamado el número de veces necesario.
    // Verificar que NO se ha llamado a otros métodos del repositorio.
    // Comprobar el resultado del servicio.
    @Test
    void testObtenerOrdenesPorUsuario() {
        String usuarioId = "1";
        Orden orden1 = new Orden("1", usuarioId, 100.0);
        Orden orden2 = new Orden("2", usuarioId, 150.0);
        List<Orden> ordenes = Arrays.asList(orden1, orden2);

        // Obtener todas las órdenes por usuario
        List<Orden> result = ordenService.obtenerOrdenesPorUsuario(usuarioId);

        // Verificar que el método del repositorio fue llamado
        verify(ordenRepository).obtenerOrdenesPorUsuario(any(String.class)); // Verificación del método
        verify(ordenRepository, times(1)).obtenerOrdenesPorUsuario(any(String.class)); //
        verify(ordenRepository, never()).eliminarOrden(any());
    }

    // Testear un registro, búsqueda y eliminación de una orden.
    // Verificar el orden de las llamadas (Usaremos InOrder de Mockito).
    @Test
    void testInvocationOrder() {
        Orden orden = new Orden("1", "1", 100.0);

        // Registrar la orden
        ordenService.registrarOrden(orden);
        // Buscar la orden
        ordenService.buscarOrden("1");
        // Eliminar la orden
        ordenService.eliminarOrden("1");

        // Verificar el orden de invocaciones
        InOrder inOrder = Mockito.inOrder(ordenRepository);
        inOrder.verify(ordenRepository).agregarOrden(orden);
        inOrder.verify(ordenRepository).obtenerOrden("1");
        inOrder.verify(ordenRepository).eliminarOrden("1");
    }
}
