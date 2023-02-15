package org.mc.aneras.perfectskins.MySQL;

import org.bukkit.entity.Player;
import org.mc.aneras.perfectskins.PerfectSkins;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UseMySQL {
    private final PerfectSkins plugin;
    public UseMySQL(PerfectSkins plugin){
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS skins "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),SWORD INT(100),BOWS INT(100),PICKAXES INT(100), PRIMARY KEY (NAME))");
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player){
        try {
            UUID uuid = player.getUniqueId();
            if(!exists(uuid)){
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO skins (NAME,UUID,SWORD,BOWS,PICKAXES) VALUES (?,?,?,?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.setInt(3, 0);
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.executeUpdate();
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean exists(UUID uuid){
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT * FROM skins WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            if(results.next()){
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void changeSwordSkin(UUID uuid, int name){
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("UPDATE skins SET SWORD=? WHERE UUID=?");
            preparedStatement.setInt(1, name);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getSwordSkin(UUID uuid){
        try{
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT SWORD FROM skins WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            int skin;
            if(results.next()){
                skin = results.getInt("SWORD");
                return skin;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void changePickaxeSkin(UUID uuid, int name){
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("UPDATE skins SET PICKAXES=? WHERE UUID=?");
            preparedStatement.setInt(1, name);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getPickaxeSkin(UUID uuid){
        try{
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT PICKAXES FROM skins WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            int Tag;
            if(results.next()){
                Tag = results.getInt("PICKAXES");
                return Tag;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void changeBowSkin(UUID uuid, int name){
        try {
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("UPDATE skins SET BOWS=? WHERE UUID=?");
            preparedStatement.setInt(1, name);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getBowSkin(UUID uuid){
        try{
            PreparedStatement preparedStatement = plugin.SQL.getConnection().prepareStatement("SELECT BOWS FROM skins WHERE UUID=?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            int Tag;
            if(results.next()){
                Tag = results.getInt("BOWS");
                return Tag;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}