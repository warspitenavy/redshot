package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.LoadJsons
import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.PlaySound
import org.bukkit.entity.*
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.util.Vector

object ShootProjectile {
    private val plugin = Main.instance
    fun shoot(player: Player) {
        val itemMeta = player.inventory.itemInMainHand.itemMeta ?: return
        val weaponId = GetMeta.weaponId(itemMeta) ?: return
        val weapon = LoadJsons.weaponJson[weaponId] ?: return
        val shooting = weapon.shooting

        for (i in 1..shooting.projectileAmount) {
            projectile(
                shooting.projectileType,
                shooting.projectileSpeed,
                shooting.projectileDamage,
                player,
                shooting.bulletSpread
            )
        }

        if (shooting.shootSounds != null) PlaySound.playSound(shooting.shootSounds, player)
    }

    fun projectile(type: String, speed: Int, damage: Int, player: Player, accuracy: Double) {
        val projectile = when (type) {
            "snowball" -> player.launchProjectile(Snowball::class.java)
            "egg" -> player.launchProjectile(Egg::class.java)
            "witherSkull" -> player.launchProjectile(WitherSkull::class.java)
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
                Math.random() * accuracy
            )
        )
        projectile.velocity = velocity
    }
}