package com.mcstarrysky.taboolib.module.starrysky.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.component.CommandComponent

/**
 * ExampleProject
 * com.github.username.CommandUtils
 *
 * @author 米擦亮
 * @since 2023/9/10 19:15
 */
fun CommandComponent.executeAsCommandSender(function: (sender: ProxyCommandSender, context: CommandContext<ProxyCommandSender>, argument: String) -> Unit) {
    execute(bind = ProxyCommandSender::class.java, function = function)
}

fun CommandComponent.executeAsPlayer(function: (sender: ProxyPlayer, context: CommandContext<ProxyPlayer>, argument: String) -> Unit) {
    execute(bind = ProxyPlayer::class.java, function = function)
}

fun CommandComponent.executeAsBukkitCommandSender(function: (sender: CommandSender, context: CommandContext<CommandSender>, argument: String) -> Unit) {
    execute(bind = CommandSender::class.java, function = function)
}

fun CommandComponent.executeAsBukkitPlayer(function: (sender: Player, context: CommandContext<Player>, argument: String) -> Unit) {
    execute(bind = Player::class.java, function = function)
}