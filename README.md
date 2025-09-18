# Ejercicio: Gestión de Personajes de Hogwarts

Como estudiante de **2º de DAW** y un gran fanático de **Harry Potter**, se te ha encomendado la tarea de desarrollar una pequeña aplicación en **Kotlin** para gestionar un listado de personajes del universo mágico.

---

## Requisitos de la aplicación

1. **Leer datos desde un fichero CSV**  
   El fichero `personajes_hogwarts.csv` contendrá información básica de algunos personajes (**nombre, casa y patronus**).  
   Deberás procesar este fichero línea a línea.

2. **Cargar los datos en una Base de Datos en Memoria (H2)**  
   Una vez leídos los datos del CSV, deberás insertarlos en una base de datos **H2 en memoria**.  
   - Crearás una tabla `Personajes` con los campos necesarios.  
   - Utilizarás **JDBC** para la conexión y las operaciones.

3. **Implementar operaciones CRUD básicas**  
   Sobre la base de datos H2, deberás poder realizar las siguientes operaciones:
   - **Crear (Create):** Insertar un nuevo personaje en la base de datos.  
   - **Leer (Read):**
     - Obtener todos los personajes existentes.  
     - Obtener un personaje por su identificador (**ID**).  
   - **Actualizar (Update):** Modificar la información de un personaje existente (por ejemplo, su patronus o casa).  
   - **Eliminar (Delete):** Borrar un personaje de la base de datos por su **ID**.

📌 Recuerda utilizar **buenas prácticas de cierre de recursos**, como la función `use` de Kotlin o `try-catch` con recursos de Java, aunque `use` es más idiomática en Kotlin.

---

## CSV a procesar: `personajes_hogwarts.csv`
```CSV
Harry Potter,Gryffindor,Ciervo
Hermione Granger,Gryffindor,Nutria
Ron Weasley,Gryffindor,Jack Russell Terrier
Albus Dumbledore,Gryffindor,Fénix
Severus Snape,Slytherin,Cierva
Draco Malfoy,Slytherin,Ninguno
Luna Lovegood,Ravenclaw,Liebre
Cedric Diggory,Hufflepuff,Perro
Minerva McGonagall,Gryffindor,Gato atigrado
Rubeus Hagrid,Gryffindor,Ninguno
```

---

Cada línea representa un personaje, con los datos separados por comas:  

**Nombre, Casa, Patronus**  

Si un personaje no tiene un patronus conocido, se indicará como `"Ninguno"`.
