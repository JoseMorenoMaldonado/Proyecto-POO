package ec.edu.espol.hospedaje;

import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Department extends Hospedaje {
    
    private String department_name;
    private int num_habitaciones;
    private boolean has_wifi;
    private boolean has_pool;
    private ArrayList<Habitacion> habitaciones;
    
    public Department(long id, String place, float service_price, float punctuation, long hospedaje_id,
            String name, int num_habitaciones, boolean has_wifi, boolean has_pool) {
        super(id, place, service_price, punctuation, hospedaje_id);
        this.department_name = name;
        this.num_habitaciones = num_habitaciones;
        this.has_wifi = has_wifi;
        this.has_pool = has_pool;
    }
    
    public int getNum_habitaciones() {
        return num_habitaciones;
    }
    
    public void setNum_habitaciones(int num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
    }
    
    public boolean isHas_wifi() {
        return has_wifi;
    }
    
    public void setHas_wifi(boolean has_wifi) {
        this.has_wifi = has_wifi;
    }
    
    public boolean isHas_pool() {
        return has_pool;
    }
    
    public void setHas_pool(boolean has_pool) {
        this.has_pool = has_pool;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
    
    @Override
    public String toString() {
        String wifi = (this.has_wifi == true) ? "Si" : "No";
        String pool = (this.has_pool == true) ? "Si" : "No";
        return "Nombre: " + this.department_name + "\nNumero de habitaciones: " + this.num_habitaciones 
                + "\nIncluye wifi: " + wifi
                + "\nIncluye piscina: " + pool;

    }
    
    public static ArrayList<Department> get_every_department() {
        ArrayList<Department> departamentos = new ArrayList<>();
        try ( Scanner sc = new Scanner(new File(Util.getDepartamentos_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                Hospedaje hospedaje = Hospedaje.get_Hospedaje(Long.parseLong(tokens[0]));
                Department departamento = new Department(hospedaje.getId(), hospedaje.getPlace(), hospedaje.getService_price(), hospedaje.getPunctuation(),
                        hospedaje.getHospedaje_id(), tokens[1], Integer.parseInt(tokens[2]), Boolean.parseBoolean(tokens[3]), Boolean.parseBoolean(tokens[4]));
                departamento.setHabitaciones(Habitacion.get_available_habitaciones(hospedaje.getHospedaje_id()));
                departamentos.add(departamento);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return departamentos;
    }
    
    public static ArrayList<Department> get_available_departamentos(String ciudad) {
        ArrayList<Department> available_departamentos = new ArrayList<>();
        for (Department departamento : get_every_department()) {
            if (departamento.getPlace().equals(ciudad)) {
                available_departamentos.add(departamento);
            }
        }
        
        return available_departamentos;
    }
}
