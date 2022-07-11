package ec.edu.espol.usuarios;

import ec.edu.espol.entretenimiento.Entertainment;
import ec.edu.espol.hospedaje.Department;
import ec.edu.espol.hospedaje.Habitacion;
import ec.edu.espol.hospedaje.HabitacionType;
import ec.edu.espol.hospedaje.Hospedaje;
import ec.edu.espol.hospedaje.Hotel;
import ec.edu.espol.service.Service;
import ec.edu.espol.transport.Transport;
import ec.edu.espol.util.StatusType;
import ec.edu.espol.transport.Vehicle;
import ec.edu.espol.util.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Cliente extends Usuario {

    private int edad;
    private long creditCard;
    private ArrayList<Service> servicios_reservados = new ArrayList<>();

    public Cliente(int cedula, String name, String lastname, String username, String password, int phone_number, UserType user_type, int edad, long creditCard) {
        //super(cedula, name, lastname, username, password, phone_number, user_type);
        this.edad = edad;
        this.creditCard = creditCard;
        this.cedula = cedula;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.user_type = user_type;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public long getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(long creditCard) {
        this.creditCard = creditCard;
    }

    public ArrayList<Service> getServicios_reservados() {
        return servicios_reservados;
    }

    public void setServicios_reservados(ArrayList<Service> servicios_reservados) {
        this.servicios_reservados = servicios_reservados;
    }

    /**
     * Metodo para que un cliente reserve hospedaje
     *
     * @param sc
     */
    public void reservar_hospedaje(Scanner sc) {
        System.out.println("/******************RESERVACION********************/");
        System.out.println("/*                                               */");
        System.out.println("/*************************************************/");
        String fecha_inicial = "Ingrese una fecha inicial de reserva(dia/mes/año): ";
        String fecha_final = "Ingrese una fecha final de reserva(dia/mes/año): ";
        Date entrada = Util.ask_date(fecha_inicial);
        Date salida = Util.ask_date(fecha_final);
        String opcion_hospedaje;
        do {
            System.out.println("¿Que tipo de hospedaje busca?");
            System.out.println("1. Hotel");
            System.out.println("2. Departamento");
            System.out.print("Elija una opcion: ");
            opcion_hospedaje = sc.nextLine();
        } while (!(opcion_hospedaje.equals("1") || opcion_hospedaje.equals("2")));
        String msj = "Ingrese el nombre de la ciudad donde se alojara: ";
        String ciudad = Util.ask_ciudad(msj);
        //ArrayList<Hospedaje> available_hospedajes = Hospedaje.get_available_hospedajes(ciudad);
        if (opcion_hospedaje.equals("1")) {
            reservar_hotel(sc, ciudad, salida, entrada);
        } else {
            reservar_departamento(sc, ciudad, salida, entrada);
        }

    }
/**
 * Metodo para reservar hotel
 * @param sc
 * @param ciudad
 * @param salida
 * @param entrada 
 */
    public void reservar_hotel(Scanner sc, String ciudad, Date salida, Date entrada) {
        ArrayList<Hotel> hoteles = Hotel.get_available_hoteles(ciudad);
        System.out.println("Estos son los hoteles disponibles:");
        for (int i = 0; i < hoteles.size(); i++) {
            System.out.println(i + 1 + ". " + hoteles.get(i).getHotel_name());
        }
        System.out.println("");
        String mensaje_opcion = "Elija una opcion: ";
        int opcion = Util.ask_option(mensaje_opcion, hoteles.size()) - 1;
        Hotel hotel = hoteles.get(opcion);
        System.out.println("Datos de " + hotel.getHotel_name());
        System.out.println("/*************************************************/");
        System.out.println(opcion + 1 + ".\n" + hotel.toString());
        System.out.println("/*************************************************/");
        System.out.println("");
        ArrayList<Habitacion> habitaciones = hotel.getHabitaciones();
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles");
        } else {
            System.out.println("Elija el tipo de habitacion que desea:");
            System.out.println(1 + ". " + "Individual" + " - " + "1 persona" + " - " + Hotel.individual_price(hotel));
            System.out.println(2 + ". " + "Doble" + " - " + "2 personas" + " - " + Hotel.doble_price(hotel));
            System.out.println(3 + ". " + "Familiar" + " - " + "4 personas" + " - " + Hotel.familiar_price(hotel));
            System.out.println("");
            String habitacion_opcion = "Elija una opcion: ";
            System.out.println("");
            int opcion2 = Util.ask_option(habitacion_opcion, 3) - 1;
            String tipo = null;
            float precio_habitacion = 0;
            if (opcion2 + 1 == 1) {
                tipo = "Individual";
                precio_habitacion = Hotel.individual_price(hotel);
            }
            if (opcion2 + 1 == 2) {
                tipo = "Doble";
                precio_habitacion = Hotel.doble_price(hotel);
            }
            if (opcion2 + 1 == 3) {
                tipo = "Familiar";
                precio_habitacion = Hotel.familiar_price(hotel);
            }
            long difference_In_Time = salida.getTime() - entrada.getTime();
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

            float precio_total = difference_In_Days * precio_habitacion;
            System.out.println("Usted ha elegido una habitacion " + tipo + " para un total de "
                    + difference_In_Days + " noches.");
            System.out.println("El costo a pagar es de " + precio_total + " dolares.");
            String eleccion;
            do {
                System.out.print("Desea reservar?(Si/No): ");
                eleccion = sc.nextLine();
                System.out.println("");
                switch (eleccion.toUpperCase()) {
                    case "SI":
                        Random rand = new Random();
                        int upperbound = 100000;
                        int int_random = rand.nextInt(upperbound);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        Util.add_reserva(int_random, formatter.format(date), "hospedaje",
                                this.name + " " + this.lastname,
                                formatter.format(entrada), formatter.format(salida), precio_total,ciudad);
                        Util.add_reservaHospedaje(int_random, ciudad, hotel.getHotel_name());
                        Service servicio = new Hospedaje(hotel.getId(), hotel.getPlace(), hotel.getService_price(), hotel.getStars(), hotel.getHospedaje_id());
                        servicio.setReserva_id(int_random);
                        servicio.print_reserva(int_random);
                        this.getServicios_reservados().add(servicio);
                        break;
                    case "NO":
                        break;
                    default:
                        System.out.println("SI o NO para reservar");
                        System.out.println("");
                        break;
                }
            } while (!eleccion.toUpperCase().matches("SI|NO"));
        }

    }
/**
 * Metodo para reservar departamento
 * @param sc
 * @param ciudad
 * @param salida
 * @param entrada 
 */
    public void reservar_departamento(Scanner sc, String ciudad, Date salida, Date entrada) {
        ArrayList<Department> departamentos = Department.get_available_departamentos(ciudad);
        System.out.println("Estos son los departamentos disponibles: ");
        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println(i + 1 + ". " + departamentos.get(i).getDepartment_name());
        }
        System.out.println("");
        String mensaje_opcion = "Elija una opcion: ";
        int opcion = Util.ask_option(mensaje_opcion, departamentos.size()) - 1;
        Department departamento = departamentos.get(opcion);
        System.out.println("Datos de " + departamento.getDepartment_name());
        System.out.println("/*************************************************/");
        System.out.println(opcion + 1 + ".\n" + departamento.toString());
        System.out.println("/*************************************************/");
        System.out.println("");
        ArrayList<Habitacion> habitaciones = departamento.getHabitaciones();
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles");
        } else {
            System.out.println("Elija la habitacion que desea:");
            for (int i = 0; i < habitaciones.size(); i++) {
                System.out.println(i + 1 + ". Costo: " + habitaciones.get(i).getHabitacion_price() + " Tipo: " + habitaciones.get(i).getTipo() + " para "
                        + habitaciones.get(i).getCapacidad() + " personas");
            }
            System.out.println("");
            String habitacion_opcion = "Elija una opcion: ";
            System.out.println("");
            int opcion2 = Util.ask_option(habitacion_opcion, habitaciones.size()) - 1;
            Habitacion habitacion = habitaciones.get(opcion2);
            long difference_In_Time = salida.getTime() - entrada.getTime();
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            float precio_total = difference_In_Days * habitacion.getHabitacion_price();
            System.out.println("Usted ha elegido una habitacion " + habitacion.getTipo() + " para un total de "
                    + difference_In_Days + " noches.");
            System.out.println("El costo a pagar es de " + precio_total + " dolares.");
            String eleccion;
            do {
                System.out.print("Desea reservar?(Si/No): ");
                eleccion = sc.nextLine();
                System.out.println("");
                switch (eleccion.toUpperCase()) {
                    case "SI":
                        Random rand = new Random();
                        int upperbound = 100000;
                        int int_random = rand.nextInt(upperbound);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        Util.add_reserva(int_random, formatter.format(date), "hospedaje",
                                this.name + " " + this.lastname,
                                formatter.format(entrada), formatter.format(salida), precio_total,ciudad);
                        Util.add_reservaDepartamento(int_random, ciudad, departamento.getDepartment_name());
                        Service servicio = new Hospedaje(departamento.getId(), departamento.getPlace(), departamento.getService_price(),
                                departamento.getPunctuation(), departamento.getHospedaje_id());
                        servicio.setReserva_id(int_random);
                        servicio.print_reserva(int_random);
                        this.getServicios_reservados().add(servicio);
                        break;
                    case "NO":
                        break;
                    default:
                        System.out.println("SI o NO para reservar");
                        System.out.println("");
                        break;
                }
            } while (!eleccion.toUpperCase().matches("SI|NO"));
        }

    }

    /**
     * Metodo para que un cliente reserve transporte
     *
     * @param sc
     */
    public void reservar_transporte(Scanner sc) {
        System.out.println("/******************RESERVACION********************/");
        System.out.println("/*                                               */");
        System.out.println("/*************************************************/");
        String fecha_inicial = "Ingrese una fecha inicial de reserva(dia/mes/año): ";
        String fecha_final = "Ingrese una fecha final de reserva(dia/mes/año): ";
        Date entrada = Util.ask_date(fecha_inicial);
        Date salida = Util.ask_date(fecha_final);
        String msj = "Ciudad: ";
        String ciudad = Util.ask_ciudad(msj);
        ArrayList<Transport> transportes = Transport.get_available_transportes(ciudad);
        //ArrayList<Vehicle> every_vehicle_list = Vehicle.get_every_vehicle();
        ArrayList<Vehicle> available_vehicles = new ArrayList<>();
        String capacity_message = "Ingrese la capacidad del vehiculo: ";
        do {
            int requested_size = Util.ask_number(capacity_message);
            for (Transport transport : transportes) {
                if (transport.getVehiculo().getCapacity() == requested_size && transport.getVehiculo().getStatus().equals(StatusType.DISPONIBLE)) {
                    available_vehicles.add(transport.getVehiculo());
                }
            }
            if (available_vehicles.isEmpty()) {
                System.out.println("No hay vehiculos disponibles con esa capacidad");
                System.out.println("");
            }
        } while (available_vehicles.isEmpty());
        for (int i = 0; i < available_vehicles.size(); i++) {
            System.out.println(i + 1 + ". " + available_vehicles.get(i).getBrand() + "-" + available_vehicles.get(i).getModel());
        }
        System.out.println("");
        String mensaje_opcion = "Elija una opcion: ";
        int vehicle = Util.ask_option(mensaje_opcion, available_vehicles.size()) - 1;
        String vehiculo = available_vehicles.get(vehicle).getBrand() + "-" + available_vehicles.get(vehicle).getModel();
        System.out.println("Datos de " + vehiculo);
        System.out.println("/*************************************************/");
        System.out.println(vehicle + 1 + ".\n" + available_vehicles.get(vehicle).toString());
        System.out.println("/*************************************************/");
        System.out.println("");
        long difference_In_Time = salida.getTime() - entrada.getTime();
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
        float precio_total = difference_In_Days * available_vehicles.get(vehicle).getCost();
        System.out.println("Usted ha elegido reservar un " + vehiculo + " por "
                + difference_In_Days + " dias.");
        System.out.println("El costo a pagar es de " + precio_total + " dolares.");
        String eleccion;
        do {
            System.out.print("Desea reservar?(Si/No): ");
            eleccion = sc.nextLine();
            System.out.println("");
            switch (eleccion.toUpperCase()) {
                case "SI":
                    Random rand = new Random();
                    int upperbound = 100000;
                    int int_random = rand.nextInt(upperbound);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    Util.add_reserva(int_random, formatter.format(date), "transporte",
                            this.name + " " + this.lastname,
                            formatter.format(entrada), formatter.format(salida), precio_total,ciudad);
                    Util.add_reservaTransporte(int_random, available_vehicles.get(vehicle).getId(), precio_total,ciudad);
                    int int_service = rand.nextInt(upperbound);
                    Service servicio = new Transport(int_service, ciudad, precio_total, 5, available_vehicles.get(vehicle));
                    servicio.setReserva_id(int_random);
                    servicio.print_reserva(int_random);
                    this.getServicios_reservados().add(servicio);
                    break;
                case "NO":
                    break;
                default:
                    System.out.println("SI o NO para reservar");
                    System.out.println("");
                    break;
            }
        } while (!eleccion.toUpperCase().matches("SI|NO"));

    }

    /**
     * Metodo para que un cliente reserve entretenimiento
     *
     *
     *
     * @param sc
     */
    public void reservar_entretenimiento(Scanner sc) {
        System.out.println("/******************RESERVACION********************/");
        System.out.println("/*                                               */");
        System.out.println("/*************************************************/");
        String msj = "Ingrese el nombre de la ciudad donde se alojara: ";
        String ciudad = Util.ask_ciudad(msj);
        ArrayList<Entertainment> available_entertainments = Entertainment.get_available_entertainments(ciudad);
        int num_personas = Util.ask_number("Numero de personas: ");
        for (int i = 0; i < available_entertainments.size(); i++) {
            System.out.println(i + 1 + ". " + available_entertainments.get(i).getTitulo());
        }
        System.out.println("");
        String mensaje_opcion = "Elija una opcion para conocer mas: ";
        int paquete = Util.ask_option(mensaje_opcion, available_entertainments.size()) - 1;
        System.out.println(paquete + 1 + ".\n" + available_entertainments.get(paquete).toString());
        float precio_total = available_entertainments.get(paquete).getCost() * num_personas;
        System.out.println("Usted ha elegido reservar un paquete de entretenimiento para "
                + num_personas + " personas.");
        System.out.println("El costo a pagar es de " + precio_total + " dolares.");
        String eleccion;
        do {
            System.out.print("Desea reservar?(Si/No): ");
            eleccion = sc.nextLine();
            System.out.println("");
            switch (eleccion.toUpperCase()) {
                case "SI":
                    Random rand = new Random();
                    int upperbound = 100000;
                    int int_random = rand.nextInt(upperbound);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    Util.add_reserva(int_random, formatter.format(date), "entretenimiento",
                            this.name + " " + this.lastname,
                            available_entertainments.get(paquete).getSalida(),
                            available_entertainments.get(paquete).getRegreso(), precio_total,ciudad);
                    Util.add_reservaEntertainment(int_random, ciudad, available_entertainments.get(paquete).getTitulo());
                    //int int_service = rand.nextInt(upperbound);
                    Service servicio = available_entertainments.get(paquete);
                    servicio.setReserva_id(int_random);
                    servicio.print_reserva(int_random);
                    this.getServicios_reservados().add(servicio);
                    break;
                case "NO":
                    break;
                default:
                    System.out.println("SI o NO para reservar");
                    System.out.println("");
                    break;
            }
        } while (!eleccion.toUpperCase().matches("SI|NO"));
    }
