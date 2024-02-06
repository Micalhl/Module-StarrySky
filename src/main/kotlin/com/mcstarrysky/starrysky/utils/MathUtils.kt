package com.mcstarrysky.starrysky.utils

import taboolib.common.util.randomDouble
import kotlin.math.abs

val romanUnits = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
val romanSymbols = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")

fun Int.roman(simplified: Boolean = false, blank: Boolean = false): String {
    if ((this == 1 && simplified) || this !in 1..3999) return ""
    var number = this
    val roman = StringBuilder()
    for (i in romanUnits.indices)
        while (number >= romanUnits[i]) {
            roman.append(romanSymbols[i])
            number -= romanUnits[i]
        }
    return if (blank) " $roman" else "$roman"
}

val num = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九")
val unit = arrayOf("", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "万") // 最后一个是万亿，去掉怪异读法

fun Long.chinese(): String {
    var number = this
    var negative = false
    val builder = StringBuilder()
    if (number < 0) {
        number = abs(number)
        negative = true
    }
    val array = number.toString().toCharArray()
    for (i in array.indices) {
        val c = array[i].code - 48
        if (c != 0) {
            builder.append(num[c - 1] + unit[array.size - i - 1])
        }
    }
    var result = builder.toString()
    // 去掉一十三、一十三万这种怪异的读法
    if (result.startsWith("一十")) {
        result = result.removePrefix("一")
    }
    return if (negative) "负$result" else result
}

fun chance(a: Int, b: Int): Boolean = randomDouble() < a * 1.0 / b