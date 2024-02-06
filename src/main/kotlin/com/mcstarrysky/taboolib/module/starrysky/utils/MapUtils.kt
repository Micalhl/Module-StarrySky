package com.mcstarrysky.taboolib.module.starrysky.utils

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.utils.MapUtils
 *
 * @author mical
 * @since 2023/8/16 4:12 PM
 */

/**
 * 判断 HashMap 中是否包含集合的某一元素
 */
inline fun <reified T, R> Map<T, R>.containsKey(elements: List<T>): Boolean {
    return elements.any { containsKey(it) }
}

/**
 * 获取 HashMap 所有键中所有与集合中元素重复的元素
 */
inline fun <reified T, R> Map<T, R>.getContains(elements: List<T>): List<T> {
    return elements.filter { containsKey(it) }
}

/**
 * 获取 HashMap 中某个 Value 对应的所有 Key
 */
inline fun <reified T, R> Map<T, R>.getKeys(value: Any): List<T> {
    return entries.filter { it.value == value }.map { it.key }
}