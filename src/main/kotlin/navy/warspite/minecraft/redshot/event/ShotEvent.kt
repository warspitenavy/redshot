package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadWeapons
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object ShotEvent : Listener {
    private val plugin = Main.instance
    private val shootingPlayer = linkedMapOf<String, Boolean>()
    private val zoomingPlayer = linkedMapOf<String, Boolean>()
    private val shootCount = linkedMapOf<String, Int>()

    private fun checkWeapon(player: Player): Any? {
        val itemStack = player.inventory.itemInMainHand
        val key = NamespacedKey(plugin, "RedShotKey")
        val itemMeta = itemStack.itemMeta
        val container = itemMeta?.persistentDataContainer
        return (if (container != null) {
            if (container.has(key, PersistentDataType.STRING)) {
                container.get(key, PersistentDataType.STRING)
            } else null
        } else null)
    }

    private fun playSound(sounds: ArrayList<*>, player: Player) {
        sounds.forEach {
            it as String
            val soundParam = it.split('-')
            Bukkit.getScheduler().runTaskLater(
                plugin, Runnable {
                    player.location.world?.playSound(
                        player.location,
                        Sound.valueOf(soundParam[0]),
                        soundParam[1].toFloat(),
                        soundParam[2].toFloat()
                    )
                }, soundParam[3].toLong()
            )
        }
    }

    @EventHandler
    fun catchShotEvent(e: PlayerInteractEvent) {
        if (shootCount[e.player.name] == null) shootCount[e.player.name] = 0
        if (shootingPlayer[e.player.name] == null) shootingPlayer[e.player.name] = false
        if (zoomingPlayer[e.player.name] == null) zoomingPlayer[e.player.name] = false

        if (e.action == Action.RIGHT_CLICK_AIR ||
            e.action == Action.RIGHT_CLICK_BLOCK
        ) shot(e.player)
        if (e.action == Action.LEFT_CLICK_AIR ||
            e.action == Action.LEFT_CLICK_BLOCK
        ) playerToggleZoomEvent(e.player)
    }

    private fun playerToggleZoomEvent(player: Player) {
        val id =
            if (checkWeapon(player) != null) checkWeapon(player) as String
            else return
        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>? ?: return
        val scope = weapon["scope"] as LinkedHashMap<*, *>? ?: return
        val zoomAmount = (scope["zoomAmount"] ?: 1) as Int
        val toggleZoomSounds = scope["toggleZoomSounds"] as ArrayList<*>?

        if (zoomingPlayer[player.name]!!)
        if (toggleZoomSounds != null) playSound(toggleZoomSounds, player)
    }

    private fun shot(player: Player) {
        val id =
            if (checkWeapon(player) != null) checkWeapon(player) as String
            else return

        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>? ?: return

        val shooting = weapon["shooting"] as LinkedHashMap<*, *>?
        if (shooting == null) {
            player.sendMessage(colouredMessage("&cERROR&r: \"shooting\""))
            return
        }

        val delayBetweenShots = (shooting["delayBetweenShots"] ?: 4) as Int
        val projectileAmount = (shooting["projectileAmount"] ?: 1) as Int
        val projectileDamage = (shooting["projectileDamage"] ?: 0) as Int
        val projectileSpeed = (shooting["projectileSpeed"] ?: 40) as Int
        val projectileType = (shooting["projectileType"] ?: "snowball") as String
        val shootSounds = shooting["shootSounds"] as ArrayList<*>?
        val bulletSpread = (shooting["bulletSpread"] ?: 0.0) as Double

        val burstFire = weapon["burstFire"] as LinkedHashMap<*, *>?
        val shotPerBurst = (burstFire?.get("shotsPerBurst") ?: 1) as Int
        val delayBetweenShotsInBurst = (burstFire?.get("delayBetweenShotsInBurst") ?: 0) as Int

        val sneak = weapon["sneak"] as LinkedHashMap<*, *>?
        val noRecoil = (sneak?.get("noRecoil") ?: false) as Boolean
        val recoilX = (sneak?.get("recoilX") ?: 0.0) as Double
        val recoilY = (sneak?.get("recoilY") ?: 0.0) as Double
        val sneakBulletSpread = (sneak?.get("bulletSpread") ?: 0.0) as Double

        val scope = weapon["scope"] as LinkedHashMap<*, *>?
        val zoomBulletSpread = (scope?.get("bulletSpread")?: 0.0) as Double

        fun shootProjectile(accuracy: Double) {
            when (projectileType) {
                "snowball" -> {
                    if (shootSounds != null) playSound(shootSounds, player)
                    for (i in 1..projectileAmount) {
                        val snowball = player.launchProjectile(Snowball::class.java)
                        snowball.setMetadata(
                            "damage", FixedMetadataValue(
                                plugin,
                                projectileDamage
                            )
                        )
                        snowball.setMetadata(
                            "shooter", FixedMetadataValue(
                                plugin,
                                player
                            )
                        )
//                        snowball.velocity = e.player.location.direction.multiply(projectileSpeed/10.0)
                        val velocity = player.location.direction
                        velocity.multiply(projectileSpeed / 10.0)
                        velocity.add(
                            Vector(
                                Math.random() * accuracy,
                                Math.random() * accuracy,
                                Math.random() * accuracy
                            )
                        )
                        snowball.velocity = velocity
                    }
                }
            }
        }

        fun shoot() {
            if (zoomingPlayer[player.name]!!) {
                shootProjectile(0.0)
                return
            }
            if (player.isSneaking) {
                shootProjectile(sneakBulletSpread)
                return
            }
            shootProjectile(bulletSpread)
        }

        fun burstFireShot() {
            var runCount = 0
            object : BukkitRunnable() {
                override fun run() {
                    if (runCount < shotPerBurst) {
                        shoot()
                        runCount++
                    } else {
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0, delayBetweenShotsInBurst.toLong())
        }

        if (shootingPlayer[player.name]!!) return

        if (burstFire == null) {
            shoot()
            shootingPlayer[player.name] = true
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                shootingPlayer[player.name] = false
            }, delayBetweenShots.toLong())
        } else {
            burstFireShot()
            shootingPlayer[player.name] = true
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                shootingPlayer[player.name] = false
            }, delayBetweenShots.toLong())
        }

