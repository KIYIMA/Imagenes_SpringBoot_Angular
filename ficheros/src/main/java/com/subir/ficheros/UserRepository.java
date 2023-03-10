/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.subir.ficheros;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findById(Long id);

    public Optional<UserEntity> findByNombre(String nombre);

    public Optional<UserEntity> findByApellido(String apellido);

    public Optional<UserEntity> findByImagen(String imagen);

    public boolean existsById(Long id);

    public boolean existsByNombre(String nombre);

    public boolean existsByApellido(String apellido);

    public boolean existsByImagen(String imagen);

}
