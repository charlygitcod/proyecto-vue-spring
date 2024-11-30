package com.example.sprinbootsql.Entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entregas_epp")
public class EntregaEPP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Integer id_entrega;

    @Column(name = "empleado_documento", nullable = false)
    private Integer empleado_documento;

    @Column(name = "nombre_ope", nullable = false, length = 255)
    private String nombre_ope;

    @Column(name = "nombre_epp", nullable = false, length = 255)
    private String nombre_epp;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_documento", referencedColumnName = "documento", insertable = false, updatable = false, nullable = false)
    private Usuario usuario;

    // Constructor vacío
    public EntregaEPP() {}

    // Constructor con parámetros
    public EntregaEPP(Integer empleado_documento, String nombre_ope, String nombre_epp, Integer cantidad, Date fecha) {
        this.empleado_documento = empleado_documento;
        this.nombre_ope = nombre_ope;
        this.nombre_epp = nombre_epp;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    // Getters y setters
    public Integer getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(Integer id_entrega) {
        this.id_entrega = id_entrega;
    }

    public Integer getEmpleado_documento() {
        return empleado_documento;
    }

    public void setEmpleado_documento(Integer empleado_documento) {
        this.empleado_documento = empleado_documento;
    }

    public String getNombre_ope() {
        return nombre_ope;
    }

    public void setNombre_ope(String nombre_ope) {
        this.nombre_ope = nombre_ope;
    }

    public String getNombre_epp() {
        return nombre_epp;
    }

    public void setNombre_epp(String nombre_epp) {
        this.nombre_epp = nombre_epp;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "EntregaEPP{" +
                "id_entrega=" + id_entrega +
                ", empleado_documento=" + empleado_documento +
                ", nombre_ope='" + nombre_ope + '\'' +
                ", nombre_epp='" + nombre_epp + '\'' +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                '}';
    }
}
