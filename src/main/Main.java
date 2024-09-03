package main;

import manager.KonohaDatabaseManager;
import models.Mision;
import models.Ninja;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static KonohaDatabaseManager dbManager = new KonohaDatabaseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Bienvenida();
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    listarNinjas();
                    break;
                case 2:
                    mostrarMisionesDisponiblesParaNinja();
                    break;
                case 3:
                    mostrarMisionesCompletadasParaNinja();
                    break;
                case 4:
                    asignarMisionANinja();
                    break;
                case 5:
                    marcarMisionCompletada();
                    break;
                case 6:
                    mostrarMisionesCompletadas();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
// 
    private static void mostrarMenu() {  
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║               Sistema de Gestión              ║");
        System.out.println("║                   de Konoha                   ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║                                               ║");
        System.out.println("║ 1. Listar todos los ninjas                    ║");
        System.out.println("║                                               ║");
        System.out.println("║ 2. Mostrar misiones disponibles para un ninja ║");
        System.out.println("║                                               ║");
        System.out.println("║ 3. Mostar misiones completadas de un ninja    ║");
        System.out.println("║                                               ║");
        System.out.println("║ 4. Asignar una mision a un ninja              ║");
        System.out.println("║                                               ║");
        System.out.println("║ 5. Marcar una mision como completada          ║");
        System.out.println("║                                               ║");
        System.out.println("║ 6. Mostrar todas las misiones completadas     ║");
        System.out.println("║                                               ║");
        System.out.println("║ 0. Salir                                      ║");
        System.out.println("║                                               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Select an option: ");
        System.out.print("-> ");
    }

    private static void listarNinjas() {
    List<Ninja> ninjas = dbManager.listarNinjas();
    if (ninjas.isEmpty()) {
        System.out.println("\nNo se encontraron ninjas.");
    } else {
        System.out.println("\n╔════════════╦═════════════════════════════╦════════════╦═════════════════╗");
        System.out.println("║     ID     ║            Nombre           ║    Rango   ║      Aldea      ║");
        System.out.println("╠════════════╬═════════════════════════════╬════════════╬═════════════════╣");

        for (Ninja ninja : ninjas) {
            System.out.printf("║ %-10d ║ %-27s ║ %-10s ║ %-15s ║\n",
                              ninja.getId(), ninja.getNombre(), ninja.getRango(), ninja.getAldea());
        }

        System.out.println("╚════════════╩═════════════════════════════╩════════════╩═════════════════╝\n");
    }
}


    private static void mostrarMisionesDisponiblesParaNinja() {
    Ninja ninja = obtenerNinjaPorId();
    if (ninja != null) {
        List<Mision> misiones = dbManager.misionesParaNinja(ninja);
        if (misiones.isEmpty()) {
            System.out.println("\nNo hay misiones disponibles para este ninja.");
        } else {// \n
            System.out.println("╔════════════╦════════════════════════════════════╦═══════════════╦═════════════════╗");
            System.out.println("║     ID     ║               Descripción          ║     Rango     ║   Recompensa    ║");
            System.out.println("╠════════════╬════════════════════════════════════╬═══════════════╬═════════════════╣");

            for (Mision mision : misiones) {
                System.out.printf("║ %-10d ║ %-34s ║ %-13s ║ %-15s ║\n",
                                  mision.getId(), mision.getDescripcion(), mision.getRango(), mision.getRecompensa());
            }

            System.out.println("╚════════════╩════════════════════════════════════╩═══════════════╩═════════════════╝\n");
        }
    } else {
        System.out.println("\nNinja no encontrado.");
    }
}


    private static void mostrarMisionesCompletadasParaNinja() {
    Ninja ninja = obtenerNinjaPorId();
    if (ninja != null) {
        List<Mision> misiones = dbManager.misionesCompletadasPorNinja(ninja);
        if (misiones.isEmpty()) {
            System.out.println("\nEste ninja no ha completado ninguna misión.");
        } else {
            System.out.println("\n╔════════════╦════════════════════════════════════════════════╦═══════════════╦═════════════════╗");
            System.out.println("║     ID     ║                    Descripción                 ║     Rango     ║   Recompensa    ║");
            System.out.println("╠════════════╬════════════════════════════════════════════════╬═══════════════╬═════════════════╣");

            for (Mision mision : misiones) {
                System.out.printf("║ %-10d ║ %-27s ║ %-13s ║ %-15s ║\n",
                                  mision.getId(), mision.getDescripcion(), mision.getRango(), mision.getRecompensa());
            }

            System.out.println("╚════════════╩════════════════════════════════════════════════╩═══════════════╩═════════════════╝\n");
        }
    } else {
        System.out.println("\nNinja no encontrado.");
    }
}


    private static void asignarMisionANinja() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Ingrese el ID del ninja: ");
    int idNinja = scanner.nextInt();
    System.out.print("Ingrese el ID de la misión: ");
    int idMision = scanner.nextInt();
    System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
    String fechaInicioStr = scanner.next();
    LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);

    try {
        dbManager.asignarMision(idNinja, idMision, fechaInicio);
    } catch (Exception e) {
        System.err.println("Error al asignar la misión: " + e.getMessage());
    }
}


    private static void marcarMisionCompletada() {
        Ninja ninja = obtenerNinjaPorId();
        if (ninja != null) {
            System.out.print("Ingrese el ID de la misión a marcar como completada: ");
            int misionId = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            Mision mision = new Mision(misionId, "", "", 0); // ID de misión
            System.out.print("Ingrese la fecha de finalización (YYYY-MM-DD): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine());

            dbManager.marcarMisionCompletada(ninja, mision, fechaFin);
            System.out.println("Misión marcada como completada.");
        } else {
            System.out.println("Ninja no encontrado.");
        }
    }

    private static void mostrarMisionesCompletadas() {
    List<Mision> misiones = dbManager.obtenerTodasLasMisionesCompletadas();
    if (misiones.isEmpty()) {
        System.out.println("\nNo se encontraron misiones completadas.");
    } else {
            System.out.println("\n╔════════════╦════════════════════════════════════════════════╦═══════════════╦═════════════════╗");
            System.out.println("║     ID     ║                    Descripción                 ║     Rango     ║   Recompensa    ║");
            System.out.println("╠════════════╬════════════════════════════════════════════════╬═══════════════╬═════════════════╣");

        for (Mision mision : misiones) {
                System.out.printf("║ %-10d ║ %-27s ║ %-13s ║ %-15s ║\n",
                              mision.getId(), mision.getDescripcion(), mision.getRango(), mision.getRecompensa());
        }

        System.out.println("╚════════════╩════════════════════════════════════════════════╩═══════════════╩═════════════════╝\n");
    }
}


    private static Ninja obtenerNinjaPorId() {
        System.out.print("Ingrese el ID del ninja: ");
        int ninjaId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        return dbManager.obtenerNinjaPorId(ninjaId);
    }
    
    private static void Bienvenida() {
        System.out.println("      ______          __  _                    __        ");
        System.out.println("     / ____/__  _____/ /_(_)___  ____     ____/ /__      ");
        System.out.println("    / / __/ _ \\/ ___/ __/ / __ \\/ __ \\   / __  / _ \\     ");
        System.out.println("   / /_/ /  __(__  ) /_/ / /_/ / / / /  / /_/ /  __/     ");
        System.out.println("   \\____/\\___/____/\\__/_/\\____/_/ /_/   \\__,_/\\___/  ");    
        System.out.println("      / //_/___  ____  ____  / /_  ____ _                ");
        System.out.println("     / ,< / __ \\/ __ \\/ __ \\/ __ \\/ __ `/              ");  
        System.out.println("    / /| / /_/ / / / / /_/ / / / / /_/ /                 ");
        System.out.println("   /_/ |_\\____/_/ /_/\\____/_/ /_/\\__,_/                 "); 
        System.out.println("                                                         ");
    }
}
