package com.mcstarrysky.starrysky

import com.mcstarrysky.starrysky.i18n.I18n
import com.mcstarrysky.starrysky.i18n.sendRaw
import com.mcstarrysky.starrysky.utils.replace
import taboolib.common.platform.function.console
import taboolib.common.platform.function.info

/**
 * Module-Starrysky
 * com.mcstarrysky.starrysky.StarrySky
 *
 * @author mical
 * @since 2023/8/16 3:47 PM
 */
object StarrySky {

    const val VERSION: String = "1.0.12"
    const val IS_DEVELOPMENT_MODE: Boolean = false

    fun log(msg: String, vararg args: Pair<String, Any>) {
        if (I18n.loaded)
            console().sendRaw(msg, *args)
        else info(msg.replace(*args))
    }
}