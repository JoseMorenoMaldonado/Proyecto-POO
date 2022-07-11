package ec.edu.espol.usuarios;

/**
 *
 * @author Grupo#n
 */
public enum UserType {

    CLIENTE("C"), CLIENTE_VIP("V"), ADMIN("A");

    private final String character;

    private UserType(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return this.character;
    }
}
