package ar.com.grupoesfera.piopio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favorito {

    @Id private Long id;
    @ManyToOne @JoinColumn(name="pioId") private Pio pio;
    @ManyToOne @JoinColumn(name="fanId") private Usuario fan;

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
    
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Favorito other = (Favorito) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
