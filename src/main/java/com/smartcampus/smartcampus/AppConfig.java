/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus;

/**
 *
 * @author Fahath
 */
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.smartcampus.smartcampus.filters.LoggingFilter;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("/api/v1") // Set base path for the API
public class AppConfig extends Application {
    // Register the logging filter globally for all requests and responses
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(LoggingFilter.class); // Add the LoggingFilter class
        return resources;
    }
}