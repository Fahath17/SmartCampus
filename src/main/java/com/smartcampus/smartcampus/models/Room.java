/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smartcampus.models;

/**
 *
 * @author Fahath
 */
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private String name;
    private int capacity;
    private List<String> sensorIds = new ArrayList<>();

    // Default constructor
    public Room() {
    }

    // Parameterized constructor
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // Full constructor
    public Room(String id, String name, int capacity, List<String> sensorIds) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.sensorIds = sensorIds;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }

    // Helper methods
    public void addSensor(String sensorId) {
        if (!sensorIds.contains(sensorId)) {
            sensorIds.add(sensorId);
        }
    }

    public void removeSensor(String sensorId) {
        sensorIds.remove(sensorId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", sensorIds=" + sensorIds +
                '}';
    }
}