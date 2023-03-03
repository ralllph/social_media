package com.task.socialmedia.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.task.socialmedia.entity.User;
import com.task.socialmedia.security.manager.CustomAuthenticationManager;
import com.task.socialmedia.security.manager.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

//we inherit  the username and password in order to have access to the override methods attempt authentication and successful authentication
//note that the methods in this class only get called if you are actually doing an authentication else it goes to the next filter
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //wire authentication manager as we need an object of it
    private CustomAuthenticationManager authenticationManager;
    private Gson gson;
    //type in attempt authentication to view over ride
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //here we recieve the body of the request sent to /login as binary via request
        //try catch is used cuz if any input which is wrong will not be mapped no @valid function ere
        try{
            // here we do the deserialization into the user entity
            //new object mapper helps transfer binary data to java classes using read value
            //request.getInputStream contains the body passed via the http post request and its being passed to user.class
            User user = new  ObjectMapper().readValue(request.getInputStream(), User.class);
            //create a new authentication object containing user details to pass to authentication manager
            //first argument is principal which identifies user asin user.getUsername
            //second argument is most often a password
            //the third is usually authorities
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
           //pass in the authentication object to auth Manager
            //we are returning the object authenticate from auth manager returns
           return  authenticationManager.authenticate(authentication);
        }
        catch (IOException ioException){
            //note that custom exceptions and controller advice won't work here because the security filter comes before dispatcher servlet
            // where those exceptions can be thrown
            throw new RuntimeException();
        }
        //this is auto generated and not needed
        //return super.attemptAuthentication(request, response);
        //getting to this point means authentication was successful. It now calls successful Authentication method
    }

    @Override
    //method called when attempt auth fails
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //the statement below comes with over riding but we want to implement our event for failedl authentication
        //super.unsuccessfulAuthentication(request, response, failed);
       // System.out.println("Sorry man authentication failed");
        // snd back unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());    //failed.getMessage contains the mesage thrown from the exception caught in check exception filter
        response.getWriter().flush();
    }

    @Override
    //method called when attempt auth is successful
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //the statement below comes with over riding but we want to implement our eventfor successful authentication
        // super.successfulAuthentication(request, response, chain, authResult);
       // System.out.println("Softtt successfully authenticated");
        //create jwt token
        String token = JWT.create()//create jwt token
                .withSubject(authResult.getName()) // auth result contains user details. this means identify with name
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.TOKEN_EXPIRATION)) //it should expire 2 hours after the current time
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY)); //THE HMCK 512 IS AN ALGORITHM THATaccepts a 512 bit secret key  TAKES A SECRET KEY WE ADDEDD IN SECURTIY CONSTANTS
        //create the header, first parameter authorization, second parameter bearer + token
        // response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER+token);
        //return the token as a response instead of header
        //create a json object containing token we want to send back
        JsonObject tokenObject = new JsonObject();
        //add token to the object
        tokenObject.addProperty("token", token);
        //make token json string
        String tokenJson = gson.toJson(tokenObject);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(tokenJson);
        response.getWriter().flush();
    }
}
