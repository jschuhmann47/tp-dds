package models.repositories;

import lombok.Getter;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

@Getter
public class BusquedaCondicional {
    private final Predicate condicionPredicado;
    private final CriteriaQuery condicionCriterio;

    public BusquedaCondicional(Predicate condicionPredicado, CriteriaQuery condicionCriterio) {
        this.condicionPredicado = condicionPredicado;
        this.condicionCriterio = condicionCriterio;
    }
}
