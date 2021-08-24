package com.prince.skyblocksandbox.skyblockinventories

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockitems.SkyblockBow
import com.prince.skyblocksandbox.skyblockitems.SkyblockSword
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemStackData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.awt.Point

object BowsInventory : SkyblockInventory() {
    override val holder = SkyblockHolder("bowsInv")
    val bows = ArrayList<SkyblockBow>()
    val pages = ArrayList<ArrayList<SkyblockBow>>()
    init {
        loadBows()
        var page = 0
        pages.add(ArrayList())
        for(bow in bows){
            val currentPage = pages[page]
            currentPage.add(bow)
            if(currentPage.size==45){
                page++
                pages.add(ArrayList())
            }
        }
    }
    fun loadBows() {
        bows.add(SkyblockBow(
            ItemData(
                name = "Juju Shortbow",
                rarity = SkyblockRarities.EPIC,
                reforgable = true,
                item = ItemStackData(Material.BOW),
                damage = 310.toBigInteger(),
                strength = 40.toBigInteger(),
                critChance = 10,
                critDamage = 110.toBigInteger(),
                abilities = listOf(AbilityTypes.JUJUSHORTBOW)
            )
        ))
        bows.add(SkyblockBow(
            ItemData(
                name = "Terminator",
                rarity = SkyblockRarities.LEGENDARY,
                reforgable = true,
                item = ItemStackData(Material.BOW),
                damage = 310.toBigInteger(),
                strength = 50.toBigInteger(),
                critDamage = 250.toBigInteger(),
                abilities = listOf(AbilityTypes.TERMSHORTBOW,AbilityTypes.SALVATION)
            )
        ))
        bows.add(SkyblockBow(
            ItemData(
                name = "Bonemerang",
                rarity = SkyblockRarities.LEGENDARY,
                reforgable = true,
                item = ItemStackData(Material.BONE),
                damage = 270.toBigInteger(),
                strength = 120.toBigInteger(),
                abilities = listOf(AbilityTypes.BONEMERANG)
            )
        ))
    }
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(null,9,"b")
    }
    fun getInventory(p: Player): Inventory {
        return loadInventory(0,p)
    }
    fun loadInventory(pageNum:Int,p: Player): Inventory {
        val inv = Bukkit.createInventory(holder,54,"§aPage $pageNum")
        val bowPage = pages.get(pageNum)
        bowPage.forEachIndexed { index,bow->
            inv.setItem(index,bow.createItem(p))
        }
        for(i in 1..9){
            val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
            val greenGlassMeta = greenGlass.itemMeta
            greenGlassMeta.displayName = " "
            greenGlass.itemMeta = greenGlassMeta
            SwordsInventory.setItem(Point(i, 6), inv, greenGlass)
        }
        if(pageNum != 0){
            val leftArrow = ItemStack(Material.SKULL_ITEM,1)
            leftArrow.durability = 3.toShort()
            val leftArrowMeta = leftArrow.itemMeta as SkullMeta
            leftArrowMeta.owner = "MHF_ArrowLeft"
            leftArrowMeta.displayName = "§aPrev. Page"
            leftArrow.itemMeta = leftArrowMeta
            SwordsInventory.setItem(Point(3, 6), inv, leftArrow)
        }
        if(pageNum+1!=pages.size){
            val rightArrow = ItemStack(Material.SKULL_ITEM,1)
            rightArrow.durability = 3.toShort()
            val rightArrowMeta = rightArrow.itemMeta as SkullMeta
            rightArrowMeta.owner = "MHF_ArrowRight"
            rightArrowMeta.displayName = "§aNext Page"
            rightArrow.itemMeta = rightArrowMeta
            SwordsInventory.setItem(Point(7, 6), inv, rightArrow)
        }
        return  inv
    }

    override fun onClick(e: InventoryClickEvent) {
        val player = e.whoClicked
        val topInv = player.openInventory.topInventory
        val slot = e.slot
        if(!BowsInventory.isInventory(topInv)) return
        e.isCancelled = true
        if(e.clickedInventory==topInv){
            if(slot<=44){
                val item = topInv.getItem(slot)
                if(item==null||item.type== Material.AIR){
                    return
                }
                val sbBow = item.getSkyblockData()
                player.inventory.addItem(sbBow.createItem(player as Player));
                return
            }
        }
    }
}