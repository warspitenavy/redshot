package navy.warspite.minecraft.redshot;

import org.bukkit.plugin.java.JavaPlugin
import org.omg.CORBA.Object
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class LoadWeapons(private val plugin: JavaPlugin) {
    val weaponYaml = linkedMapOf<String, Object>()
    fun loadWeapon() {
        val weaponFiles = loadWeaponFiles()
        if (weaponFiles.isEmpty()) return
        weaponFiles.forEach {
            val file = Files.newInputStream(it)
            val yaml: LinkedHashMap<String, Object> = Yaml().load(file)
            weaponYaml.putAll(yaml)
        }
        plugin.logger.info("Loaded weapons data: ${weaponYaml.keys}")
    }

    private fun loadWeaponFiles(): ArrayList<Path> {
        val dir = Paths.get("./plugins/${plugin.name}/Weapons")
        val fileExtension = ".yml"
        if (!Files.isDirectory(dir)) Files.createDirectory(dir)
        if (Files.notExists(dir)) Files.createDirectory(dir)

        val weaponFiles = arrayListOf<Path>()

        Files.list(dir).forEach {
            if ("$it".endsWith(fileExtension)) weaponFiles.add(it)
        }
        plugin.logger.info("Loaded Yaml: ${weaponFiles.map { it.fileName }}")

        return weaponFiles
    }
}
