package com.mcstarrysky.starrysky.command

import com.mcstarrysky.starrysky.i18n.asLangTextString
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.SimpleCommandBody

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.command.CommandExecutor
 *
 * @author mical
 * @since 2023/8/16 3:48 PM
 */
interface CommandExecutor {

    val command: SimpleCommandBody

    val name: String

    fun desc(sender: ProxyCommandSender): String {
        return sender.asLangTextString("command.subCommands.$name.description")
    }

    fun usage(sender: ProxyCommandSender): String {
        return sender.asLangTextString("command.subCommands.$name.usage")
    }
}