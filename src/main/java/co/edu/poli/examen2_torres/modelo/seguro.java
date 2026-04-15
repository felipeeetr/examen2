package co.edu.poli.examen2_torres.modelo;

public abstract class seguro {

    private String numero;
    private String fechaExpedicion;
    private boolean estado;
    private asegurado asegurado;

    public seguro(String numero, String fechaExpedicion, boolean estado, asegurado asegurado) {
        this.numero = numero;
        this.fechaExpedicion = fechaExpedicion;
        this.estado = estado;
        this.asegurado = asegurado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public asegurado getasegurado() {
        return asegurado;
    }

    public void setasegurado(asegurado asegurado) {
        this.asegurado = asegurado;
    }

    public String cancelar() {
        this.estado = false;
        return "Seguro " + numero + " CANCELADO.";
    }

    public String activar() {
        this.estado = true;
        return "Seguro " + numero + " ACTIVADO.";
    }

    @Override
    public String toString() {
        return "numero=" + numero + ", fechaExpedicion=" + fechaExpedicion + ", estado=" + estado + ", asegurado=" + asegurado;
    }
}
