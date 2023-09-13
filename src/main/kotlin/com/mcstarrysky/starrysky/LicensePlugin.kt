package com.mcstarrysky.starrysky

import com.mcstarrysky.starrysky.i18n.I18n
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformFactory
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.severe
import taboolib.module.configuration.Configuration
import taboolib.module.metrics.Metrics
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer
import kotlin.system.measureTimeMillis

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.LicensePlugin
 *
 * 专为验证库使用的插件加载器, 功能可能会有一些缺失
 * 使用方法与 AbstractPlugin 大体相同, 但不太一样
 * 使用这玩意你必须为本模块进行重定向, 否则多插件使用会出问题
 *
 * @author 米擦亮
 * @since 2023/8/30 9:27 PM
 */
interface LicensePlugin {

    fun enable() {
        measureTimeMillis {
            preload()
            if (loadI18n) I18n.initialize()
            load()

            runCatching {
                // Module-StarrySky
                Metrics(19573, StarrySky.VERSION, Platform.BUKKIT)

                // Own Plugin
                for ((serviceId, callback) in pluginIds) {
                    val pluginMetrics = Metrics(serviceId, pluginVersion, Platform.BUKKIT)
                    callback?.accept(pluginMetrics)
                }

                StarrySky.log("已启用 bStats 数据统计.")
                StarrySky.log("若您需要禁用此功能, 一般情况下可于配置文件{file}中编辑或新增 \"bStats: false\" 关闭此功能.", "file" to  if (config.file != null) " ${config.file!!.name} " else "")
            }.onFailure {
                if (it is UninitializedPropertyAccessException) {
                    StarrySky.log("bStats 数据统计已被禁用.")
                    return
                }
                if (I18n.loaded) {
                    I18n.error(I18n.LOAD, "bStats 数据统计", it, null)
                } else {
                    severe("§7加载 §cbStats 数据统计 §7时遇到错误 (§c${it}§7).")
                    I18n.printStackTrace(it)
                }
            }
        }.let { time ->
            StarrySky.log(timeLog, "time" to time)
        }
        afterLoad()
    }

    fun preload()
    fun load()
    fun afterLoad()

    companion object {

        var timeLog = "插件加载完成, 共耗时&a{time}ms&r."
        var loadI18n: Boolean = true
        lateinit var config: Configuration

        private val pluginIds = ConcurrentHashMap<Int, Consumer<Metrics>?>()

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
}