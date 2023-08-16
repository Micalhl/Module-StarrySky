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