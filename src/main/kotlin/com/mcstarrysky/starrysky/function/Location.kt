package com.mcstarrysky.starrysky.function

import org.bukkit.Bukkit
import org.bukkit.Location
import taboolib.common.util.unsafeLazy

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.function.Location
 *
 * @author mical
 * @since 2023/8/22 8:05 PM
 */
val emptyLocation: Location by unsafeLazy {
    Location(Bukkit.getWorlds()[0], 0.0, 0.0, 0.0)
}