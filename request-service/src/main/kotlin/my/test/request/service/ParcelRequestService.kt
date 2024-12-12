package my.test.request.service

import my.test.common.KafkaConf.Topic
import my.test.common.Parcel
import my.test.request.service.kafka.KafkaService
import org.apache.kafka.common.header.internals.RecordHeader
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParcelRequestService(
    private val kafkaService: KafkaService,
) {
    private val logger = LoggerFactory.getLogger(ParcelRequestService::class.java)

    fun sendParcel(parcel: Parcel) {
        val key = parcel.id.toString()
        val headers = listOf(
            RecordHeader("content-type", "application/json".toByteArray()),
            RecordHeader("parcel-destination", parcel.destination.toByteArray())
        )
        logger.info("Send parcel ${parcel.position} ${parcel.destination} ${parcel.id}")
        kafkaService.sendMessage(
            topic = Topic.PARCEL_TOPIC,
            key = key,
            entity = parcel,
            headers = headers
        )
    }
}