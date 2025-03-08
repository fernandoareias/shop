package com.fernando.produtos.api.filters;

import com.fernando.produtos.api.configurations.SecurityConfiguration;
import com.fernando.produtos.api.models.User;
import com.fernando.produtos.api.models.UserDetailsImpl;
import com.fernando.produtos.api.repositories.UserRepository;
import com.fernando.produtos.api.services.JwtTokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    @Autowired
    private JwtTokenServiceImpl jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (!checkIfEndpointIsNotPublic(request)) {

            filterChain.doFilter(request, response);
            return;
        }

            String token = recoveryToken(request);
            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                User user = userRepository.findByEmail(subject).get();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);


                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                throw new RuntimeException("O token está ausente.");
            }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        String contextPath = request.getContextPath();
        String uriWithoutContext = requestURI.substring(contextPath.length());

        return Arrays.stream(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .noneMatch(pattern -> pathMatcher.match(pattern, uriWithoutContext));
    }

}