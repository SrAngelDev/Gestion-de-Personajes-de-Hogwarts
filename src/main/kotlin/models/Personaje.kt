package srangeldev.models

data class Personaje(
    val id: Long = 0, // Esto será generado por la BBDD
    val nombre: String,
    val casa: String,
    val patronus: String?, //Puede ser nulo si no pertenece a ninguna casa
)
