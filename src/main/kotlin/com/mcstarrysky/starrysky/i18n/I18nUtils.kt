package com.mcstarrysky.starrysky.i18n

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.serverct.parrot.parrotx.function.VariableFunction
import org.serverct.parrot.parrotx.function.VariableReaders
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.adaptCommandSender
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.VariableReader
import taboolib.module.chat.ComponentText

/** sendLang */
fun Player.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptPlayer(this)).send(this, node, *args, prefix = prefix)
}

fun CommandSender.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptCommandSender(this)).send(adaptCommandSender(this), node, *args, prefix = prefix)
}

fun ProxyCommandSender.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(this).send(this, node, *args, prefix = prefix)
}

/** sendRaw */
fun Player.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptPlayer(this)).sendRaw(this, msg, *args, prefix = prefix)
}

fun CommandSender.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptCommandSender(this)).sendRaw(adaptCommandSender(this), msg, *args, prefix = prefix)
}

fun ProxyCommandSender.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(this).sendRaw(this, msg, *args, prefix = prefix)
}

/** asLangText */
fun Player.asLangText(node: String, vararg args: Pair<String, Any>, prefix: Boolean = false): ComponentText {
    return with(I18n.getLocaleFile(adaptPlayer(this))) {
        if (prefix) {
            cacheWithPrefix(node, *args, player = this@asLangText)
        } else {
            cache(node, *args, player = this@asLangText)
        }
    }
}

fun CommandSender.asLangText(node: String, vararg args: Pair<String, Any>, prefix: Boolean = false): ComponentText {
    return with(I18n.getLocaleFile(adaptCommandSender(this))) {
        if (prefix) {
            cacheWithPrefix(node, *args)
        } else {
            cache(node, *args)
        }
    }
}

fun ProxyCommandSender.asLangText(node: String, vararg args: Pair<String, Any>, prefix: Boolean = false): ComponentText {
    return with(I18n.getLocaleFile(this)) {
        if (prefix) {
            cacheWithPrefix(node, *args)
        } else {
            cache(node, *args)
        }
    }
}

/** asLangTextString 获取原始行内复合文本 */
fun Player.asLangTextString(node: String, vararg args: Pair<String, Any>): String {
    return I18n.getLocaleFile(adaptPlayer(this)).origin(node, *args, player = this)
}

fun CommandSender.asLangTextString(node: String, vararg args: Pair<String, Any>): String {
    return I18n.getLocaleFile(adaptCommandSender(this)).origin(node, *args)
}

fun ProxyCommandSender.asLangTextString(node: String, vararg args: Pair<String, Any>): String {
    return I18n.getLocaleFile(this).origin(node, *args, player = this.castSafely<CommandSender>() as? Player)
}

/** sendLangVariables 替换 List 变量 */
fun Player.sendLangVariables(node: String, vararg args: Pair<String, Any>, reader: VariableReader = VariableReaders.BRACES, prefix: Boolean = true, variables: VariableFunction) {
    with(I18n.getLocaleFile(adaptPlayer(this))) {
        if (prefix) {
            variablesWithPrefix(node, reader = reader, args = args, variables = variables).sendTo(adaptPlayer(this@sendLangVariables))
        } else {
            variables(node, reader = reader, args = args, variables = variables).sendTo(adaptPlayer(this@sendLangVariables))
        }
    }
}

fun CommandSender.sendLangVariables(node: String, vararg args: Pair<String, Any>, reader: VariableReader = VariableReaders.BRACES, prefix: Boolean = true, variables: VariableFunction) {
    with(I18n.getLocaleFile(adaptCommandSender(this))) {
        if (prefix) {
            variablesWithPrefix(node, reader = reader, args = args, variables = variables).sendTo(adaptCommandSender(this@sendLangVariables))
        } else {
            variables(node, reader = reader, args = args, variables = variables).sendTo(adaptCommandSender(this@sendLangVariables))
        }
    }
}

fun ProxyCommandSender.sendLangVariables(node: String, vararg args: Pair<String, Any>, reader: VariableReader = VariableReaders.BRACES, prefix: Boolean = true, variables: VariableFunction) {
    with(I18n.getLocaleFile(this)) {
        if (prefix) {
            variablesWithPrefix(node, reader = reader, args = args, variables = variables).sendTo(this@sendLangVariables)
        } else {
            variables(node, reader = reader, args = args, variables = variables).sendTo(this@sendLangVariables)
        }
    }
}