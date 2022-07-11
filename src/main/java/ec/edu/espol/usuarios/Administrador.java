package ec.edu.espol.usuarios;

import ec.edu.espol.util.Reserva;
import ec.edu.espol.util.Util;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Administrador extends Usuario {

    int edad;
    String ciudad;

    public Administrador(int cedula, String name, String lastname, String username, String password, int phone_number, UserType user_type) {
        super(cedula, name, lastname, username, password, phone_number, user_type);

    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public void consultar_reservas(Scanner sc) {
        ArrayList<Reserva> reservas = Reserva.get_every_reserva();
        if (!reservas.isEmpty()) {
            System.out.println("/****************** RESUMEN DE SERVICIOS RESERVADOS********************/");
            System.out.println("/*                                                                    */");
            System.out.println("/**********************************************************************/");
            System.out.println("");
            ArrayList<Reserva> guayaquil = Reserva.get_available_reservas("guayaquil");
            ArrayList<Reserva> quito = Reserva.get_available_reservas("quito");
            ArrayList<Reserva> cuenca = Reserva.get_available_reservas("cuenca");
            int total_guayaquil = reservas_por_ciudad(guayaquil);
            int total_quito = reservas_por_ciudad(quito);
            int total_cuenca = reservas_por_ciudad(cuenca);
            System.out.println("En total se han generado " + (total_guayaquil + total_quito + total_cuenca) + " reservas.");
        } else {
            System.out.println("No existen servicios reservados");
        }
    }

    private int reservas_por_ciudad(ArrayList<Reserva> lista) {
        int total = 0;
        int hospedajes = 0;
        int transportes = 0;
        int entretenimientos = 0;
        if (!lista.isEmpty()) {
            for (Reserva reserva : lista) {
                switch (reserva.getTipo()) {
                    case "hospedaje":
                        hospedajes += 1;
                        break;
                    case "transporte":
                        transportes += 1;
                        break;
                    case "entretenimiento":
                        entretenimientos += 1;
                        break;
                }
            }
            System.out.println("/********** "+lista.get(0).getCiudad()+" **********/");
            System.out.println("Hospedaje: " + hospedajes);
            System.out.println("Transportes: " + transportes);
            System.out.println("Entretenimiento: " + entretenimientos);
        }
        return total + hospedajes + transportes + entretenimientos;
    }

}
