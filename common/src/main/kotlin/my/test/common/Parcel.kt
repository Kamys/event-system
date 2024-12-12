package my.test.common

import java.time.LocalDateTime
import java.util.*

data class Parcel(
    var id: UUID,
    val weight: Double,
    val position: Int,
    val destination: String,
    val createdAt: LocalDateTime,
)