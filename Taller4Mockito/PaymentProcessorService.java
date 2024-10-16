package Taller4Mockito;

public class PaymentProcessorService {

    private final ExternalPaymentGatewayService externalPaymentGatewayService;

    public PaymentProcessorService(ExternalPaymentGatewayService externalPaymentGatewayService) {
        this.externalPaymentGatewayService = externalPaymentGatewayService;
    }

    public String processPayment(String userId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        boolean paymentSuccess = externalPaymentGatewayService.makePayment(userId, amount);

        if (paymentSuccess) {
            return "Payment successful";
        } else {
            return "Payment failed";
        }
    }

    // Nuevo método para reembolsar pagos
    public String refundPayment(String userId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Refund amount must be greater than zero");
        }

        boolean refundSuccess = externalPaymentGatewayService.processRefund(userId, amount);

        if (refundSuccess) {
            return "Refund successful";
        } else {
            return "Refund failed";
        }
    }

    // Método que valida los detalles de la tarjeta
    public boolean validateCardDetails(String cardNumber, String cardHolderName) {
        return externalPaymentGatewayService.verifyCard(cardNumber, cardHolderName);
    }

    // Método que combina varios métodos para hacer un pago
    public String makeFullPayment(String userId, String cardNumber, String cardHolderName, double amount) {
        if (!validateCardDetails(cardNumber, cardHolderName)) {
            return "Invalid card details";
        }
        return processPayment(userId, amount);
    }
}
