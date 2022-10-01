package models.repositories.daos;

import models.repositories.BusquedaCondicional;

import java.util.List;

public interface DAO<T> {
    List<T> buscarTodos();
    T buscar(int id);
    T buscar(BusquedaCondicional condicional); //BusquedaCondicional
    void agregar(Object unObjeto);
    void modificar(Object unObjeto);
    void eliminar(Object unObjeto);
}
