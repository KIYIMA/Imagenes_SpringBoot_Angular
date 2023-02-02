
package com.subir.ficheros;


public class User {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String imagen;

    public User( String nombre, String apellidos, String email, String imagen) {
        
        this.nombre = nombre;
        this.apellido = apellidos;
        this.email = email;
        this.imagen = imagen;
    }

    public User() {
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public String getImagen() {
        return imagen;
    }
 
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
