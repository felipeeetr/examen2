package co.edu.poli.examen2_torres.modelo;

public class seguroVida extends seguro {

    private String beneficiario;

    public seguroVida(String numero, String fechaExpedicion, boolean estado, asegurado asegurado, String beneficiario) {
        super(numero, fechaExpedicion, estado, asegurado);
        this.beneficiario = beneficiario;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    @Override
    public String toString() {
        return "SeguroVida [" + super.toString() + ", beneficiario=" + beneficiario + "]";
    }
}