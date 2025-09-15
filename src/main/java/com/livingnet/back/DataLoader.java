// package com.livingnet.back;

// import java.util.Date;
// import java.util.Random;
// import java.util.stream.IntStream;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.livingnet.back.Gestion.ReportesGestion;
// import com.livingnet.back.Model.ReporteModel;


// @Component
// public class DataLoader implements CommandLineRunner {

//     private ReportesGestion re;
//     private final Random random = new Random();

//     public DataLoader(ReportesGestion re) {
//         this.re = re ;
//     }

//     @Override
//     public void run(String... args) throws Exception {
        
//             IntStream.rangeClosed(1, 2000).forEach(i -> {
//                 ReporteModel r = new ReporteModel();
                
//                 Date now = new Date();

//                 r.setFecha(now);
//                 r.setHorainicio(now);
//                 r.setHorafin(now);

//                 r.setAgencia("Agencia " + (i % 10));
//                 r.setActividad("Actividad " + i);
//                 r.setCuadrilla("Cuadrilla " + (i % 5));
//                 r.setJefe_cuadrilla("Jefe " + (i % 20));
//                 r.setAyudante_tecnico("Ayudante " + (i % 30));
//                 r.setTipo_actividad("Tipo " + (i % 3));
//                 r.setFormato_actividad("Formato " + (i % 2));
//                 r.setComplejidad_actividad(i % 2 == 0 ? "Alta" : "Baja");
//                 r.setEstado_actividad(i % 2 == 0 ? "Completado" : "Pendiente");
//                 r.setClima(i % 2 == 0 ? "Soleado" : "Lluvioso");
//                 r.setMotivo_retraso(i % 5 == 0 ? "Retraso técnico" : null);
//                 r.setObservaciones("Observación " + i);
//                 r.setFoto_url("http://example.com/foto" + i + ".jpg");

//                 r.setKilometraje_inicio(random.nextDouble() * 1000);
//                 r.setKilometraje_fin(r.getKilometraje_inicio() + random.nextDouble() * 50);

//                 r.setRouter(random.nextInt(10));
//                 r.setOnu(random.nextInt(5));
//                 r.setDrop(random.nextInt(20));
//                 r.setRoseta(random.nextInt(15));
//                 r.setTensores(random.nextInt(10));
//                 r.setConectores(random.nextInt(50));
//                 r.setCamara(random.nextInt(5));

//                 re.addReporte(r);
//             });
//         }
//     }
