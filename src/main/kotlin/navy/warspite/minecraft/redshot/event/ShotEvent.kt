package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadWeapons
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable

object ShotEvent : Listener {
    private val plugin = Main.instance
    private val clickPlayers = linkedMapOf<String, Long>()
    private val shootCount = linkedMapOf<String, Int>()

    @EventHandler
    fun catchShotEvent(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_AIR ||
            e.action == Action.RIGHT_CLICK_BLOCK) shot(e)
    }

    private fun shot(e: PlayerInteractEvent) {
        if (shootCount[e.player.name] == null) shootCount[e.player.name] = 0
        val itemStack = e.player.inventory.itemInMainHand
        val key = NamespacedKey(plugin, "RedShotKey")
        val itemMeta = itemStack.itemMeta
        val container = itemMeta?.persistentDataContainer
        val id = (if (container != null) {
            if (container.has(key, PersistentDataType.STRING)) {
                container.get(key, PersistentDataType.STRING)
            } else null
        } else null)
            ?: return

        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>? ?: return
        val shooting = weapon["shooting"] as LinkedHashMap<*, *>?
        if (shooting == null) {
            e.player.sendMessage(colouredMessage("&cERROR&r: \"shooting\""))
            return
        }

        val delayBetweenShots = shooting["delayBetweenShots"] as Int?
        val projectileAmount = shooting["projectileAmount"] as Int
        val projectileDamage = shooting["projectileDamage"] as Int
        val projectileSpeed = shooting["projectileSpeed"] as Int
        val projectileType = shooting["projectileType"] as String
        val shootSounds = shooting["shootSounds"]

        val fullyAutomatic = weapon["fullyAutomatic"] as LinkedHashMap<*, *>?
        val fireRate = fullyAutomatic?.get("fireRate") as Int

        fun getRate(i: Int): Int {
            val min = 240 + (i * 60)
            return min / 60
        }

        fun playShootSounds() {
            shootSounds as ArrayList<*>?
            shootSounds?.forEach {
                it as String
                val soundParam = it.split('-')
                Bukkit.getScheduler().runTaskLater(
                    plugin, Runnable {
                        e.player.playSound(
                            e.player.location,
                            Sound.valueOf(soundParam[0]),
                            soundParam[1].toFloat(),
                            soundParam[2].toFloat()
                        )
                    }, soundParam[3].toLong()
                )
            }
        }

        fun shootProjectile() {
            when (projectileType) {
                "snowball" -> {
                    for (i in 1..projectileAmount) {
                        val snowball = e.player.launchProjectile(Snowball::class.java)
                        val damage = NamespacedKey(plugin, "damage")
                        snowball.persistentDataContainer
                            .set(damage, PersistentDataType.INTEGER, projectileDamage)
                    }
                }
            }
        }

        fun repeatShoot() {
            var count = 0
            val period: ArrayList<Long> = when(fireRate) {
                1 -> arrayListOf(1,1,1,1)
                2 -> arrayListOf(1,2,1,2)
                3 -> arrayListOf(1,2,2,2)
                4 -> arrayListOf(2,2,2,2)
                5 -> arrayListOf(2,2,2,3)
                6 -> arrayListOf(2,3,2,3)
                7 -> arrayListOf(2,3,3,3)
                8 -> arrayListOf(3,3,3,3)
                9 -> arrayListOf(3,3,3,4)
                10 -> arrayListOf(3,4,3,4)
                11 -> arrayListOf(3,4,4,4)
                12 -> arrayListOf(4,4,4,4)
                13 -> arrayListOf(4,4,4,5)
                14 -> arrayListOf(4,5,4,5)
                15 -> arrayListOf(4,5,5,5)
                16 -> arrayListOf(5,5,5,5)
                else -> arrayListOf(1,1,1,1)
            }
            val pattern = when(fireRate) {
                1 -> arrayListOf(0,0,0,0)
                2 -> arrayListOf(0,0,0,0)
                3 -> arrayListOf(1,1,1,1)
                4 -> arrayListOf(1,1,1,1)
                5 -> arrayListOf(1,1,1,0)
                6 -> arrayListOf(0,1,0,1)
                7 -> arrayListOf()
                8 -> arrayListOf(3,3,3,3)
                9 -> arrayListOf(3,3,3,4)
                10 -> arrayListOf(3,4,3,4)
                11 -> arrayListOf(3,4,4,4)
                12 -> arrayListOf(4,4,4,4)
                13 -> arrayListOf(4,4,4,5)
                14 -> arrayListOf(4,5,4,5)
                15 -> arrayListOf(4,5,5,5)
                16 -> arrayListOf(5,5,5,5)
                else -> arrayListOf(1,1,1,1)
            }
            shootCount[e.player.name] = shootCount[e.player.name]!!.plus(1)

            object : BukkitRunnable() {
                override fun run() {
                    if (count < period[shootCount[e.player.name]!!]) {
                        println("count:${shootCount[e.player.name]}")
                        println(count)
                        playShootSounds()
                        shootProjectile()
                        count++
                    } else cancel()
                }
            }.runTaskTimer(plugin, 0, 2)
            if (shootCount[e.player.name]!! > 3) shootCount[e.player.name] = 0
        }
        if (fullyAutomatic != null) repeatShoot()
    }
}

//        val currTime = System.currentTimeMillis()
//        if (!clickPlayers.containsKey(e.player.name)) {
//            if (clickPlayers.isEmpty()) {
//                clickPlayers[e.player.name] = currTime
////                return
//            }
//        }
//        val lastClick = clickPlayers[e.player.name]!!
//        if (currTime - lastClick > 250) {
//            clickPlayers.remove(e.player.name)
//        } else {
//            clickPlayers[e.player.name] = currTime
//        }