/**
 * Metodo para pagar una reserva dada segun el codigo
 * @param sc 
 */
    public void pagar_reserva(Scanner sc) {
        String ingreso = "Ingrese el codigo de reserva: ";
        int codigo = Util.ask_number(ingreso);
        System.out.println("Metodos de pago disponibles: ");
        System.out.println(1 + ". " + "Tarjeta de credito");
        System.out.println(2 + ". " + "Cheque");
        System.out.println("");
        String pago_opcion = "Seleccion un metodo de pago: ";
        System.out.println("");
        int opcion = Util.ask_option(pago_opcion, 2) - 1;
        if (opcion + 1 == 1) {
            long tarjetaCredito;
            int year;
            int month;
            String error_message;
            do {
                tarjetaCredito = Util.ask_long_number("Por favor, inserte su numero de tarjeta de credito: ");
                year = Util.ask_number("Por favor, inserte el año de la tarjeta: ");
                month = Util.ask_number("Por favor, inserte el numero del mes de vencimiento: ");
                error_message = Util.check_tarjeta_length(tarjetaCredito) ? "" : "Ingrese una tarjeta valida";
                System.out.println(error_message);
            } while (error_message.equals("Ingrese una tarjeta valida"));
            pagar(tarjetaCredito, year, month, codigo, sc);
        } else {
            int cheque = Util.ask_number("Por favor, inserte el numero del cheque: ");
            int year = Util.ask_number("Por favor, inserte el año de la tarjeta: ");
            int month = Util.ask_number("Por favor, inserte el numero del mes de vencimiento: ");
            System.out.println("En las próximas 24 horas debe ir a depositarlo o de lo contrario su pago no será válido ni la reserva será considerada");
            pagar(cheque, year, month, codigo, sc);
        }
    }
