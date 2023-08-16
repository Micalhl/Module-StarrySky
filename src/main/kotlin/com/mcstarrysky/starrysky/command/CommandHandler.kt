package com.mcstarrysky.starrysky.command

import com.mcstarrysky.starrysky.i18n.asLangTextString
import com.mcstarrysky.starrysky.i18n.sendLang
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.component.CommandBase
import taboolib.common.platform.command.component.CommandComponent
import taboolib.common.platform.command.component.CommandComponentLiteral
import taboolib.common.platform.function.pluginVersion
import taboolib.common.util.Strings
import taboolib.module.chat.component
import taboolib.module.nms.MinecraftVersion
import java.util.concurrent.ConcurrentHashMap

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.command.CommandHandler
 *
 * @author mical
 * @since 2023/8/16 3:55 PM
 */
interface CommandHandler {

    val sub: ConcurrentHashMap<String, CommandExecutor>

    fun CommandComponent.createTabooLibLegacyHelper() {
        execute<ProxyCommandSender> { sender, _, _ ->
            val text = mutableListOf<String>()

            for (command in children.filterIsInstance<CommandComponentLiteral>()) {
                if (!sender.isOp) {
                    if (!sender.hasPermission(command.permission)) {
                        continue
                    } else {
                        if (command.hidden) continue
                    }
                }
                val name = command.aliases[0]
                var usage = sub[name]?.usage(sender) ?: sender.asLangTextString("command.no-usage")
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.desc(sender) ?: sender.asLangTextString("command.no-description")

                text += sender.asLangTextString("command.sub", "name" to name, "usage" to usage, "description" to description)
            }

            sender.asLangTextString("command.helper", "pluginVersion" to pluginVersion, "minecraftVersion" to MinecraftVersion.minecraftVersion).replace("{subCommands}", text.joinToString(separator = "[](br)", prefix = "", postfix = ""))
                .component()
                .build { colored() }
                .sendTo(sender)
        }

        if (this is CommandBase) {
            incorrectCommand { sender, ctx, _, state ->

                val input = ctx.args().first()
                val name = children.filterIsInstance<CommandComponentLiteral>().firstOrNull { it.aliases.contains(input) }?.aliases?.get(0) ?: input
                var usage = sub[name]?.usage(sender) ?: sender.asLangTextString("command.no-usage")
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.desc(sender) ?: sender.asLangTextString("command.no-description")
                when (state) {
                    1 -> {
                        sender.sendLang("command.argument-missing", "name" to name, "usage" to usage, "description" to description)
                    }

                    2 -> {
                        if (ctx.args().size > 1) {
                            sender.sendLang("command.argument-wrong", "name" to name, "usage" to usage, "description" to description)
                        } else {
                            val similar = sub.keys
                                .asSequence()
                                .map { children.filterIsInstance<CommandComponentLiteral>().firstOrNull { c -> c.aliases[0] == it } }
                                .filterNotNull()
                                .filterNot { it.hidden }
                                .filter { sender.hasPermission(it.permission) }
                                .maxByOrNull { Strings.similarDegree(name, it.aliases[0]) }!!
                                .aliases[0]
                            sender.sendLang("command.argument-unknown", "name" to name, "similar" to similar)
                        }
                    }
                }
            }
            incorrectSender { sender, ctx ->
                sender.sendLang("command.incorrect-sender", "name" to ctx.args().first())
            }
        }
    }
}