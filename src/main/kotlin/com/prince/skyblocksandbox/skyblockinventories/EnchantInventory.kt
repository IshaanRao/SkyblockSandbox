package com.prince.skyblocksandbox.skyblockinventories

import com.prince.skyblocksandbox.SkyblockSandbox.Companion.log
import com.prince.skyblocksandbox.skyblockenchants.SkyblockEnchant
import com.prince.skyblocksandbox.skyblockenchants.SkyblockEnchants
import com.prince.skyblocksandbox.skyblockitems.data.ItemData
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.getSkyblockData
import com.prince.skyblocksandbox.skyblockutils.ItemExtensions.isSkyblockItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.awt.Point
import java.util.ArrayList


object EnchantInventory : SkyblockInventory(){
    override val holder: SkyblockHolder
        get() = SkyblockHolder("enchant_inventory")
    val playersForceClosed = HashSet<Player>()
    override fun getInventory(): Inventory {
        val inv = Bukkit.createInventory(holder,45,"§aSkyblock Enchant")
        val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
        val greenGlassMeta = greenGlass.itemMeta
        greenGlassMeta.displayName = " "
        greenGlass.itemMeta = greenGlassMeta
        fillBorders(inv,greenGlass)

        //Set Item Borders
        setItem(Point(2,2),inv,greenGlass)
        setItem(Point(3,2),inv,greenGlass)
        setItem(Point(3,3),inv,greenGlass)
        setItem(Point(2,4),inv,greenGlass)
        setItem(Point(3,4),inv,greenGlass)

        val rightArrow = ItemStack(Material.SKULL_ITEM,1)
        rightArrow.durability = 3.toShort()
        val rightArrowMeta = rightArrow.itemMeta as SkullMeta
        rightArrowMeta.owner = "MHF_ArrowRight"
        rightArrowMeta.displayName = "§aNext Page"
        rightArrow.itemMeta = rightArrowMeta

        val leftArrow = ItemStack(Material.SKULL_ITEM,1)
        leftArrow.durability = 3.toShort()
        val leftArrowMeta = leftArrow.itemMeta as SkullMeta
        leftArrowMeta.owner = "MHF_ArrowLeft"
        leftArrowMeta.displayName = "§aPrev. Page"
        leftArrow.itemMeta = leftArrowMeta

        //Set Arrows
        setItem(Point(9,3),inv,rightArrow)
        setItem(Point(9,4),inv,leftArrow)
        loadEnchants(inv)
        return inv
    }

    fun getInventory(item:ItemStack?=null): Inventory {
        val inv = Bukkit.createInventory(holder,45,"§aSkyblock Enchant")
        val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
        val greenGlassMeta = greenGlass.itemMeta
        greenGlassMeta.displayName = " "
        greenGlass.itemMeta = greenGlassMeta
        fillBorders(inv,greenGlass)

        //Set Item Borders
        setItem(Point(2,2),inv,greenGlass)
        setItem(Point(3,2),inv,greenGlass)
        setItem(Point(3,3),inv,greenGlass)
        setItem(Point(2,4),inv,greenGlass)
        setItem(Point(3,4),inv,greenGlass)

        val rightArrow = ItemStack(Material.SKULL_ITEM,1)
        rightArrow.durability = 3.toShort()
        val rightArrowMeta = rightArrow.itemMeta as SkullMeta
        rightArrowMeta.owner = "MHF_ArrowRight"
        rightArrowMeta.displayName = "§aNext Page"
        rightArrow.itemMeta = rightArrowMeta

        val leftArrow = ItemStack(Material.SKULL_ITEM,1)
        leftArrow.durability = 3.toShort()
        val leftArrowMeta = leftArrow.itemMeta as SkullMeta
        leftArrowMeta.owner = "MHF_ArrowLeft"
        leftArrowMeta.displayName = "§aPrev. Page"
        leftArrow.itemMeta = leftArrowMeta

        //Set Arrows
        setItem(Point(9,3),inv,rightArrow)
        setItem(Point(9,4),inv,leftArrow)

        if(item!=null){
            setItem(Point(2,3),inv,item)
        }

        loadEnchants(inv)
        return inv
    }

