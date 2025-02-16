package models;

/**
 * Clase singleton para manejar el estado global de la aplicación,
 * manteniendo la cuenta bancaria actual del usuario autenticado.
 */
public class AppState {
    private static AppState instance;
    private CuentaBancaria cuentaActual;

    private AppState() {
        // Se inicializa con una cuenta predeterminada de 1000€
        cuentaActual = new CuentaBancaria(1000.0);
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public CuentaBancaria getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(CuentaBancaria cuenta) {
        this.cuentaActual = cuenta;
    }
}