package com.livingnet.back.JWT;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.livingnet.back.Gestion.UsuarioGestion;
import com.livingnet.back.Model.UsuarioModel;

/**
 * Filtro JWT para autenticación y autorización de solicitudes HTTP.
 * Intercepta las solicitudes configuradas en FilterConfig para validar tokens JWT y permisos de usuario.
 */
@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil = new JwtUtil();  // util sirve para generar y parsear los tokens jwt
    private final UsuarioGestion usuarioGestion; // sirve para eralizar las transacciones y validar usuarios y roles

    // variables de texto, permiten tener los mismos mensajes de error, y enviar códigos distintos dependiendo el error.
    private final String sinPermiso = "Permisos Insuficientes";
    private final String invalida = "Acción invalida";
    private final String usuarioInexistente= "Usuario no existe";
    private final String tokenInvalido= "Token inválido";

    public JwtFilter(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }


    /**
     * Procesa cada solicitud HTTP, extrayendo y validando el token JWT, y verificando permisos.
     * @param request La solicitud Servlet.
     * @param response La respuesta Servlet.
     * @param chain La cadena de filtros.
     * @throws IOException Si ocurre un error de I/O.
     * @throws ServletException Si ocurre un error de servlet.
     */
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

                if (username == null) {
                    throw new Exception(tokenInvalido);
                }
                
                ValidatePermissions(req, username);
                
                chain.doFilter(request, response);
            } catch (Exception e) {
                if(e.getMessage().equals(sinPermiso)){
                    System.out.print("sin permiso");
                    res.sendError(403, e.getMessage());    
                } else if(e.getMessage().equals(invalida)){
                    System.out.print("invalida");
                    res.sendError(409, e.getMessage());    
                } else if(e.getMessage().equals(tokenInvalido)){
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token faltante");
                }else{
                    System.out.print(e);
                    res.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
                }
            }
        } else {
            
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token faltante");
        }
    }


    /**
     * Valida los permisos del usuario para la solicitud HTTP actual.
     * @param req La solicitud HTTP.
     * @param username El nombre de usuario extraído del token.
     * @throws Exception Si no tiene permisos o la acción es inválida.
     */
    private void ValidatePermissions(HttpServletRequest req, String username) throws Exception {

        Optional<UsuarioModel> userOpt = usuarioGestion.getUsuarioPorMail(username);
        if(userOpt.isEmpty()){
            throw new Exception(usuarioInexistente);
        }

        UsuarioModel user = userOpt.get();


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

    /**
     * Valida si el ID del usuario en la URL coincide con el ID del usuario autenticado.
     * @param req La solicitud HTTP.
     * @param user El modelo de usuario.
     * @return true si coinciden, false en caso contrario.
     */
    private boolean validateParity(HttpServletRequest req, UsuarioModel user ){
        String path = req.getRequestURI();   
        String userIdStr = path.substring(path.lastIndexOf("/") + 1);
        long userId = Long.parseLong(userIdStr);
        return (user.getId() == userId);
    }
}
