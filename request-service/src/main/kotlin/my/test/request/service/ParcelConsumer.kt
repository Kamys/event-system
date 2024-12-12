package my.test.request.service

import com.fasterxml.jackson.databind.ObjectMapper
import my.test.common.KafkaConf.Topic
import my.test.common.Parcel
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.*

@Service
class ParcelConsumer(
    private val objectMapper: ObjectMapper,
    private val parcelService: ParcelService
) {
    private val logger = LoggerFactory.getLogger(ParcelRequestService::class.java)

    @KafkaListener(topics = [Topic.PARCEL_TOPIC_NAME], groupId = "parcel-service")
    fun listen(message: String) {
        val parcel = objectMapper.readValue(message, Parcel::class.java)
        logger.info("Event ${parcel.position} - ${parcel.destination}: ${parcel.id}")
        parcel.id = UUID.randomUUID()
        parcelService.saveParcel(parcel)
    }
}