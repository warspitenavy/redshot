package navy.warspite.redshot.controller

import navy.warspite.redshot.Main.Companion.plugin
import navy.warspite.redshot.Main.Companion.reloadingState
import navy.warspite.redshot.Main.Companion.scopingState
import navy.warspite.redshot.Main.Companion.shootingState
import navy.warspite.redshot.controller.AmmoController.getAmmo
import navy.warspite.redshot.controller.AmmoController.setAmmo
import navy.warspite.redshot.controller.ReloadController.reloading
import navy.warspite.redshot.params.WeaponParam
import navy.warspite.redshot.utils.Sound.playSoundByList
import org.bukkit.entity.*
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

object ProjectileController {
    fun shooting(player: Player, weapon: WeaponParam.Weapon) {
        val itemStack = player.inventory.itemInMainHand
        val itemMeta = itemStack.itemMeta ?: return
        /**
         * 連打対策
         * 発射中ならキャンセル
         */
        if (shootingState[player.uniqueId] == true) return

        fun projectile() {
            /** スピード, 精密動作性 */
            val shootingBulletSpread = weapon.shooting.bulletSpread
            val scopeBulletSpread = weapon.scope?.bulletSpread ?: shootingBulletSpread
            val sneakBulletSpread = weapon.sneak?.bulletSpread ?: scopeBulletSpread
            val bulletSpread = when {
                scopingState[player.uniqueId] == true -> scopeBulletSpread
                player.isSneaking -> sneakBulletSpread
                else -> shootingBulletSpread
            }
            val direction = player.location.direction
            direction.multiply(weapon.shooting.projectileSpeed / 10.0)
            fun randomVector() = ((Math.random() * 1) - 0.5) * bulletSpread
            direction.add(
                Vector(
                    randomVector(),
                    randomVector(),
                    randomVector()
                )
            )

            /** 弾丸 */
            val projectile = when (weapon.shooting.projectileType) {
                "snowball" -> player.launchProjectile(Snowball::class.java)
                "arrow" -> player.launchProjectile(Arrow::class.java)
                "egg" -> player.launchProjectile(Egg::class.java)
                "fireball" -> player.launchProjectile(Fireball::class.java)
                "witherskull" -> player.launchProjectile(WitherSkull::class.java)
                else -> player.launchProjectile(Snowball::class.java)
            }
            /** 攻撃力 */
            projectile.setMetadata(
                "damage", FixedMetadataValue(
                    plugin,
                    weapon.shooting.projectileDamage
                )
            )
            /** 発射した人 */
            projectile.setMetadata(
                "shooter", FixedMetadataValue(
                    plugin,
                    player
                )
            )
            /** 武器のID(未使用) */
            projectile.setMetadata(
                "weaponId", FixedMetadataValue(
                    plugin,
                    weapon.detail.id
                )
            )
            projectile.velocity = direction
        }

        /** 単発 */
        fun shoot() {
            if (reloadingState[player.uniqueId] == true) return
            val ammo = getAmmo(itemMeta) ?: 0
            if (ammo - 1 < 0) {
                playSoundByList(weapon.reload.outOfAmmoSounds, player)
                reloading(player, itemMeta, weapon)
                return
            } else {
                itemStack.itemMeta = setAmmo(itemMeta, ammo - 1, weapon.detail.name)
            }
            for (i in 1..weapon.shooting.projectileAmount) {
                projectile()
            }
            if (weapon.shooting.shootSounds.isNotEmpty()) playSoundByList(weapon.shooting.shootSounds, player)
        }

        /** 連射 */
        fun burst() {
            object : BukkitRunnable() {
                var count = 0
                override fun run() {
                    if (reloadingState[player.uniqueId] == true) cancel()
                    if (count < weapon.burstFire?.shotsPerBurst!!) {
                        shoot()
                        count++
                    } else {
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0, weapon.burstFire?.delayBetweenShotsInBurst!!.toLong())
        }

        /** 連射が有効の場合 */
        if (weapon.burstFire?.shotsPerBurst != null) {
            burst()
            shootingState[player.uniqueId] = true
            plugin.server.scheduler.runTaskLater(plugin, Runnable {
                shootingState[player.uniqueId] = false
            }, weapon.shooting.delayBetweenShots.toLong())
        } else {
            /** 連射が無効の場合 */
            shoot()
            shootingState[player.uniqueId] = true
            plugin.server.scheduler.runTaskLater(plugin, Runnable {
                shootingState[player.uniqueId] = false
            }, weapon.shooting.delayBetweenShots.toLong())
        }
    }
}