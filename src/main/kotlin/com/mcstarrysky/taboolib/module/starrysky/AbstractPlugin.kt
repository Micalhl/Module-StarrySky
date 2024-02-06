package com.mcstarrysky.taboolib.module.starrysky

import com.mcstarrysky.taboolib.module.starrysky.i18n.I18n
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformFactory
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.severe
import taboolib.module.configuration.Configuration
import taboolib.module.metrics.Metrics
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer
import kotlin.system.measureTimeMillis

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.AbstractPlugin
 *
 * @author mical
 * @since 2023/8/19 11:23 PM
 */
@Deprecated("Please use StarrySky#setup instead")
abstract class AbstractPlugin : Plugin() {

    open var timeLog = "插件加载完成, 共耗时&a{time}ms&r."
    open var loadI18n: Boolean = true
    lateinit var config: Configuration

    private val pluginIds = ConcurrentHashMap<Int, Consumer<Metrics>?>()

    override fun onEnable() {
        measureTimeMillis {
            preload()
            if (loadI18n) I18n.initialize()
            load()

            if (!::config.isInitialized) return
            if (config.getBoolean("bStats", true)) {
                runCatching {
                    // Module-StarrySky
                    Metrics(19573, StarrySky.VERSION, Platform.BUKKIT)

                    // Own Plugin
                    for ((serviceId, callback) in pluginIds) {
                        val pluginMetrics = Metrics(serviceId, pluginVersion, Platform.BUKKIT)
                        callback?.accept(pluginMetrics)
                    }

                    StarrySky.log("已启用 bStats 数据统计.")
                    StarrySky.log(
                        "若您需要禁用此功能, 一般情况下可于配置文件{file}中编辑或新增 \"bStats: false\" 关闭此功能.",
                        "file" to if (config.file != null) " ${config.file!!.name} " else ""
                    )
                }.onFailure {
                    if (I18n.loaded) {
                        I18n.error(I18n.LOAD, "bStats 数据统计", it, null)
                    } else {
                        severe("§7加载 §cbStats 数据统计 §7时遇到错误 (§c${it}§7).")
                        I18n.printStackTrace(it)
                    }
                }
            } else {
                StarrySky.log("bStats 数据统计已被禁用.")
            }
        }.let { time ->
            StarrySky.log(timeLog, "time" to time)
        }
        afterLoad()
    }

    open fun preload() {
    }

    open fun load() {
    }

    open fun afterLoad() {
    }

    fun registerBStats(serviceId: Int, callback: Consumer<Metrics>? = null) {
        pluginIds += serviceId to callback
    }

    inline fun <reified T> getAPI(): T {
        return PlatformFactory.getAPI()
    }

    inline fun <reified T : Any> registerAPI(instance: T) {
        PlatformFactory.registerAPI(instance)
    }
}