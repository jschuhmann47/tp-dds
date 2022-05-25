package domain.seguridad.chequeos;


public abstract class Chequeo {

    boolean estaActivado=true;
    public abstract boolean chequear(String contrasenia);

    public void activarChequeo(){
        this.estaActivado=true;
    }

    public void desactivarChequeo(){
        this.estaActivado=false;
    }

    public boolean estaActivo() {
        return estaActivado;
    }
}

