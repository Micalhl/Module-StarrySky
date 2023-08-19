package com.mcstarrysky.starrysky.utils

import org.serverct.parrot.parrotx.ui.config.advance.ShapeConfiguration
import org.serverct.parrot.parrotx.ui.config.advance.TemplateConfiguration
import taboolib.module.ui.type.Basic

fun Basic.setSlots(
    shape: ShapeConfiguration,
    templates: TemplateConfiguration,
    key: String,
    elements: List<Any?>,
    vararg args: Pair<String, Any>
) {
    var tot = 0
    shape[key].forEach { slot ->
        val map = args.toMap().mapValues {
            val parts = it.value.toString().split("=")
            when (parts[0]) {
                "expression" -> parts[1].calcToInt("tot" to "$tot")
                "element" -> if (parts.size > 1) elements.getOrNull(parts[1].calcToInt("tot" to "$tot"))
                else elements.getOrNull(tot)
                else -> it.value
            }
        }
        set(slot, templates(key, slot, 0, false, "Fallback") { this += map })
        onClick(slot) { templates[it.rawSlot]?.handle(this, it) { this += map } }
        tot++
    }
}

fun Basic.initialize(
    shape: ShapeConfiguration,
    templates: TemplateConfiguration,
    vararg ignored: String,
    args: MutableMap<String, Any?>.() -> Unit
) {
    onBuild { _, inventory ->
        shape.all(*ignored) { slot, index, item, _ ->
            inventory.setItem(slot, item(slot, index) {
                args.invoke(this)
            })
        }
    }

    onClick {
        it.isCancelled = true
        if (it.rawSlot in shape) {
            templates[it.rawSlot]?.handle(this, it) {
                args.invoke(this)
            }
        }
    }
}