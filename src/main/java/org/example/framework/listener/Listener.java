package org.example.framework.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import org.example.framework.handler.WebHandler;
import org.example.framework.attribute.ContextAttributes;
import org.example.app.controller.UserController;
import org.example.app.manager.UserManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.example.framework.attribute.ContextAttributes.SPRING_CONTEXT;

@WebListener
public class Listener implements jakarta.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final var context = new AnnotationConfigApplicationContext("org.example.app");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(SPRING_CONTEXT, context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final var servletContext = sce.getServletContext();
        final AnnotationConfigApplicationContext springContext = (AnnotationConfigApplicationContext) servletContext.getAttribute(SPRING_CONTEXT);
        springContext.close();
    }
}
