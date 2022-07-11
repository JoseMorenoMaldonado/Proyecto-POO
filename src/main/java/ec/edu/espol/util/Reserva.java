package ec.edu.espol.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reserva {

    int id_reserva;
    String date;
    String tipo;
    String cliente;
    String fecha_inicial;
    String fecha_final;
    float precio_total;
    String ciudad;

    public Reserva(int id_reserva, String date, String tipo, String cliente, String fecha_inicial, String fecha_final, float precio_total, String ciudad) {
        this.id_reserva = id_reserva;
        this.date = date;
        this.tipo = tipo;
        this.cliente = cliente;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.precio_total = precio_total;
        this.ciudad = ciudad;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(String fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public static ArrayList<Reserva> get_every_reserva() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getReservas_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                Reserva reserva = new Reserva(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], Float.parseFloat(tokens[6]), tokens[7]);
                reservas.add(reserva);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reservas;
    }
    
    public static ArrayList<Reserva> get_available_reservas(String ciudad) {
        ArrayList<Reserva> available_reservas = new ArrayList<>();
        for (Reserva reserva : get_every_reserva()) {
            if (reserva.getCiudad().equals(ciudad)) {
                available_reservas.add(reserva);
            }
        }

        return available_reservas;
    }

}
