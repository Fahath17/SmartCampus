/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.resources;

/**
 *
 * @author Fahath
 */
import com.smartcampus.smartcampus.models.Sensor;
import com.smartcampus.smartcampus.DataStore;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sensors")
public class SensorResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (DataStore.getRoomById(sensor.getRoomId()) == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room not found.")
                    .build();
        }
        DataStore.sensors.add(sensor);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors() {
        return DataStore.sensors;
    }
}
