package com.godwarrior.paginationfx.application;

import com.godwarrior.paginationfx.utils.annotation.ColumnName;

public class Usuario {

    @ColumnName(name = "id")
    private long id;

    @ColumnName(name = "nombre")
    private String nombre;

    @ColumnName(name = "apellido")
    private String apellido;

    @ColumnName(name = "email")
    private String email;

    @ColumnName(name = "fecha_nacimiento")
    private java.sql.Date fechaNacimiento;

    @ColumnName(name = "telefono")
    private String telefono;

    @ColumnName(name = "direccion")
    private String direccion;

    @ColumnName(name = "ciudad")
    private String ciudad;

    @ColumnName(name = "pais")
    private String pais;

    @ColumnName(name = "activo")
    private boolean activo;

    @ColumnName(name = "hora_registro")
    private java.sql.Time horaRegistro;

    // Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.sql.Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.sql.Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public java.sql.Time getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(java.sql.Time horaRegistro) {
        this.horaRegistro = horaRegistro;
    }
}
