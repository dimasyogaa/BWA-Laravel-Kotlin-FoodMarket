package com.yogadimas.yogadimas_foodmarketbwa.utils

import android.os.Build
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// TypeConverter to handle Date type since Room doesn't support it natively
class Converters {
    companion object {

        private const val ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

        // Convert ISO 8601 string to Long timestamp
        @TypeConverter
        fun isoStringToTimestamp(value: String?): Long? {
            return value?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    java.time.Instant.parse(it).toEpochMilli()
                } else {
                    val formatter = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
                    formatter.timeZone = TimeZone.getTimeZone("UTC")
                    formatter.parse(it)?.time
                }
            }
        }

        // Convert Long timestamp to ISO 8601 string
        @TypeConverter
        fun timestampToIsoString(value: Long?): String? {
            return value?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochMilli(it))
                } else {
                    val formatter = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
                    formatter.timeZone = TimeZone.getTimeZone("UTC")
                    formatter.format(Date(it))
                }
            }
        }

        @TypeConverter
        fun fromIsoString(value: String?): Date? {
            return value?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Date.from(java.time.Instant.parse(value))
                } else {
                    val formatter = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
                    formatter.timeZone = TimeZone.getTimeZone("UTC")
                    formatter.parse(value)
                }
            }
        }

        @TypeConverter
        fun dateToIsoString(date: Date?): String? {
            return date?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    java.time.format.DateTimeFormatter.ISO_INSTANT.format(date.toInstant())
                } else {
                    val formatter = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)
                    formatter.timeZone = TimeZone.getTimeZone("UTC")
                    formatter.format(date)
                }
            }
        }
    }
}