package me.fanta.custombk;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class OpenZaino implements Listener {

    Custombk main;

    public OpenZaino(Custombk main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getHand() == EquipmentSlot.HAND){
            if(e.getItem() != null && e.getItem().getItemMeta().getDisplayName().contains("§7Zaino")){
                if(e.getItem().getType().equals(Material.CHEST)){
                    if(!e.getPlayer().hasPermission("backpack.iniziale")){
                        e.getPlayer().sendMessage("§cNon hai il permesso!");
                        return;


                    }


                    Inventory inv = main.zaini.get(e.getPlayer().getUniqueId());

    /*                if(inv.getSize() != main.yaml.get(e.getPlayer().getUniqueId()).getInt("slot")){

                        ItemStack[] itemvecchi = inv.getContents();


                        inv = Bukkit.createInventory(null, main.yaml.get(e.getPlayer().getUniqueId()).getInt("slot"), main.yaml.get(e.getPlayer().getUniqueId()).getString("name"));

                        inv.setContents(itemvecchi);


                        main.zaini.remove(e.getPlayer().getUniqueId());
                        main.zaini.put(e.getPlayer().getUniqueId(), inv);






                    } */



                    Player p = e.getPlayer();
                    p.openInventory(inv);
                }
            }
        }


    }



 /*   @EventHandler
    public void onInvInteract(InventoryClickEvent e){


        System.out.println("Call evento inventoryinteract senza title");

        if(e.getView().getTitle().contains("§7Zaino")){



            Player p = (Player) e.getWhoClicked();
            File file = main.file.get(p.getUniqueId());
            YamlConfiguration yaml = main.yaml.get(p.getUniqueId());
            System.out.println("Call evento inventoryinteract");



            for(int x = 9; x < 9; x++){

                if(e.getInventory().getItem(x) == yaml.getItemStack("BackPackInv." + x)){

                } else {
                    if(e.getInventory().getItem(x) == null){
                        yaml.set("BackPackInv." + x, null);
                    } else {
                        yaml.set("BackPackInv." + x, e.getInventory().getItem(x));
                    }
                }

                try {
                    yaml.save(file);
                } catch (IOException ex) {
                    System.out.println("Errore nel salvare un file, nome file " + file.getName());
                }


            }









        }
        }



            */




    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        ItemStack is = e.getItemInHand();
        if(is.getItemMeta().getDisplayName().equals("§7Zaino")){
            e.setCancelled(true);
        }
    }





}
