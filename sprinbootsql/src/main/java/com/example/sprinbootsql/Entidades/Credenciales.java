package com.example.sprinbootsql.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "credenciales")
public class Credenciales {

    @Id
    @Column(name = "empleado_documento")
    private Integer empleadoDocumento;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // Relación ManyToOne con Usuario, donde empleadoDocumento se mapea a documento
    @ManyToOne
    @JoinColumn(name = "empleado_documento", referencedColumnName = "documento", insertable = false, updatable = false)
    private Usuario usuario;  // Relación con la entidad Usuario

    public Credenciales() {}

    public Credenciales(Integer empleadoDocumento, String password, Usuario usuario) {
        this.empleadoDocumento = empleadoDocumento;
        this.password = password;
        this.usuario = usuario;
    }

    // Getters y setters
    public Integer getEmpleadoDocumento() {
        return empleadoDocumento;
    }

    public void setEmpleadoDocumento(Integer empleadoDocumento) {
        this.empleadoDocumento = empleadoDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
