package com.mcstarrysky.taboolib.module.starrysky.utils

import taboolib.module.kether.compileToJexl
import kotlin.math.roundToInt

fun String.replace(holders: List<Pair<String, Any>>): String {
    var tmp = this
    holders.forEach { (holder, value) -> tmp = tmp.replace("{$holder}", "$value") }
    return tmp
}

fun String.replace(holders: Map<String, Any>): String = replace(holders.toList())

fun String.replace(vararg holders: Pair<String, Any>): String = replace(holders.toList())

fun String.calculate(holders: List<Pair<String, Any>>): String = replace(holders).compileToJexl().eval().toString()
fun String.calculate(holders: Map<String, Any>): String = calculate(holders.toList())
fun String.calculate(vararg holders: Pair<String, Any>): String = calculate(holders.toList())
fun String.calcToDouble(vararg holders: Pair<String, Any>): Double = calculate(*holders).toDouble()
fun String.calcToInt(vararg holders: Pair<String, Any>): Int = calcToDouble(*holders).roundToInt()

/**
 * 按照字符串的长度, 通过给定字符生成新的字符串
 *
 * 使用场景：
 * 怪物图鉴 线索：位置：海德利尔森林
 * 未解锁图鉴时： 位置：？？？？？？
 */
fun String.replaceTo(char: String): String {
    val size = length
    val builder = StringBuilder()
    repeat(size) { builder.append(char) }
    return builder.toString()
}