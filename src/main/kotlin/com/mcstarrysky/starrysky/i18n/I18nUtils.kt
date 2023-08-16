package com.mcstarrysky.starrysky.i18n

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.adaptCommandSender
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.chat.ComponentText

fun Player.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptPlayer(this)).send(this, node, *args, prefix = prefix)
}

fun Player.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptPlayer(this)).send(this, msg, *args, prefix = prefix)
}

fun Player.asLangText(node: String, vararg args: Pair<String, Any>, prefix: Boolean = false) {
    with(I18n.getLocaleFile(adaptPlayer(this))) {
        if (prefix) {
            cacheWithPrefix(node, *args, player = this@asLangText)
        } else {
            cache(node, *args, player = this@asLangText)
        }
    }
}

fun CommandSender.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptCommandSender(this)).send(adaptCommandSender(this), node, *args, prefix = prefix)
}

fun CommandSender.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(adaptCommandSender(this)).send(adaptCommandSender(this), msg, *args, prefix = prefix)
}

fun ProxyCommandSender.sendLang(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(this).send(this, node, *args, prefix = prefix)
}

fun ProxyCommandSender.sendRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
    I18n.getLocaleFile(this).send(this, msg, *args, prefix = prefix)
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

fun ProxyCommandSender.asLangTextString(node: String, vararg args: Pair<String, Any>): String {
    return I18n.getLocaleFile(this).origin(node, *args, player = this.castSafely<CommandSender>() as? Player)
}
