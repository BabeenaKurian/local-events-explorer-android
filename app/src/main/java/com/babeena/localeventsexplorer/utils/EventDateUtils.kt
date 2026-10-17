package com.babeena.localeventsexplorer.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object EventDateUtils {
    private val outputFormatter =
        DateTimeFormatter.ofPattern(
            "MMMM d, yyyy 'at' h:mm a",
            Locale.getDefault()
        )

    fun formatEventDate(dateTime: String): String {
        return try {
            OffsetDateTime.parse(dateTime)
                .format(outputFormatter)
        } catch (exception: Exception) {
            dateTime
        }
    }
}