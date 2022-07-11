/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.transport;

import ec.edu.espol.util.StatusType;
import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */

public class Vehicle {
    
    private long id;
    private String brand;
    private String model;
    private String plate;
    private String color;
    private int capacity;
    private StatusType status;
    private float cost;
    private CarType carType;

    public Vehicle(long id, String brand, String model, String plate, String color, int capacity, StatusType status, float cost, CarType carType) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.color = color;
        this.capacity = capacity;
        this.status = status;
        this.cost = cost;
        this.carType = carType;
    }

    public long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }

    public String getColor() {
        return color;
    }

    public int getCapacity() {
        return capacity;
    }

    public StatusType getStatus() {
        return status;
    }

    public float getCost() {
        return cost;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
    
    @Override
    public String toString(){
        return "Marca: " + getBrand() + "\nModelo: " + getModel() + "\nColor: " + getColor() + "\nCapacidad: " + getCapacity() + "\nCosto por dia: " + getCost() 
                + "\nTipo de transmision: " + getCarType();
        
    }  
    
    /**
     * Este metodo devuelve una lista de todos los vehiculos
     * @return ArrayList<Vehicle>
     */
    public static ArrayList<Vehicle> get_every_vehicle() {
        ArrayList<Vehicle> vehiculos = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(Util.getVehiculos_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                StatusType statustype = null;
                switch (tokens[6]) {
                    case "DISPONIBLE" ->
                        statustype = StatusType.DISPONIBLE;
                    case "OCUPADO" ->
                        statustype = StatusType.OCUPADO;
                }
                CarType cartype = null;
                switch (tokens[8]) {
                    case "MANUAL" ->
                        cartype = CarType.MANUAL;
                    case "AUTOMATICO" ->
                        cartype = CarType.AUTOMATICO;
                }
                Vehicle new_vehiculo = new Vehicle(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                        tokens[3], tokens[4], Integer.parseInt(tokens[5]), statustype, Float.parseFloat(tokens[7]), cartype);
                vehiculos.add(new_vehiculo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return vehiculos;
    }
    /**
     * Este metodo retorna un vehiculo segun un ID
     * @param id
     * @return Vehicle
     */
     public static Vehicle get_Vehicle(long id) {
        for(Vehicle vehicle : get_every_vehicle()){
            if(vehicle.getId() == id){
                return vehicle;
            }
        }
        return null;
    }
        
}
