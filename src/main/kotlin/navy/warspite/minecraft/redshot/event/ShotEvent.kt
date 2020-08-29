package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadWeapons
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colouredMessage
import navy.warspite.minecraft.redshot.util.GetColoured.colouredText
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object ShotEvent : Listener {
    private val plugin = Main.instance
    private val shootingPlayer = linkedMapOf<String, Boolean>()
    private val zoomingPlayer = linkedMapOf<String, Boolean>()
    private val reloadingPlayer = linkedMapOf<String, Boolean>()
    private val shootCount = linkedMapOf<String, Int>()

    private fun checkWeapon(itemStack: ItemStack): Any? {
        val key = NamespacedKey(plugin, "weaponId")
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
    fun catchWeaponsEvent(e: PlayerInteractEvent) {
        if (shootCount[e.player.name] == null) shootCount[e.player.name] = 0
        if (shootingPlayer[e.player.name] == null) shootingPlayer[e.player.name] = false
        if (zoomingPlayer[e.player.name] == null) zoomingPlayer[e.player.name] = false
        if (reloadingPlayer[e.player.name] == null) reloadingPlayer[e.player.name] = false

        if (e.action == Action.RIGHT_CLICK_AIR ||
            e.action == Action.RIGHT_CLICK_BLOCK
        ) shot(e.player)
        if (e.action == Action.LEFT_CLICK_AIR ||
            e.action == Action.LEFT_CLICK_BLOCK
        ) playerToggleZoomEvent(e.player)
    }

    private fun playerToggleZoomEvent(player: Player) {
//        if (zoomingPlayer[player.name]!!) return
        val id =
            if (checkWeapon(player.inventory.itemInMainHand) != null) {
                checkWeapon(player.inventory.itemInMainHand) as String
            } else return
        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>? ?: return
        val scope = weapon["scope"] as LinkedHashMap<*, *>? ?: return
        val zoomAmount = (scope["zoomAmount"] ?: 1) as Int
        val toggleZoomSounds = scope["toggleZoomSounds"] as ArrayList<*>?
        val sight = (scope["sight"] ?: false) as Boolean

        if (toggleZoomSounds != null) playSound(toggleZoomSounds, player)
        if (zoomingPlayer[player.name]!!) {
            zoomingPlayer[player.name] = false
            if (sight) player.inventory.helmet = ItemStack(Material.AIR)
            player.removePotionEffect(PotionEffectType.SLOW)
            return
        }
        if (!zoomingPlayer[player.name]!!) {
            zoomingPlayer[player.name] = true
            if (sight) player.inventory.helmet = ItemStack(Material.CARVED_PUMPKIN)
            player.addPotionEffect(
                PotionEffect(
                    PotionEffectType.SLOW,
                    1000,
                    zoomAmount
                )
            )
            return
        }
    }

    private fun setAmmoAmount(itemMeta: ItemMeta, amount: Int): ItemMeta {
        val key = NamespacedKey(plugin, "ammo")
        val weaponIdKey = NamespacedKey(plugin, "weaponId")

        val container = itemMeta.persistentDataContainer

        val id = if (container.has(weaponIdKey, PersistentDataType.STRING)) {
            container.get(weaponIdKey, PersistentDataType.STRING)
        } else return itemMeta

        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>?
            ?: return itemMeta
        val itemInformation = weapon["itemInformation"] ?: return itemMeta
        itemInformation as LinkedHashMap<*, *>

        val itemName = itemInformation["itemName"] as String

        itemMeta.persistentDataContainer.set(key, PersistentDataType.INTEGER, amount)
        itemMeta.setDisplayName(colouredText("""$itemName □ «$amount»"""))
        //        val container = itemMeta?.persistentDataContainer ?: return
//        if (container.has(key, PersistentDataType.INTEGER)) {
//            container.set(key, PersistentDataType.INTEGER, amount)
//        } else return
        return itemMeta
    }

    private fun shot(player: Player) {
        val id =
            if (checkWeapon(player.inventory.itemInMainHand) != null) {
                checkWeapon(player.inventory.itemInMainHand) as String
            } else return

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
        val noRecoil = sneak?.get("noRecoil") as Boolean?
        val recoilX = sneak?.get("recoilX") as Double?
        val recoilY = sneak?.get("recoilY") as Double?
        val sneakBulletSpread = sneak?.get("bulletSpread") as Double?

        val scope = weapon["scope"] as LinkedHashMap<*, *>?
        val zoomBulletSpread = scope?.get("bulletSpread") as Double?

        fun shootProjectile(accuracy: Double) {
            when (projectileType) {
                "snowball" -> {
                    val itemStack = player.inventory.itemInMainHand
                    if (itemStack.itemMeta == null) return
                    val key = NamespacedKey(plugin, "ammo")
                    val container = itemStack.itemMeta!!.persistentDataContainer
                    val ammoAmount =
                        if (container.has(key, PersistentDataType.INTEGER)) {
                            container.get(key, PersistentDataType.INTEGER)
                        } else null
                    if (ammoAmount != null) {
                        itemStack.itemMeta =
                            setAmmoAmount(itemStack.itemMeta!!, ammoAmount-1)
                    }
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
                    if (shootSounds != null) playSound(shootSounds, player)
                }
            }
        }

        fun shoot() {
            if (zoomingPlayer[player.name]!!) {
                if (zoomBulletSpread != null) {
                    shootProjectile(zoomBulletSpread)
                }
                return
            }
            if (player.isSneaking) {
                if (sneak == null) return
                if (sneakBulletSpread != null) shootProjectile(sneakBulletSpread)
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
            }, delayBetweenShots.toLong()) }

        else {
            burstFireShot()
            shootingPlayer[player.name] = true
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                shootingPlayer[player.name] = false
            }, delayBetweenShots.toLong())
        }
    }

    @EventHandler
    fun manualReloadEvent(e: PlayerDropItemEvent) {
        val item = e.itemDrop
        val id =
            if (checkWeapon(item.itemStack) != null) checkWeapon(item.itemStack) as String
            else return
        val weapon = LoadWeapons.weaponsHashMap[id] as LinkedHashMap<*, *>? ?: return
        val reload = weapon["reload"] as LinkedHashMap<*, *>? ?: return
        val reloadAmount = reload["reloadAmount"] as Int? ?: return
        item.itemStack.itemMeta = item.itemStack.itemMeta?.let { setAmmoAmount(it, reloadAmount) }
        e.isCancelled = true
    }
}
