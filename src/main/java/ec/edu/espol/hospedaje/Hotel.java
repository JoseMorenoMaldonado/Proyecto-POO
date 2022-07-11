/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.hospedaje;

import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Hotel extends Hospedaje {

    //private int hotel_id;
    private String hotel_name;
    private int stars;
    private String direccion;
    private boolean has_breakfast;
    private boolean has_parking;
    private boolean has_free_cancellation;
    private ArrayList<Habitacion> habitaciones;

    public Hotel(long id, String place, float service_price, float punctuation, long hospedaje_id,
            String hotel_name, int stars, String direccion, boolean has_breakfast, boolean has_parking,
            boolean has_free_cancellation) {
        super(id, place, service_price, punctuation, hospedaje_id);
        this.hotel_name = hotel_name;
        this.stars = stars;
        this.direccion = direccion;
        this.has_breakfast = has_breakfast;
        this.has_parking = has_parking;
        this.has_free_cancellation = has_free_cancellation;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isHas_breakfast() {
        return has_breakfast;
    }

    public void setHas_breakfast(boolean has_breakfast) {
        this.has_breakfast = has_breakfast;
    }

    public boolean isHas_parking() {
        return has_parking;
    }

    public void setHas_parking(boolean has_parking) {
        this.has_parking = has_parking;
    }

    public boolean isHas_free_cancellation() {
        return has_free_cancellation;
    }

    public void setHas_free_cancellation(boolean has_free_cancellation) {
        this.has_free_cancellation = has_free_cancellation;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    @Override
    public String toString() {
        String desayuno = (this.has_breakfast == true) ? "Si" : "No";
        String parking = (this.has_parking == true) ? "Si" : "No";
        String cancelacion = (this.has_free_cancellation == true) ? "Si" : "No";
        return "Direccion: " + this.direccion + "\nCosto por noche: " + this.service_price + "\nEstrellas: " + this.stars
                + "\nIncluye desayuno: " + desayuno
                + "\nIncluye parqueo: " + parking
                + "\nPermite cancelacion gratis: " + cancelacion;

    }

    public static ArrayList<Hotel> get_every_hotel() {
        ArrayList<Hotel> hoteles = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getHoteles_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                Hospedaje hospedaje = Hospedaje.get_Hospedaje(Long.parseLong(tokens[0]));
                Hotel hotel = new Hotel(hospedaje.getId(), hospedaje.getPlace(), hospedaje.getService_price(), hospedaje.getPunctuation(),
                        hospedaje.getHospedaje_id(), tokens[1], Integer.parseInt(tokens[2]), tokens[3], Boolean.parseBoolean(tokens[4]),
                        Boolean.parseBoolean(tokens[5]), Boolean.parseBoolean(tokens[6]));
                hotel.setHabitaciones(Habitacion.get_available_habitaciones(hospedaje.getHospedaje_id()));
                hoteles.add(hotel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hoteles;
    }

    public static ArrayList<Hotel> get_available_hoteles(String ciudad) {
        ArrayList<Hotel> available_hoteles = new ArrayList<>();
        for (Hotel hotel : get_every_hotel()) {
            if (hotel.getPlace().equals(ciudad)) {
                available_hoteles.add(hotel);
            }
        }

        return available_hoteles;
    }

    public static float individual_price(Hotel hotel) {
        float price = 0;
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equals(HabitacionType.INDIVIDUAL)) {
                price = habitacion.getHabitacion_price();
            }
        }
        return price;
    }

    public static float doble_price(Hotel hotel) {
        float price = 0;
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equals(HabitacionType.DOBLE)) {
                price = habitacion.getHabitacion_price();
            }
        }
        return price;
    }

    public static float familiar_price(Hotel hotel) {
        float price = 0;
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getTipo().equals(HabitacionType.FAMILIAR)) {
                price = habitacion.getHabitacion_price();
            }
        }
        return price;
    }

}