    fun getItemInEnchantSlot(inv:Inventory):ItemStack?{
        return getItem(Point(2,3),inv)
    }
    fun getEnchants(itemType: ItemTypes): List<SkyblockEnchant> {
        val list = ArrayList<SkyblockEnchant>()
        for(enchant in SkyblockEnchants.values()){
            if(enchant.obj.items == itemType){
                list.add(enchant.obj)
            }
        }
        return list
    }
    fun loadEnchants(inv:Inventory){
        if(getItemInEnchantSlot(inv)==null|| getItemInEnchantSlot(inv)?.type==Material.AIR){
            for(y in 2..4){
                for(x in 4..8){
                    setItem(Point(x, y),inv, ItemStack(Material.AIR))
                }
            }
            return
        }
        val sbItem = getItemInEnchantSlot(inv)!!.getSkyblockData()
        val enchants = getEnchants(sbItem.itemType)
        var enchInd = 0
        for(y in 2..4){
            if(enchInd==enchants.size){
                break
            }
            for(x in 4..8){
                if(enchInd==enchants.size){
                    break
                }
                val enchant = enchants[enchInd]
                setItem(Point(x, y),inv, createEnchantItem(enchant))
                enchInd++
            }
        }
    }
    fun createEnchantItem(enchant:SkyblockEnchant):ItemStack{
        val book = ItemStack(Material.ENCHANTED_BOOK)
        val bookMeta = book.itemMeta
        bookMeta.displayName = "§a${enchant.name}"
        val lore = enchant.descAtLevel(1).toMutableList()
        lore.add("")
        lore.add("§eClick to enchant!")
        bookMeta.lore = lore
        book.itemMeta = bookMeta
        return book
    }
    override fun onClick(e: InventoryClickEvent) {
        val player:Player = e.whoClicked as Player
        val topInv = player.openInventory.topInventory
        val playerInv = player.inventory
        val slot = e.slot
        if(!isInventory(topInv)) return
        e.isCancelled = true
        if(e.clickedInventory==playerInv){
            val item = player.inventory.getItem(slot) ?: return
            if(item.isSkyblockItem()){
                val itemData = item.getSkyblockData()
                if(getItemInEnchantSlot(topInv)!=null){
                    if(getItemInEnchantSlot(e.inventory)!!.type!=Material.AIR) {
                        return
                    }
                }
                if(itemData.itemType!=ItemTypes.SWORD&&itemData.itemType!=ItemTypes.ARMOR){
                    return
                }
                setItem(Point(2,3),topInv,item)
                playerInv.setItem(slot,ItemStack(Material.AIR))
                loadEnchants(topInv)
            }
        }else if(e.clickedInventory==topInv){
            val item = topInv.getItem(slot) ?: return
            if(item.isSkyblockItem()){
                if(slot==19){
                    playerInv.addItem(item)
                    topInv.removeItem(item)
                }
            }else if(item.type == Material.ENCHANTED_BOOK){
                val sbItem = getItemInEnchantSlot(topInv)
                if(sbItem==null||sbItem.type==Material.AIR){
                    return
                }
                var name = item.itemMeta.displayName
                name = name.replace("§a","")
                val enchant = SkyblockEnchants.getEnchantFromName(name) ?: return
                playersForceClosed.add(player)
                player.openInventory(ApplyEnchantInventory.getInventory(enchant,sbItem.getSkyblockData(),player))
            }
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(getItemInEnchantSlot(e.inventory)!=null){
            if(getItemInEnchantSlot(e.inventory)!!.type!=Material.AIR) {
                if(playersForceClosed.contains(e.player)) {
                    playersForceClosed.remove(e.player)
                }else{
                    e.player.inventory.addItem(getItemInEnchantSlot(e.inventory))
                }
            }
        }
    }

}