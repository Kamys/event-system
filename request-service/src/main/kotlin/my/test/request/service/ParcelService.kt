package my.test.request.service

import my.test.common.Parcel
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ParcelService(
    private val mongoTemplate: MongoTemplate
) {
    fun saveParcel(parcel: Parcel) {
        mongoTemplate.save(parcel, "parcels")
    }

    fun getAllParcels(): List<Parcel> {
        return mongoTemplate.findAll(Parcel::class.java, "parcels")
    }

    fun createRequest(parcel: Parcel) {
        mongoTemplate.save(parcel, "request")
    }

    fun getRequest(): Parcel? {
        return mongoTemplate.findOne(Query(), Parcel::class.java, "request")
    }

    fun removeRequest(parcel: Parcel) {
        mongoTemplate.remove(parcel, "request")
    }

    fun clearAllCollections() {
        mongoTemplate.remove(Query(), "parcels")
        mongoTemplate.remove(Query(), "request")
    }
}