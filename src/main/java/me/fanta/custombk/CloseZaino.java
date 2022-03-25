package me.fanta.custombk;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class CloseZaino implements Listener {

    Custombk main;

    public CloseZaino(Custombk main) {
        this.main = main;
    }


    @EventHandler
    public void closeZaino(InventoryCloseEvent e) throws IOException {

        Player p = (Player) e.getPlayer();


        if (e.getView().getTitle().contains("§7Zaino di ")) {


            Inventory inv = e.getInventory();

            if (main.zaini.containsValue(inv)) {
                for (Map.Entry<UUID, Inventory> entry : main.zaini.entrySet()) {
                    if (entry.getValue().equals(inv)) {
                        UUID u = entry.getKey();
                        File f = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), u + ".yml");
                        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);

                        for (int x = 0; x < inv.getSize(); x++) {
                            ItemStack is = inv.getItem(x);

                            if(is == y.getItemStack("BackPackInv." + x)){
                                x++;

                            }


                            if (is != null) {
                                y.set("BackPackInv." + x, is);


                            } else {
                                y.set("BackPackInv." + x, null);
                            }




                        }
                        y.save(f);


                    }


                }


            }





        }





 /*   @EventHandler
    public void closeZaino(InventoryCloseEvent e) throws IOException {


        Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().contains("Zaino")) {




            File file = main.file.get(p.getUniqueId());
            YamlConfiguration yaml = main.yaml.get(p.getUniqueId());


            for (int x = 0; x < 9; x++) {


                if (e.getInventory().getItem(x) != null) {

                    if (e.getInventory().getItem(x).getItemMeta().getDisplayName().contains("§7Zaino")) {
                        ItemStack chest = new ItemStack(Material.CHEST);
                        ItemMeta chestmeta = chest.getItemMeta();
                        chestmeta.setDisplayName("§7Zaino");
                        chest.setItemMeta(chestmeta);
                        p.getInventory().addItem(chest);
                        p.sendMessage("§7Non puoi mettere il tuo zaino nello zaino!");

                    } else {
                        yaml.set("BackPackInv." + x, e.getInventory().getItem(x));
                    }


                } else {
                    yaml.set("BackPackInv." + x, null);
                }


            }

            yaml.save(file);



        }


    }*/
    }
}




