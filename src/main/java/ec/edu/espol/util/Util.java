package ec.edu.espol.util;

import ec.edu.espol.service.Service;
import ec.edu.espol.usuarios.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Util {

    private final static String usuarios_txt = "data/usuarios/usuarios.txt";
    private final static String clientes_txt = "data/usuarios/clientes.txt";

    private final static String servicios_txt = "data/servicios/servicios.txt";
    private final static String hospedajes_txt = "data/servicios/hospedajes.txt";
    private final static String hoteles_txt = "data/hoteles.txt";
    private final static String departamentos_txt = "data/departamentos.txt";
    private final static String habitaciones_txt = "data/habitaciones.txt";
    private final static String entretenimientos_txt = "data/servicios/entretenimientos.txt";
    private final static String transportes_txt = "data/servicios/transportes.txt";
    private final static String vehiculos_txt = "data/vehiculos.txt";

    private final static String reservas_txt = "data/reservas/reservas.txt";
    private final static String reservas_Transporte_txt = "data/reservas/reservasTransporte.txt";
    private final static String reservas_entretenimientos_txt = "data/reservas/reservasEntretenimiento.txt";
    private final static String reservas_hospedaje_txt = "data/reservas/reservasHospedaje.txt";

    private final static String pagos_txt = "data/pagos.txt";
    private static Scanner sc = new Scanner(System.in);

    public static String getTransportes_txt() {
        return transportes_txt;
    }

    public static String getPagos_txt() {
        return pagos_txt;
    }

    public static String getDepartamentos_txt() {
        return departamentos_txt;
    }

    public static String getReservas_hospedaje_txt() {
        return reservas_hospedaje_txt;
    }

    public static String getHabitaciones_txt() {
        return habitaciones_txt;
    }

    public static String getHoteles_txt() {
        return hoteles_txt;
    }

    public static String getHospedajes_txt() {
        return hospedajes_txt;
    }

    public static String getReservas_entretenimientos_txt() {
        return reservas_entretenimientos_txt;
    }

    public static String getEntretenimientos_txt() {
        return entretenimientos_txt;
    }

    public static String getReservas_Transporte_txt() {
        return reservas_Transporte_txt;
    }

    public static String getServicios_txt() {
        return servicios_txt;
    }

    public static String getUsuarios_txt() {
        return usuarios_txt;
    }

    public static String getReservas_txt() {
        return reservas_txt;
    }

    public static String getClientes_txt() {
        return clientes_txt;
    }

    public static String getVehiculos_txt() {
        return vehiculos_txt;
    }

    public static Date ask_date(String message) {
        boolean flag;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        do {
            System.out.print(message);
            String fecha = sc.nextLine();
            System.out.println("");
            try {
                date = dateFormat.parse(fecha);
                flag = false;

            } catch (ParseException e) {
                System.out.println("Por favor, ingrese una fecha valida");
                System.out.println("");
                flag = true;
            }
        } while (flag);
        return date;
    }

    public static int ask_number(String message) {
        boolean flag;
        String num;
        do {
            System.out.print(message);
            num = sc.nextLine();
            System.out.println("");
            try {
                Integer.parseInt(num);
                flag = false;

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un valor entero");
                System.out.println("");
                flag = true;

            }
        } while (flag);
        return Integer.parseInt(num);
    }

    public static String ask_ciudad(String msj) {
        ArrayList<Service> every_service = Service.get_every_service();
        String ciudad;
        boolean flag;
        do {
            System.out.print(msj);
            ciudad = sc.nextLine();
            System.out.println("");
            for (int i = 0; i < every_service.size(); i++) {
                if (every_service.get(i).getPlace().equals(ciudad.toLowerCase())) {
                    return ciudad.toLowerCase();
                }
            }
            flag = true;
            System.out.println("No existen servicios en esa ciudad");
            System.out.println("");
        } while (flag);
        return ciudad;

    }

    public static long ask_long_number(String message) {
        boolean flag;
        String num;
        do {
            System.out.print(message);
            num = sc.nextLine();
            System.out.println("");
            try {
                Long.parseLong(num);
                flag = false;

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un valor correcto");
                System.out.println("");
                flag = true;

            }
        } while (flag);
        return Long.parseLong(num);
    }

    public static int ask_option(String mensaje_opcion, int size) {
        int user_option = 0;
        do {
            user_option = ask_number(mensaje_opcion);
            if (!(user_option > 0 && user_option <= size)) {
                System.out.println("Por favor, elija una opcion correcta");
                System.out.println("");
            }
        } while (!(user_option > 0 && user_option <= size));
        return user_option;
    }

    /**
     * Este método verifica si el username y la contraseña recibidas existen y
     * son correctas
     *
     * @param username Input de un username
     * @param password Input de una contraseña
     * @return boolean retorna true si el username y la contraseña son
     * correctas, false si no lo son.
     *
     */
    public static boolean verify_user_And_password(String username, String password) {
        Usuario user = Usuario.get_user(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    /**
     *
     * Este metodo asegura que la edad ingresada sea la correcta
     *
     * @param edad
     * @return boolean
     *
     */
    public static boolean check_edad(int edad) {
        return edad > 0 && edad < 150;
    }

    /**
     *
     * Este metodo se asegura que el numero de tarjeta de credito ingresado sea
     * el correcto
     *
     * @param tarjetaCredito
     * @return boolean
     *
     */
    public static boolean check_tarjeta_length(long tarjetaCredito) {
        return String.valueOf(tarjetaCredito).length() == 14;
    }

    /**
     * Este metodo verifica que los datos extras del usuario han sido
     * registrados previamente
     *
     * @param usuario
     * @return boolean
     *
     */
    public static boolean has_extra_data(Usuario usuario) {
        try ( Scanner sc = new Scanner(new File(clientes_txt))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == usuario.getCedula()) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static float get_valor_reserva(int codigo) {
        float valor = 0;
        try ( Scanner sc = new Scanner(new File(reservas_txt))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == codigo) {
                    valor = Float.parseFloat(tokens[6]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return valor;
    }

    public static void add_reserva(int numero_reserva, String fecha_reserva, String tipo_reserva, String nombre_cliente, String fecha_inicial, String fecha_final, float precio_total, String ciudad) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(reservas_txt), true))) {
            pw.print(numero_reserva + "," + fecha_reserva + "," + tipo_reserva + "," + nombre_cliente + "," + fecha_inicial + "," + fecha_final + "," + precio_total + "," + ciudad.toLowerCase() + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_pago(int reserva_id, int pago_id, String nombre_cliente, String fecha_pago, float valorPagar, float valorPagarFinal,
            String tipoPago, long tarjeta_cheque, int mes, int year) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(pagos_txt), true))) {
            pw.print(reserva_id + "," + pago_id + "," + nombre_cliente + "," + fecha_pago + "," + valorPagar + "," + valorPagarFinal + "," + tipoPago + ","
                    + tarjeta_cheque + "," + mes + "/" + year + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_reservaTransporte(int numero_reserva, long id, float precio_total, String ciudad) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(reservas_Transporte_txt), true))) {
            pw.print(numero_reserva + "," + id + "," + precio_total + "," + ciudad +"\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_reservaEntertainment(int numero_reserva, String ciudad, String paquete) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(reservas_entretenimientos_txt), true))) {
            pw.print(numero_reserva + "," + ciudad + "," + paquete + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_reservaHospedaje(int numero_reserva, String ciudad, String hotel_name) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(reservas_hospedaje_txt), true))) {
            pw.print(numero_reserva + "," + ciudad + "," + hotel_name + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_reservaDepartamento(int numero_reserva, String ciudad, String hotel_name) {
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(new File(reservas_hospedaje_txt), true))) {
            pw.print(numero_reserva + "," + ciudad + "," + hotel_name + "\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
