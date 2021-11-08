package ru.skillbranch.devintensive.utils

import java.util.*

private val translationMap = mapOf("а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d",
    "е" to "e", "ё" to "e", "ж" to "zh", "з" to "z", "и" to "i", "й" to "i", "к" to "k", "л" to "l",
    "м" to "m", "н" to "n", "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t", "у" to "u",
    "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch", "ш" to "sh", "щ" to "sh'", "ъ" to "", "ы" to "i",
    "ь" to "", "э" to "e", "ю" to "yu", "я" to "ya",)

object Utils {

    fun parseFullName(fullName: String?) : Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return checkNotBlankString(firstName) to checkNotBlankString(lastName)
    }

    private fun checkNotBlankString(string: String?) : String? {
        return if (string != null && string.isNotBlank()) string
        else null
    }

    fun transliteration(payload: String, divider: String = " " ): String {
        var translatePayload = ""
        val trimPayload = payload.trim()
        for (count in 0 until trimPayload.count()) {
            val currentSymbol = trimPayload[count].toString()
            translatePayload += when  {
                currentSymbol == " " -> divider
                alphabetCheck(currentSymbol) -> currentSymbol
                translationMap[currentSymbol] != null -> translationMap[currentSymbol]
                else -> {
                    var lowUpString = translationMap[currentSymbol.lowercase(Locale("ru"))] ?: ""
                    if (lowUpString != "") lowUpString = lowUpString[0].uppercase(Locale("en")) + lowUpString.substring(1)
                    lowUpString
                }
            }
        }
        return translatePayload
    }

    fun alphabetCheck(input: String): Boolean {
        val regex = Regex("[a-zA-Z]+?")
        return regex.matches(input)
    }


    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial: String? = checkNotBlankString(firstName)?.get(0)?.uppercase(Locale("ru"))
        val secondInitial: String? = checkNotBlankString(lastName)?.get(0)?.uppercase(Locale("ru"))
        return if (firstInitial != null || secondInitial != null) "${firstInitial ?: ""}${secondInitial ?: ""}"
        else null
    }
}