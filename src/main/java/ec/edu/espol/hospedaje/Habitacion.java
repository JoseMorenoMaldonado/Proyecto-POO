package ec.edu.espol.hospedaje;

import ec.edu.espol.util.StatusType;
import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Habitacion {

    private long hospedaje_id;
    private HabitacionType tipo;
    private float habitacion_price;
    private int capacidad;
    private int habitacion_number;
    private StatusType estado;

    public Habitacion(long hospedaje_id, HabitacionType tipo, float habitacion_price, int capacidad, int habitacion_number, StatusType estado) {
        this.hospedaje_id = hospedaje_id;
        this.tipo = tipo;
        this.habitacion_price = habitacion_price;
        this.capacidad = capacidad;
        this.habitacion_number = habitacion_number;
        this.estado = estado;
    }

    public HabitacionType getTipo() {
        return tipo;
    }

    public void setTipo(HabitacionType tipo) {
        this.tipo = tipo;
    }

    public float getHabitacion_price() {
        return habitacion_price;
    }

    public void setHabitacion_price(float habitacion_price) {
        this.habitacion_price = habitacion_price;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getHabitacion_number() {
        return habitacion_number;
    }

    public void setHabitacion_number(int habitacion_number) {
        this.habitacion_number = habitacion_number;
    }

    public StatusType getEstado() {
        return estado;
    }

    public void setEstado(StatusType estado) {
        this.estado = estado;
    }

    public long getHospedaje_id() {
        return hospedaje_id;
    }

    public void setHospedaje_id(long hospedaje_id) {
        this.hospedaje_id = hospedaje_id;
    }

    public static ArrayList<Habitacion> get_every_habitacion() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getHabitaciones_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                StatusType statustype = null;
                switch (tokens[5]) {
                    case "DISPONIBLE" ->
                        statustype = StatusType.DISPONIBLE;
                    case "OCUPADO" ->
                        statustype = StatusType.OCUPADO;
                }
                HabitacionType tipo = null;
                switch (tokens[1]) {
                    case "INDIVIDUAL" ->
                        tipo = HabitacionType.INDIVIDUAL;
                    case "DOBLE" ->
                        tipo = HabitacionType.DOBLE;
                    case "FAMILIAR" ->
                        tipo = HabitacionType.FAMILIAR;
                }
                Habitacion habitacion = new Habitacion(Long.parseLong(tokens[0]), tipo, Float.parseFloat(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]),
                        statustype);
                habitaciones.add(habitacion);
            }
        } catch (Exception e) {
            System.out.println("aquio");
            System.out.println(e.getMessage());
        }
        return habitaciones;

    }

    public static ArrayList<Habitacion> get_available_habitaciones(long hospedaje_id) {
        ArrayList<Habitacion> available_habitaciones = new ArrayList<>();
        for (Habitacion habitacion : get_every_habitacion()) {
            if (habitacion.getHospedaje_id() == hospedaje_id && habitacion.getEstado().equals(StatusType.DISPONIBLE)) {
                available_habitaciones.add(habitacion);
            }
        }

        return available_habitaciones;
    }

}
