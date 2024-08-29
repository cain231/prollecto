import java.util.*;

class Cine {
    // Diccionario para almacenar los usuarios registrados
    private static Map<String, String> usuarios = new HashMap<>();

    // Diccionario para almacenar las películas y sus horarios
    private static Map<String, Pelicula> cartelera = new HashMap<>();

    public static void main(String[] args) {
        inicializarCartelera();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al Cine");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Comprar boletos");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume el salto de línea

            switch (opcion) {
                case 1:
                    registro(scanner);
                    break;
                case 2:
                    gestionDelCine(scanner);
                    break;
                case 3:
                    System.out.println("Gracias por usar el sistema de cine. ¡Hasta luego!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    // Función para inicializar la cartelera
    private static void inicializarCartelera() {
        cartelera.put("Pelicula A", new Pelicula("Pelicula A", 10, Arrays.asList("14:00", "17:00", "20:00")));
        cartelera.put("Pelicula B", new Pelicula("Pelicula B", 12, Arrays.asList("13:00", "16:00", "19:00")));
        cartelera.put("Pelicula C", new Pelicula("Pelicula C", 8, Arrays.asList("15:00", "18:00", "21:00")));
    }

    // Función para registrar al usuario
    private static void registro(Scanner scanner) {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su DNI: ");
        String dni = scanner.nextLine();

        if (usuarios.containsKey(dni)) {
            System.out.println("Este DNI ya está registrado.");
        } else {
            usuarios.put(dni, nombre);
            System.out.println("Usuario " + nombre + " registrado correctamente.");
        }
    }

    // Función para gestionar la compra de boletos en el cine
    private static void gestionDelCine(Scanner scanner) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados. Registre un usuario primero.");
            return;
        }

        System.out.print("Ingrese su DNI para continuar: ");
        String dni = scanner.nextLine();

        if (!usuarios.containsKey(dni)) {
            System.out.println("DNI no encontrado. Regístrese primero.");
            return;
        }

        System.out.println("Películas disponibles:");
        List<String> peliculas = new ArrayList<>(cartelera.keySet());
        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula pelicula = cartelera.get(peliculas.get(i));
            System.out.println((i + 1) + ". " + pelicula.getNombre() + " - Precio: $" + pelicula.getPrecio() +
                               " - Horarios: " + String.join(", ", pelicula.getHorarios()));
        }

        System.out.print("Seleccione una película (número): ");
        int opcion = scanner.nextInt() - 1;
        scanner.nextLine();  // Consume el salto de línea
        if (opcion < 0 || opcion >= peliculas.size()) {
            System.out.println("Opción no válida.");
            return;
        }

        Pelicula peliculaSeleccionada = cartelera.get(peliculas.get(opcion));

        if (peliculaSeleccionada.getDisponibilidad() <= 0) {
            System.out.println("Lo sentimos, no hay más asientos disponibles para esta película.");
            return;
        }

        System.out.println("Horarios disponibles para " + peliculaSeleccionada.getNombre() + ": " +
                           String.join(", ", peliculaSeleccionada.getHorarios()));
        System.out.print("Seleccione un horario: ");
        String horarioSeleccionado = scanner.nextLine();

        if (!peliculaSeleccionada.getHorarios().contains(horarioSeleccionado)) {
            System.out.println("Horario no disponible.");
            return;
        } 

        // Reducción de la disponibilidad en la sala
        peliculaSeleccionada.reducirDisponibilidad();

        // Imprimir ticket
        System.out.println("\n--- TICKET ---");
        System.out.println("Usuario: " + usuarios.get(dni));
        System.out.println("DNI: " + dni);
        System.out.println("Película: " + peliculaSeleccionada.getNombre());
        System.out.println("Horario: " + horarioSeleccionado);
        System.out.println("Precio: $" + peliculaSeleccionada.getPrecio());
        System.out.println("Asientos restantes: " + peliculaSeleccionada.getDisponibilidad());
        System.out.println("-----------------\n");
        System.out.println("¡Gracias por su compra!");
    }
}

// Clase para representar una película
class Pelicula {
    private String nombre;
    private int precio;
    private List<String> horarios;
    private int disponibilidad;

    public Pelicula(String nombre, int precio, List<String> horarios) {
        this.nombre = nombre;
        this.precio = precio;
        this.horarios = horarios;
        this.disponibilidad = 30;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void reducirDisponibilidad() {
        if (disponibilidad > 0) {
            disponibilidad--;
        }
    }
}