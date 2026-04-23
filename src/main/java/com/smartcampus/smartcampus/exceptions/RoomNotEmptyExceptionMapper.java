/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.exceptions;

/**
 *
 * @author Fahath
 */
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    public Response toResponse(RoomNotEmptyException exception) {
        return Response.status(409).entity("Room has active sensors").build();
    }
}
