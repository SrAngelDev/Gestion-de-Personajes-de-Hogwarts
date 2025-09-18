package srangeldev.Repositories

import srangeldev.models.Personaje
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class PersonajeRepository(private val databaseUrl: String) {

    //Primero iniciamos la base de datos y creamos la tabla si no existe
    init {
        DriverManager.getConnection(databaseUrl).use { connection ->
            val sql = """
                CREATE TABLE IF NOT EXISTS Personajes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(255) NOT NULL,
                casa VARCHAR(255) NOT NULL,
                patronus VARCHAR(255)
            """.trimIndent()
            connection.createStatement().use { statement ->
                statement.execute(sql) //Ejecuto la creacion de la tabla
            }
        }
    }

    //Método para mapear un ResultSet a un Personaje
    private fun mapResultSetToPersonajes(resultSet: ResultSet): Personaje {
        return Personaje(
            id = resultSet.getLong("id"),
            nombre = resultSet.getString("nombre"),
            casa = resultSet.getString("casa"),
            patronus = resultSet.getString("patronus"),
        )
    }

    //CRUD
    fun save(personaje: Personaje): Personaje {
        val sql = "INSERT INTO Personajes (nombre, casa, patronus) VALUES (?,?,?)"
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, personaje.nombre)
                statement.setString(2, personaje.casa)
                statement.setString(3, personaje.patronus)
                statement.executeUpdate()

                val generatedKeys = statement.generatedKeys
                if (generatedKeys.next()) {
                    return personaje.copy(id = generatedKeys.getLong("id"))
                } else {
                    throw SQLException("No se pudo obtener el ID generado para el personaje")
                }
            }
        }
    }

    fun findAll(): List<Personaje> {
        val personajes = mutableListOf<Personaje>()
        val sql = "SELECT * FROM Personajes"
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        personajes.add(mapResultSetToPersonajes(resultSet))
                    }
                }
            }
        }
        return personajes
    }

    fun findById(id: Long): Personaje? {
        val sql = "SELECT * FROM Personajes WHERE id = ?"
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                statement.executeQuery().use { resultSet ->
                    return if (resultSet.next()) {
                        mapResultSetToPersonajes(resultSet)
                    } else {
                        println("No se ha encontrado ningun personaje con $id")
                        null
                    }
                }
            }
        }
    }

    fun update(personaje: Personaje): Int {
        val sql = "UPDATE Personajes SET nombre = ?, casa = ?, patronus = ? WHERE id = ?"
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, personaje.nombre)
                statement.setString(2, personaje.casa)
                statement.setString(3, personaje.patronus)
                statement.setLong(4, personaje.id)
                return statement.executeUpdate() //Devuelve el numero de filas modificadas
            }
        }
    }

    fun deleteById(id: Long): Int {
        val sql = "DELETE FROM Personajes WHERE id = ?"
        DriverManager.getConnection(databaseUrl).use { connection ->
            connection.prepareStatement(sql).use { statement ->
                return statement.executeUpdate() //Devuelve el numero de filas eliminadas
            }
        }
    }
}