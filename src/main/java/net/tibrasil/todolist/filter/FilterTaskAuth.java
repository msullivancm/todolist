package net.tibrasil.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.tibrasil.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
                //pegar a autorizacao usuario e senha
                var authorization = request.getHeader("Authorization");
                System.out.println("Authorization");
                System.out.println(authorization);

                var authEncoded = authorization.substring("Basic".length()).trim();

                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecoded);

                System.out.println("user_password");
                System.out.println(authString);
                
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                System.out.println("username");
                System.out.println(username);
                System.out.println("password");
                System.out.println(password);

                //validar usuario
                var user = this.userRepository.findByUsername(username);
                if(user == null || user.size() == 0) {
                    response.sendError(401, "User not found");
                    return;
                }else {
                    //valida senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.get(0).getPassword());
                    if(passwordVerify.verified) {
                        System.out.println("Password verified");
                        filterChain.doFilter(request, response);
                    } else {
                        System.out.println("Password not verified");
                        response.sendError(401, "Password not verified");
                        return;
                    }
                    
                }
 
    }
    
}
