/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.resources;

/**
 *
 * @author Fahath
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscoveryInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("version", "1.0");
        info.put("rooms", "/api/v1/rooms");
        info.put("sensors", "/api/v1/sensors");
        return Response.ok(info).build();
    }
}
