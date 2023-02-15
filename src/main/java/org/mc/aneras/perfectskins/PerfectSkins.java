package org.mc.aneras.perfectskins;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.mc.aneras.perfectskins.MySQL.MySQL;
import org.mc.aneras.perfectskins.MySQL.UseMySQL;
import org.mc.aneras.perfectskins.commands.PfSkins;
import org.mc.aneras.perfectskins.configs.ItemsToSave;
import org.mc.aneras.perfectskins.configs.Names;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

public final class PerfectSkins extends JavaPlugin {
    public MySQL SQL;
    public UseMySQL data;
    private static final Map<Player, Inventory> SkinsInvHash = new HashMap<>();
    private static final Map<Player, Inventory> SwordInvHash = new HashMap<>();
    private static final Map<Player, Inventory> BowInvHash = new HashMap<>();
    private static final Map<Player, Inventory> PickaxeInvHash = new HashMap<>();
    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning(ChatColor.RED + "The PlaceholderAPI wasn't found. The PerfectSkins may not work correctly");
        }
        ItemsToSave.setup();
        List<ItemStack> Swords = new ArrayList<>();
        List<ItemStack> Bows = new ArrayList<>();
        List<ItemStack> Pickaxes = new ArrayList<>();
        ItemsToSave.get().addDefault("Swords", Swords);
        ItemsToSave.get().addDefault("Bows", Bows);
        ItemsToSave.get().addDefault("Pickaxes", Pickaxes);
        ItemsToSave.get().options().copyDefaults(true);
        ItemsToSave.save();
        Names.setup();
        Names.get().addDefault("Storage", "MySQL");
        Names.get().addDefault("host", "localhost");
        Names.get().addDefault("port", "3306");
        Names.get().addDefault("database", "PerfectTable");
        Names.get().addDefault("username", "Default");
        Names.get().addDefault("password", "Default");
        Names.get().addDefault("NameOfInventory", "Skins");
        Names.get().addDefault("NameOfSwordInventory", "Swords");
        Names.get().addDefault("NameOfBowInventory", "Bows");
        Names.get().addDefault("NameOfPickaxeInventory", "Pickaxes");
        Names.get().addDefault("Name-of-sword", "Sword skins");
        Names.get().addDefault("Name-of-bow", "Bow skins");
        Names.get().addDefault("Name-of-pickaxes", "Pickaxe skins");
        Names.get().addDefault("Default", "Default");
        Names.get().addDefault("SuccessChangedSkin","You successful changed the skin");
        Names.get().addDefault("Don'tHavePermission", "You don't have permission");
        Names.get().addDefault("TimeToCHeckItem", "You have 20 seconds to inspect this item");
        Names.get().addDefault("TimeToCHeckItemOut", "Time out");
        Names.get().addDefault("Lore1", "LMB to apply the skin");
        Names.get().addDefault("Lore2", "RMB to see skin");
        Names.get().addDefault("Select", "Select");
        Names.get().addDefault("Lobby", true);
        Names.get().addDefault("LobbyFalse", "You can't change skin at the moment");
        Names.get().addDefault("SkullURL", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzMDFhMTdjOTU1ODA3ZDg5ZjljNzJhMTkyMDdkMTM5M2I4YzU4YzRlNmU0MjBmNzE0ZjY5NmE4N2ZkZCJ9fX0=");
        Names.get().addDefault("Name-of-skull", "Back");
        Names.get().addDefault("Command", "Null");
        Names.get().addDefault("Back", "Back");
        Names.get().options().copyDefaults(true);
        Names.save();
        this.SQL = new MySQL();
        this.data = new UseMySQL(this);
        if(Objects.requireNonNull(Names.get().get("Storage")).equals("MySQL")) {
            try {
                SQL.Connect();
            } catch (ClassNotFoundException | SQLException e) {
                //e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("[PerfectSkins] " + ChatColor.RED + "Database is not connected");
            }
            if (SQL.isConnected()) {
                Bukkit.getLogger().info(ChatColor.AQUA + "[PerfectSkins] " + ChatColor.GREEN + "Database is connected");
                data.createTable();

            }
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("pfskins")).setExecutor(new PfSkins(this, SkinsInvHash, SwordInvHash, BowInvHash, PickaxeInvHash));
        Bukkit.getPluginManager().registerEvents(new Events(this, SkinsInvHash, SwordInvHash, BowInvHash, PickaxeInvHash), this);
        Bukkit.getConsoleSender().sendMessage("[PerfectSkins] Plugin was enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[PerfectSkins] Plugin was disabled");
    }
    public void createSkinsInv(Player p){
        Inventory SkinInv = Bukkit.createInventory(null, 9, Objects.requireNonNull(Names.get().getString("NameOfInventory")));
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        ItemStack head = getSkull(Names.get().getString("SkullURL"));
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Name-of-skull"))));
        head.setItemMeta(headMeta);
        swordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Name-of-sword"))));
        sword.setItemMeta(swordMeta);
        bowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Name-of-bow"))));
        bow.setItemMeta(bowMeta);
        pickaxeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Name-of-pickaxes"))));
        pickaxe.setItemMeta(pickaxeMeta);

        SkinInv.setItem(0, head);
        SkinInv.setItem(2, sword);
        SkinInv.setItem(4, pickaxe);
        SkinInv.setItem(6, bow);
        SkinsInvHash.put(p, SkinInv);
    }
    public static ItemStack getSkull(String value){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;

    }
    public void createSwordInv(Player p){
        Inventory SwordInv = Bukkit.createInventory(null, 54, Objects.requireNonNull(Names.get().getString("NameOfSwordInventory")));
        ItemStack empty = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemStack Default = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta DefaultMeta = Default.getItemMeta();
        DefaultMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Default"))));
        Default.setItemMeta(DefaultMeta);


        ItemStack back = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzMDFhMTdjOTU1ODA3ZDg5ZjljNzJhMTkyMDdkMTM5M2I4YzU4YzRlNmU0MjBmNzE0ZjY5NmE4N2ZkZCJ9fX0=");
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Back"))));
        back.setItemMeta(backMeta);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        empty.setItemMeta(emptyMeta);
        ArrayList<String> addTagLore = new ArrayList<>();
        ArrayList<String> chooseSkin = new ArrayList<>();
        List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Swords");
        SwordInv.setItem(0, empty);
        SwordInv.setItem(1, empty);
        SwordInv.setItem(2, empty);
        SwordInv.setItem(3, empty);
        SwordInv.setItem(4, Default);
        SwordInv.setItem(5, empty);
        SwordInv.setItem(6, empty);
        SwordInv.setItem(7, empty);
        SwordInv.setItem(8, empty);
        SwordInv.setItem(9, empty);
        SwordInv.setItem(17, empty);
        SwordInv.setItem(18, empty);
        SwordInv.setItem(26, empty);
        SwordInv.setItem(27, empty);
        SwordInv.setItem(35, empty);
        SwordInv.setItem(36, empty);
        SwordInv.setItem(44, empty);
        SwordInv.setItem(45, empty);
        SwordInv.setItem(46, empty);
        SwordInv.setItem(47, empty);
        SwordInv.setItem(48, back);
        SwordInv.setItem(49, Default);
        SwordInv.setItem(50, empty);
        SwordInv.setItem(51, empty);
        SwordInv.setItem(52, empty);
        SwordInv.setItem(53, empty);
        for (ItemStack addItem : Objects.requireNonNull(Items)) {
            if (p.hasPermission("PerfectSkins." + addItem.getItemMeta().getDisplayName())) {
                if(addItem.getItemMeta().hasCustomModelData()) {
                    addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore1"))));
                    addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore2"))));
                    if (data.getSwordSkin(p.getUniqueId()) == addItem.getItemMeta().getCustomModelData()) {
                        chooseSkin.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Select"))));
                        addItem.setLore(chooseSkin);
                        SwordInv.setItem(4, addItem);
                        Objects.requireNonNull(addItem.getLore()).clear();
                    }
                    addItem.setLore(addTagLore);
                    SwordInv.addItem(addItem);
                    addTagLore.clear();
                }
            }
        }
        SwordInvHash.put(p, SwordInv);
        }
        public void createPickaxeInv(Player p){
            Inventory PickaxeInv = Bukkit.createInventory(null, 54, Objects.requireNonNull(Names.get().getString("NameOfPickaxeInventory")));
            ItemStack empty = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            ItemMeta emptyMeta = empty.getItemMeta();
            ItemStack Default = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta DefaultMeta = Default.getItemMeta();
            DefaultMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Default"))));
            Default.setItemMeta(DefaultMeta);
            ItemStack back = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzMDFhMTdjOTU1ODA3ZDg5ZjljNzJhMTkyMDdkMTM5M2I4YzU4YzRlNmU0MjBmNzE0ZjY5NmE4N2ZkZCJ9fX0=");
            ItemMeta backMeta = back.getItemMeta();
            backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Back"))));
            back.setItemMeta(backMeta);
            emptyMeta.setDisplayName(" ");
            empty.setItemMeta(emptyMeta);
            ArrayList<String> addTagLore = new ArrayList<>();
            ArrayList<String> chooseSkin = new ArrayList<>();
            List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Pickaxes");
            PickaxeInv.setItem(0, empty);
            PickaxeInv.setItem(1, empty);
            PickaxeInv.setItem(2, empty);
            PickaxeInv.setItem(3, empty);
            PickaxeInv.setItem(4, Default);
            PickaxeInv.setItem(5, empty);
            PickaxeInv.setItem(6, empty);
            PickaxeInv.setItem(7, empty);
            PickaxeInv.setItem(8, empty);
            PickaxeInv.setItem(9, empty);
            PickaxeInv.setItem(17, empty);
            PickaxeInv.setItem(18, empty);
            PickaxeInv.setItem(26, empty);
            PickaxeInv.setItem(27, empty);
            PickaxeInv.setItem(35, empty);
            PickaxeInv.setItem(36, empty);
            PickaxeInv.setItem(44, empty);
            PickaxeInv.setItem(45, empty);
            PickaxeInv.setItem(46, empty);
            PickaxeInv.setItem(47, empty);
            PickaxeInv.setItem(48, back);
            PickaxeInv.setItem(49, Default);
            PickaxeInv.setItem(50, empty);
            PickaxeInv.setItem(51, empty);
            PickaxeInv.setItem(52, empty);
            PickaxeInv.setItem(53, empty);

            for (ItemStack addItem : Objects.requireNonNull(Items)) {
                if(addItem.getItemMeta().hasCustomModelData()) {
                    if (p.hasPermission("PerfectSkins." + addItem.getItemMeta().getDisplayName())) {
                        addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore1"))));
                        addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore2"))));
                        if (data.getPickaxeSkin(p.getUniqueId()) == addItem.getItemMeta().getCustomModelData()) {
                            chooseSkin.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Select"))));
                            addItem.setLore(chooseSkin);
                            PickaxeInv.setItem(4, addItem);
                            Objects.requireNonNull(addItem.getLore()).clear();
                        }
                        addItem.setLore(addTagLore);
                        PickaxeInv.addItem(addItem);
                        addTagLore.clear();
                    }
                }
            }

            PickaxeInvHash.put(p, PickaxeInv);
        }
        public void createBowInv(Player p){
            Inventory BowInv = Bukkit.createInventory(null, 54, Objects.requireNonNull(Names.get().getString("NameOfBowInventory")));
            ItemStack empty = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            ItemMeta emptyMeta = empty.getItemMeta();
            ItemStack Default = new ItemStack(Material.BOW);
            ItemMeta DefaultMeta = Default.getItemMeta();
            DefaultMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Default"))));
            Default.setItemMeta(DefaultMeta);
            ItemStack back = getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzMDFhMTdjOTU1ODA3ZDg5ZjljNzJhMTkyMDdkMTM5M2I4YzU4YzRlNmU0MjBmNzE0ZjY5NmE4N2ZkZCJ9fX0=");
            ItemMeta backMeta = back.getItemMeta();
            backMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Back"))));
            back.setItemMeta(backMeta);
            emptyMeta.setDisplayName(" ");
            empty.setItemMeta(emptyMeta);
            ArrayList<String> addTagLore = new ArrayList<>();
            ArrayList<String> chooseSkin = new ArrayList<>();
            List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Bows");

            BowInv.setItem(0, empty);
            BowInv.setItem(1, empty);
            BowInv.setItem(2, empty);
            BowInv.setItem(3, empty);
            BowInv.setItem(4, Default);
            BowInv.setItem(5, empty);
            BowInv.setItem(6, empty);
            BowInv.setItem(7, empty);
            BowInv.setItem(8, empty);
            BowInv.setItem(9, empty);
            BowInv.setItem(17, empty);
            BowInv.setItem(18, empty);
            BowInv.setItem(26, empty);
            BowInv.setItem(27, empty);
            BowInv.setItem(35, empty);
            BowInv.setItem(36, empty);
            BowInv.setItem(44, empty);
            BowInv.setItem(45, empty);
            BowInv.setItem(46, empty);
            BowInv.setItem(47, empty);

            BowInv.setItem(49, Default);
            BowInv.setItem(50, empty);
            BowInv.setItem(51, empty);
            BowInv.setItem(52, empty);
            BowInv.setItem(53, empty);

            BowInv.setItem(48, back);
            for (ItemStack addItem : Objects.requireNonNull(Items)) {
                if(addItem.getItemMeta().hasCustomModelData()) {
                    if (p.hasPermission("PerfectSkins." + addItem.getItemMeta().getDisplayName())) {
                        addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore1"))));
                        addTagLore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Lore2"))));
                        if (data.getBowSkin(p.getUniqueId()) == addItem.getItemMeta().getCustomModelData()) {
                            chooseSkin.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Select"))));
                            addItem.setLore(chooseSkin);
                            BowInv.setItem(4, addItem);
                            Objects.requireNonNull(addItem.getLore()).clear();
                        }
                        addItem.setLore(addTagLore);
                        BowInv.addItem(addItem);
                        addTagLore.clear();
                    }
                }
            }
            BowInvHash.put(p, BowInv);
        }
}
