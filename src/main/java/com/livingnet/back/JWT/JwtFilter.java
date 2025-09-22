package com.livingnet.back.JWT;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.livingnet.back.Gestion.UsuarioGestion;
import com.livingnet.back.Model.UsuarioModel;

//clase de filtro, recibe todas las solicitudes http que estan configuradas según la clase FilterCOnfig
@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil = new JwtUtil();  // util sirve para generar y parsear los tokens jwt
    private final UsuarioGestion usuarioGestion; // sirve para eralizar las transacciones y validar usuarios y roles

    // variables de texto, permiten tener los mismos mensajes de error, y enviar códigos distintos dependiendo el error.
    private final String sinPermiso = "Permisos Insuficientes";
    private final String invalida = "Acción invalida";
    private final String usuarioInexistente= "Usuario no existe";

    public JwtFilter(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }


    // método sobreescrito, contiene la lógica del token jwt en cada validación
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                
                ValidatePermissions(req, username);
                
                chain.doFilter(request, response);
            } catch (Exception e) {
                if(e.getMessage().equals(sinPermiso)){
                    System.out.print("sin permiso");
                    res.sendError(403, e.getMessage());    
                } else if(e.getMessage().equals(invalida)){
                    System.out.print("invalida");
                    res.sendError(409, e.getMessage());    
                } 
                System.out.print(e);
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        } else {
            
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token faltante");
        }
    }


    //método para validar permisos en las solicitudes http, por el momento, edición es solo permitida por administradores.
    private void ValidatePermissions(HttpServletRequest req, String username) throws Exception {

        UsuarioModel user = usuarioGestion.getUsuarioPorMail(username);
        if(user == null){
            throw new Exception(usuarioInexistente);
        }


        //si método == usuarios, solo se puede hacer un get.
        StringBuilder du = new StringBuilder(FilterConfig.direccionUsers); //String para usuarios
        du.deleteCharAt(7);
        du.deleteCharAt(6); 
        
        // 
        StringBuilder drv = new StringBuilder(FilterConfig.direccionReporteVacio); //String para reportesVacio
        drv.deleteCharAt(7);
        drv.deleteCharAt(6); 


        ///AGREGAR NUEVAS DIRECCIONES DESDE EL FILTRO PARA PODER MANIPULARLAS
        /// 
        /// 
        /// 
        /// 
        /// 
        
        

        // si es solicitud a usuarios
        if (req.getRequestURI().contains(du)){

            if(req.getMethod().equals("GET")){ // todos pueden hacer get
                return;
            } 

            if (!user.getRol().equals(UsuarioModel.ROL_ADMIN)) { //  si es distinto de get, y no es admin hay error
                throw new Exception(this.sinPermiso);
            }

            if(req.getMethod().equals("DELETE")) { // un administrador no se puede eliminar a si mismo
                
                if(validateParity(req, user)){ // si id coincide error
                    throw new Exception(this.invalida);
                }

            }
        }
        
        
        //si es solicitud a reportesvacio
        if (req.getRequestURI().contains(drv)){
            
            // solo se puede generar si es tecnico

            if(!validateParity(req, user)){ // si el id es distinto da error
                throw new Exception(this.invalida);
            }

            
            if(user.getRol().equals(UsuarioModel.ROL_TECNICO) || user.getRol().equals(UsuarioModel.ROL_ADMIN)){ 
                return;
            }
        }
    
    
    }

    private boolean validateParity(HttpServletRequest req, UsuarioModel user ){
        String path = req.getRequestURI();   
        String userIdStr = path.substring(path.lastIndexOf("/") + 1);
        long userId = Long.parseLong(userIdStr);
        return (user.getId() == userId);
    }
}
