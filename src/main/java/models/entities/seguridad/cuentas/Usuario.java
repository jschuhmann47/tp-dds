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
    private String nombreDeUsuario;
    @Column(name = "contrasenia",nullable = false)
    private String contrasenia;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_recurso")
    private TipoRecurso tipoRecurso;


    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol, Integer idRecurso, TipoRecurso tipo, Permiso ... permisos) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = HashingHelper.hashear(contrasenia); //validar que la contrasenia sea fuerte antes de instanciar
        this.rol = rol;
        this.permisos.addAll(Arrays.asList(permisos));
        this.setearTipo(idRecurso,tipo);
    }

    private void setearTipo(Integer idRecurso, TipoRecurso tipo) {
        this.tipoRecurso = tipo;
        switch (tipo){
            case ORGANIZACION:
                this.organizacionId = idRecurso;
            case TRABAJADOR:
                this.trabajadorId = idRecurso;
            case AGENTE:
                this.agenteSectorialId = idRecurso;
        }
    }

    public Integer obtenerIdRecurso(){
        switch (this.getTipoRecurso()){
            case ORGANIZACION:
                return this.getOrganizacionId();
            case TRABAJADOR:
                return this.getTrabajadorId();
            case AGENTE:
                return this.getAgenteSectorialId();
        }
        return null;
    }

    public void agregarPermiso(Permiso permiso){
        this.getPermisos().add(permiso);
    }

    public Boolean tienePermisos(Permiso ... permisos){
        return Arrays.stream(permisos).allMatch(this::tienePermiso);
    }

    public Boolean tienePermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public Boolean tieneRol(Rol rol){
        return this.getRol()==rol;
    }

    public Boolean tieneRecurso(TipoRecurso tipoRecurso){
        return this.getTipoRecurso()==tipoRecurso;
    }


    public Usuario(){

    }


}
