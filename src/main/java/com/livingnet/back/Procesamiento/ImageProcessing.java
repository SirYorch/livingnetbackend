package com.livingnet.back.Procesamiento;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.livingnet.back.Model.ReporteModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/image")
public class ImageProcessing {

    public static final String UPLOAD_DIR = "uploads/";

    // POST /image/upload
    @PostMapping("/upload")
    public ReporteModel uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Crear carpeta si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Guardar la imagen
            // Definir el formato: dd-MM-yyyy-HH-mm-ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            // Formatear fecha y hora
            String formattedDateTime = now.format(formatter);

            // Construir la ruta del archivo
            String filePath = "file-" + formattedDateTime + ".jpg";
            
            File savedFile = new File(UPLOAD_DIR +filePath);
            try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                fos.write(file.getBytes());
            }

            

            ReporteModel reporte = new ReporteModel();
            reporte.setFoto_url(filePath);
            reporte  = runPythonScript(filePath, reporte);
                
            return reporte;

        } catch (IOException e) {
            // Manejar el error devolviendo un ReporteModel vacío o con información de error
            ReporteModel errorReporte = new ReporteModel();
            errorReporte.setFoto_url(null); // o algún valor que indique error
            // Puedes agregar más información de error si tu modelo lo permite
            return errorReporte;
        }
    }

    @GetMapping("/{file}")
    public ResponseEntity<byte[]> getImage(@PathVariable String file) {
        try {
            System.out.println("Buscando imagen: " + file);
            Path imgPath = new File(UPLOAD_DIR + file).toPath();

            if (!Files.exists(imgPath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] bytes = Files.readAllBytes(imgPath);

            // Detectar tipo MIME automáticamente
            String mimeType = Files.probeContentType(imgPath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    // Método para ejecutar el script de Python
    private ReporteModel runPythonScript(String imagePath, ReporteModel reporte) {
        try {
            // Ajusta la ruta del script
            String pythonScript = "scripts/imageProcessing.py";

            ProcessBuilder pb = new ProcessBuilder("python", pythonScript, imagePath);
            pb.redirectErrorStream(true);

            // Process process = pb.start();

            // Leer salida del script
            // String output;
            // try (Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
            //     output = scanner.hasNext() ? scanner.next() : "";
            // }

            // int exitCode = process.waitFor();
            return reporte;

        } catch (Exception e) {
            return reporte;
        }
    }
}