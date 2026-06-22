package com.automotora.service_auth.model;

import java.lang.annotation.Inherited;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.automotora.service_auth.model.Rol;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString (exclude = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column (name = "nombre_usuario", unique = true , nullable = false)
    private String nombreUsuario;

    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "rut", unique = true, nullable = false)
    private String rut;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    name = "cliente_roles",
    joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
)
private Set<Rol> roles = new HashSet<>();


    public void agregarRol (Rol rol){
        if(this.roles == null){
            this.roles = new HashSet<>();
        }
        this.roles.add(rol);

    }
}