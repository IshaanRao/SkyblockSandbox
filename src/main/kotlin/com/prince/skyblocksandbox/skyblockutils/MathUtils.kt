package com.prince.skyblocksandbox.skyblockutils

import org.bukkit.Location
import kotlin.math.abs

object MathUtils {
    fun parabola(loc1:Location, loc2:Location,points:Int):List<Location>{
        val locList = ArrayList<Location>()
        val distance = loc1.distance(loc2)
        for(i in 1 until points){
            val ratio = i.toDouble()/points
            val location = loc1.lerp(loc2, ratio)
            val distanceToMid = abs(ratio-0.5)
            val additionalHeight = ((-(4.0*(distance/3.0)))*(distanceToMid*distanceToMid))+(distance/3.0)
            location.add(0.0, additionalHeight,0.0)
            locList.add(location)
        }
        return locList
    }
    fun lerp(point1:Double,point2:Double,alpha:Double):Double{
        return point1 + alpha * (point2 - point1)
    }
    fun Location.lerp(loc2: Location, alpha: Double): Location {
        val xLerp = lerp(x,loc2.x,alpha)
        val yLerp = lerp(y,loc2.y,alpha)
        val zLerp = lerp(z,loc2.z,alpha)
        return Location(world,xLerp,yLerp,zLerp)
    }
}