package com.dennie170.common

fun String.splitInHalf(): List<String>  {
    val mid: Int = this.length / 2
    return listOf(this.substring(0, mid), this.substring(mid))
}
