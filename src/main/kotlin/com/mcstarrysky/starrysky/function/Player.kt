package com.mcstarrysky.starrysky.function

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.function.Player
 *
 * @author mical
 * @since 2023/8/16 4:11 PM
 */
class VirtualPlayer(player: Player) : Player by player {

    override fun isOp(): Boolean = true

    override fun hasPermission(p0: String): Boolean = true

    override fun hasPermission(p0: Permission): Boolean = true
}

fun Player.executeAsOp(vararg commands: Any) {
    val virtual = VirtualPlayer(this)
    commands.forEach {
        Bukkit.dispatchCommand(virtual, it.toString())
    }
}


fun Player.sound(type: Sound, volume: Float = 1f, pitch: Float = 1f) {
    playSound(location, type, volume, pitch)
}

fun Player.takeOneFromHand() {
    val item = inventory.itemInHand
    item.amount -= 1
}
