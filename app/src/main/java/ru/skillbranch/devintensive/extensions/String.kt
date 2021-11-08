package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16) : String {
    var result = this.trim()
    if (result.length > length) result = result.substring(0, length).trimEnd() + "..."
    return result
}