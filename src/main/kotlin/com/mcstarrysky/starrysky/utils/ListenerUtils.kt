package com.mcstarrysky.starrysky.utils

import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.ProxyListener
import taboolib.common.platform.function.registerBukkitListener
import java.io.Closeable

/**
 * @author xiaomu
 * @since 2022/8/17 10:35
 */

/**
 * 函数式监听事件, 自行决定监听何时停止
 * 使用方法: subscribe<PlayerJoinEvent> { it.player.sendMessage("我是脑瘫") }
 */
inline fun <reified T> subscribe(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline event: (T, Closeable) -> Unit
): ProxyListener {
    return registerBukkitListener(T::class.java, priority, ignoreCancelled) {
        event.invoke(it, this)
    }
}

/**
 * 函数式监听事件, 只监听一次
 * 使用方法: subscribeOnce<PlayerJoinEvent> { it.player.sendMessage("我是脑瘫") }
 */
inline fun <reified T> subscribeOnce(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline event: (T) -> Unit
): ProxyListener {
    return registerBukkitListener(T::class.java, priority, ignoreCancelled) {
        event.invoke(it)
        close()
    }
}

/**
 * 函数式监听事件, 持续监听
 * 使用方法: subscribeAlways<PlayerJoinEvent> { it.player.sendMessage("我是脑瘫") }
 */
inline fun <reified T> subscribeAlways(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline event: (T) -> Unit
): ProxyListener {
    return registerBukkitListener(T::class.java, priority, ignoreCancelled) {
        event.invoke(it)
    }
}