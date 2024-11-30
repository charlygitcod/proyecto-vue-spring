package com.example.sprinbootsql.Entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empleados")
public class Usuario {

    @Id
    @Column(name = "documento")
    private Integer documento;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "segundo_nombre", length = 255)
    private String segundo_nombre;

    @Column(name = "apellido", nullable = false, length = 255)
    private String apellido;

    @Column(name = "segundo_apellido", length = 255)
    private String segundo_apellido;

    @Column(name = "correo", unique = true, length = 255)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private TipoUsuario rol;  // Mantengo "rol" como nombre de la propiedad

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha_registro;  // Cambié el nombre de "fechaRegistro" a "fecha_registro"

    @Column(name = "direccion", length = 255)
    private String direccion;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Credenciales credenciales;

    @OneToMany(mappedBy = "empleado_documento")
    private List<EntregaEPP> entregasEPP;

    @OneToMany(mappedBy = "empleado_documento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroPeso> registrosPesos;

    public Usuario() {
    }

    public Usuario(Integer documento, String nombre, String segundo_nombre, String apellido, String segundo_apellido, String correo, String telefono, TipoUsuario rol, Integer edad, String descripcion, Date fecha_registro, String direccion) {
        this.documento = documento;
        this.nombre = nombre;
        this.segundo_nombre = segundo_nombre;
        this.apellido = apellido;
        this.segundo_apellido = segundo_apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.edad = edad;
        this.descripcion = descripcion;
        this.fecha_registro = fecha_registro;  // Usamos el nuevo nombre de la propiedad
        this.direccion = direccion;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuario getRol() {
        return rol;
    }

    public void setRol(TipoUsuario rol) {
        this.rol = rol;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "Usuario:" +
                "\ndocumento= " + documento +
                "\nnombre= " + nombre +
                "\nsegundo_nombre= " + segundo_nombre +
                "\napellido= " + apellido +
                "\nsegundo_apellido= " + segundo_apellido +
                "\ncorreo= " + correo +
                "\ntelefono= " + telefono +
                "\nRol= " + rol +
                "\nedad= " + edad +
                "\ndescripcion= " + descripcion +
                "\nfecha_registro= " + fecha_registro +
                "\ndireccion= " + direccion;
    }
}
