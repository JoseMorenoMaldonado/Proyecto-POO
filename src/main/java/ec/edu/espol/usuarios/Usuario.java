package ec.edu.espol.usuarios;

import ec.edu.espol.util.Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo#n
 */
public class Usuario {

    protected int cedula;
    protected String name;
    protected String lastname;
    protected String username;
    protected String password;
    protected int phone_number;
    protected UserType user_type;

    public Usuario(int cedula, String name, String lastname, String username, String password, int phone_number, UserType user_type) {
        this.cedula = cedula;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.user_type = user_type;

    }

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
    
    
    public void consultar_reservas(Scanner sc) {}

    /**
     * Este método retorna una lista de todos los usuarios en el sistema leyendo
     * el archivo usuarios.txt
     *
     * @return Retorna un ArrayList de todos los usuarios que existen.
     *
     */
    public static ArrayList<Usuario> get_lista_usuarios_txt() {
        ArrayList<Usuario> user_list = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(Util.getUsuarios_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                UserType usertype = null;
                switch (tokens[6].charAt(0)) {
                    case 'C' ->
                        usertype = UserType.CLIENTE;
                    case 'V' ->
                        usertype = UserType.CLIENTE_VIP;
                    case 'A' ->
                        usertype = UserType.ADMIN;
                }
                Usuario new_user = new Usuario(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), usertype);
                user_list.add(new_user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user_list;
    }

    /**
     *
     * Este metodo retorna un administrador o cliente dado un username por medio
     * de polimorfismo
     *
     * @param username
     * @return Usuario
     *
     *
     */
    public static Usuario get_user(String username) {
        for (Usuario user : get_lista_usuarios_txt()) {
            if (user.getUsername().equals(username)) {
                if (user.getUser_type().equals(UserType.ADMIN)) {
                    return new Administrador(user.getCedula(), user.getName(), user.getLastname(), user.getUsername(),
                            user.getPassword(), user.getPhone_number(), user.getUser_type());
                } else {
                    return new Cliente(user.getCedula(), user.getName(), user.getLastname(), user.getUsername(),
                            user.getPassword(), user.getPhone_number(), user.getUser_type(), get_edad_data(user), get_tarjeta_data(user));
                }
            }
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
    private static int get_edad_data(Usuario usuario) {
        try (Scanner sc = new Scanner(new File(Util.getClientes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == usuario.getCedula()) {
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
    private static long get_tarjeta_data(Usuario usuario) {
        try ( Scanner sc = new Scanner(new File(Util.getClientes_txt()))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] tokens = linea.split(",");
                if (Integer.parseInt(tokens[0]) == usuario.getCedula()) {
                    return Long.parseLong(tokens[2]);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
