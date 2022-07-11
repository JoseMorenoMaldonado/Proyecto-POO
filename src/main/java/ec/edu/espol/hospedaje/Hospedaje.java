package ec.edu.espol.hospedaje;

import ec.edu.espol.service.Service;
import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Hospedaje extends Service {

    private long hospedaje_id;

    public Hospedaje(long id, String place, float service_price, float punctuation, long hospedaje_id) {
        super(id, place, service_price, punctuation);
        this.hospedaje_id = hospedaje_id;
    }

    public long getHospedaje_id() {
        return hospedaje_id;
    }
    /**
     * Este metodo imprime todas la reserva de hospedaje realizada por el cliente
     * @param id 
     */
    @Override
    public void print_reserva(int id) {
        super.print_reserva(id);
        try ( Scanner sc = new Scanner(new File(Util.getReservas_hospedaje_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == id) {
                    System.out.println("CIUDAD: " + tokens[1]);
                    System.out.println("NOMBRE DE HOTEL O DEPARTAMENTO: " + tokens[2]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
/**
 * Este metodo retorna un objeto Hospedaje segun un ID especifico
 * @param hospedaje_id
 * @return Hospedaje
 */
    public static Hospedaje get_Hospedaje(long hospedaje_id) {
        Hospedaje hospedaje = null;
        try ( Scanner sc = new Scanner(new File(Util.getHospedajes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                if (Long.parseLong(tokens[4]) == hospedaje_id) {
                    hospedaje = new Hospedaje(Long.parseLong(tokens[0]), tokens[1], Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]), Long.parseLong(tokens[4]));
                    return hospedaje;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hospedaje;
    }
/**
 * Este metodo retorna todos los servicios de hospedajes
 * @return ArrayList<Hospedaje>
 */
    public static ArrayList<Hospedaje> get_every_hospedaje() {
        ArrayList<Hospedaje> hospedajes = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getHospedajes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split("\\|");
                Hospedaje hospedaje = new Hospedaje(Long.parseLong(tokens[0]), tokens[1], Float.parseFloat(tokens[2]),
                        Float.parseFloat(tokens[3]), Long.parseLong(tokens[4]));
                hospedajes.add(hospedaje);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hospedajes;
    }
/**
 * Este metodo retorna todos los hospedajes disponibles en una ciudad especifica
 * @param ciudad
 * @return ArrayList<Hospedaje>
 */
    public static ArrayList<Hospedaje> get_available_hospedajes(String ciudad) {
        ArrayList<Hospedaje> available_hospedajes = new ArrayList<>();
        for (Hospedaje hospedaje : Hospedaje.get_every_hospedaje()) {
            if (hospedaje.getPlace().equals(ciudad)) {
                available_hospedajes.add(hospedaje);
            }
        }

        return available_hospedajes;
    }

}
