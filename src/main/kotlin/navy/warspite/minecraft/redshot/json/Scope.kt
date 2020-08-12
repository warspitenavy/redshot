package navy.warspite.minecraft.redshot.json

data class Scope (
    val enable: Boolean? = true,
    val zoomAmount: Int? = null,
    val bulletSpread: Double? = null,
    val toggleZoomSounds: ArrayList<String>? = null
)