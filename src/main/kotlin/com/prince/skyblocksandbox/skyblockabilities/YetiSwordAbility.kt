package com.prince.skyblocksandbox.skyblockabilities

import com.prince.skyblocksandbox.SkyblockSandbox
import com.prince.skyblocksandbox.skyblockhandlers.DamageHandler
import com.prince.skyblocksandbox.skyblockhandlers.MobHandler.Companion.isSkyblockMob
import com.prince.skyblocksandbox.skyblockitems.data.ItemTypes
import com.prince.skyblocksandbox.skyblockitems.data.StatsData
import com.prince.skyblocksandbox.skyblockutils.MathUtils
import com.prince.skyblocksandbox.skyblockutils.SkyblockHolograms
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object YetiSwordAbility : ItemAbility() {
    override val manaCost=250
    override val actions = listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    override val abilityType=AbilityTypes.TERRAINTOSS
    override val title = "§6Ability: Terrain Toss §e§lRIGHT CLICK"
    override val name: String = "Terrain Toss"
    override val itemType = ItemTypes.SWORD
    override fun getDesc(stats: StatsData) : List<String> {
        val damage = ((stats.abilityDamage+ ability.abilityDamage) * ((1+(stats.intelligence.toDouble()/100))* ability.multiplier)).toBigDecimal().toBigInteger()
        return listOf("§7Throws a chunk of terrain in the","§7direction you are facing! Deals","§7up to §c${damage} §7damage.")
    }
    override val ability = SkyblockAbility(15000,0.3)
    override fun execute(e: PlayerInteractEvent) {
        startCooldown(e.player,20)
        val armorStands = ArrayList<ArmorStand>()
        val player = e.player
        val landingLoc = player.getTargetBlock(null as Set<Material?>?, 30).location
        if(landingLoc.block!=null){
            landingLoc.add(Vector(0.0,1.1,0.0))
        }
        val loc1 = player.eyeLocation.add(Vector(0.0,0.1,0.0))
        val path = MathUtils.parabola(loc1, landingLoc, 18)
        val locationIterator = path.iterator()

        //Bottom Layer
        addStand(loc1,player.location,0,0,0,armorStands)
        addStand(loc1,player.location,1,0,0,armorStands)
        addStand(loc1,player.location,-1,0,0,armorStands)
        addStand(loc1,player.location,0,0,1,armorStands)
        addStand(loc1,player.location,0,0,-1,armorStands)

        //Top Layer
        addStand(loc1,player.location,0,1,0,armorStands)
        addStand(loc1,player.location,1,1,0,armorStands)
        addStand(loc1,player.location,-1,1,0,armorStands)
        addStand(loc1,player.location,2,1,0,armorStands)
        addStand(loc1,player.location,-2,1,0,armorStands)
        addStand(loc1,player.location,0,1,1,armorStands)
        addStand(loc1,player.location,0,1,-1,armorStands)
        addStand(loc1,player.location,0,1,2,armorStands)
        addStand(loc1,player.location,0,1,-2,armorStands)
        addStand(loc1,player.location,1,1,1,armorStands)
        addStand(loc1,player.location,-1,1,-1,armorStands)
        addStand(loc1,player.location,-1,1,1,armorStands)
        addStand(loc1,player.location,1,1,-1,armorStands)

        object : BukkitRunnable() {
            override fun run() {
                if(locationIterator.hasNext()){
                    moveStands(armorStands,locationIterator.next())
                }else{
                    loc1.world.createExplosion(armorStands[0].location.x,armorStands[0].location.y,armorStands[0].location.z,5f,false,false)
                    val nearbyEntities = armorStands[0].getNearbyEntities(5.0,5.0,5.0).filter { entity -> entity.isSkyblockMob() != null }
                    for(entity in nearbyEntities){
                        val mob = entity.isSkyblockMob()!!
                        DamageHandler.magicDamage(mob,player,YetiSwordAbility)
                    }
                    for(stand in armorStands){
                        try{
                            stand.passenger.remove()
                        }catch (ignored:NullPointerException){}
                        stand.remove()
                    }
                    cancel()
                }
            }
        }.runTaskTimer(SkyblockSandbox.instance,0,1)
    }
    fun addStand(baseLoc:Location,playerLoc:Location,xChange:Int,yChange:Int,zChange:Int,arrayList:ArrayList<ArmorStand>){
        val blockLoc = playerLoc.add(0.0,-2.0,0.0).add(Vector(xChange,yChange,zChange))
        val block: ItemStack = if(blockLoc.block!=null&&blockLoc.block.type!=Material.AIR){
            blockLoc.block.state.data.toItemStack()
        }else{
            ItemStack(Material.STAINED_GLASS,1,3.toShort())
        }
        arrayList.add(SkyblockHolograms.createFloatingBlock(baseLoc.clone().add(Vector(xChange,yChange,zChange)),block))
    }
    fun moveStands(stands:List<ArmorStand>, moveTo:Location){
        val mainStand = stands[0]
        val xMove = moveTo.x-mainStand.location.x
        val yMove = moveTo.y-mainStand.location.y
        val zMove = moveTo.z-mainStand.location.z
        for(stand in stands){
            try {
                val passenger: Entity = stand.passenger
                stand.eject()
                stand.teleport(stand.location.add(xMove, yMove, zMove))
                stand.passenger = passenger
            }catch (ignored:NullPointerException){}
        }
    }
}