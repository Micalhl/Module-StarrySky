package com.mcstarrysky.starrysky.command

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.command.BuiltInMessages
 *
 * @author 米擦亮
 * @since 2023/9/10 17:54
 */
object BuiltInMessages {

    /** 命令无用法 */
    const val COMMAND_NO_USAGE: String = ""

    /** 命令无描述 */
    const val COMMAND_NO_DESCRIPTION: String = "没有描述"

    /** 子命令 */
    const val COMMAND_SUB: String =
        "    &8- [&f{name}](h=/{cmd} {name} {usage}&8- &7{description};suggest=/{cmd} {name})[](br)" +
                "      &7{description}"

    /** 命令帮助 */
    const val COMMAND_HELPER: String = " [](br)" +
            "  [&f&l{pluginId}](h=&7{description}) &f[{pluginVersion}](h=&7插件版本: &2{pluginVersion}\\n&7游戏版本: &b{minecraftVersion})[](br)" +
            " [](br)" +
            "  &7命令: [&f/{cmd} &8\\[...\\]](h=&f/{cmd} &8\\[...\\];suggest=/{cmd} )[](br)" +
            "  &7参数:[](br)" +
            "{subCommands}[](br)" +
            " "

    /** 参数不足 */
    const val COMMAND_ARGUMENT_MISSING: String = "&8\\[&f&l{pluginId}&8\\] &7指令 &f{name} &7参数不足.[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &7正确用法:[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &f/{cmd} {name} {usage}&8- &7{description}"

    /** 参数有误 */
    const val COMMAND_ARGUMENT_WRONG: String = "&8\\[&f&l{pluginId}&8\\] &7指令 &f{name} &7参数有误.[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &7正确用法:[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &f/{cmd} {name} {usage}&8- &7{description}"

    /** 不存在 */
    const val COMMAND_ARGUMENT_UNKNOWN: String = "&8\\[&f&l{pluginId}&8\\] &7指令 &f{name} &7不存在.[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &7你可能想要:[](br)" +
            "&8\\[&f&l{pluginId}&8\\] &f{similar}"

    /** 只能由玩家执行 */
    const val COMMAND_INCORRECT_SENDER: String = "&8\\[&f&l{pluginId}&8\\] &7指令 &f{name} &7只能由 &f玩家 &7执行"
}