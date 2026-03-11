package cesde.miplata.logic;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class User {

    // Atributos
    private final String id;
    private String userName;
    private String email;
    private String password;
    private double balance;

    // Movimientos
    private final String[] movements;
    private int movCount = 0;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public User(String id, String userName, String email, String password, double balance) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id requerido");
        if (userName == null || userName.isBlank()) throw new IllegalArgumentException("userName requerido");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email requerido");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("password requerido");
        if (balance < 0) throw new IllegalArgumentException("balance inicial no puede ser negativo");

        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.balance = balance;

        this.movements = new String[200]; // capacidad
        addMovement("Registro", 0.0);     // primer movimiento
    }

    // --- Getters/Setters

    public String getId() { return id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public void setPassword(String password) { this.password = password; }
    public double getBalance() { return balance; }

    public void setBalance(double amount) {
        if (amount < 0) throw new IllegalArgumentException("El balance no puede ser negativo");
        this.balance = amount;
    }

    // Lógica

    public boolean verifyPassword(String raw) {
        return raw != null && raw.equals(this.password);
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Depósito debe ser > 0");
        this.balance += amount;
        addMovement("Consignación", amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Retiro debe ser > 0");
        if (this.balance >= amount) {
            this.balance -= amount;
            addMovement("Retiro", -amount);
            return true;
        }
        return false;
    }

    private void addMovement(String concept, double value) {
        if (movCount >= movements.length) return; // si se llena, ignoramos
        String line = String.format(
                "%s | %s | %,.2f | %,.2f",
                LocalDateTime.now().format(FMT),
                concept,
                value,
                balance
        );
        movements[movCount++] = line;
    }

    public String[] getMovements() {
        String[] out = new String[movCount];
        System.arraycopy(movements, 0, out, 0, movCount);
        return out;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', userName='" + userName + "', email='" + email + "', balance=" + balance + "}";
    }
}

