package navy.warspite.minecraft.redshot.event

import navy.warspite.minecraft.redshot.Main
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.metadata.MetadataValue

object HitEvent : Listener {
    @EventHandler
    private fun projectileHitEvent(e: ProjectileHitEvent) {
        val projectile = e.entity
        if (!projectile.hasMetadata("damage")) return
        if (!projectile.hasMetadata("shooter")) return
        val getMetadata = fun(string: String): MetadataValue? {
            for (value in projectile.getMetadata(string)) {
                if (value.owningPlugin?.name == Main.instance.name) return value
            }
            return null
        }

        val damage =
            if (getMetadata("damage") != null) getMetadata("damage")?.value() as Int
            else return
        val shooter =
            if (getMetadata("shooter") != null) getMetadata("shooter")?.value() as Player
            else return
        val victim =
            if (e.hitEntity is LivingEntity) e.hitEntity as LivingEntity
            else return

        victim.noDamageTicks = 0
        victim.damage(damage.toDouble(), shooter)
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
