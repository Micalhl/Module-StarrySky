package com.mcstarrysky.taboolib.module.starrysky.command

import com.mcstarrysky.taboolib.module.starrysky.i18n.I18n
import com.mcstarrysky.taboolib.module.starrysky.i18n.asLangTextString
import com.mcstarrysky.taboolib.module.starrysky.i18n.sendLang
import com.mcstarrysky.taboolib.module.starrysky.utils.replace
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.component.CommandBase
import taboolib.common.platform.command.component.CommandComponent
import taboolib.common.platform.command.component.CommandComponentLiteral
import taboolib.common.platform.function.pluginId
import taboolib.common.platform.function.pluginVersion
import taboolib.common.util.Strings
import taboolib.module.chat.component
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.util.bukkitPlugin
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
        execute<ProxyCommandSender> { sender, ctx, _ ->
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
                var usage = sub[name]?.usage(sender) ?: if (I18n.loaded) sender.asLangTextString("command.no-usage") else BuiltInMessages.COMMAND_NO_USAGE
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.desc(sender) ?: if (I18n.loaded) sender.asLangTextString("command.no-description") else BuiltInMessages.COMMAND_NO_DESCRIPTION

                text +=
                    if (I18n.loaded) sender.asLangTextString("command.sub", "name" to name, "usage" to usage, "description" to description)
                else BuiltInMessages.COMMAND_SUB.replace("cmd" to ctx.command.name, "name" to name, "usage" to usage, "description" to description)
            }

            (if (I18n.loaded)
                sender.asLangTextString("command.helper", "pluginVersion" to pluginVersion, "minecraftVersion" to MinecraftVersion.minecraftVersion).replace("{subCommands}", text.joinToString(separator = "[](br)", prefix = "", postfix = ""))
                    else BuiltInMessages.COMMAND_HELPER.replace("pluginId" to pluginId, "description" to (bukkitPlugin.description.description ?: ""), "pluginVersion" to pluginVersion, "minecraftVersion" to MinecraftVersion.minecraftVersion).replace("{subCommands}", text.joinToString(separator = "[](br)", prefix = "", postfix = "")))
                .component()
                .build { colored() }
                .sendTo(sender)
        }

        if (this is CommandBase) {
            incorrectCommand { sender, ctx, _, state ->

                val input = ctx.args().first()
                val name = children.filterIsInstance<CommandComponentLiteral>().firstOrNull { it.aliases.contains(input) }?.aliases?.get(0) ?: input
                var usage = sub[name]?.usage(sender) ?: if (I18n.loaded) sender.asLangTextString("command.no-usage") else BuiltInMessages.COMMAND_NO_USAGE
                if (usage.isNotEmpty()) {
                    usage += " "
                }
                val description = sub[name]?.desc(sender) ?: if (I18n.loaded) sender.asLangTextString("command.no-description") else BuiltInMessages.COMMAND_NO_DESCRIPTION
                when (state) {
                    1 -> {
                        if (I18n.loaded)
                            sender.sendLang("command.argument-missing", "name" to name, "usage" to usage, "description" to description, prefix = false)
                        else BuiltInMessages.COMMAND_ARGUMENT_MISSING
                            .replace("pluginId" to pluginId, "cmd" to ctx.command.name, "name" to name, "usage" to usage, "description" to description)
                            .component()
                            .build { colored() }
                            .sendTo(sender)
                    }

                    2 -> {
                        if (ctx.args().size > 1) {
                            if (I18n.loaded)
                                sender.sendLang("command.argument-wrong", "name" to name, "usage" to usage, "description" to description, prefix = false)
                            else BuiltInMessages.COMMAND_ARGUMENT_WRONG
                                .replace("pluginId" to pluginId, "cmd" to ctx.command.name, "name" to name, "usage" to usage, "description" to description)
                                .component()
                                .build { colored() }
                                .sendTo(sender)
                        } else {
                            val similar = sub.keys
                                .asSequence()
                                .map { children.filterIsInstance<CommandComponentLiteral>().firstOrNull { c -> c.aliases[0] == it } }
                                .filterNotNull()
                                .filterNot { it.hidden }
                                .filter { sender.hasPermission(it.permission) }
                                .maxByOrNull { Strings.similarDegree(name, it.aliases[0]) }!!
                                .aliases[0]
                            if (I18n.loaded)
                                sender.sendLang("command.argument-unknown", "name" to name, "similar" to similar, prefix = false)
                            else BuiltInMessages.COMMAND_ARGUMENT_UNKNOWN
                                .replace("pluginId" to pluginId, "name" to name, "similar" to similar)
                                .component()
                                .build { colored() }
                                .sendTo(sender)
                        }
                    }
                }
            }
            incorrectSender { sender, ctx ->
                if (I18n.loaded)
                    sender.sendLang("command.incorrect-sender", "name" to ctx.args().first(), prefix = false)
                else BuiltInMessages.COMMAND_INCORRECT_SENDER
                    .replace("pluginId" to pluginId, "name" to ctx.args().first())
                    .component()
                    .build { colored() }
                    .sendTo(sender)
            }
        }
    }
}