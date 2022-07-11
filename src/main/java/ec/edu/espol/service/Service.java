/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.service;

import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Service {

    protected long id;
    protected String place;
    protected float service_price;
    protected float punctuation;
    private int reserva_id;

    public Service(long id, String place, float service_price, float punctuation) {
        this.place = place;
        this.service_price = service_price;
        this.punctuation = punctuation;
        this.id = id;
    }

    public int getReserva_id() {
        return reserva_id;
    }

    public void setReserva_id(int reserva_id) {
        this.reserva_id = reserva_id;
    }

    public String getPlace() {
        return place;
    }

    public float getService_price() {
        return service_price;
    }

    public float getPunctuation() {
        return punctuation;
    }

    public long getId() {
        return id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setService_price(float service_price) {
        this.service_price = service_price;
    }

    public void setPunctuation(float punctuation) {
        this.punctuation = punctuation;
    }

    public void setId(long id) {
        this.id = id;
    }
   /**
    * Este metodo retorna todos los servicios disponibles
    * @return ArrayList<Service>
    */
    public static ArrayList<Service> get_every_service() {
        ArrayList<Service> servicios = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getServicios_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                Service servicio = new Service(Long.parseLong(tokens[0]), tokens[1], Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
                servicios.add(servicio);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return servicios;
    }
/**
 * Este metodo imprime los datos generales de una reserva
 * @param id 
 */
    public void print_reserva(int id) {
        try ( Scanner sc = new Scanner(new File(Util.getReservas_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == id) {
                    System.out.println("/******************RESERVA GENERADA********************/");
                    System.out.println("/*                                                    */");
                    System.out.println("/******************************************************/");
                    System.out.println("CODIGO RESERVA: " + tokens[0]);
                    System.out.println("CLIENTE: " + tokens[3]);
                    System.out.println("TIPO RESERVA: " + tokens[2]);
                    System.out.println("FECHAS: " + tokens[4] + "-" + tokens[5]);
                    System.out.println("VALOR A PAGAR: " + tokens[6]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
