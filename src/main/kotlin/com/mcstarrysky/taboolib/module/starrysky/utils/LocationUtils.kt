package com.mcstarrysky.taboolib.module.starrysky.utils

import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.utils.LocationUtils
 *
 * @author mical
 * @since 2023/8/16 4:04 PM
 */
fun Location.toRoundedLocation(): Location {
    return Location(world, blockX.toDouble(), blockY.toDouble(), blockZ.toDouble())
}

fun Location.parseToString(): String {
    return "${world?.name}~$blockX,$blockY,$blockZ"
}

fun String.parseLocation(): Location {
    val (world, loc) = split("~", limit = 2)
    val (x, y, z) = loc.split(",", limit = 3).map { it.toDouble() }

    return Location(Bukkit.getWorld(world), x, y, z)
}