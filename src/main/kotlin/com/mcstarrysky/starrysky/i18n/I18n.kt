package com.mcstarrysky.starrysky.i18n

import com.mcstarrysky.starrysky.i18n.exception.severe
import org.bukkit.ChatColor
import taboolib.common.io.newFile
import taboolib.common.io.newFolder
import taboolib.common.io.runningResources
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.console
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFile
import taboolib.common.util.unsafeLazy
import taboolib.module.chat.colored
import java.io.File
import java.util.Locale
import kotlin.system.measureTimeMillis

object I18n {

    const val LOAD = "加载"
    const val SAVE = "保存"
    const val REGISTER = "注册"
    const val RELOAD = "重载"
    const val DELETE = "删除"
    const val CALCULATE = "计算"
    const val PROTECT = "保护"
    const val UPGRADE = "升级"
    const val PARSE = "赋值变量"
    const val GET = "获取值"
    const val SET = "设置值"
    const val MSG = "发送消息"
    const val BUILD = "构建"
    const val LOG = "记录"
    const val CONTRIBUTE = "贡献"
    const val EXECUTE = "执行"
    const val INIT = "初始化"
    const val CREATE = "创建"
    const val GENERATE = "生成"
    const val UPLOAD = "上传"
    const val REFRESH = "刷新"
    const val CLEAR = "清空"
    const val INDEX = "构建索引"

    private val localesMap = HashMap<String, I18nConfig>()
    private val folder: File by unsafeLazy {
        newFolder(getDataFolder(), "locales", create = false)
    }

    private val languageCodeTransfer = hashMapOf(
        "zh_hans_cn" to "zh_CN",
        "zh_hant_cn" to "zh_TW",
        "en_ca" to "en_US",
        "en_au" to "en_US",
        "en_gb" to "en_US",
        "en_nz" to "en_US"
    )
    private const val defaultLanguageCode: String = "zh_CN"

    /**
     * 初始化语言系统
     */
    fun initialize() {
        console().sendMessage("|- Loading I18n System version 1.0.4 by &{#FFD0DB}Micalhl§7...".colored())
        measureTimeMillis {
            // 预热
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    console().sendMessage("|- Missing language folder, has been generated automatically")
                } else {
                    console().sendMessage("|- Failed to generate language folder")
                    return
                }
            }
            runningResources
                .filter { it.startsWith("locales/") }
                .filterNot { newFile(getDataFolder(), it, create = false).exists() }.forEach {
                    releaseResourceFile(it, true)
                    console().sendMessage("|- Releasing language file ${it.removePrefix("locales/")}")
            }
            // 加载
            folder.listFiles { file -> file.extension == "yml" }?.forEach { file ->
                localesMap += file.nameWithoutExtension to I18nConfig(file.nameWithoutExtension)
            }
        }.let { time ->
            console().sendMessage("|- Loaded I18n System with ${localesMap.size} language files in §6${time}ms")
        }
    }

    /**
     * 重载
     */
    fun reload() {
        console().sendMessage("|- Reloading I18n System...")
        measureTimeMillis {
            folder.listFiles { file -> file.extension == "yml" }?.forEach { file ->
                val config = localesMap.computeIfAbsent(file.nameWithoutExtension) { I18nConfig(file.nameWithoutExtension) }
                config.reload()
            }
        }.let { time ->
            console().sendMessage("|- Reloaded I18n System in §6${time}ms")
        }
    }

    fun getLocale(sender: ProxyCommandSender): String {
        return if (sender is ProxyPlayer)
            languageCodeTransfer[sender.locale.lowercase()] ?: sender.locale
        else Locale.getDefault().toLanguageTag().replace("-", "_").lowercase()
    }

    fun getLocaleFile(sender: ProxyCommandSender): I18nConfig {
        val locale = getLocale(sender)
        return localesMap.entries.firstOrNull { it.key.equals(locale, true) }?.value
            ?: localesMap[defaultLanguageCode]
            ?: localesMap.values.firstOrNull()
            ?: severe("No available language file")
    }

    fun action(action: String, obj: String) {
        getLocaleFile(console()).logRaw("&7尝试$action &c$obj&7.")
    }

    fun error(action: String, obj: String, exception: String) {
        getLocaleFile(console()).logRaw("&7$action &c$obj &7时遇到错误(&c$exception&7).")
    }

    fun error(action: String, obj: String, exception: Throwable, packageFilter: String? = null) {
        error(action, obj, exception.toString())
        printStackTrace(exception, packageFilter)
    }

    fun printStackTrace(exception: Throwable, packageFilter: String? = null) {
        val msg = exception.localizedMessage
        console().sendMessage("§7===================================§c§l printStackTrace §7===================================")
        console().sendMessage("§7Exception Type ▶")
        console().sendMessage(ChatColor.RED.toString() + exception.javaClass.getName())
        console().sendMessage(ChatColor.RED.toString() + if (msg.isNullOrEmpty()) "§7No description." else msg)

        var lastPackage = ""
        for (elem in exception.stackTrace) {
            val key = elem.className
            var pass = true
            if (packageFilter != null) {
                pass = key.contains(packageFilter)
            }
            val nameSet = key.split("[.]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val className = nameSet[nameSet.size - 1]
            val packageSet = arrayOfNulls<String>(nameSet.size - 2)
            System.arraycopy(nameSet, 0, packageSet, 0, nameSet.size - 2)
            val packageName = StringBuilder()
            for ((counter, nameElem) in packageSet.withIndex()) {
                packageName.append(nameElem)
                if (counter < packageSet.size - 1) {
                    packageName.append(".")
                }
            }
            if (pass) {
                if (packageName.toString() != lastPackage) {
                    lastPackage = packageName.toString()
                    console().sendMessage("")
                    console().sendMessage("§7Package §c$packageName §7▶")
                }
                console().sendMessage("  §7▶ at Class §c$className§7, Method §c${elem.methodName}§7. (§c${elem.fileName}§7, Line §c${elem.lineNumber}§7)")
            }
        }
        console().sendMessage("§7===================================§c§l printStackTrace §7===================================")
    }
}