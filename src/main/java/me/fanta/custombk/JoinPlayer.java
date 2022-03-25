package me.fanta.custombk;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class JoinPlayer implements Listener {
    Custombk main;

    public JoinPlayer(Custombk main) {
    this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {

        main.createZaino(e.getPlayer());




    }

//Logica: Al quit salvare tutti gli item, salvare tutti item anche allo spegnimento del pl!
//Fare comando upgrade? Parlare con mrmarco
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();


        if(main.zaini.containsKey(p.getUniqueId())){
            Inventory inv = main.zaini.get(p.getUniqueId());
            YamlConfiguration yaml = main.yaml.get(p.getUniqueId());

            File file = main.file.get(p.getUniqueId());

            for(int x = 0; x < yaml.getInt("slot"); x++){



                ItemStack is = inv.getItem(x);

                if(is != null){
                    yaml.set("BackPackInv." + x, is);

                } else {
                    yaml.set("BackPackInv." + x, null);
                }




            }
            try {
                yaml.save(file);
            } catch (IOException ex) {
                System.out.println("IOException, errore nel saving di un file (PlayerQuitEvent)");
            }


        }



    }



}
