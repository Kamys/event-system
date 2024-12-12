package my.test.common

object KafkaConf {
    enum class Topic {
        PARCEL_TOPIC,
        DELIVERY_TOPIC;

        val topicName: String
            get() = when (this) {
                PARCEL_TOPIC -> PARCEL_TOPIC_NAME
                DELIVERY_TOPIC -> DELIVERY_TOPIC_NAME
            }

        companion object {
            const val PARCEL_TOPIC_NAME = "parcel-topic"
            const val DELIVERY_TOPIC_NAME = "delivery-topic"
        }
    }

    enum class Group {
        PARCEL_SERVICE;

        val topicName: String
            get() = when (this) {
                PARCEL_SERVICE -> PARCEL_SERVICE_NAME
            }

        companion object {
            const val PARCEL_SERVICE_NAME = "parcel-service"
        }
    }
}