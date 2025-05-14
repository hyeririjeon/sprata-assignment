package com.study.usermanagementsystem.common.filter;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.study.usermanagementsystem.common.jwt.JwtUtil.AUTHORIZATION_KEY;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtil.getJwtFromHeader(request);

            if (token != null && jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                String role = claims.get(AUTHORIZATION_KEY, String.class);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null,
                                AuthorityUtils.createAuthorityList("ROLE_" + role));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (UserException e) {

            response.setStatus(e.getStatusCode().getHttpStatus().value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    String.format("""
                {
                  "error": {
                    "code": "%s",
                    "message": "%s"
                  }
                }
                """, e.getStatusCode().getName(), e.getStatusCode().getMessage())
            );
        }
    }

}