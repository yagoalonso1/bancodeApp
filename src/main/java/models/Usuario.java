package models;

/**
 * Clase que representa a un usuario del sistema con autenticación básica.
 */
public class Usuario {
    private String nombre;
    private String password;
    private String respuestaSeguridad;

    public Usuario(String nombre, String password, String respuestaSeguridad) {
        this.nombre = nombre;
        this.password = password;
        this.respuestaSeguridad = respuestaSeguridad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Verifica si la contraseña ingresada es correcta.
     * @param password Contraseña ingresada.
     * @return true si la contraseña es correcta, false en caso contrario.
     */
    public boolean autenticar(String password) {
        return this.password.equals(password);
    }

    /**
     * Verifica la respuesta de seguridad del usuario.
     * @param respuesta Respuesta ingresada.
     * @return true si la respuesta es correcta, false en caso contrario.
     */
    public boolean verificarRespuestaSeguridad(String respuesta) {
        return this.respuestaSeguridad.equals(respuesta);
    }
}