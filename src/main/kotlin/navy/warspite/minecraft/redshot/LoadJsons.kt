package navy.warspite.minecraft.redshot

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import navy.warspite.minecraft.redshot.parse.Parse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object LoadJsons {
//    private val plugin = Main.instance

    val weaponJson = arrayListOf<Parse.Weapon>()

    fun generateWeapon() {
        weaponJson.clear()
        val jsons = loadWeapon()
        weaponJson.addAll(jsons)

        val weaponsName = arrayListOf<String>()
        for (json in jsons) { weaponsName.add(json.id) }
        Main.instance.logger.info("Loaded Weapons: $weaponsName")
    }

    private fun loadWeapon(): ArrayList<Parse.Weapon> {
        val jsons = arrayListOf<Parse.Weapon>()
        for (f in loadWeaponFiles()) {
            val file = f.toFile().readText()
            val data = Json.decodeFromString<List<Parse.Weapon>>(file)
            for (d in data) { jsons.add(d) }
        }
        return jsons
    }

    private fun loadWeaponFiles(): ArrayList<Path> {
            val dir = Paths.get("./plugins/RedShot/Weapons")
            val fileExtension = ".json"
            if (!Files.isDirectory(dir)) Files.createDirectory(dir)
            if (Files.notExists(dir)) Files.createDirectory(dir)

            val weaponFiles = arrayListOf<Path>()

            Files.list(dir).forEach {
                if ("$it".endsWith(fileExtension)) weaponFiles.add(it)
            }
            return weaponFiles
        }
}