/**
 * Metodo para pagar con tarjeta de credito
 * @param tarjeta
 * @param year
 * @param month
 * @param codigo
 * @param sc 
 */
    private void pagar(long tarjeta, int year, int month, int codigo, Scanner sc) {
        float valorPagar = Util.get_valor_reserva(codigo);
        double iva = 0.10;
        double descuento = 0;
        if (this.user_type.equals(UserType.CLIENTE_VIP)) {
            descuento = 0.15;
        }
        float valorPagarFinal = (float) (valorPagar - (valorPagar * descuento) + (valorPagar * iva));
        String eleccion;
        do {
            System.out.print("Confirmar pago(Si/No): ");
            eleccion = sc.nextLine();
            System.out.println("");
            switch (eleccion.toUpperCase()) {
                case "SI":
                    Random rand = new Random();
                    int upperbound = 100000;
                    int int_random = rand.nextInt(upperbound);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    Util.add_pago(codigo, int_random, this.name + " " + this.lastname, formatter.format(date), valorPagar, valorPagarFinal,
                            "TARJETA", tarjeta, month, year);
                    System.out.println("Pago realizado!");
                    break;
                case "NO":
                    break;
                default:
                    System.out.println("SI o NO para reservar");
                    System.out.println("");
                    break;
            }
        } while (!eleccion.toUpperCase().matches("SI|NO"));

    }
