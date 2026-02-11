import java.io.*;
import java.util.*;

public class GestionAlumnos {
    public static void main(String[] args) {

        //Nombre de los ficheros
        String archivoEntrada = "alumnos.txt";
        String archivoSalida = "resultado.txt";

        // Guardo la suma total de notas de cada alumno. clave -> alumno , valor -> suma notas
        HashMap<String, Double> sumaNotas = new HashMap<>();

        // Guardo cuántas notas tiene cada alumno
        HashMap<String, Integer> contadorNotas = new HashMap<>();

        // Ahora abro el fichero alumnos.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada))) {
            //Aqui se guarda cada linea que lee del fichero
            String linea;

            // Aqui lee el fichero linea a linea y el bucle termina cuando no quedan lineas
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";"); // Aqui divide la linea con ";"

                // Guardo nombre y nota
                String nombre = partes[0];
                double nota = Double.parseDouble(partes[1]);
                // Si el alumno ya existe suma la nota y si no existe empieza desde 0
                sumaNotas.put(nombre, sumaNotas.getOrDefault(nombre, 0.0) + nota);

                // Cada vez que aparece el alumno, sumamos 1
                contadorNotas.put(nombre, contadorNotas.getOrDefault(nombre, 0) + 1);
            }

            // Si hay error leyendo el fichero, se muestra un mensaje
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            return;
        }

        // Guardo la nota media de cada alumno
        HashMap<String, Double> medias = new HashMap<>();

        // Recorro todos los alumnos guardados
        for (String nombre : sumaNotas.keySet()) {
            // Calculo la media: suma de notas / número de nota
            double media = sumaNotas.get(nombre) / contadorNotas.get(nombre);
            // Guardo la media del alumno
            medias.put(nombre, media);
        }

        // Convierto el HashMap en una lista para poderlo ordenar
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(medias.entrySet());
        // Ordeno los alumnos por su nota media de menor a mayor
        listaOrdenada.sort(Map.Entry.comparingByValue());

        // Escribo los resultados en el fichero
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {
            writer.write("Alumnos Aprobados");
            writer.newLine();

            // Recorro los alumnos ordenados por media
            for (Map.Entry<String, Double> entry : listaOrdenada) {
                // Solo mayores o iguales a 5
                if (entry.getValue() >= 5) {
                    // Escribo nombre y nota media en el fichero.
                    writer.write(entry.getKey() + " -> " + entry.getValue());
                    writer.newLine();
                }
            }

        } catch(IOException e){
            System.out.println("Error al escribir el archivo");
        }
    }
}
