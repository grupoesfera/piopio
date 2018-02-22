package ar.com.grupoesfera.redlink.piopio.modelo;


public class Favorito {

    private Pio pio;
    private Usuario fan;
    
    public Pio getPio() {
    
        return pio;
    }
    
    public void setPio(Pio pio) {
    
        this.pio = pio;
    }
    
    public Usuario getFan() {
    
        return fan;
    }
    
    public void setFan(Usuario fan) {
    
        this.fan = fan;
    }
}
