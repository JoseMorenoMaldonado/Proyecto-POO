package ec.edu.espol.poo_proyect;

import ec.edu.espol.usuarios.Administrador;
import ec.edu.espol.usuarios.Cliente;
import ec.edu.espol.usuarios.UserType;
import ec.edu.espol.usuarios.Usuario;
import ec.edu.espol.util.Menus;
import ec.edu.espol.util.Util;
import java.util.Scanner;



public class Main {
    /**
     * Inicia el programa
     * @param args
     **/
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("++++++++++++++++++++++");
        System.out.println("                      ");
        System.out.println("BIENVENIDO AL SISTEMA");
        System.out.println("                      ");
        System.out.println("++++++++++++++++++++++");
        String username;
        String password;
        String error_message;
        
        do {
            System.out.print("USUARIO: ");
            username = sc.nextLine();
            System.out.print("CONTRASEÑA: ");
            password = sc.nextLine();
            error_message = Util.verify_user_And_password(username, password) ? "" : "Datos incorrectos";
            System.out.println(error_message);
        } while (error_message.equals("Datos incorrectos"));
        Usuario usuario = Usuario.get_user(username);
        if (!usuario.getUser_type().equals(UserType.ADMIN) && !Util.has_extra_data(usuario)) {
            Menus.add_extra_data_menu((Cliente) usuario);
        }
        if (usuario instanceof Cliente) {
            Menus.menu_cliente((Cliente) usuario);
        } else {
            Menus.menu_admin((Administrador) usuario);
        }
    }
}
