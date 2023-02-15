package org.mc.aneras.perfectskins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mc.aneras.perfectskins.configs.ItemsToSave;
import org.mc.aneras.perfectskins.configs.Names;

import java.util.*;

public class Events implements Listener {
    private final PerfectSkins plugin;
    private static Map<Player, Inventory> SkinInvHash = new HashMap<>();
    private static Map<Player, Inventory> SwordInvHash = new HashMap<>();
    private static Map<Player, Inventory> BowInvHash = new HashMap<>();
    private static Map<Player, Inventory> PickaxeInvHash = new HashMap<>();
    public Events(PerfectSkins perfectSkins, Map<Player, Inventory>  skinsInv, Map<Player, Inventory> swordInvHash, Map<Player, Inventory> bowInvHash, Map<Player, Inventory> pickaxeInvHash) {
        plugin = perfectSkins;
        SwordInvHash = swordInvHash;
        BowInvHash = bowInvHash;
        PickaxeInvHash = pickaxeInvHash;
        SkinInvHash = skinsInv;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        plugin.data.createPlayer(p);
        if(Names.get().getBoolean("Lobby")) {
            for (ItemStack itemStack : p.getInventory().getContents()) {
                if(itemStack != null) {
                    if (itemStack.getType().equals(Material.DIAMOND_SWORD) || itemStack.getType().equals(Material.IRON_SWORD) || itemStack.getType().equals(Material.STONE_SWORD) || itemStack.getType().equals(Material.WOODEN_SWORD) ||
                            itemStack.getType().equals(Material.BOW) ||
                            itemStack.getType().equals(Material.DIAMOND_PICKAXE) || itemStack.getType().equals(Material.STONE_PICKAXE) || itemStack.getType().equals(Material.IRON_PICKAXE) || itemStack.getType().equals(Material.WOODEN_PICKAXE)) {
                        if (itemStack.getItemMeta().hasCustomModelData()) {
                            p.getInventory().remove(itemStack);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void ClickInv(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getClick().isLeftClick()) {
            if (e.getClickedInventory() == SkinInvHash.get(p)) {
                if (e.getCurrentItem() != null && e.getCurrentItem().equals(e.getInventory().getItem(0))) {
                    if(Names.get().getString("Command").equalsIgnoreCase("Null")){
                     p.closeInventory();
                    }else{
                        p.chat(Names.get().getString("Command"));
                    }
                }else if (e.getCurrentItem() != null && e.getCurrentItem().equals(e.getInventory().getItem(2))) {
                    SwordInvHash.remove(p);
                    plugin.createSwordInv(p);
                    p.openInventory(SwordInvHash.get(p));
                } else if (e.getCurrentItem() != null && e.getCurrentItem().equals(e.getInventory().getItem(4))) {
                    PickaxeInvHash.remove(p);
                    plugin.createPickaxeInv(p);
                    p.openInventory(PickaxeInvHash.get(p));
                } else if (e.getCurrentItem() != null && e.getCurrentItem().equals(e.getInventory().getItem(6))) {
                    BowInvHash.remove(p);
                    plugin.createBowInv(p);
                    p.openInventory(BowInvHash.get(p));
                }
                e.setCancelled(true);
            } else if (e.getInventory() == SwordInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    }else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    }else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    }else if (e.getCurrentItem().equals(e.getInventory().getItem(49))) {
                        plugin.data.changeSwordSkin(p.getUniqueId(), 0);
                        SwordInvHash.remove(p);
                        plugin.createSwordInv(p);
                        p.openInventory(SwordInvHash.get(p));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                    }else {
                        if(e.getCurrentItem().getItemMeta().hasCustomModelData()){
                            int name = e.getCurrentItem().getItemMeta().getCustomModelData();
                            if (p.hasPermission("PerfectSkins." + name)) {
                                plugin.data.changeSwordSkin(p.getUniqueId(), name);
                                SwordInvHash.remove(p);
                                plugin.createSwordInv(p);
                                p.openInventory(SwordInvHash.get(p));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            } else if (e.getInventory() == PickaxeInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    } else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    } else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    }else if (e.getCurrentItem().equals(e.getInventory().getItem(49))) {
                        plugin.data.changePickaxeSkin(p.getUniqueId(), 0);
                        PickaxeInvHash.remove(p);
                        plugin.createPickaxeInv(p);
                        p.openInventory(PickaxeInvHash.get(p));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                    }else {
                        if(e.getCurrentItem().getItemMeta().hasCustomModelData()) {
                            int name = e.getCurrentItem().getItemMeta().getCustomModelData();
                            if (p.hasPermission("PerfectSkins." + name)) {
                                plugin.data.changePickaxeSkin(p.getUniqueId(), name);
                                PickaxeInvHash.remove(p);
                                plugin.createPickaxeInv(p);
                                p.openInventory(PickaxeInvHash.get(p));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            } else if (e.getInventory() == BowInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    } else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    }else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    }else if (e.getCurrentItem().equals(e.getInventory().getItem(49))) {
                        plugin.data.changeBowSkin(p.getUniqueId(), 0);
                        BowInvHash.remove(p);
                        plugin.createBowInv(p);
                        p.openInventory(BowInvHash.get(p));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                    }else {
                        if(e.getCurrentItem().getItemMeta().hasCustomModelData()) {
                            int name = e.getCurrentItem().getItemMeta().getCustomModelData();
                            if (p.hasPermission("PerfectSkins." + name)) {
                                plugin.data.changeBowSkin(p.getUniqueId(), name);
                                BowInvHash.remove(p);
                                plugin.createBowInv(p);
                                p.openInventory(BowInvHash.get(p));
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("SuccessChangedSkin"))));
                            } else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("Don'tHavePermission"))));
                            }
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }else if(e.getClick().isRightClick()){
            if (e.getClickedInventory() == SkinInvHash.get(p)){
                e.setCancelled(true);
            }
            if (e.getInventory() == SwordInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    } else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    }else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    } else {
                        ItemStack item = e.getCurrentItem();
                       p.getInventory().addItem(item);
                       p.closeInventory();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                       Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                           for(ItemStack item_to_check : p.getInventory()){
                               if(item_to_check != null) {
                                   if (item_to_check.equals(item)) {
                                       p.getInventory().remove(item);
                                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                   }
                               }
                           }
                       }, 400);
                    }
                    e.setCancelled(true);
                }
            } else if (e.getInventory() == PickaxeInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    } else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    }else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    } else if (e.getCurrentItem() != null) {
                        ItemStack item = e.getCurrentItem();
                        p.getInventory().addItem(item);
                        p.closeInventory();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                            for(ItemStack item_to_check : p.getInventory()){
                                if(item_to_check != null) {
                                    if (item_to_check.equals(item)) {
                                        p.getInventory().remove(item);
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                    }
                                }
                            }
                        }, 400);
                    }
                    e.setCancelled(true);
                }
            } else if (e.getInventory() == BowInvHash.get(p)) {
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().equals(e.getInventory().getItem(48))) {
                        plugin.createSkinsInv(p);
                        p.openInventory(SkinInvHash.get(p));
                    } else if (e.getCurrentItem().equals(e.getInventory().getItem(1))) {
                        e.setCancelled(true);
                    }else if(e.getCurrentItem().equals(e.getInventory().getItem(4))){
                        e.setCancelled(true);
                    } else if (e.getCurrentItem() != null) {
                        ItemStack item = e.getCurrentItem();
                        p.getInventory().addItem(item);
                        p.closeInventory();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItem"))));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PerfectSkins.getPlugin(PerfectSkins.class), () -> {
                            for(ItemStack item_to_check : p.getInventory()){
                                if(item_to_check != null) {
                                    if (item_to_check.equals(item)) {
                                        p.getInventory().remove(item);
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Names.get().getString("TimeToCHeckItemOut"))));
                                    }
                                }
                            }
                        }, 400);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        if(e.getInventory() == SwordInvHash.get(p)){
            SwordInvHash.remove(p);
        }else if(e.getInventory() == PickaxeInvHash.get(p)){
            PickaxeInvHash.remove(p);
        }else if(e.getInventory() == BowInvHash.get(p)){
            BowInvHash.remove(p);
        }else if(e.getInventory() == SkinInvHash.get(p)){
            SkinInvHash.remove(p);
        }
    }
    @EventHandler
    public void addInv(PlayerItemHeldEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItem(e.getNewSlot());
        if(item != null) {
            if (item.getType() == Material.DIAMOND_SWORD) {
                if(plugin.data.getSwordSkin(p.getUniqueId()) != 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.IRON_SWORD) {
                if(plugin.data.getSwordSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.STONE_SWORD) {
                if(plugin.data.getSwordSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.WOODEN_SWORD) {
                if(plugin.data.getSwordSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            }else if (item.getType() == Material.BOW) {
                if (plugin.data.getBowSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            }else if (item.getType() == Material.WOODEN_PICKAXE) {
                if(plugin.data.getPickaxeSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.STONE_PICKAXE) {
                if(plugin.data.getPickaxeSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.IRON_PICKAXE) {
                if(plugin.data.getPickaxeSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            } else if (item.getType() == Material.DIAMOND_PICKAXE) {
                if(plugin.data.getPickaxeSkin(p.getUniqueId())!= 0) {
                    if (!item.getItemMeta().hasCustomModelData()) {
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setCustomModelData(plugin.data.getSwordSkin(p.getUniqueId()));
                        item.setItemMeta(itemMeta);
                    }
                }
            }
        }
    }
}