/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.entretenimiento;

import ec.edu.espol.service.Service;
import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Entertainment extends Service {

    private String titulo;
    private String description;
    private float cost;
    private String dias;
    private String salida;
    private String regreso;

    public Entertainment(long id, String place, float service_price, float punctuation, String titulo, String description, float cost, String dias, String salida, String regreso) {
        super(id, place, service_price, punctuation);
        this.titulo = titulo;
        this.description = description;
        this.cost = cost;
        this.dias = dias;
        this.regreso = regreso;
        this.salida = salida;

    }

    public String getTitulo() {
        return titulo;
    }

    public float getCost() {
        return cost;
    }

    public String getDias() {
        return dias;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getRegreso() {
        return regreso;
    }

    public void setRegreso(String regreso) {
        this.regreso = regreso;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description + "\n\nCosto por persona: " + this.cost + "\n\nSalidas: " + this.dias + "\n\nFechas para realizar la ruta: " + this.salida
                + " - " + this.regreso;

    }

    @Override
    public void print_reserva(int id) {
        super.print_reserva(id);
        try ( Scanner sc = new Scanner(new File(Util.getReservas_entretenimientos_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == id) {
                    System.out.println("CIUDAD: " + tokens[1]);
                    System.out.println("PAQUETE: " + tokens[2]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Entertainment> get_every_entertainment() {
        ArrayList<Entertainment> entertainments = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getEntretenimientos_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                Entertainment entretenimiento = new Entertainment(Long.parseLong(tokens[0]), tokens[1], Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]),
                        tokens[4], tokens[5], Float.parseFloat(tokens[6]), tokens[7], tokens[8], tokens[9]);
                entertainments.add(entretenimiento);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entertainments;
    }

    public static ArrayList<Entertainment> get_available_entertainments(String ciudad) {
        ArrayList<Entertainment> available_entertainment = new ArrayList<>();
        for (Entertainment entretenimiento : Entertainment.get_every_entertainment()) {
            if (entretenimiento.getPlace().equals(ciudad)) {
                available_entertainment.add(entretenimiento);
            }
        }

        return available_entertainment;
    }

}
