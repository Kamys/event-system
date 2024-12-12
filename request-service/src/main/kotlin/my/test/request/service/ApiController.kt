package my.test.request.service

import my.test.request.service.kafka.KafkaService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ApiController(
    private val parcelService: ParcelService,
    private val kafkaService: KafkaService,
) {
    private var parcelAllCount = 100

    @PostMapping("/send-parcels")
    fun sendParcels(@RequestParam count: Int, @RequestParam type: String?) {
        if (type == "AtLeastOnce") {
            kafkaService.setAtLeastOnceSettings()
        }
        if (type == "AtMostOnce") {
            kafkaService.setAtMostOnceSettings()
        }
        if (type == "ExactlyOnce") {
            kafkaService.setExactlyOnceSettings()
        }
        parcelAllCount = count
        val destination = UUID.randomUUID().toString()
        parcelService.clearAllCollections()
        repeat(parcelAllCount) {
            parcelService.createRequest(
                createRandomParcel(destination, it)
            )
        }
    }

    @PostMapping("/show-result")
    fun showResults(): String {
        val allParcels = parcelService.getAllParcels()
        val uniqueCount = allParcels.map { "${it.position}${it.destination}" }.toSet().count()
        val duplicateCount = allParcels.size - uniqueCount
        val lostCount = parcelAllCount - uniqueCount

        val result = """
            Result size: ${allParcels.size}
            Unique count: $uniqueCount
            Duplicate count: $duplicateCount
            Lost count: $lostCount
        """.trimIndent()
        println(result)
        return result;
    }
}