package com.fernando.produtos.api.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;


@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        try {
            var aggregateId = UUID.randomUUID();
            log.info("AggregateId: {} | REQUEST: {} {}?{}",
                    aggregateId,
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getQueryString());

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                log.debug("Header: {}: {}", header, request.getHeader(header));
            }

            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            filterChain.doFilter(wrappedRequest, response);

            long duration = System.currentTimeMillis() - startTime;
            log.info("AggregateId: {} | REQUEST: {} {}?{} | RESPONSE: {}ms",
                    aggregateId,
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getQueryString(), duration);

        } finally {
        }
    }
}