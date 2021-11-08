package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

val Long.asMin get() = this.absoluteValue / TimeUnits.MINUTE.size
val Long.asHour get() = this.absoluteValue / TimeUnits.HOUR.size
val Long.asDay get() = this.absoluteValue / TimeUnits.DAY.size

fun Date.humanizeDiff(date: Date = Date()): String {
    val timeDiff = date.time - time //millis. If that > 0 - will be, if that < 0 - was
    return if (timeDiff < 0) (timeDiff * -1).negative
    else (timeDiff).positive
}

val Long.positive get() = when (this) {
    in 0..SECOND -> "только что"
    in SECOND..45 * SECOND -> "несколько секунд назад"
    in 45 * SECOND..75 * SECOND -> "минуту назад"
    in 75 * SECOND.. 45 * MINUTE -> "${TimeUnits.MINUTE.plural(this.asMin)} назад"
    in 45 * MINUTE..75 * MINUTE -> "час назад"
    in 75 * MINUTE..22 * HOUR -> "${TimeUnits.HOUR.plural(this.asHour)} назад"
    in 22 * HOUR..26 * HOUR -> "день назад"
    in 26 * HOUR..360 * DAY -> "${TimeUnits.DAY.plural(this.asDay)} назад"
    else -> "более года назад"
}

val Long.negative get() = when (this) {
    in 0..SECOND -> "прямо сейчас"
    in SECOND..45 * SECOND -> "через несколько секунд"
    in 45 * SECOND..75 * SECOND -> "через минуту"
    in 75 * SECOND.. 45 * MINUTE -> "через ${TimeUnits.MINUTE.plural(this.asMin)}"
    in 45 * MINUTE..75 * MINUTE -> "через час"
    in 75 * MINUTE..22 * HOUR -> "через ${TimeUnits.HOUR.plural(this.asHour)}"
    in 22 * HOUR..26 * HOUR -> "через день"
    in 26 * HOUR..360 * DAY -> "через ${TimeUnits.DAY.plural(this.asDay)}"
    else -> "более чем через год"
}

enum class TimeUnits(val size: Long, val valueName: List<String>) {
    SECOND(1000L, listOf("секунд", "секунду", "секунды")),
    MINUTE(60000L, listOf("минут", "минуту", "минуты")),
    HOUR(3600000L, listOf("часов", "час", "часа")),
    DAY(86400000L, listOf("дней", "день", "дня"));

    fun plural(value: Long) : String = "$value ${this.valueName[calculateEndValue(value)]}"

    private fun calculateEndValue(value: Long) = when {
        value % 100 in 5..20 -> 0
        value % 10 in 2..4 -> 2
        value % 10 == 1L -> 1
        else -> 0
    }
}