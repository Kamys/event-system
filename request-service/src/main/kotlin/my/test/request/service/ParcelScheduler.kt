package my.test.request.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.concurrent.thread

@Component
class ParcelScheduler(
    private val parcelRequestService: ParcelRequestService,
    private val parcelService: ParcelService
) {
    private val logger = LoggerFactory.getLogger(ParcelScheduler::class.java)

    @Scheduled(fixedRate = 100)
    fun sendParcelEvent() {
        try {
        val parcel = parcelService.getRequest() ?: return
        parcelService.removeRequest(parcel)
            parcelRequestService.sendParcel(parcel)
        } catch (e: Exception) {
            logger.error("Failed send sendRandomParcel")
            throw e
        }
    }
}