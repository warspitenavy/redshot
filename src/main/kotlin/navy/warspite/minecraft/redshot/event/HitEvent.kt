package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import navy.warspite.minecraft.redshot.util.GetColoured.colourCode
import navy.warspite.minecraft.redshot.util.GetColoured.coloured
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.permission.Permission
import org.bukkit.ChatColor
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ProjectileHitEvent

object HitEvent : Listener {
    val plugin = Main.instance

    @EventHandler
    private fun projectileHitEvent(e: ProjectileHitEvent) {
        val projectile = e.entity //雪玉とか
        if (!projectile.hasMetadata("damage")) return
        if (!projectile.hasMetadata("shooter")) return
        val damage = if (GetMeta.meta(projectile.getMetadata("damage")) != null) {
            GetMeta.meta(projectile.getMetadata("damage"))?.value() as Int
        } else return
        val shooter = if (GetMeta.meta(projectile.getMetadata("shooter")) != null) {
            GetMeta.meta(projectile.getMetadata("shooter"))?.value() as Player
        } else return
        val weaponName = if (GetMeta.meta(projectile.getMetadata("weapon")) != null) {
            GetMeta.meta(projectile.getMetadata("weapon"))?.value() as String
        } else null
        val victim = if (e.hitEntity is LivingEntity) e.hitEntity as LivingEntity
        else return

        victim.noDamageTicks = 0
        victim.damage(damage.toDouble(), shooter)
//        if (victim.isDead)
//            plugin.server.broadcastMessage(
//                coloured(
//                    "${shooter.name}&r --<${weaponName}&r>--> ${(victim as Player).name}"
//                )
//            )
//        }
    }

//    @EventHandler
//    fun snowballHitEvent(e: ProjectileHitEvent) {
//        val snowball = if (e.entity is Snowball) e.entity else return
//        if (!snowball.hasMetadata("damage")) return
//        if (!snowball.hasMetadata("shooter")) return
//        var damageMetadata: MetadataValue? = null
//        for (v in snowball.getMetadata("damage")) {
//            if (v.owningPlugin?.name == plugin.name) damageMetadata = v
//        }
//        var shooterMetadata: MetadataValue? = null
//        for (v in snowball.getMetadata("shooter")) {
//            if (v.owningPlugin?.name.equals(plugin.name)) shooterMetadata = v
//        }
//        if (damageMetadata == null) return
//        if (shooterMetadata == null) return
//
//        val damage = damageMetadata.value() as Int
//        val shooter = shooterMetadata.value() as Player
//
//        val victim = e.hitEntity
//        if (e.hitEntity is LivingEntity) victim as LivingEntity
//        else return
//
//        victim.noDamageTicks = 0
//        victim.damage(damage.toDouble(), shooter)
//    }
}