/**
 * Metodo para pagar con cheque
 * @param cheque
 * @param year
 * @param month
 * @param codigo
 * @param sc 
 */
    private void pagar(int cheque, int year, int month, int codigo, Scanner sc) {
        float valorPagar = Util.get_valor_reserva(codigo);
        double descuento = 0;
        if (this.user_type.equals(UserType.CLIENTE_VIP)) {
            descuento = 0.15;
        }
        float valorPagarFinal = (float) (valorPagar - (valorPagar * descuento));
        String eleccion;
        do {
            System.out.print("Confirmar pago(Si/No): ");
            eleccion = sc.nextLine();
            System.out.println("");
            switch (eleccion.toUpperCase()) {
                case "SI":
                    Random rand = new Random();
                    int upperbound = 100000;
                    int int_random = rand.nextInt(upperbound);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    Util.add_pago(codigo, int_random, this.name + " " + this.lastname, formatter.format(date), valorPagar, valorPagarFinal,
                            "CHEQUE", cheque, month, year);
                    System.out.println("Pago realizado!");
                    break;
                case "NO":
                    break;
                default:
                    System.out.println("SI o NO para reservar");
                    System.out.println("");
                    break;
            }
        } while (!eleccion.toUpperCase().matches("SI|NO"));
    }
/**
 * Metodo para consultar las reservas hechs por el cliente
 * @param sc 
 */
    @Override
    public void consultar_reservas(Scanner sc) {
        ArrayList<Service> servicios = this.getServicios_reservados();
        if (!servicios.isEmpty()) {
            System.out.println("/******************SERVICIOS RESERVADOS********************/");
            System.out.println("/*                                                        */");
            System.out.println("/**********************************************************/");
            System.out.println("");
            for (Service servicio : servicios) {
                if (servicio instanceof Hospedaje) {
                    servicio.print_reserva(servicio.getReserva_id());
                    System.out.println("/**********************************************************/");
                    System.out.println("");
                }
                if (servicio instanceof Entertainment) {
                    servicio.print_reserva(servicio.getReserva_id());
                    System.out.println("/**********************************************************/");
                    System.out.println("");
                }
                if (servicio instanceof Transport) {
                    servicio.print_reserva(servicio.getReserva_id());
                    System.out.println("/**********************************************************/");
                    System.out.println("");
                }
            }
        } else {
            System.out.println("Primero reserve un servicio.");
        }

    }
    
}
