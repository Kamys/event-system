package my.test.request.service.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import my.test.common.KafkaConf.Topic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val objectMapper: ObjectMapper,
    private val producerFactory: ProducerFactory<String, String>
) {
    private var acks: String = "1"
    private var retries: Int = 0
    private var enableIdempotence: Boolean = false
    private var kafkaTemplate: KafkaTemplate<String?, String> = createKafkaTemplate()

    fun sendMessage(topic: Topic, key: String?, entity: Any, headers: List<RecordHeader>) {
        val message = objectMapper.writeValueAsString(entity)
        val producerRecord = ProducerRecord(
            /* topic = */ topic.topicName,
            /* partition = */ null,
            /* key = */ key,
            /* value = */ message,
            /* headers = */ headers
        )
        kafkaTemplate.send(producerRecord)
    }

    fun setAtLeastOnceSettings() {
        println("setAtLeastOnceSettings")
        acks = "all"
        retries = 100
        enableIdempotence = false
        kafkaTemplate = createKafkaTemplate()
    }

    fun setAtMostOnceSettings() {
        println("setAtLeastOnceSettings")
        acks = "0"
        retries = 0
        enableIdempotence = false
        kafkaTemplate = createKafkaTemplate()
    }

    fun setExactlyOnceSettings() {
        println("setExactlyOnceSettings")
        acks = "all"
        retries = 100
        enableIdempotence = true
        kafkaTemplate = createKafkaTemplate()
    }

    private fun createKafkaTemplate(): KafkaTemplate<String?, String> {
        val producerProps = producerFactory.configurationProperties.toMutableMap()
        settingProducerProps(producerProps)
        return KafkaTemplate(
            DefaultKafkaProducerFactory<String, String>(producerProps)
        )
    }

    private fun settingProducerProps(producerProps: MutableMap<String, Any>) {
        producerProps[ProducerConfig.ACKS_CONFIG] = acks
        producerProps[ProducerConfig.RETRIES_CONFIG] = retries
        producerProps[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = enableIdempotence
    }
}