//        {
//            fun repeatShoot() {
////            fun getRate(i: Int): Int {
////                val min = 240 + (i * 60)
////                return min / 60
////            }
//
//                shootingPlayer[e.player.name] = true
//
//                var count = 0
//                val perBurst = when (fireRate) {
//                    1 -> arrayListOf(1, 1, 1, 1)
//                    2 -> arrayListOf(1, 2, 1, 2)
//                    3 -> arrayListOf(1, 2, 2, 2)
//                    4 -> arrayListOf(2, 2, 2, 2)
//                    5 -> arrayListOf(2, 2, 2, 3)
//                    6 -> arrayListOf(2, 3, 2, 3)
//                    7 -> arrayListOf(2, 3, 3, 3)
//                    8 -> arrayListOf(3, 3, 3, 3)
//                    9 -> arrayListOf(3, 3, 3, 4)
//                    10 -> arrayListOf(3, 4, 3, 4)
//                    11 -> arrayListOf(3, 4, 4, 4)
//                    12 -> arrayListOf(4, 4, 4, 4)
//                    13 -> arrayListOf(4, 4, 4, 5)
//                    14 -> arrayListOf(4, 5, 4, 5)
//                    15 -> arrayListOf(4, 5, 5, 5)
//                    16 -> arrayListOf(5, 5, 5, 5)
//                    else -> arrayListOf(1, 1, 1, 1)
//                }
//
//                val period = when {
//                    fireRate <= 6 -> arrayListOf(2, 2, 2, 2)
//                    fireRate == 7 -> arrayListOf(2, 2, 2, 1)
//                    fireRate >= 8 -> arrayListOf(1, 1, 1, 1)
//                    else -> arrayListOf(1, 1, 1, 1)
//                }
//
//                println("purBurst:${perBurst[shootCount[e.player.name]!!]}")
//                println("period:${period[shootCount[e.player.name]!!]}")
//                println("count:${shootCount[e.player.name]!!}")
//
//                object : BukkitRunnable() {
//                    override fun run() {
//                        if (count < perBurst[shootCount[e.player.name]!!]) {
//                            playShootSounds()
//                            shootProjectile()
//                            count++
//                        } else {
//                            shootingPlayer[e.player.name] = false
//                            cancel()
//                        }
//                    }
//                }.runTaskTimer(plugin, 0, period[shootCount[e.player.name]!!].toLong())
//                shootCount[e.player.name] = shootCount[e.player.name]!!.plus(1)
//                if (shootCount[e.player.name]!! > 3) shootCount[e.player.name] = 0
//            }
//        } // repeatShoot

//        {
//        if (delayBetweenShots != null) {
//            playShootSounds()
//            shootProjectile()
//        }
//        else {
//            if (!shootingPlayer[e.player.name]!!) repeatShoot()
//            else {
//                if (!burstPlayers[e.player.name]!!) {
//                    burstPlayers[e.player.name] = true
//                    Bukkit.getScheduler().runTaskLater(
//                        plugin, Runnable {
//                            repeatShoot()
//                            burstPlayers[e.player.name] = false
//                            e.player.sendMessage(colouredMessage("delay"))
//                        }, 2
//                    )
//                }
//            }
//        }

//            if (shootingPlayers[e.player.name]!!) {
//                e.player.sendMessage(colouredMessage("You are shot"))
//                return
//            }
//            if (shootingPlayers[e.player.name] == false) repeatShoot()
//            if (shootingPlayers[e.player.name] == true) {
//                if (burstPlayers[e.player.name] == true) return
//                burstPlayers[e.player.name] = true
//                Bukkit.getScheduler().runTaskLater(
//                    plugin, Runnable {
//                        repeatShoot()
//                        e.player.sendMessage(colouredMessage("delay"))
//                        if (burstPlayers[e.player.name] == true) burstPlayers[e.player.name] = false
//                    }, 4
//                )
//            }

//        val currTime = System.currentTimeMillis()
//        if (!clickPlayers.containsKey(e.player.name)) {
//            if (clickPlayers.isEmpty()) {
//                clickPlayers[e.player.name] = currTime
//                return
//            }
//        }
//        val lastClick = clickPlayers[e.player.name]!!
//        if (currTime - lastClick > (delayBetweenShots / 20) * 1000) {
//            clickPlayers.remove(e.player.name)
//        } else {
//            clickPlayers[e.player.name] = currTime
//        }
//        } // other
    }
}
