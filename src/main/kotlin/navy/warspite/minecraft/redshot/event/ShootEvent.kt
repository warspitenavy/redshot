package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadFiles
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.Bukkit
import org.bukkit.entity.*
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object ShootEvent {
    private val plugin = Main.instance
    fun shooting(player: Player) {
        if (CatchEvent.shootingPlayer[player]!!) return

        val itemMeta = player.inventory.itemInMainHand.itemMeta ?: return
        val weaponId = GetMeta.weaponId(itemMeta) ?: return
        val weapon = LoadFiles.weaponJson[weaponId] ?: return
        val shooting = weapon.shooting
        val sneak = weapon.sneak
        val scope = weapon.scope
        val burstFire = weapon.burstFire

        fun shoot() {
            val accuracy =
                if (CatchEvent.scopingPlayer[player]!!) scope?.bulletSpread ?: 0.0
                else if (player.isSneaking) sneak?.bulletSpread ?: 0.0
                else shooting.bulletSpread
            for (i in 1..shooting.projectileAmount) {
                projectile(
                    shooting.projectileType,
                    shooting.projectileSpeed,
                    shooting.projectileDamage,
                    player,
                    accuracy
                )
            }
            if (shooting.shootSounds != null) PlaySound.play(shooting.shootSounds, player)
        }

        fun burstShoot() {
            var runCount = 0
            object : BukkitRunnable() {
                override fun run() {
                    if (runCount < burstFire!!.shotsPerBurst) {
                        shoot()
                        runCount++
                    } else {
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0, burstFire!!.delayBetweenShotsInBurst.toLong())
        }

        if (burstFire != null) {
            burstShoot()
            CatchEvent.shootingPlayer[player] = true
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                CatchEvent.shootingPlayer[player] = false
            }, shooting.delayBetweenShots.toLong())
        } else {
            shoot()
            CatchEvent.shootingPlayer[player] = true
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                CatchEvent.shootingPlayer[player] = false
            }, shooting.delayBetweenShots.toLong())
        }
    }

    private fun projectile(type: String, speed: Int, damage: Int, player: Player, accuracy: Double) {
        val projectile = when (type) {
            "snowball" -> player.launchProjectile(Snowball::class.java)
            "arrow" -> player.launchProjectile(Arrow::class.java)
            "egg" -> player.launchProjectile(Egg::class.java)
            "witherskull" -> player.launchProjectile(WitherSkull::class.java)
            else -> player.launchProjectile(Snowball::class.java)
        }
        projectile.setMetadata(
            "damage", FixedMetadataValue(
                plugin,
                damage
            )
        )
        projectile.setMetadata(
            "shooter", FixedMetadataValue(
                plugin,
                player
            )
        )
        val velocity = player.location.direction
        velocity.multiply(speed / 10.0)
        velocity.add(
            Vector(
                Math.random() * accuracy,
                Math.random() * accuracy,
                0.0
            )
        )
        projectile.velocity = velocity
    }
}