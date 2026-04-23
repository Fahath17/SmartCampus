/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.resources;

/**
 *
 * @author Fahath
 */
import com.smartcampus.smartcampus.models.SensorReading;
import com.smartcampus.smartcampus.DataStore;
import com.smartcampus.smartcampus.models.Sensor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    @GET
    public List<SensorReading> getReadings(@PathParam("sensorId") String sensorId) {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public Response postReading(@PathParam("sensorId") String sensorId,
                                SensorReading reading) {

        // ensure list exists
        DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);

        // find sensor safely
        Optional<Sensor> sensorOpt = DataStore.sensors.stream()
                .filter(s -> s.getId().equals(sensorId))
                .findFirst();

        if (!sensorOpt.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        Sensor sensor = sensorOpt.get();
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).build();
    }
}