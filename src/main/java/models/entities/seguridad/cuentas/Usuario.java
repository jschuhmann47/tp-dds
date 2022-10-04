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

    @Column(name = "nombre_de_usuario")
    private String nombreUsuario;
    @Column(name = "contrasenia")
    private String contrasenia;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    @ElementCollection
    @CollectionTable(name = "permisos",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"))
    @Enumerated(value = EnumType.STRING)
    private List<Permiso> permisos = new ArrayList<>();

    public Usuario(String nombreUsuario, String contrasenia, Rol rol, Permiso ... permisos) { //todo preguntar de crear cuentas mediante la pagina
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = HashingHelper.hashear(contrasenia); //validar que la contrasenia sea fuerte antes de instanciar
        this.rol = rol;
        this.permisos.addAll(Arrays.asList(permisos));
    }

    public Usuario(){

    }


}
