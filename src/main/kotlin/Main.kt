package srangeldev

import srangeldev.Repositories.PersonajeRepository
import srangeldev.models.Personaje
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //Primero creo la base de datos donde voy a meter los datos del csv
    val databaseUrl = "jdbc:h2:mem:harrypotter_db;DB_CLOSE_DELAY=-1"

    val repository = PersonajeRepository(databaseUrl)

    println("--- Base de Datos H2 Inicializada ---")

    // Ahora proceso el archivo csv dentro de resources/data

    val csvFilePath = "data/personajes_hogwarts.csv" // Ruta relativa dentro de resources
    val inputStream: InputStream? = ClassLoader.getSystemResourceAsStream(csvFilePath)

    if (inputStream != null) {
        inputStream.bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val partes = line.split(",")
                if (partes.size == 3) {
                    val nombre = partes[1].trim()
                    val casa = partes[2].trim()
                    val patronus = partes[3].trim().let { if (it == "Ninguno") null else it }
                    val nuevoPersonajes = Personaje(nombre = nombre, casa = casa, patronus = patronus)
                    repository.save(nuevoPersonajes)
                    println("Insertado: $nuevoPersonajes")
                }
            }
        }
        println("--- Carga de personajes desde CSV finalizada ---")
    } else {
        println("ERROR: El fichero CSV '$csvFilePath' no se encontró en el directorio resources.")
        println("Asegúrate de que la ruta es correcta y el archivo está en 'src/main/resources/data/personajes_hogwarts.csv'")
    }

    println("\n--- Demostrando Operaciones CRUD ---")
    println("\n--- Todos los Personajes ---")

    //READ: Obtener todos los personajes
    repository.findAll().forEach { personaje -> println(personaje) }

    //CREATE: Crear un nuevo personaje
    println("\n--- Insertando Nuevo Personaje: Dobby ---")
    val dobby = Personaje(nombre = "Dobby", casa = "Elfo Doméstico", patronus = null)
    val dobbyGuardado = repository.save(dobby)
    println("Peronaje insertado: $dobbyGuardado")

    //UPDATE: Actualizar un personaje (ej. Draco Malfoy con ID 6)
    val draco = repository.findById(6L)
    if(draco != null) {
        val dracoActualizado = draco.copy(patronus = "Oso")
        val filasAfectadas = repository.update(dracoActualizado)
        println("Filas afectadas en el cambio: $filasAfectadas")
    }

}