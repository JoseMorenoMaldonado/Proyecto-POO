/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.transport;

import ec.edu.espol.service.Service;
import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Transport extends Service {

    protected Vehicle vehiculo;

    public Transport(long id, String place, float service_price, float punctuation, Vehicle vehiculo) {
        super(id, place, service_price, punctuation);
        this.vehiculo = vehiculo;
    }

    public Vehicle getVehiculo() {
        return vehiculo;
    }

    @Override
    public void print_reserva(int id) {
        super.print_reserva(id);
        try ( Scanner sc = new Scanner(new File(Util.getReservas_Transporte_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == id) {
                    System.out.println("Id del vehiculo: " + tokens[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Transport> get_every_transport() {
        ArrayList<Transport> transportes = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getTransportes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                Vehicle vehiculo = Vehicle.get_Vehicle(Long.parseLong(tokens[4]));
                Transport transporte = new Transport(Long.parseLong(tokens[0]), tokens[1], Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]), vehiculo);
                transportes.add(transporte);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return transportes;
    }

    public static ArrayList<Transport> get_available_transportes(String ciudad) {
        ArrayList<Transport> available_transportes = new ArrayList<>();
        for (Transport transport : get_every_transport()) {
            if (transport.getPlace().equals(ciudad)) {
                available_transportes.add(transport);
            }
        }

        return available_transportes;
    }

}
