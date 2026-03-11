package cesde.miplata.app;
import cesde.miplata.logic.BankManager;
import cesde.miplata.logic.User;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankManager bank = new BankManager(200); // capacidad de usuarios

        User current = null;

        while (true) {
            System.out.println("\n=== Menú principal ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    current = bank.registerUser(sc);
                    break;
                case "2":
                    current = bank.login(sc);
                    break;
                case "3":
                    System.out.println("¡Hasta luego!");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción inválida.");
            }

            if (current != null) {
                while (true) {
                    System.out.println("\n--- Transacciones ---");
                    System.out.println("1. Retirar dinero");
                    System.out.println("2. Consignar dinero");
                    System.out.println("3. Consultar saldo");
                    System.out.println("4. Consultar movimientos");
                    System.out.println("5. Cerrar sesión");
                    System.out.print("Opción: ");
                    String t = sc.nextLine().trim();

                    switch (t) {
                        case "1": {
                            double amount = BankManager.readDouble(sc, "Monto a retirar: ", 0.01);
                            boolean ok = current.withdraw(amount);
                            if (ok) {
                                System.out.printf("Retiro exitoso. Saldo: %,.2f%n", current.getBalance());
                            } else {
                                System.out.println("Saldo insuficiente.");
                            }
                            break;
                        }
                        case "2": {
                            double amount = BankManager.readDouble(sc, "Monto a consignar: ", 0.01);
                            current.deposit(amount);
                            System.out.printf("Consignación exitosa. Saldo: %,.2f%n", current.getBalance());
                            break;
                        }
                        case "3":
                            System.out.printf("Saldo actual: %,.2f%n", current.getBalance());
                            break;
                        case "4": {
                            System.out.println("Fecha y hora | Concepto | Valor | Saldo");
                            String[] movs = current.getMovements();
                            if (movs.length == 0) {
                                System.out.println("(sin movimientos)");
                            } else {
                                for (String line : movs) {
                                    System.out.println(line);
                                }
                            }
                            break;
                        }
                        case "5":
                            current = null; // cerrar sesión
                            System.out.println("Sesión cerrada.");
                            break;
                        default:
                            System.out.println("Opción inválida.");
                    }

                    if (current == null) break; // salir al menú
                }
            }
        }
    }
}
