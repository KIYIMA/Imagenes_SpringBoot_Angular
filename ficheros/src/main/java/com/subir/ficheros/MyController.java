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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
 
@RestController
@CrossOrigin(origins = "http://127.0.0.1:8000/" )
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
            
            System.out.println("Error = "+ e.getMessage());
        }
        
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
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
    public ResponseEntity<UserEntity> saveUser(@RequestParam("usuario") String strUsuario, @RequestParam("fichero") MultipartFile multipartFile) throws IOException {
        
        UserEntity usuarioEntity ;
        User usuario = new User();
        
        if(!multipartFile.isEmpty()){
            
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();  
            try {
                byte[] byteImg= multipartFile.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + multipartFile.getOriginalFilename());
                Files.write(rutaCompleta, byteImg);
                
                usuario.setImagen(multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        
        Gson gson = new Gson();
        //Convertimos a objeto el JSON que recibimos del Front, diciendole de que clase es.
        usuario = gson.fromJson(strUsuario, User.class);
        usuario.setImagen(multipartFile.getOriginalFilename());
         
        usuarioEntity = new UserEntity(usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getImagen());
        userService.save(usuarioEntity);
        
        return new ResponseEntity(usuarioEntity,HttpStatus.OK);
        
    }
    @GetMapping("/lista")
    public ResponseEntity<List<UserEntity>> list(){
        
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
        List<UserEntity> list = userService.list();
        return new ResponseEntity(list, HttpStatus.OK);
        
    }
    
    
}
