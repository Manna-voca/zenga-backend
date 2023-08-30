package com.mannavoca.zenga.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mannavoca.zenga.common.consts.IgnoredPathConst;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.security.exception.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (BusinessException e){
            request.setAttribute("exception", e.getError());
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String ignoredPath : IgnoredPathConst.IGNORED_PATHS) {
            if(antPathMatcher.match(ignoredPath, path)) {
                return true;
            }
        }
        return false;
    }
}
