package Taller4Mockito;

public class ExternalPaymentGatewayService {

    public boolean makePayment(String userId, double amount) {
        // Simulación de pago (en la vida real, interactuaría con un sistema externo)
        return amount > 100;
    }

    public boolean processRefund(String userId, double amount) {
        // Simulación de reembolso (en la vida real, interactuaría con un sistema externo)
        return amount < 500;
    }

    public boolean verifyCard(String cardNumber, String cardHolderName) {
        // Simulación de verificación de tarjeta
        return cardNumber.startsWith("4") && cardHolderName.length() > 2;
    }
}
