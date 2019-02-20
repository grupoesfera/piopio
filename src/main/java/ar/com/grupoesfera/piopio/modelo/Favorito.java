package ar.com.grupoesfera.piopio.modelo;

public class Favorito {

    private Long id;
    private Pio pio;
    private Usuario fan;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

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

    public static Favorito nuevo() {

        return new Favorito();
    }

    public Favorito conId(Long id) {

        setId(id);
        return this;
    }

    public Favorito conPio(Pio pio) {

        setPio(pio);
        return this;
    }

    public Favorito conFan(Usuario fan) {

        setFan(fan);
        return this;
    }
}
