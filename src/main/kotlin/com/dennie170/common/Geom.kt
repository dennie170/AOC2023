package com.dennie170.common

import java.awt.geom.Line2D
import java.awt.geom.Point2D


fun Line2D.getIntersectionPoint(other: Line2D): Point2D? {
    val x1 = x1
    val y1 = y1
    val x2 = x2
    val y2 = y2
    val x3 = other.x1
    val y3 = other.y1
    val x4 = other.x2
    val y4 = other.y2
    val d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)

    if (d == 0.0) {
        return null
    }

    val xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d
    val yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d

    return Point2D.Double(xi, yi)
}


data class Vector3d<out T : Number>(val x: T, val y: T, val z: T)


data class Coordinates(val row: Int, val col: Int)
