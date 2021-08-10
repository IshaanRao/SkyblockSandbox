package com.prince.skyblocksandbox.skyblockinventories

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.awt.Point

object ItemsInventory : SkyblockInventory() {
    override val holder = SkyblockHolder("itemsInv")
    override fun getInventory(): Inventory {
        val inv = Bukkit.createInventory(holder,27,"§aItems")
        val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
        val greenGlassMeta = greenGlass.itemMeta
        greenGlassMeta.displayName = " "
        greenGlass.itemMeta = greenGlassMeta
        fillBorders(inv,greenGlass)
        setItem(Point(2,2),inv,greenGlass)
        setItem(Point(3,2),inv,greenGlass)
        setItem(Point(7,2),inv,greenGlass)
        setItem(Point(8,2),inv,greenGlass)

        //Sword ItemStack
        val sword = ItemStack(Material.DIAMOND_SWORD,1)
        val swordMeta = sword.itemMeta
        swordMeta.displayName = "§dSwords"
        swordMeta.lore = listOf(" ","§eClick here to browse swords")
        swordMeta.addEnchant(Enchantment.DURABILITY,1,true)
        swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        sword.itemMeta = swordMeta

        //Bow ItemStack
        val bow = ItemStack(Material.BOW,1)
        val bowMeta = bow.itemMeta
        bowMeta.displayName = "§dBows"
        bowMeta.lore = listOf(" ","§eClick here to browse bows")
        bowMeta.addEnchant(Enchantment.DURABILITY,1,true)
        bowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        bow.itemMeta = bowMeta

        //Armor ItemStack
        val armor = ItemStack(Material.DIAMOND_CHESTPLATE,1)
        val armorMeta = armor.itemMeta
        armorMeta.displayName = "§dArmors"
        armorMeta.lore = listOf(" ","§eClick here to browse armors")
        armorMeta.addEnchant(Enchantment.DURABILITY,1,true)
        armorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        armor.itemMeta = armorMeta

        setItem(Point(4,2),inv,sword)
        setItem(Point(5,2),inv,bow)
        setItem(Point(6,2),inv,armor)

        return inv
    }

    override fun onClick(e: InventoryClickEvent) {
        val player: Player = e.whoClicked as Player
        val topInv = player.openInventory.topInventory
        val slot = e.slot
        if(!isInventory(topInv)) return
        e.isCancelled = true
        if(e.clickedInventory==topInv){
            if(slot==12){
                player.openInventory(SwordsInventory.getInventory(player))
                return
            }else if(slot==13){
                player.openInventory(BowsInventory.getInventory(player))
                return
            }
        }
    }
}