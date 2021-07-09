package com.prince.skyblocksandbox.skyblockinventories

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.awt.Point

abstract class SkyblockInventory : Listener {
    abstract val holder:SkyblockHolder
    abstract fun getInventory(): Inventory
    fun isInventory(inv:Inventory): Boolean {
        if(inv.holder is SkyblockHolder){
            if((inv.holder as SkyblockHolder).name==holder.name){
                return true
            }
        }
        return false
    }
    abstract fun onClick(e:InventoryClickEvent)
    open fun onClose(e:InventoryCloseEvent){}
    fun fillBorders(inv:Inventory,item:ItemStack){
        val rows = inv.size/9
        for(collumn in 1..9){
            setItem(Point(collumn,1),inv,item)
            setItem(Point(collumn,rows),inv,item)
        }
        for(row in 1..rows){
            setItem(Point(1,row),inv,item)
            setItem(Point(9,row),inv,item)
        }
    }
    fun getItem(point: Point,inv:Inventory):ItemStack? {
        val rows = point.y-1
        val collumns = point.x-1
        val rowAdd = rows*9
        return inv.getItem(rowAdd+collumns)
    }
    fun setItem(point: Point,inv:Inventory,item:ItemStack){
        val rows = point.y-1
        val collumns = point.x-1
        val rowAdd = rows*9
        inv.setItem(rowAdd+collumns,item)
    }
    @EventHandler
    fun onInventoryClick(e:InventoryClickEvent){
        onClick(e)
    }
    @EventHandler
    fun onInventoryClose(e:InventoryCloseEvent) {
        if(isInventory(e.inventory)) {
            onClose(e)
        }
    }
}