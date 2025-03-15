package spring.security_jwt.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return (path.equals("/users") && method.equals("POST")) ||
                path.equals("/login") ||
//                path.startsWith("/h2-console") ||
                path.contains("/swagger") ||
                path.contains("/v2/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Get token from header
            String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

            // Only process if token exists
            if (token != null && !token.isEmpty()) {
                // Remove Bearer prefix if present
                if (token.startsWith(SecurityConfig.PREFIX)) {
                    token = token.substring(SecurityConfig.PREFIX.length()).trim();
                }

                // Validate token format before parsing
                if (!token.isEmpty() && token.contains(".")) {
                    JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);
                    List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

                    UsernamePasswordAuthenticationToken userToken =
                            new UsernamePasswordAuthenticationToken(
                                    tokenObject.getSubject(),
                                    null,
                                    authorities);

                    SecurityContextHolder.getContext().setAuthentication(userToken);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private List<SimpleGrantedAuthority> authorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}