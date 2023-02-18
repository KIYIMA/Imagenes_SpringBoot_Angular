/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.subir.ficheros;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
// @CrossOrigin(origins = "http://127.0.0.1:8000/" )
@CrossOrigin(origins = "http://localhost:4200/")
public class MyController {

    @Autowired
    UserService userService;

    private final Path directorioImagenes = Paths.get("images");

    @GetMapping("/photos/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(directorioImagenes + "/" + filename));

        } catch (IOException e) {

            return new ResponseEntity(new Mensaje(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/photos/delete/{usuario_id}")
    public ResponseEntity<UserEntity> deleteImage(@PathVariable("usuario_id") Long id) throws IOException {

        UserEntity usuarioEntity;
        User usuario = new User();

        if (userService.existById(id)) {
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                usuarioEntity = userService.getById(id).get();
                // byte[] byteImg = multipartFile.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + usuarioEntity.getImagen());
                Files.delete(rutaCompleta);
                usuarioEntity.setImagen(null);
                userService.save(usuarioEntity);

                // usuario.setImagen(multipartFile.getOriginalFilename());
            } catch (IOException e) {
                return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity(new Mensaje("Imagen eliminada"), HttpStatus.OK);
    }

    @PostMapping("/photos/edit")
    public ResponseEntity<?> editImage(@RequestParam("user_id") Long user_id,
            @RequestParam("fichero") MultipartFile multipartFile) throws IOException {

        UserEntity usuarioEntity;
        User usuario = new User();

        if (userService.existById(user_id)) {
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                usuarioEntity = userService.getById(user_id).get();

                // Elimino la imagen anterior
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + usuarioEntity.getImagen());
                if (Files.exists(rutaCompleta)) {
                    Files.delete(rutaCompleta);
                }

                // Agrego la imagen al fichero y a la base de datos
                byte[] byteImg = multipartFile.getBytes();
                rutaCompleta = Paths.get(rutaAbsoluta + "//" + getNameForSave(multipartFile.getOriginalFilename()));
                Files.write(rutaCompleta, byteImg);

                usuarioEntity.setImagen(getNameForSave(multipartFile.getOriginalFilename()));
                userService.save(usuarioEntity);

            } catch (IOException e) {
                return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity(new Mensaje("Imagen editada"), HttpStatus.OK);
    }

    @PostMapping("/photos/add")
    public ResponseEntity<?> addImage(@RequestParam("user_id") Long user_id,
            @RequestParam("fichero") MultipartFile multipartFile) throws IOException {

        UserEntity usuarioEntity;
        User usuario = new User();

        if (userService.existById(user_id)) {
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                // Guardo imagen en el directorio "images"
                byte[] byteImg = multipartFile.getBytes();
                Path rutaCompleta = Paths
                        .get(rutaAbsoluta + "//" + getNameForSave(multipartFile.getOriginalFilename()));
                Files.write(rutaCompleta, byteImg);

                // usuario.setImagen(multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            usuarioEntity = userService.getOne(user_id).get();
            usuarioEntity.setImagen(getNameForSave(multipartFile.getOriginalFilename()));
            userService.save(usuarioEntity);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(new Mensaje("Imagen agregada"), HttpStatus.OK);
    }

    @GetMapping("/listar/imagenes")
    public Stream<Path> loadAll() {
        try {

            return Files.walk(directorioImagenes, 1).filter(path -> !path.equals(directorioImagenes))
                    .map(directorioImagenes::relativize);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("No se pueden cargar los archivos!! ");
        }

    }

    @PostMapping("/users/save")
    public ResponseEntity<UserEntity> saveUser(@RequestParam("usuario") String strUsuario,
            @RequestParam("fichero") MultipartFile multipartFile) throws IOException {

        UserEntity usuarioEntity;
        User usuario = new User();

        Date localdate = new Date();
        SimpleDateFormat fech = new SimpleDateFormat("ddMMyyyyHHmmssZ");

        String imageName = fech.format(localdate) + "-" + multipartFile.getOriginalFilename();

        if (!multipartFile.isEmpty()) {

            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] byteImg = multipartFile.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imageName);
                Files.write(rutaCompleta, byteImg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        // Convertimos a objeto el JSON que recibimos del Front, diciendole de que clase
        // es.
        usuario = gson.fromJson(strUsuario, User.class);
        usuario.setImagen(imageName);

        // Cargo los datos en la tabla de user_entity
        usuarioEntity = new UserEntity(usuario.getNombre(), usuario.getApellido(), usuario.getEmail(),
                usuario.getImagen());
        userService.save(usuarioEntity);

        return new ResponseEntity(usuarioEntity, HttpStatus.OK);

    }

    @GetMapping("/lista")
    public ResponseEntity<List<UserEntity>> list() {

        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
        List<UserEntity> list = userService.list();

        return new ResponseEntity(list, HttpStatus.OK);

    }

    // Methods helpers
    public String getNameForSave(String originalImageName) {
        Date localdate = new Date();
        SimpleDateFormat fech = new SimpleDateFormat("ddMMyyyyHHmmssZ");

        String imageName = fech.format(localdate) + "-" + originalImageName;

        return imageName;
    }
}
