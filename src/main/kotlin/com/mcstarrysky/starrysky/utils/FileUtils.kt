package com.mcstarrysky.starrysky.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * module-starrysky
 * com.mcstarrysky.starrysky.utils.FileUtils
 *
 * @author mical
 * @since 2023/8/22 8:05 PM
 */
/**
 * 压缩文件 (忽略 .lock 文件)
 */
fun File.zip(target: File, skipParent: Boolean = false) {
    if (skipParent) {
        if (isDirectory) {
            FileOutputStream(target).use { fileOutputStream -> ZipOutputStream(fileOutputStream).use { listFiles()?.forEach { file -> it.putFile(file, "") } } }
        } else {
            error("is not directory")
        }
    } else {
        FileOutputStream(target).use { fileOutputStream ->
            ZipOutputStream(fileOutputStream).use { it.putFile(this, "") }
        }
    }
}

fun ZipOutputStream.putFile(file: File, path: String) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { putFile(it, path + file.name + "/") }
    } else {
        if (file.extension == "lock") return
        FileInputStream(file).use {
            putNextEntry(ZipEntry(path + file.name))
            write(it.readBytes())
            flush()
            closeEntry()
        }
    }
}