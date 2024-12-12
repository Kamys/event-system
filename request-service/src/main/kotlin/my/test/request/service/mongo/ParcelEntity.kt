package my.test.request.service.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "parcels")
class ParcelEntity(
    @Id
    val id: String,
    val sender: String,
    val recipient: String,
    val weight: Double
)