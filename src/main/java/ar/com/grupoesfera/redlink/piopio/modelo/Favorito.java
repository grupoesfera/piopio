package ar.com.grupoesfera.redlink.piopio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Favorito {

    @Id private Long id;
    @ManyToOne private Pio pio;
    @ManyToOne private Usuario fan;

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
