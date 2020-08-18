package navy.warspite.minecraft.redshot;

import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object LoadWeapons {
    private val plugin = Main.instance
    val weaponsHashMap = linkedMapOf<String, Any>()

    fun generateMap() {
        val weaponsYaml = loadWeapon()
        weaponsYaml.forEach { (k, v) ->
            val data = linkedMapOf<String, Any>()
            v as LinkedHashMap<*, *>
            v.forEach { data["${it.key}"] = it.value }
            weaponsHashMap[k] = data
        }
        plugin.logger.info("Loaded weapons: ${weaponsHashMap.keys}")
    }

    private fun loadWeapon(): LinkedHashMap<String, Any> {
        val weaponsYaml = linkedMapOf<String, Any>()
        val weaponFiles = loadWeaponFiles()
        weaponFiles.forEach {
            val file = Files.newInputStream(it)
            val yaml: LinkedHashMap<String, Any> = Yaml().load(file)
            weaponsYaml.putAll(yaml)
        }
        return weaponsYaml
    }

    private fun loadWeaponFiles(): ArrayList<Path> {
        val dir = Paths.get("./plugins/RedShot/Weapons")
        val fileExtension = ".yml"
        if (!Files.isDirectory(dir)) Files.createDirectory(dir)
        if (Files.notExists(dir)) Files.createDirectory(dir)

        val weaponFiles = arrayListOf<Path>()

        Files.list(dir).forEach {
            if ("$it".endsWith(fileExtension)) weaponFiles.add(it)
        }
        return weaponFiles
    }
}
