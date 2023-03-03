package com.task.socialmedia.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.task.socialmedia.security.manager.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //validate their jwt token which is inside the request
        //Remember the header contains Authorization as the key then value is Bearer Token i.e Authorization: Bearer JWT
        String bearerToken = request.getHeader("Authorization"); // what we get here is Bearer JWT but we need to isolate only JWT
        //using string function replace to isolate only JWT

        //The very first time when you are registering a user and the gilter chain gets to this filter
        //remember they don't have a token yet so check if token is null to know if they're trying to register
        //or check if the headers does not start with bearer to also know
        if(bearerToken==null || !bearerToken.startsWith(SecurityConstants.BEARER)){
            //if they don't have token move to next filter. remember if it's the last filter it just perform's operations
            filterChain.doFilter(request,response);
            //you don't want the app to do anything else in this class
            return;
        }
        String tokenAlone = bearerToken.replace(SecurityConstants.BEARER, "").trim(); //replace bearer with empty string. Trim is very important to trim white spaces. any trailing white space can lead to invalid token
        //now verify the token is correct
        //Jwt.require requires you to pass in the same algorithm you used to sign the token with the same secret key. Copy it directly from the .sign in AuthenticationFilter class
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(tokenAlone) //this does the verification of the token and returns a decoded jwt which you can extract subject
                .getSubject(); //extract subject which stores username of the client
        //At this point if exceptions have not been thrown, the token has been verified
        //Now create an authentication object. principal identifies which is user. credentials is null cuz the token doesn't carry sensitive data
        //the third parameter is authorities which is important because it makes the authentication be true. we didn't include any authorities  yet which is why an empty array is there
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        //populate security context holder with the current successfully authenticated user
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //now tell the filter chain to move to the next filter. Note that if this is the last filter in the chain tellling it to go to the next means perform the operations e.g get drone blah blah
        filterChain.doFilter(request,response);
    }
}
