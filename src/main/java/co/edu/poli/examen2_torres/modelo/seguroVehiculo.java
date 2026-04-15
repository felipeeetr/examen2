package co.edu.poli.examen2_torres.modelo;

public class seguroVehiculo extends seguro {

    private String marca;

    public seguroVehiculo(String numero, String fechaExpedicion, boolean estado, asegurado asegurado, String marca) {
        super(numero, fechaExpedicion, estado, asegurado);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "SeguroVehiculo [" + super.toString() + ", marca=" + marca + "]";
    }
}
