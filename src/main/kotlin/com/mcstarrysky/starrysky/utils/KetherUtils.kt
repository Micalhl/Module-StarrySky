package com.mcstarrysky.starrysky.utils

import org.bukkit.entity.Player
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import java.util.concurrent.CompletableFuture

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.utils.KetherUtils
 *
 * @author mical
 * @since 2023/8/16 4:05 PM
 */
fun runKether(player: Player, scripts: List<String>): CompletableFuture<Any?> {
    return KetherShell.eval(scripts, ScriptOptions.new {
        sender(player)
        detailError(true)
    })
}