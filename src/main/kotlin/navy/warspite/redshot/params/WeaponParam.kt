package navy.warspite.redshot.params

import kotlinx.serialization.Serializable

object WeaponParam {
    @Serializable
    data class Weapon(
        val detail: Detail,
        val shooting: Shooting,
        val sneak: Sneak? = null,
        val burstFire: BurstFire? = null,
        val reload: Reload,
        val firearmAction: FirearmAction,
        val scope: Scope? = null,
        val headShot: HeadShot? = null,
        val particleEffects: ParticleEffects
    )

    @Serializable
    data class Detail(
        val id: String,
        val name: String,
        val type: String,
        val lore: List<String> = listOf(),
        val inventoryControl: List<String> = listOf(),
        val acquiredSounds: List<String> = listOf()
    )

    @Serializable
    data class Shooting(
        val delayBetweenShots: Int = 4,
        val projectileAmount: Int = 1,
        val projectileSpeed: Int = 20,
        val projectileDamage: Int = 0,
        val projectileType: String = "snowball",
        val bulletSpread: Double = 0.0,
        val recoil: Boolean = true,
        val recoilX: Double = 0.0,
        val recoilY: Double = 0.0,
        val shootSounds: List<String> = listOf(),
    )

    @Serializable
    data class Sneak(
        val recoil: Boolean = true,
        val recoilX: Double = 0.0,
        val recoilY: Double = 0.0,
        val bulletSpread: Double = 0.0
    )

    @Serializable
    data class BurstFire(
        val shotsPerBurst: Int,
        val delayBetweenShotsInBurst: Int,
    )

    @Serializable
    data class Reload(
        val reloadAmount: Int = 0,
        val reloadDuration: Int = 0,
        val outOfAmmoSounds: List<String> = listOf(),
        val reloadingSounds: List<String> = listOf()
    )

    @Serializable
    data class FirearmAction(
        val type: String = "slide",
        val closeDuration: Int = 0,
        val closeShootDelay: Int = 0,
        val closeSounds: List<String> = listOf()
    )

    @Serializable
    data class Scope(
        val sight: Boolean = false,
        val nightVision: Boolean = false,
        val zoomAmount: Int = 0,
        val bulletSpread: Double = 0.0,
        val toggleZoomSounds: List<String> = listOf()
    )

    @Serializable
    data class HeadShot(
        val damage: Int = 0,
        val messageToShooter: String? = null,
        val messageToVictim: String? = null,
        val soundToShooter: List<String> = listOf(),
        val soundToVictim: List<String> = listOf()
    )

    @Serializable
    data class ParticleEffects(
        val terrain: Boolean = true
    )
}