package com.task.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//config to send and recieve messages via websocket.
/*Web socket messaging works by creating a connection long lasting. users connect to a topic and messages are sent to
a topic and users subscribed to that topic can recieve the message
* */
@Configuration
//allows for sending messgaes
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    //this method is concerned with handling messages sent to a given topic in /topic
    //The configureMessageBroker method sets up a simple message broker that will handle messages sent to the /topic destination.
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //set the prefix for the application destinations
        registry.setApplicationDestinationPrefixes("/app");
        /* next line only required if you plan on doing a broadcast*/
       // registry.enableSimpleBroker("/topic");
    }

    @Override
    //The registerStompEndpoints method registers a WebSocket endpoint that clients can connect to.
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //specifies the endpoint and withsockjs helps with clients whose browsers don't support web sockets normally
        registry.addEndpoint("/websocket").withSockJS();
    }
}
