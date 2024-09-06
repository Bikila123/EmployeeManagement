package com.Internship.Backend.security;

import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import com.Internship.Backend.mappers.UserMapper;
import com.Internship.Backend.models.RefreshToken;
import com.Internship.Backend.models.UserModel;
import com.Internship.Backend.security.jwt.AuthTokenFilter;
import com.Internship.Backend.security.jwt.JwtUtils;
import org.springframework.util.StringUtils;


@Configuration
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userAgentDataRepository;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get IP address
        String ipAddress = request.getRemoteAddr();
        String username = jwtUtils.getUserNameFromJwtToken(parseJwt(request));
        // Get browser information
        String browser = request.getHeader("User-Agent");
            // Extract browser version from User-Agent header
            String browserVersion = null;
            if (browser != null && browser.contains("rv")) {
                browserVersion = browser.substring(browser.indexOf("rv") + 3);
            } else if (browser != null && browser.contains("MSIE")) {
                browserVersion = browser.substring(browser.indexOf("MSIE") + 5);
            } else if (browser != null && browser.contains("Chrome")) {
                browserVersion = browser.substring(browser.indexOf("Chrome") + 7);
            } else if (browser != null && browser.contains("Firefox")) {
                browserVersion = browser.substring(browser.indexOf("Firefox") + 8);
            } else if (browser != null && browser.contains("Safari")) {
                browserVersion = browser.substring(browser.indexOf("Version") + 8);
            }

            Optional<UserModel> user = userAgentDataRepository.findByUsername(username);
            RefreshToken userAgentData=null;
            if(user.isPresent()){
                 userAgentData = userAgentDataRepository.findByIpAddressAndBrowserAndBrowserVersion(ipAddress, browser, browserVersion, user.get().getId());
            }

        // Perform database lookup
        if (userAgentData == null) {
            // Handle case when data doesn't exist in the database
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("X-Interceptor-Error", "true");
                        // Set custom error message in the response body
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("{\"error\": \"deviceerror\"}");
            out.flush();

            return false; // or perform redirect, return error response, etc.
        }

        return true; // Proceed with the request
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
    
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
          return headerAuth.substring(7, headerAuth.length());
        }
    
        return null;
      }
}

