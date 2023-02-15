package org.mc.aneras.perfectskins.configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemsToSave {
    private static File file;
    private static FileConfiguration SavePlayer;
    public static void setup(){

        file = new File(Bukkit.getPluginManager().getPlugin("PerfectSkins").getDataFolder() + File.separator + "Configs", "ItemsToSave.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){

            }
        }
        SavePlayer = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get(){
        return SavePlayer;
    }
    public static void save(){
        try {
            SavePlayer.save(file);
        }catch (IOException e){
            System.out.println("File gg");
        }
    }

    public static void reload() {
        SavePlayer = YamlConfiguration.loadConfiguration(file);
    }

}
