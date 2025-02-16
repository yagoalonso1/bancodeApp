package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona el historial de operaciones bancarias de un usuario.
 */
public class HistorialOperaciones {
    private List<Operacion> operaciones;

    public HistorialOperaciones() {
        this.operaciones = new ArrayList<>();
    }

    /**
     * Agrega una nueva operación al historial.
     * @param operacion La operación a agregar (depósito o retiro).
     */
    public void agregarOperacion(Operacion operacion) {
        operaciones.add(operacion);
    }

    /**
     * Obtiene el historial completo de operaciones.
     * @return Lista de operaciones realizadas.
     */
    public List<Operacion> obtenerHistorial() {
        return operaciones;
    }
}