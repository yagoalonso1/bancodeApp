package models;

/**
 * Clase singleton que mantiene el estado global de la aplicación.
 * Gestiona la cuenta bancaria actualmente en uso.
 * 
 * @author Yago
 * @version 1.0
 */
public class AppState {
    private static AppState instance;
    private CuentaBancaria cuentaActual;

    /**
     * Constructor privado para implementar el patrón singleton.
     */
    private AppState() {
        // Se inicializa con una cuenta predeterminada de 1000€
        cuentaActual = new CuentaBancaria(1000.0);
    }

    /**
     * Obtiene la instancia única de AppState.
     * Si no existe, la crea.
     * 
     * @return Instancia única de AppState
     */
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    /**
     * Obtiene la cuenta bancaria actual.
     * @return CuentaBancaria actualmente en uso
     */
    public CuentaBancaria getCuentaActual() {
        return cuentaActual;
    }

    /**
     * Establece la cuenta bancaria actual.
     * @param cuenta Nueva cuenta bancaria a utilizar
     */
    public void setCuentaActual(CuentaBancaria cuenta) {
        this.cuentaActual = cuenta;
    }
}