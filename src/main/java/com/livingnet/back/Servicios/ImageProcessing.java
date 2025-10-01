package com.livingnet.back.Servicios;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// import com.livingnet.back.Gestion.ReportesGestion;
import com.livingnet.back.Model.ReporteModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//clase de servicio, sirve para solicitudes http referenciales a imagenes
@RestController
@RequestMapping("/image")
public class ImageProcessing {


    // variable estática de dónde se guardan las imagenes, usada para eliminar imagenes, crear imagenes, y obtener imagenes.
    public static final String UPLOAD_SIGN = "uploads/signatures/";
    public static final String UPLOAD_IMG = "uploads/images/";

    // private final ReportesGestion reportesGestion;

    // public ImageProcessing(ReportesGestion reportesGestion) {
    //     this.reportesGestion = reportesGestion;
    // }



    // permite subir imagenes, serán guardadas la variable estática según upload DIR
    public String uploadImage(MultipartFile file) {
        try {
            // Crear carpeta si no existe
            File uploadDir = new File(UPLOAD_IMG);
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
            String filePath = "file-" + formattedDateTime + ".jpg"; // las imagenes se construyen de la misma forma siempre, utilizamos file- la fecha, hora minutos y segundos a la que fue subida, y el archivo de extensión.
            
            File savedFile = new File(UPLOAD_IMG +filePath.toLowerCase());
            try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                fos.write(file.getBytes());
            }

            

            ReporteModel reporte = new ReporteModel();
            reporte.setFoto_url(filePath);
            //reporte  = runPythonScript(filePath, reporte);

            // scheduleImageValidation(filePath);
                
            return filePath;

        } catch (IOException e) {
            ReporteModel errorReporte = new ReporteModel();
            errorReporte.setFoto_url(null);
            return null;
        }
    }


    //método para poder visualizar las imagenes desde la vista del frontend.
    @GetMapping("/{file}")
    public ResponseEntity<byte[]> getImage(@PathVariable String file) {
        try {
            Path imgPath = new File(UPLOAD_IMG + file.toLowerCase()).toPath();
            System.out.println(imgPath.toString());

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


    //método para poder visualizar las imagenes desde la vista del frontend.
    @GetMapping("sign/{file}")
    public ResponseEntity<byte[]> getSignature(@PathVariable String file) {
        try {
            Path imgPath = new File(UPLOAD_SIGN + file.toLowerCase()).toPath();
            System.out.println(imgPath.toString());

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

    public static void deleteImagen(String url) {
        File oldfile = new File(ImageProcessing.UPLOAD_IMG + url.toLowerCase());
        if (oldfile.exists()) {
            oldfile.delete();
        }
    }
    public static void deleteSignature(String url) {
        File oldfile = new File(ImageProcessing.UPLOAD_SIGN + url.toLowerCase());
        if (oldfile.exists()) {
            oldfile.delete();
        }
    }


    public String uploadSignature(MultipartFile file) {
          try {
            // Crear carpeta si no existe
            File uploadDir = new File(UPLOAD_SIGN);
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
            String filePath = "file-" + formattedDateTime + ".jpg"; // las imagenes se construyen de la misma forma siempre, utilizamos file- la fecha, hora minutos y segundos a la que fue subida, y el archivo de extensión.
            
            File savedFile = new File(UPLOAD_SIGN +filePath.toLowerCase());
            try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                fos.write(file.getBytes());
            }

            

            ReporteModel reporte = new ReporteModel();
            reporte.setFoto_url(filePath);
            //reporte  = runPythonScript(filePath, reporte);

            // scheduleImageValidation(filePath);
                
            return filePath;

        } catch (IOException e) {
            ReporteModel errorReporte = new ReporteModel();
            errorReporte.setFoto_url(null);
            return null;
        }
    }
    
    
    // método de validación de creación de reporte, siguiendo el flujo de trabajo
    // una imagen se sube y se es devuelta su url, si no se crea un reporte con esa url de imagen en 15 mintuos la imagen es eliminada, este método controla el tiempo y la función de eliminación.
    // @Async
    // public void scheduleImageValidation(String filePath) {
    //     CompletableFuture.delayedExecutor(15, TimeUnit.MINUTES).execute(() -> {
    //         try {
    //             // Verifica si hay algún reporte con esa URL
    //             boolean exists = reportesGestion.checkImageExist(filePath);
    //             if (!exists) {
    //                 File file = new File(UPLOAD_DIR + filePath);
    //                 if (file.exists()) {
    //                     file.delete();
    //                 }
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     });
    // }

    // Método para ejecutar el script de Python    //// aún no usado debido a los formatos de reportes
    // private ReporteModel runPythonScript(String imagePath, ReporteModel repSYsorte) {
    //     try {
    //         // Ajusta la ruta del script
    //         String pythonScript = "scripts/imageProcessing.py";

    //         ProcessBuilder pb = new ProcessBuilder("python", pythonScript, imagePath);
    //         pb.redirectErrorStream(true);

    //         // Process process = pb.start();

    //         // Leer salida del script
    //         // String output;
    //         // try (Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
    //         //     output = scanner.hasNext() ? scanner.next() : "";
    //         // }

    //         // int exitCode = process.waitFor();
    //         return reporte;

    //     } catch (Exception e) {
    //         return reporte;
    //     }
    // }
}
