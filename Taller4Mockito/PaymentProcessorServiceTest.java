package Taller4Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PaymentProcessorServiceTest {

    @Mock
    private ExternalPaymentGatewayService externalPaymentGatewayService;

    @InjectMocks
    private PaymentProcessorService paymentProcessorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    // Test de processPayment (Mockito: usaremos patrón: whenThenReturn, y matchers: anyString() y eq())
    // Podremos comprobar el resultado utilizando un assert de JUnit
    @Test
    public void testProcessPayment_Success() {
        when(externalPaymentGatewayService.makePayment(anyString(), eq(150.0))).thenReturn(true);
        String result = paymentProcessorService.processPayment("user123", 150.0);
        assertEquals("Payment successful", result);
    }

    // Test de refundPayment (Mockito: usaremos patrón: whenThenReturn, y matchers: anyString() y eq())
    // Podremos comprobar el resultado utilizando un assert de JUnit
    @Test
    public void testRefundPayment_Success() {
        when(externalPaymentGatewayService.processRefund(anyString(), eq(200.0))).thenReturn(true);
        String result = paymentProcessorService.refundPayment("user123", 200.0);
        assertEquals("Refund successful", result);
    }

    // Test de la excepción de refundPayment (Mockito: usaremos patrón: whenThenThrow, y matchers: anyString() y anyDouble())
    // Podremos comprobar si se lanza la excepción o no con un assert de JUnit.
    @Test
    public void testRefundPayment_ThrowsException() {
        when(externalPaymentGatewayService.processRefund(anyString(), anyDouble()))
                .thenThrow(new RuntimeException("Refund error"));

        assertThrows(RuntimeException.class, () -> {
            paymentProcessorService.refundPayment("user123", 100.0);
        });
    }

    // Test de validateCardDetails (Mockito: usaremos patrón: whenThenReturn, y matchers: argThat para comprobar
    // que el cardNumber empieza por 4, en ese caso verificamos la tarjeta. Usaremos también anyString()
    // Podremos comprobar el resultado utilizando un assert de JUnit
    @Test
    public void testValidateCardDetails_CustomMatcher() {
        when(externalPaymentGatewayService.verifyCard(argThat(cardNumber -> cardNumber.startsWith("4")), anyString()))
                .thenReturn(true);

        boolean result = paymentProcessorService.validateCardDetails("5111111111111111", "John Doe");

        assertTrue(result);
    }

    // Test de makeFullPayment (Mockito: usaremos patrón: whenThenReturn y whenThenCallRealMethod (para el método verifyCard
    // de paymentGatewayService, y matchers: anyString(), anyDouble().
    // Podremos comprobar el resultado utilizando un assert de JUnit
    @Test
    public void testMakeFullPayment_RealMethodCall() {
        when(externalPaymentGatewayService.verifyCard(anyString(), anyString())).thenCallRealMethod();
        when(externalPaymentGatewayService.makePayment(anyString(), anyDouble())).thenReturn(true);

        String result = paymentProcessorService.makeFullPayment("user123", "4111111111111111", "John Doe", 150.0);

        assertEquals("Payment successful", result);
    }

    // Test de processPayment (Mockito: usaremos patrón: whenThenReturn) para comprobar un caso fallido.
    // y matchers: anyString(), anyDouble().
    // Podremos comprobar el resultado falido utilizando un assert de JUnit
    @Test
    public void testProcessPayment_Failure() {
        when(externalPaymentGatewayService.makePayment(anyString(), anyDouble())).thenReturn(false);
        String result = paymentProcessorService.processPayment("user123", 150.0);
        assertEquals("Payment failed", result);
    }
}