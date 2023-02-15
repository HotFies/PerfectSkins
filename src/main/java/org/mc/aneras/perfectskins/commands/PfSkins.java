package org.mc.aneras.perfectskins.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.mc.aneras.perfectskins.PerfectSkins;
import org.mc.aneras.perfectskins.configs.ItemsToSave;
import org.mc.aneras.perfectskins.configs.Names;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PfSkins implements CommandExecutor {
    private final PerfectSkins plugin;
    private static Map<Player, Inventory> SkinInvHash = new HashMap<>();
    private static Map<Player, Inventory> SwordInvHash = new HashMap<>();
    private static Map<Player, Inventory> BowInvHash = new HashMap<>();
    private static Map<Player, Inventory> PickaxeInvHash = new HashMap<>();
    public PfSkins(PerfectSkins perfectSkins, Map<Player, Inventory>  skinsInv, Map<Player, Inventory> swordInvHash, Map<Player, Inventory> bowInvHash, Map<Player, Inventory> pickaxeInvHash) {
        plugin = perfectSkins;
        SwordInvHash = swordInvHash;
        BowInvHash = bowInvHash;
        PickaxeInvHash = pickaxeInvHash;
        SkinInvHash = skinsInv;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(command.getName().equalsIgnoreCase("pfskins")){
            if(Names.get().getBoolean("Lobby")) {
                if (args.length == 0) {
                    SkinInvHash.remove(p);
                    plugin.createSkinsInv(p);
                    p.openInventory(SkinInvHash.get(p));
                    return true;
                }
                switch (args[0]) {
                    case "view":
                        if(p.hasPermission("PerfectSkins.View")){
                            switch (args[1]) {
                                case "sword":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins view sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    List<ItemStack> Swords = (List<ItemStack>) ItemsToSave.get().getList("Swords");
                                    for(ItemStack check : Swords){
                                        if(check.getItemMeta().getDisplayName().equalsIgnoreCase(args[2])){
                                            p.closeInventory();
                                            p.getInventory().addItem(check);
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                                                for(ItemStack item_to_check : p.getInventory()){
                                                    if(item_to_check != null) {
                                                        if (item_to_check.equals(check)) {
                                                            p.getInventory().remove(check);
                                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                                        }
                                                    }
                                                }
                                            }, 400);
                                        }
                                    }
                                    return true;
                                case "pickaxe":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins view sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    List<ItemStack> Pickaxes = (List<ItemStack>) ItemsToSave.get().getList("Pickaxes");
                                    for(ItemStack check : Pickaxes){
                                        if(check.getItemMeta().getDisplayName().equalsIgnoreCase(args[2])){
                                            p.closeInventory();
                                            p.getInventory().addItem(check);
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                                                for(ItemStack item_to_check : p.getInventory()){
                                                    if(item_to_check != null) {
                                                        if (item_to_check.equals(check)) {
                                                            p.getInventory().remove(check);
                                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                                        }
                                                    }
                                                }
                                            }, 400);
                                        }
                                    }
                                    return true;
                                case "bow":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins change sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    List<ItemStack> Bows = (List<ItemStack>) ItemsToSave.get().getList("Bows");
                                    for(ItemStack check : Bows){
                                        if(check.getItemMeta().getDisplayName().equalsIgnoreCase(args[2])){
                                            p.closeInventory();
                                            p.getInventory().addItem(check);
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                                                for(ItemStack item_to_check : p.getInventory()){
                                                    if(item_to_check != null) {
                                                        if (item_to_check.equals(check)) {
                                                            p.getInventory().remove(check);
                                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                                        }
                                                    }
                                                }
                                            }, 400);
                                        }
                                    }
                                    return true;
                                default: {
                                    p.sendMessage(ChatColor.RED + "Unexpected value: " + args[1]);
                                    return true;
                                }
                            }

                        }
                    case "change":
                        if(args.length == 1){
                            p.sendMessage(ChatColor.GOLD + "/pfskins change sword/pickaxe/bow %name%");
                            return true;
                        }
                        if(p.hasPermission("PerfectSkins.ChangeSkin")) {
                            switch (args[1]) {
                                case "sword":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins change sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    if (p.hasPermission("PerfectSkins." + args[2])) {
                                        plugin.data.changeSwordSkin(p.getUniqueId(), Integer.parseInt(args[2]));
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                                    }
                                    return true;
                                case "pickaxe":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins change sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    if (p.hasPermission("PerfectSkins." + args[2])) {
                                        plugin.data.changePickaxeSkin(p.getUniqueId(), Integer.parseInt(args[2]));
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                                    }
                                    return true;
                                case "bow":
                                    if(args.length == 2){
                                        p.sendMessage(ChatColor.GOLD + "/pfskins change sword/pickaxe/bow %name%");
                                        return true;
                                    }
                                    if (p.hasPermission("PerfectSkins." + args[2])) {
                                        plugin.data.changeBowSkin(p.getUniqueId(), Integer.parseInt(args[2]));
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                                    }
                                    return true;
                                default: {
                                    p.sendMessage(ChatColor.RED + "Unexpected value: " + args[1]);
                                    return true;
                                }
                            }
                        }
                    case "swords":
                        if (p.hasPermission("PerfectSkins.SwordInventory")) {
                            SwordInvHash.remove(p);
                            plugin.createSwordInv(p);
                            p.openInventory(SwordInvHash.get(p));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                        }
                        return true;
                    case "pickaxes":
                        if (p.hasPermission("PerfectSkins.PickaxeInventory")) {
                            PickaxeInvHash.remove(p);
                            plugin.createPickaxeInv(p);
                            p.openInventory(PickaxeInvHash.get(p));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                        }
                        return true;
                    case "bows":
                        if (p.hasPermission("PerfectSkins.BowInventory")) {
                            BowInvHash.remove(p);
                            plugin.createBowInv(p);
                            p.openInventory(BowInvHash.get(p));
                        }else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                        }
                        return true;
                    case "add":
                        if (p.hasPermission("PerfectSkins.Admin")) {
                            if (args.length == 1) {
                                p.sendMessage(ChatColor.GOLD + "/pfskins add sword %name%");
                                p.sendMessage(ChatColor.GOLD + "/pfskins add pickaxe %name%");
                                p.sendMessage(ChatColor.GOLD + "/pfskins add bow %name%");
                                return true;
                            }
                            switch (args[1]) {
                                case "sword": {
                                    if (args.length == 2) {
                                        p.sendMessage(ChatColor.GOLD + "/pfskins add sword %name%");
                                        return true;
                                    }
                                    plugin.createSwordInv(p);
                                    List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Swords");
                                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                                    ItemMeta swordMeta = sword.getItemMeta();
                                    swordMeta.setCustomModelData(Integer.parseInt(args[2]));
                                    sword.setItemMeta(swordMeta);
                                    if (Objects.requireNonNull(Items).contains(sword)) {
                                        p.sendMessage(ChatColor.RED + "This item already exist");
                                        return true;
                                    }
                                    Items.add(sword);
                                    ItemsToSave.get().set("Swords", Items);
                                    ItemsToSave.save();
                                    ItemsToSave.reload();
                                    SwordInvHash.get(p).addItem(sword);
                                    p.sendMessage(ChatColor.GREEN + "Item successful added");
                                    SwordInvHash.remove(p);
                                    return true;
                                }
                                case "bow": {
                                    if (args.length == 2) {
                                        p.sendMessage(ChatColor.GOLD + "/pfskins add bow %name%");
                                        return true;
                                    }
                                    plugin.createBowInv(p);
                                    List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Bows");
                                    ItemStack bow = new ItemStack(Material.BOW);
                                    ItemMeta bowMeta = bow.getItemMeta();
                                    bowMeta.setCustomModelData(Integer.parseInt(args[2]));
                                    bow.setItemMeta(bowMeta);
                                    if (Objects.requireNonNull(Items).contains(bow)) {
                                        p.sendMessage(ChatColor.RED + "This item already exist");
                                        return true;
                                    }
                                    Items.add(bow);
                                    ItemsToSave.get().set("Bows", Items);
                                    ItemsToSave.save();
                                    ItemsToSave.reload();
                                    BowInvHash.get(p).addItem(bow);
                                    p.sendMessage(ChatColor.GREEN + "Item successful added");
                                    BowInvHash.remove(p);
                                    return true;
                                }
                                case "pickaxe": {
                                    if (args.length == 2) {
                                        p.sendMessage(ChatColor.GOLD + "/pfskins add pickaxe %name%");
                                        return true;
                                    }
                                    plugin.createPickaxeInv(p);
                                    List<ItemStack> Items = (List<ItemStack>) ItemsToSave.get().getList("Pickaxes");
                                    ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                                    ItemMeta pickaxeMeta = pickaxe.getItemMeta();
                                    pickaxeMeta.setCustomModelData(Integer.parseInt(args[2]));
                                    pickaxe.setItemMeta(pickaxeMeta);
                                    if (Objects.requireNonNull(Items).contains(pickaxe)) {
                                        p.sendMessage(ChatColor.RED + "This item already exist");
                                        return true;
                                    }
                                    Items.add(pickaxe);
                                    ItemsToSave.get().set("Pickaxes", Items);
                                    ItemsToSave.save();
                                    ItemsToSave.reload();
                                    PickaxeInvHash.get(p).addItem(pickaxe);
                                    p.sendMessage(ChatColor.GREEN + "Item successful added");
                                    PickaxeInvHash.remove(p);
                                    return true;
                                }
                                default: {
                                    p.sendMessage(ChatColor.RED + "Unexpected value: " + args[1]);
                                    return true;
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                        }
                    default: {
                        p.sendMessage(ChatColor.RED + "Unexpected value: " + args[0]);
                        return true;
                    }
                }
            }else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("LobbyFalse"))));
            }
        }
        return true;
    }
}
