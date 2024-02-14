package com.mcstarrysky.starrysky

import com.mcstarrysky.starrysky.i18n.I18n
import com.mcstarrysky.starrysky.i18n.asLangTextString
import com.mcstarrysky.starrysky.i18n.sendRaw
import com.mcstarrysky.starrysky.utils.replace
import taboolib.common.platform.Platform
import taboolib.common.platform.function.*
import taboolib.module.chat.colored
import taboolib.module.chat.component
import taboolib.module.configuration.Configuration
import taboolib.module.metrics.Metrics
import java.util.function.Consumer
import kotlin.system.measureTimeMillis

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.StarrySky
 *
 * @author mical
 * @since 2023/8/16 3:47 PM
 */
object StarrySky {

    const val VERSION: String = "2.0.0"
    const val IS_DEVELOPMENT_MODE: Boolean = false

    fun log(message: String?, vararg args: Pair<String, Any>, prefix: Boolean = true) {
        if (message == null) return
        val result = message.split("\n")
        for (msg in result) {
            if (I18n.loaded)
                console().sendRaw(msg, *args, prefix = prefix)
            else msg.replace(*args).component().buildColored().sendTo(console())
        }
    }

    fun setup(config: Configuration? = null,
              loadI18n: Boolean = true,
              timeLog: String? = "{prefix}插件加载完成, 共耗时&a{time}ms&r.",
              bStatsEnabled: String? = "{prefix}已启用 bStats 数据统计.\n若您需要禁用此功能, 一般情况下可于配置文件{file}中编辑或新增 \"bStats: false\" 关闭此功能.",
              bStatsDisabled: String? = "{prefix}bStats 数据统计已被禁用.",
              vararg bStats: Pair<Int, Consumer<Metrics>?>,
              load: (() -> Unit)? = null
    ): Boolean {
        return runCatching {
            measureTimeMillis {
                // 加载 I18n 系统
                if (loadI18n) I18n.initialize()

                // 运行加载代码
                load?.invoke()

                // 加载 bStats
                if (config == null) {
                    log(bStatsDisabled, "prefix" to if (loadI18n) console().asLangTextString("prefix") else "&8\\[&f&l$pluginId&8\\] &f", prefix = false)
                } else {
                    if (config.getBoolean("bStats", true)) {
                        runCatching {
                            // Module-StarrySky
                            Metrics(19573, VERSION, Platform.BUKKIT)

                            // Own Plugin
                            for ((serviceId, callback) in bStats) {
                                val pluginMetrics = Metrics(serviceId, pluginVersion, Platform.BUKKIT)
                                callback?.accept(pluginMetrics)
                            }

                            log(bStatsEnabled, "prefix" to if (loadI18n) console().asLangTextString("prefix") else "&8\\[&f&l$pluginId&8\\] &f", "file" to  if (config.file != null) " ${config.file!!.name} " else "", prefix = false)
                        }.onFailure {
                            if (I18n.loaded) {
                                I18n.error(I18n.LOAD, "bStats 数据统计", it, null)
                            } else {
                                severe("§7加载 §cbStats 数据统计 §7时遇到错误 (§c${it}§7).")
                                I18n.printStackTrace(it)
                            }
                        }
                    }
                }
            }.let { log(timeLog, "prefix" to if (loadI18n) console().asLangTextString("prefix") else "&8\\[&f&l$pluginId&8\\] &f", "time" to it, prefix = false) }
        }.isSuccess
    }
}

