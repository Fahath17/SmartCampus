/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus;

/**
 *
 * @author Fahath
 */
import com.smartcampus.smartcampus.models.Room;
import com.smartcampus.smartcampus.models.Sensor;
import com.smartcampus.smartcampus.models.SensorReading;
import java.util.*;

public class DataStore {
    public static List<Room> rooms = new ArrayList<>();
    public static List<Sensor> sensors = new ArrayList<>();
    public static Map<String, List<SensorReading>> readings = new HashMap<>();

    public static Room getRoomById(String id) {
        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }
}
