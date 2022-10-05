package models.entities.seguridad.cuentas;

import lombok.Getter;
import models.helpers.HashingHelper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
public class Usuario {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nombre_de_usuario",nullable = false)
    private String nombreUsuario;
    @Column(name = "contrasenia",nullable = false)
    private String contrasenia;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rol")
    private Rol rol; //todo que el administrador setee los valores de los .config

    @ElementCollection
    @CollectionTable(name = "permisos",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"))
    @Enumerated(value = EnumType.STRING)
    private List<Permiso> permisos = new ArrayList<>();

    @Column(name = "organizacion_id")
    private int organizacionId;
    @Column(name = "trabajador_id")
    private int trabajadorId;
    @Column(name = "agente_sectorial_id")
    private int agenteSectorialId;

    private TipoRecurso tipoRecurso;


    public Usuario(String nombreUsuario, String contrasenia, Rol rol, Integer id, TipoRecurso tipo, Permiso ... permisos) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = HashingHelper.hashear(contrasenia); //validar que la contrasenia sea fuerte antes de instanciar
        this.rol = rol;
        this.permisos.addAll(Arrays.asList(permisos));
        this.setearTipo(id,tipo);
    }

    private void setearTipo(Integer id, TipoRecurso tipo) {
        this.tipoRecurso = tipo;
        switch (tipo){
            case ORGANIZACION:
                this.organizacionId = id;
            case TRABAJADOR:
                this.trabajadorId = id;
            case AGENTE_SECTORIAL:
                this.agenteSectorialId = id;
        }
    }

    private Integer obtenerIdRecurso(){
        switch (this.getTipoRecurso()){
            case ORGANIZACION:
                return this.organizacionId;
            case TRABAJADOR:
                return this.trabajadorId;
            case AGENTE_SECTORIAL:
                return this.agenteSectorialId;
        }
        return null;
    }

    public Usuario(){

    }


}
