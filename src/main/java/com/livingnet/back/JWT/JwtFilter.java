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

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil = new JwtUtil();
    private final UsuarioGestion usuarioGestion;
    private final String sinPermiso = "Permisos Insuficientes";
    private final String invalida = "Acción invalida";

    public JwtFilter(UsuarioGestion usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }

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
                if (username == null) {
                    throw new Exception("Usuario no encontrado en el token");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                if(e.getMessage().equals(sinPermiso)){
                    res.sendError(403, e.getMessage());    
                } else if(e.getMessage().equals(invalida)){
                    res.sendError(409, e.getMessage());    
                } 
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token faltante");
        }
    }

    private void ValidatePermissions(HttpServletRequest req, String username) throws Exception {
        StringBuilder sb = new StringBuilder(FilterConfig.direccionUsers);
        sb.deleteCharAt(7);
        sb.deleteCharAt(6);
        System.out.println(req.getMethod().equals("GET"));
        System.out.println(req.getMethod());
        if(req.getMethod().equals("GET")){
            return;
        } else {
            if (req.getRequestURI().contains(sb)){
                UsuarioModel user = usuarioGestion.getUsuarioPorMail(username);
                if (!user.getRol().equals(UsuarioModel.ROL_ADMIN)) {
                    throw new Exception(this.sinPermiso);
                }

                if(req.getMethod().equals("DELETE")) {
                    String path = req.getRequestURI();      // /users/123
                    String userIdStr = path.substring(path.lastIndexOf("/") + 1); // "123"
                    long userId = Long.parseLong(userIdStr);

                    if(user.getId() == userId){
                        throw new Exception(this.invalida);
                    }

                }
            }
        }
    }
}
