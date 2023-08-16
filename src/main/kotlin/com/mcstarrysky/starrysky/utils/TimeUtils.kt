package com.mcstarrysky.starrysky.utils

import taboolib.common.util.replaceWithOrder

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.utils.TimeUtils
 *
 * @author mical
 * @since 2023/8/16 4:14 PM
 */
val DEFAULT_TIME_KEY = mapOf(
    "年" to 365 * 24 * 60 * 60, "月" to 30 * 24 * 60 * 60, "天" to 24 * 60 * 60, "时" to 60 * 60, "分" to 60, "秒" to 1
)

/**
 * 根据数字(秒)获取描述性时间长度字符串.
 *
 * @return 时间长度
 */
fun Long.duration(): String {
    var time = this
    var cache: Long
    val result = StringBuilder()

    if (time <= 0) {
        return "{0} {1}".replaceWithOrder(time, "秒")
    }

    for (entry in DEFAULT_TIME_KEY.entries) {
        cache = time % entry.value
        val number = (time - cache) / entry.value
        time -= number * entry.value
        if (number != 0L) {
            result.append("{0} {1} ".replaceWithOrder(number, entry.key))
        }
    }
    return result.toString().trim { it <= ' ' }
}

private const val YEAR = 365 * 24 * 60 * 60 // one year's int data

private const val MONTH = 30 * 24 * 60 * 60 // one month's int data

private const val DAY = 24 * 60 * 60 // one day's int data

private const val HOUR = 60 * 60 // one hour int data

private const val MINUTE = 60 // one minute int data


/**
 * 根据毫秒时间戳获取描述性时间，如3分钟前，1天前
 */
fun Long.getDescriptionTime(): String {
    val currentTime = System.currentTimeMillis()
    val timeGap = (currentTime - this) / 1000 // 与现在时间相差秒数
    return if (timeGap > YEAR) {
        (timeGap / YEAR).toString() + "年前"
    } else if (timeGap > MONTH) {
        (timeGap / MONTH).toString() + "个月前"
    } else if (timeGap > DAY) { // 1天以上
        (timeGap / DAY).toString() + "天前"
    } else if (timeGap > HOUR) { // 1小时-24小时
        (timeGap / HOUR).toString() + "小时前"
    } else if (timeGap > MINUTE) { // 1分钟-59分钟
        (timeGap / MINUTE).toString() + "分钟前"
    } else { // 1秒钟-59秒钟
        "刚刚"
    }
}