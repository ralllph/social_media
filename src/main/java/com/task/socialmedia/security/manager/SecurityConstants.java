package com.task.socialmedia.security.manager;

public class SecurityConstants {
    // register path
    public static final String REGISTER_PATH = "/createUser";

    //token expires in 2 hours  which is 7200000 milli  seconds
    public static final int TOKEN_EXPIRATION =  7200000;

    //Secret 512 but key generated from  Encryption key generator website on google  select 512
    public static final String SECRET_KEY = "2s5u8x/A?D(G+KbPeShVmYq3t6w9z$B&E)H@McQfTjWnZr4u7x!A%D*F-JaNdRgU";

    //Authorization
    public static final String AUTHORIZATION = "Authorization";

    //Bearer goes with token
    public static final String BEARER = "Bearer";
}
