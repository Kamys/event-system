package my.test.request.service

import my.test.common.Parcel
import java.time.LocalDateTime
import java.util.*

fun createRandomParcel(destination: String, position: Int): Parcel {
    val parcel = Parcel(
        id = UUID.randomUUID(),
        weight = (1..10).random() + (0..99).random() / 100.0,
        destination = "Destination-${destination}",
        position = position,
        createdAt = LocalDateTime.now()
    )
    return parcel
}