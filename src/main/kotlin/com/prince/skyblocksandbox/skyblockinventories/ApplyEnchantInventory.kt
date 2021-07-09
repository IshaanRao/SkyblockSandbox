package com.prince.skyblocksandbox.skyblockinventories

import com.prince.skyblocksandbox.skyblockenchants.SkyblockEnchant
import com.prince.skyblocksandbox.skyblockenchants.SkyblockEnchants
import com.prince.skyblocksandbox.skyblockitems.SkyblockItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.awt.Point

object ApplyEnchantInventory : SkyblockInventory(){
    override val holder: SkyblockHolder = SkyblockHolder("apply_enchant")
    val items = HashMap<Player,SkyblockItem>()
    override fun getInventory(): Inventory {
        return  Bukkit.createInventory(holder,9,"a")
    }
    val playersForceClosed = HashSet<Player>()
    fun getInventory(enchant:SkyblockEnchant,item: SkyblockItem,p:Player): Inventory {
        items.put(p,item)
        val inv = Bukkit.createInventory(holder,27,"§aEnchant ${enchant.name}")
        val greenGlass = ItemStack(Material.STAINED_GLASS_PANE,1,5.toShort())
        val greenGlassMeta = greenGlass.itemMeta
        greenGlassMeta.displayName = " "
        greenGlass.itemMeta = greenGlassMeta
        fillBorders(inv,greenGlass)
        for(y in 1..3){
            for(x in 1..9){
                if(getItem(Point(x,y),inv)==null||getItem(Point(x,y),inv)?.type==Material.AIR){
                    setItem(Point(x,y),inv,greenGlass)
                }
            }
        }
        val starting:Int = 5-((enchant.levelRange.last-1)/2)
        for(level in enchant.levelRange){
            setItem(Point(starting-1+level,2),inv, createEnchantItem(enchant,level))
        }
        val removeEnchant = ItemStack(Material.ANVIL,1)
        val removeEnchantMeta = removeEnchant.itemMeta
        removeEnchantMeta.displayName = "§cRemove Enchant"
        removeEnchantMeta.lore = listOf("§7Click this to remove the","§7enchant from the item!")
        removeEnchant.itemMeta = removeEnchantMeta
        setItem(Point(5,3),inv,removeEnchant)
        return inv
    }
    fun getEnchant(inv:Inventory):SkyblockEnchant?{
        val enchName = inv.title.replace("§aEnchant ","")
        return SkyblockEnchants.getEnchantFromName(enchName)
    }
    fun createEnchantItem(enchant:SkyblockEnchant,level:Int): ItemStack {
        val book = ItemStack(Material.ENCHANTED_BOOK)
        val bookMeta = book.itemMeta
        bookMeta.displayName = "§a${enchant.name} ${level}"
        val lore = enchant.descAtLevel(level).toMutableList()
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
        if(e.clickedInventory==topInv) {
            val item = topInv.getItem(slot) ?: return
            if(slot==22){
                val data = items[player]!!
                val enchant:SkyblockEnchant = getEnchant(topInv) ?: return
                data.itemData.enchants.remove(SkyblockEnchants.getEnumFromEnchant(enchant))
                playersForceClosed.add(player)
                player.openInventory(EnchantInventory.getInventory(items[player]!!.createItem(player)))
            }else if(item.type==Material.ENCHANTED_BOOK){
                val displayName = item.itemMeta.displayName.replace("§a","")
                val level = displayName.split(" ").last().toInt()
                val enchString = displayName.split(" ").subList(0,displayName.split(" ").size-1).joinToString(" ")
                val ench = SkyblockEnchants.getEnchantFromName(enchString) ?: return
                val data = items[player]!!
                data.itemData.enchants.put(SkyblockEnchants.getEnumFromEnchant(ench)?:return,level)
                playersForceClosed.add(player)
                player.openInventory(EnchantInventory.getInventory(items[player]!!.createItem(player)))

            }
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(playersForceClosed.contains(e.player)){
            playersForceClosed.remove(e.player)
            return
        }
        e.player.inventory.addItem(items[e.player]?.createItem(e.player as Player))
    }
}