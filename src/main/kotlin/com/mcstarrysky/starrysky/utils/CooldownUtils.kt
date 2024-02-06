package com.mcstarrysky.starrysky.utils

import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.utils.CooldownUtils
 *
 * @author mical
 * @since 2023/8/16 11:54 PM
 */
fun PersistentDataHolder.getCooldown(name: String): Long {
    return this[name, PersistentDataType.LONG] ?: 0L
}

fun PersistentDataHolder.inCooldown(name: String): Pair<Boolean, Long> {
    val cd = System.currentTimeMillis() - getCooldown(name)
    return if (cd >= 0L) true to 0L else false to cd
}

fun PersistentDataHolder.setCooldown(name: String, duration: Long) {
    this[name, PersistentDataType.LONG] = System.currentTimeMillis() + duration
}

fun PersistentDataHolder.clearCooldown(name: String) {
    return remove(name)
}