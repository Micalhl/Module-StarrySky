package com.mcstarrysky.taboolib.module.starrysky.utils

import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.console
import taboolib.common5.cbool
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
fun runKether(scripts: List<String>, player: ProxyCommandSender = console()): CompletableFuture<Any?> {
    return KetherShell.eval(scripts, ScriptOptions.new {
        sender(player)
        detailError(true)
    })
}

fun runKether(scripts: List<String>, player: ProxyPlayer): CompletableFuture<Any?> = runKether(scripts, player)

fun runKether(scripts: List<String>, player: Player): CompletableFuture<Any?> = runKether(scripts, adaptPlayer(player))

fun checkKether(scripts: List<String>, player: ProxyCommandSender = console()): CompletableFuture<Boolean> {
    return KetherShell.eval(scripts, ScriptOptions.new {
        sender(player)
        detailError(true)
    }).thenApply { it.cbool }
}

fun checkKether(scripts: List<String>, player: ProxyPlayer): CompletableFuture<Boolean> = checkKether(scripts, player)

fun checkKether(scripts: List<String>, player: Player): CompletableFuture<Boolean> = checkKether(scripts, adaptPlayer(player))