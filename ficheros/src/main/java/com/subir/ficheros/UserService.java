/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.subir.ficheros;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserEntity> list() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getOne(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getByNombreP(String nombre) {
        return userRepository.findByNombre(nombre);
    }

    public Optional<UserEntity> getById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> getByImagen(String imagen) {
        return userRepository.findByImagen(imagen);
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean existByNombre(String nombre) {
        return userRepository.existsByNombre(nombre);
    }

    public boolean existByApellido(String apellido) {
        return userRepository.existsByApellido(apellido);
    }

    public boolean existsByImagen(String imagen) {
        return userRepository.existsByImagen(imagen);
    }
}
