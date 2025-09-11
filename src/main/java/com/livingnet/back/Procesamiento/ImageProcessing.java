// package com.livingnet.back.Procesamiento;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;


// import java.util.Scanner;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.time.format.DateTimeFormatter;

// @RestController
// @RequestMapping("/image")
// public class ImageProcessing {

//     private static final String UPLOAD_DIR = "uploads/";

//     // POST /image/upload
//     @PostMapping("/upload")
//     public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//         try {
//             // Crear carpeta si no existe
//             File uploadDir = new File(UPLOAD_DIR);
//             if (!uploadDir.exists()) {
//                 uploadDir.mkdirs();
//             }

//             // Guardar la imagen
//             // Definir el formato: dd-MM-yyyy-HH-mm-ss
//             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

//             // Formatear fecha y hora
//             String formattedDateTime = now.format(formatter);

//             // Construir la ruta del archivo
//             String filePath = UPLOAD_DIR + "file-" + formattedDateTime + ".jpg";
            
//             File savedFile = new File(filePath);
//             try (FileOutputStream fos = new FileOutputStream(savedFile)) {
//                 fos.write(file.getBytes());
//             }

//             // Ejecutar script de Python con la ruta del archivo
//             String result = runPythonScript(filePath);

//             return ResponseEntity.ok("Imagen guardada en: " + filePath + "\nResultado del script: " + result);

//         } catch (IOException e) {
//             return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
//         }
//     }

//     // Método para ejecutar el script de Python
//     private String runPythonScript(String imagePath) {
//         try {
//             // Ajusta la ruta del script
//             String pythonScript = "scripts/imageProcessing.py";

//             ProcessBuilder pb = new ProcessBuilder("python", pythonScript, imagePath);
//             pb.redirectErrorStream(true);

//             Process process = pb.start();

//             // Leer salida del script
//             String output;
//             try (Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
//                 output = scanner.hasNext() ? scanner.next() : "";
//             }

//             int exitCode = process.waitFor();
//             return "ExitCode=" + exitCode + "\nOutput:\n" + output;

//         } catch (Exception e) {
//             return "Error ejecutando script: " + e.getMessage();
//         }
//     }
// }