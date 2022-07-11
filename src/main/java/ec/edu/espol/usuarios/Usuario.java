package ec.edu.espol.usuarios;

import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public abstract class Usuario {

    protected int cedula;
    protected String name;
    protected String lastname;
    protected String username;
    protected String password;
    protected int phone_number;
    protected UserType user_type;
    
    
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public UserType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserType user_type) {
        this.user_type = user_type;
    }
/**
 * Metodo asbtracto para consultar reservas
 * @param sc 
 */
    public abstract void consultar_reservas(Scanner sc);

    /**
     * Este método retorna una lista de todos los usuarios en el sistema leyendo
     * el archivo usuarios.txt
     *
     * @param username
     * @return Retorna un ArrayList de todos los usuarios que existen.
     *
     */
    public static Usuario get_user(String username) {
        try ( Scanner sc = new Scanner(new File(Util.getUsuarios_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (tokens[3].equals(username)) {
                    if (tokens[6].charAt(0) == 'C' || tokens[6].charAt(0) == 'V') {
                        Cliente cliente = new Cliente(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]),
                                UserType.CLIENTE, get_edad_data(Integer.parseInt(tokens[0])), get_tarjeta_data(Integer.parseInt(tokens[0])));
                        return cliente;
                    }
                    if (tokens[6].charAt(0) == 'A') {
                        Administrador admin = new Administrador(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4],
                                Integer.parseInt(tokens[5]), UserType.ADMIN);
                        return admin;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     *
     * Este metodo devuelve la edad de un usuario-cliente
     *
     * @param usuario
     * @return int
     *
     */
    private static int get_edad_data(int cedula) {
        try ( Scanner sc = new Scanner(new File(Util.getClientes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == cedula) {
                    return Integer.parseInt(tokens[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Este metodo devuelve el numero de tarjeta de un usuario-cliente
     *
     * @param usuario
     * @return long
     *
     */
    private static long get_tarjeta_data(int cedula) {
        try ( Scanner sc = new Scanner(new File(Util.getClientes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == cedula) {
                    return Long.parseLong(tokens[2]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
