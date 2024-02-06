package com.mcstarrysky.starrysky.function

import java.util.*

/**
 * @author Polar-Pumpkin
 * @from ParrotX
 */
enum class Position {

    x1, x2, x3, x4, x5, x6, x7, x8, x9, y1, y2, y3, y4, y5, y6;

    companion object {
        private fun resolutionLocation(posStr: String): List<String> {
            val list: MutableList<String> = ArrayList()
            if (posStr.contains(",")) {
                for (interval in posStr.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                    list.addAll(calculateInterval(interval))
                }
            } else if (posStr.contains("-")) {
                list.addAll(calculateInterval(posStr))
            } else {
                list.add(posStr)
            }
            return list
        }

        private fun calculateInterval(interval: String): List<String> {
            val list: MutableList<String> = ArrayList()
            if (interval.contains("-")) {
                val set = interval.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val start = set[0].toInt()
                val end = set[1].toInt()
                for (i in start..end) {
                    list.add(i.toString())
                }
            } else {
                list.add(interval)
            }
            return list
        }

        operator fun get(x: String, y: String): List<Int> {
            val list: MutableList<Int> = ArrayList()
            for (ySlot in resolutionLocation(y)) {
                for (xSlot in resolutionLocation(x)) {
                    list.add(Companion[xSlot.toInt(), ySlot.toInt()])
                }
            }
            return list
        }

        operator fun get(xSlot: Int, ySlot: Int): Int {
            val x = valueOf("x$xSlot")
            val y = valueOf("y$ySlot")
            return if (Objects.isNull(x) || Objects.isNull(y)) {
                0
            } else when (y) {
                y1 -> when (x) {
                    x1 -> 0
                    x2 -> 1
                    x3 -> 2
                    x4 -> 3
                    x5 -> 4
                    x6 -> 5
                    x7 -> 6
                    x8 -> 7
                    x9 -> 8
                    else -> 0
                }

                y2 -> when (x) {
                    x1 -> 9
                    x2 -> 10
                    x3 -> 11
                    x4 -> 12
                    x5 -> 13
                    x6 -> 14
                    x7 -> 15
                    x8 -> 16
                    x9 -> 17
                    else -> 0
                }

                y3 -> when (x) {
                    x1 -> 18
                    x2 -> 19
                    x3 -> 20
                    x4 -> 21
                    x5 -> 22
                    x6 -> 23
                    x7 -> 24
                    x8 -> 25
                    x9 -> 26
                    else -> 0
                }

                y4 -> when (x) {
                    x1 -> 27
                    x2 -> 28
                    x3 -> 29
                    x4 -> 30
                    x5 -> 31
                    x6 -> 32
                    x7 -> 33
                    x8 -> 34
                    x9 -> 35
                    else -> 0
                }

                y5 -> when (x) {
                    x1 -> 36
                    x2 -> 37
                    x3 -> 38
                    x4 -> 39
                    x5 -> 40
                    x6 -> 41
                    x7 -> 42
                    x8 -> 43
                    x9 -> 44
                    else -> 0
                }

                y6 -> when (x) {
                    x1 -> 45
                    x2 -> 46
                    x3 -> 47
                    x4 -> 48
                    x5 -> 49
                    x6 -> 50
                    x7 -> 51
                    x8 -> 52
                    x9 -> 53
                    else -> 0
                }

                else -> 0
            }
        }
    }
}