package com.mcstarrysky.starrysky.i18n

import com.mcstarrysky.starrysky.utils.YamlUpdater
import com.mcstarrysky.starrysky.utils.replace
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.serverct.parrot.parrotx.function.VariableFunction
import org.serverct.parrot.parrotx.function.VariableReaders
import org.serverct.parrot.parrotx.function.variables
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.console
import taboolib.common.util.VariableReader
import taboolib.common.util.replaceWithOrder
import taboolib.common.util.unsafeLazy
import taboolib.module.chat.ComponentText
import taboolib.module.chat.component
import taboolib.module.configuration.Configuration

class I18nConfig(private val locale: String) {

    private val config: Configuration by unsafeLazy {
        YamlUpdater.loadAndUpdate("locales/$locale.yml")
    }

    fun log(node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            cacheWithPrefix(node, *args).sendTo(console())
        } else {
            cache(node, *args).sendTo(console())
        }
    }

    fun logRaw(msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            buildWithPrefix(msg, *args).sendTo(console())
        } else {
            build(msg, *args).sendTo(console())
        }
    }

    fun send(player: Player, node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            cacheWithPrefix(node, *args, player = player).sendTo(adaptPlayer(player))
        } else {
            cache(node, *args, player = player).sendTo(adaptPlayer(player))
        }
    }

    fun sendRaw(player: Player, msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            buildWithPrefix(msg, *args, player = player).sendTo(adaptPlayer(player))
        } else {
            build(msg, *args, player = player).sendTo(adaptPlayer(player))
        }
    }

    fun send(sender: ProxyCommandSender, node: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            cacheWithPrefix(node, *args, player = sender.castSafely<CommandSender>() as? Player).sendTo(sender)
        } else {
            cache(node, *args, player = sender.castSafely<CommandSender>() as? Player).sendTo(sender)
        }
    }

    fun sendRaw(sender: ProxyCommandSender, msg: String, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (prefix) {
            buildWithPrefix(msg, *args, player = sender.castSafely<CommandSender>() as? Player).sendTo(sender)
        } else {
            build(msg, *args, player = sender.castSafely<CommandSender>() as? Player).sendTo(sender)
        }
    }

    /**
     * 构建行内复合文本
     */
    fun build(msg: String, vararg args: Pair<String, Any>, player: Player? = null): ComponentText {
        return msg
            .let { if (player != null) it.replaceWithOrder(player) else it }
            .replace(*args)
            .component().build { colored() }
    }

    /**
     * 构建行内复合文本（包含标题）
     */
    fun buildWithPrefix(msg: String, vararg args: Pair<String, Any>, player: Player? = null): ComponentText {
        return msg
            .let { if (player != null) it.replaceWithOrder(player) else it }
            .split("[](br)")
            .joinToString("[](br)", prefix = "", postfix = "") { getNode("prefix") + it }
            .replace(*args)
            .component().build { colored() }
    }

    /**
     * 通过给定节点获取行内复合文本替换变量并构建
     */
    fun variables(node: String, vararg args: Pair<String, Any>, reader: VariableReader = VariableReaders.BRACES, variables: VariableFunction): ComponentText {
        return getNode(node)
            .split("[](br)")
            .variables(reader, variables)
            .joinToString("[](br)", prefix = "", postfix = "") { it }
            .replace(*args)
            .component().build { colored() }
    }

    /**
     * 通过给定节点获取行内复合文本替换变量并构建（包含标题）
     */
    fun variablesWithPrefix(node: String, vararg args: Pair<String, Any>, reader: VariableReader = VariableReaders.BRACES, variables: VariableFunction): ComponentText {
        return getNode(node)
            .split("[](br)")
            .variables(reader, variables)
            .joinToString("[](br)", prefix = "", postfix = "") { getNode("prefix") + it }
            .replace(*args)
            .component().build { colored() }
    }

    /**
     * 通过给定节点获取行内复合文本并构建
     */
    fun cache(node: String, vararg args: Pair<String, Any>, player: Player? = null): ComponentText {
        return build(getNode(node), *args, player = player)
    }

    /**
     * 通过给定节点获取行内复合文本并构建（包含标题）
     */
    fun cacheWithPrefix(node: String, vararg args: Pair<String, Any>, player: Player? = null): ComponentText {
        return buildWithPrefix(getNode(node), *args, player = player)
    }

    /**
     * 直接通过节点获取原始形式
     */
    fun origin(node: String, vararg args: Pair<String, Any>, player: Player? = null): String {
        return getNode(node).let { if (player != null) it.replaceWithOrder(player) else it }.replace(*args)
    }

    /**
     * 通过给定节点获取原始行内复合文本内容
     * 可自动判断List与String
     */
    private fun getNode(node: String): String {
        return if (config.isList(node)) {
            config.getStringList(node).joinToString("[](br)", prefix = "", postfix = "")
        } else config.getString(node) ?: "{$node}"
    }

    /**
     * 重载
     */
    fun reload() {
        config.reload()
    }
}