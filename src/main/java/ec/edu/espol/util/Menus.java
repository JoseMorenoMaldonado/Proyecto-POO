package ec.edu.espol.util;

import ec.edu.espol.usuarios.Administrador;
import ec.edu.espol.usuarios.Cliente;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Menus {

    private static Scanner sc = new Scanner(System.in);

    /**
     * Este metodo registra los datos extras de un cliente
     *
     * @param cliente
     *
     *
     */
    public static void add_extra_data_menu(Cliente cliente) {
        String error_message;
        int edad;
        long tarjetaCredito;
        do {
            edad = Util.ask_number("Inserte su edad: ");
            tarjetaCredito = Util.ask_long_number("Inserte su numero de tarjeta de credito: ");
            error_message = Util.check_edad(edad) && Util.check_tarjeta_length(tarjetaCredito) ? "" : "Error al ingresar edad o tarjeta";
            System.out.println(error_message);
        } while (error_message.equals("Error al ingresar edad o tarjeta"));
        cliente.setEdad(edad);
        cliente.setCreditCard(tarjetaCredito);
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(Util.getClientes_txt()), true))) {
            pw.print(cliente.getCedula() + "," + cliente.getEdad() + "," + cliente.getCreditCard() + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Este metodo muestra el menu de un cliente
     *
     * @param cliente
     *
     */
    public static void menu_cliente(Cliente cliente) {
        String opcion;
        do {
            System.out.println("/******************MENU********************/");
            System.out.println("/*                                        */");
            System.out.println("/******************************************/");
            System.out.println("1. Reservar hospedaje");
            System.out.println("2. Reservar transporte");
            System.out.println("3. Reservar entretenimiento");
            System.out.println("4. Pagar reserva");
            System.out.println("5. Consultar reservas");
            System.out.println("6. Salir");
            System.out.println("");
            System.out.print("Elija una opcion: ");
            opcion = sc.nextLine();
            System.out.println("");
            switch (opcion) {
                case "1":
                    cliente.reservar_hospedaje(sc);
                    break;
                case "2":
                    cliente.reservar_transporte(sc);
                    break;
                case "3":
                    cliente.reservar_entretenimiento(sc);
                    break;
                case "4":
                    cliente.pagar_reserva(sc);
                    break;
                case "5":
                    cliente.consultar_reservas(sc);
                    break;
                case "6":
                    System.out.println("Muchas gracias");
                    break;
                default:
                    System.out.println("No ha elegido una opción válida");
                    break;
            }
        } while (!"6".equals(opcion));

    }

    /**
     * Este metodo muestra el menu de un administrador
     *
     * @param admin
     */
    public static void menu_admin(Administrador admin) {
        String opcion;
        do {
            System.out.println("/******************MENU********************/");
            System.out.println("/*                                        */");
            System.out.println("/******************************************/");
            System.out.println("1. Consultar reservas");
            System.out.println("2. Salir");
            System.out.println("");
            System.out.print("Elija una opcion: ");
            opcion = sc.nextLine();
            System.out.println("");
            switch (opcion) {
                case "1":
                    admin.consultar_reservas(sc);
                    break;
                case "2":
                    System.out.println("Muchas gracias");
                    break;
                default:
                    System.out.println("No ha elegido una opción válida");
                    break;
            }
        } while (!"2".equals(opcion));

    }
}
