package ar.com.grupoesfera.main;

import javax.persistence.EntityManager;

public interface Action<T> {

    T execute(EntityManager entities);
}
