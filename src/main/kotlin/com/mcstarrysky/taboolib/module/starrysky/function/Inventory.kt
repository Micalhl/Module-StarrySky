package com.mcstarrysky.taboolib.module.starrysky.function

import org.bukkit.inventory.Inventory
import taboolib.platform.util.deserializeToInventory
import taboolib.platform.util.serializeToByteArray
import java.util.*

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.function.Inventory
 *
 * @author mical
 * @since 2023/8/16 4:11 PM
 */
fun Inventory.serializeToBase64(): String {
    return Base64.getEncoder().encodeToString(serializeToByteArray())
}

fun String.deserializeInventoryFromBase64(): Inventory {
    return Base64.getDecoder().decode(this).deserializeToInventory()
}