package com.prince.skyblocksandbox.skyblockinput

import com.prince.skyblocksandbox.skyblockabilities.AbilityTypes
import com.prince.skyblocksandbox.skyblockutils.SkyblockRarities
import org.bukkit.Material
import java.util.*

enum class InputChecks {
    INT{
        override fun passesCheck(s: String): Boolean {
            return try{
                s.toInt()
                true
            }catch (e:Exception){
                false
            }
        }

        override val invalidMessage = "Please provide a valid integer"
    },
    ABILITY{
        override fun passesCheck(s: String): Boolean {
            try{
                if(s.equals("none",true)){
                    return true
                }
                AbilityTypes.valueOf(s.uppercase(Locale.getDefault()))
                return true
            }catch (e:Exception){
               return false
            }
        }
        override val invalidMessage = "Please provide a valid ability "//TODO(ABILITY LIST)
    },
    MAT{
        override fun passesCheck(s: String): Boolean {
            return try {
                val material = Material.valueOf(s.uppercase(Locale.getDefault()))
                (listOf(Material.DIAMOND_SWORD,Material.DIAMOND_AXE,Material.DIAMOND_SPADE,Material.GOLD_SWORD,Material.GOLD_AXE,Material.GOLD_SPADE,Material.IRON_SWORD,Material.IRON_AXE,Material.IRON_SPADE,Material.STONE_SWORD,Material.STONE_AXE,Material.STONE_SPADE,Material.WOOD_SWORD,Material.WOOD_AXE,Material.WOOD_SPADE).contains(material))
            }catch (e:Exception){
                false
            }
        }

        override val invalidMessage = "Please provide a material in the list \nAccepted Materials: diamond_sword,diamond_axe,diamond_spade,gold_sword,gold_axe,gold_spade,iron_sword,iron_axe,iron_spade,stone_sword,stone_axe,stone_spade,wood_sword,wood_axe,wood_spade\""
    },
    BOOLEAN{
        override fun passesCheck(s: String): Boolean {
            return true
        }
        override val invalidMessage = "Impossible :o"
    },
    RARITY{
        override fun passesCheck(s: String): Boolean {
            return try{
                SkyblockRarities.valueOf(s.uppercase(Locale.getDefault()))
                true
            }catch (e:Exception){
                false
            }
        }
        override val invalidMessage = "Please provide a valid rarity, common,uncommon,rare,epic,legendary,mythic "
    },
    STRING{
        override fun passesCheck(s: String): Boolean {
            return true
        }

        override val invalidMessage: String
            get() = "Impossible :o"
    }
    ;
    abstract fun passesCheck(s:String): Boolean
    abstract val invalidMessage:String
}