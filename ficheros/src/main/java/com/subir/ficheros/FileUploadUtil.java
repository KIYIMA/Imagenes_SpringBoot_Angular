/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.subir.ficheros;

import java.io.*;
import java.nio.file.*;
  
import org.springframework.web.multipart.MultipartFile;
  
public class FileUploadUtil {
      
    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) { //Obtiene el contenido del File para leerlo posteriormente
            //Une la ruta con el nombre de la imagen
            Path filePath = uploadPath.resolve(fileName);
            // Guarda el contenido obtenido anteriormente en la ruta "uploadDir",y si existe la reemplaza.
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
}
