package com.prince.skyblocksandbox.skyblockinventories

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
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

object SwordsInventory : SkyblockInventory() {
    override val holder = SkyblockHolder("swordsInv")
    val swords = ArrayList<SkyblockSword>()
    val pages = ArrayList<ArrayList<SkyblockSword>>()
    init {
        loadSwords()
        var page = 0
        pages.add(ArrayList())
        for(sword in swords){
            val currentPage = pages[page]
            currentPage.add(sword)
            if(currentPage.size==45){
                page++
                pages.add(ArrayList())
            }
        }
    }
    fun loadSwords() {
        swords.add(SkyblockSword(ItemData(
            name = "Rogue Sword",
            rarity = SkyblockRarities.COMMON,
            reforgable = true,
            item = ItemStackData(Material.GOLD_SWORD),
            damage = 20.toBigInteger(),
            abilities = listOf(AbilityTypes.SPEEDBOOST)
        )))

        swords.add(SkyblockSword(ItemData(
            name = "Aspect of the End",
            rarity = SkyblockRarities.RARE,
            reforgable = true,
            item = ItemStackData(Material.DIAMOND_SWORD),
            damage = 100.toBigInteger(),
            strength = 100.toBigInteger(),
            abilities = listOf(AbilityTypes.AOTE)
        )))
        swords.add(SkyblockSword(ItemData(
            name = "Hyperion",
            rarity = SkyblockRarities.LEGENDARY,
            reforgable = true,
            item = ItemStackData(Material.IRON_SWORD),
            damage = 260.toBigInteger(),
            strength = 150.toBigInteger(),
            intelligence = 350.toBigInteger(),
            abilities = listOf(AbilityTypes.WITHER_IMPACT)
        )))
        swords.add(SkyblockSword(ItemData(
            name = "Giant's Sword",
            rarity = SkyblockRarities.LEGENDARY,
            reforgable = true,
            item = ItemStackData(Material.IRON_SWORD),
            damage = 500.toBigInteger(),
            abilities = listOf(AbilityTypes.GIANTSSLAM)
        )))

        swords.add(SkyblockSword(ItemData(
            name = "Yeti Sword",
            rarity = SkyblockRarities.LEGENDARY,
            reforgable = true,
            item = ItemStackData(Material.IRON_SWORD),
            damage = 150.toBigInteger(),
            strength = 170.toBigInteger(),
            abilities = listOf(AbilityTypes.TERRAINTOSS)
        )))

        swords.add(SkyblockSword(ItemData(
            name = "Axe of the Shredded",
            rarity = SkyblockRarities.LEGENDARY,
            reforgable = true,
            item = ItemStackData(Material.DIAMOND_AXE),
            damage = 140.toBigInteger(),
            strength = 115.toBigInteger(),
            abilities = listOf(AbilityTypes.AOTSLORE,AbilityTypes.AOTSTHROW)
        )))

        swords.add(SkyblockSword(ItemData(
            name = "Midas Staff",
            rarity = SkyblockRarities.LEGENDARY,
            reforgable = true,
            item = ItemStackData(Material.GOLD_SPADE),
            damage = 130.toBigInteger(),
            strength = 150.toBigInteger(),
            intelligence = 50.toBigInteger(),
            abilities = listOf(AbilityTypes.MOLTENWAVE)
        )))
    }
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(null,9,"b")
    }
    fun getInventory(p:Player): Inventory {
        return loadInventory(0,p)
    }
    fun loadInventory(pageNum:Int,p:Player): Inventory {
        val inv = Bukkit.createInventory(holder,54,"§aPage $pageNum")
        val swordPage = pages[pageNum]
        swordPage.forEachIndexed { index,sword->
            inv.setItem(index,sword.createItem(p))
        }
        for(i in 1..9){
            val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
            val greenGlassMeta = greenGlass.itemMeta
            greenGlassMeta.displayName = " "
            greenGlass.itemMeta = greenGlassMeta
            setItem(Point(i,6),inv,greenGlass)
        }
        if(pageNum != 0){
            val leftArrow = ItemStack(Material.SKULL_ITEM,1)
            leftArrow.durability = 3.toShort()
            val leftArrowMeta = leftArrow.itemMeta as SkullMeta
            leftArrowMeta.owner = "MHF_ArrowLeft"
            leftArrowMeta.displayName = "§aPrev. Page"
            leftArrow.itemMeta = leftArrowMeta
            setItem(Point(3,6),inv,leftArrow)
        }
        if(pageNum+1!=pages.size){
            val rightArrow = ItemStack(Material.SKULL_ITEM,1)
            rightArrow.durability = 3.toShort()
            val rightArrowMeta = rightArrow.itemMeta as SkullMeta
            rightArrowMeta.owner = "MHF_ArrowRight"
            rightArrowMeta.displayName = "§aNext Page"
            rightArrow.itemMeta = rightArrowMeta
            setItem(Point(7,6),inv,rightArrow)
        }
        return  inv
    }

    override fun onClick(e: InventoryClickEvent) {
        val player = e.whoClicked
        val topInv = player.openInventory.topInventory
        val slot = e.slot
        if(!isInventory(topInv)) return
        e.isCancelled = true
        if(e.clickedInventory==topInv){
            if(slot<=44){
                val item = topInv.getItem(slot)
                if(item==null||item.type==Material.AIR){
                    return
                }
                val sbSword = item.getSkyblockData()
                player.inventory.addItem(sbSword.createItem(player as Player))
                return
            }
        }
    }
}