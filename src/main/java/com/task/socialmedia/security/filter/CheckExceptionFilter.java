package com.task.socialmedia.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.task.socialmedia.exception.NotFoundException;
import com.task.socialmedia.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

//This filter is used in the scenario to check exception before request get to authentication filter
//Remember that the filters are called before dispatcher servlet which is why you cant create those custom exceptions like DroneNot Foiund
//the once per request is guaranteed to run only once per request
@AllArgsConstructor
public class CheckExceptionFilter extends OncePerRequestFilter {
    private Gson gson;
    @Override
    //the doFilter internal is overridden it helps to allow you point the filter chain to which filter to go next
    //filters always need pointers to which one to go next when created
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //this try catch block is saying try to go to the next filter and if you get a runtime there execute this catch
        try{
            //.doFilter passes the request and response to the next filter which could be authentication filter or another filter
            filterChain.doFilter(request,response);
        }
        //always remember the exception priority. the more generic ones goo last
        //remember any error thrown within a filter is caught in this class. So user impl throwing an error would get caught here
        catch(UserNotFoundException | NotFoundException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            String errorMessage = "USER NOT FOUND";
            throwJsonError(response,errorMessage);  // send a bad request texxt
        }
        catch(JWTVerificationException E){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String errorMessage = "JWT NOT VALID";
            throwJsonError(response, errorMessage);   // send a bad request text
        }
    }

    //method to throw json exception
    public void throwJsonError(HttpServletResponse servletResponse, String message){
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", message);
        LocalDateTime currentDateTime  = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String errorDate = currentDateTime.format(formatter);
        errorObject.addProperty("time", errorDate);
        String errorJson = gson.toJson(errorObject);
        try {
            servletResponse.setContentType("application/json");
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.getWriter().write(errorJson);
            servletResponse.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
