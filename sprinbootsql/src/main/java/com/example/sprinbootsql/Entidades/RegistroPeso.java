package com.example.sprinbootsql.Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "registros_pesos")
public class RegistroPeso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer id_registro;

    @Column(name = "empleado_documento", nullable = false)
    private Integer empleado_documento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoRegistro tipo;

    @Column(name = "peso", nullable = false, precision = 10, scale = 2)
    private BigDecimal peso;

    @Column(name = "turno", nullable = false, length = 20)
    private String turno;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "hora", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;


    // Relación ManyToOne con Usuario
    @ManyToOne
    @JoinColumn(name = "empleado_documento", referencedColumnName = "documento", insertable = false, updatable = false, nullable = false)
    private Usuario usuario;

    // Constructor vacío
    public RegistroPeso() {}

    // Constructor con parámetros
    public RegistroPeso(Integer empleado_documento, TipoRegistro tipo, BigDecimal peso, String turno, Date fecha, LocalTime hora) {
        this.empleado_documento = empleado_documento;  // Asignar la referencia al documento del empleado
        this.tipo = tipo;
        this.peso = peso;
        this.turno = turno;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y setters
    public Integer getId_registro() {
        return id_registro;
    }

    public void setId_registro(Integer id_registro) {
        this.id_registro = id_registro;
    }

    public Integer getEmpleado_documento() {
        return empleado_documento;
    }

    public void setEmpleado_documento(Integer empleado_documento) {
        this.empleado_documento = empleado_documento;
    }

    public TipoRegistro getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegistro tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "RegistroPeso{" +
                "id_registro=" + id_registro +
                ", empleado_documento=" + empleado_documento +
                ", tipo=" + tipo +
                ", peso=" + peso +
                ", turno='" + turno + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
