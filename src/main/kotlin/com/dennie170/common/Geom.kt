package com.dennie170.common

import java.awt.geom.Line2D
import java.awt.geom.Point2D


fun getIntersectionPoint(a: Line2D, b: Line2D): Point2D? {
    val x1 = a.x1
    val y1 = a.y1
    val x2 = a.x2
    val y2 = a.y2
    val x3 = b.x1
    val y3 = b.y1
    val x4 = b.x2
    val y4 = b.y2
    val d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)

    if (d == 0.0) {
        return null
    }

    val xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d
    val yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d

    return Point2D.Double(xi, yi)
}

data class Vector3d<T : Number>(val x: T, val y: T, val z: T)
