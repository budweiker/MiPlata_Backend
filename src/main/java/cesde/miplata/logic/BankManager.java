package cesde.miplata.logic;
import java.util.Scanner;
public class BankManager {
    private final User[] users;
    private int count = 0;

    public BankManager() {
        this(100); // capacidad
    }

    public BankManager(int capacity) {
        this.users = new User[capacity];
    }


    public boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public User registerUser(Scanner sc) {
        System.out.println("\n=== Registro de usuario ===");

        System.out.print("Nombre de usuario: ");
        String userName = readNonEmpty(sc);

        String email;
        while (true) {
            System.out.print("Email: ");
            email = readNonEmpty(sc);
            if (validateEmail(email)) break;
            System.out.println("Email inválido. Intente de nuevo.");
        }

        System.out.print("Contraseña: ");
        String password = readNonEmpty(sc);

        double initial = readDouble(sc, "Saldo inicial: ", 0.0);

        String id = "U" + (count + 1);
        User u = new User(id, userName, email, password, initial);

        if (count < users.length) {
            users[count++] = u;
            System.out.println("✔ Usuario registrado con id " + id);
            return u;
        } else {
            System.out.println("✖ Capacidad de usuarios llena");
            return null;
        }
    }

    public User login(Scanner sc) {
        System.out.println("\n=== Iniciar sesión ===");
        System.out.print("Email: ");
        String email = readNonEmpty(sc);
        System.out.print("Contraseña: ");
        String pass = readNonEmpty(sc);

        User u = findByEmail(email);
        if (u != null && u.verifyPassword(pass)) {
            System.out.println("✔ Inicio de sesión exitoso");
            return u;
        }
        System.out.println("✖ Credenciales inválidas");
        return null;
    }

    // Utilidades internas

    public User findByEmail(String email) {
        for (int i = 0; i < count; i++) {
            if (users[i].getEmail().equalsIgnoreCase(email)) {
                return users[i];
            }
        }
        return null;
    }

    public static String readNonEmpty(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.print("Valor vacío. Intenta de nuevo: ");
        }
    }

    public static double readDouble(Scanner sc, String prompt, double minInclusive) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().replace(",", ".");
            try {
                double v = Double.parseDouble(s);
                if (v >= minInclusive) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Ingresa un número válido (>= " + minInclusive + ").");
        }
    }
}

