package co.edu.poli.examen2_torres.modelo;

public abstract class seguro {

    private String numero;
    private String fecha_exp;
    private boolean estado;
    private asegurado asegurado;

    public seguro(String numero, String fecha_exp, boolean estado, asegurado asegurado) {
        this.numero = numero;
        this.fecha_exp = fecha_exp;
        this.estado = estado;
        this.asegurado = asegurado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaExp() {
        return fecha_exp;
    }

    public void setFechaExp(String fecha_exp) {
        this.fecha_exp = fecha_exp;
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
        return "numero=" + numero + ", fechaExp=" + fecha_exp + ", estado=" + estado + ", asegurado=" + asegurado;
    }
}
