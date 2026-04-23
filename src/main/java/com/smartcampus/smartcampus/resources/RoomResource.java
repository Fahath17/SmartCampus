/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.resources;

/**
 *
 * @author Fahath
 */
import com.smartcampus.smartcampus.models.Room;
import com.smartcampus.smartcampus.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rooms")
public class RoomResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return DataStore.rooms;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        DataStore.rooms.add(room);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") String id) {
        return DataStore.getRoomById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = DataStore.getRoomById(id);
        if (room != null && room.getSensorIds().isEmpty()) {
            DataStore.rooms.remove(room);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity("Room has active sensors.").build();
        }
    }
}