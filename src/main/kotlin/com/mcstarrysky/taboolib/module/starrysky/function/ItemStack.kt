package com.mcstarrysky.taboolib.module.starrysky.function

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.common.util.unsafeLazy
import taboolib.platform.util.deserializeToItemStack
import taboolib.platform.util.serializeToByteArray
import java.util.*

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.function.ItemStack
 *
 * @author mical
 * @since 2023/8/16 4:03 PM
 */
val emptyItemStack: ItemStack by unsafeLazy {
    ItemStack(Material.AIR, 1)
}

val frameItemStack: ItemStack by unsafeLazy {
    ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
}

fun ItemStack.serializeToBase64(): String {
    return Base64.getEncoder().encodeToString(serializeToByteArray())
}

fun String.deserializeItemStackFromBase64(): ItemStack {
    return Base64.getDecoder().decode(this).deserializeToItemStack()
}