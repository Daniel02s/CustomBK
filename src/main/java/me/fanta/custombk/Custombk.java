package me.fanta.custombk;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Custombk extends JavaPlugin {





    HashMap<UUID, File> file = new HashMap<>();
    HashMap<UUID, YamlConfiguration> yaml = new HashMap<>();
    HashMap<UUID, Inventory> zaini = new HashMap<>();






    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new CloseZaino(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinPlayer(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OpenZaino(this), this);
        getCommand("backpack").setExecutor(new ZainoCmd(this));





   /*     File files = Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder();
        File[] filesx = files.listFiles();
        for(File s : filesx){
            String nome = s.getName();
            System.out.println(nome);
            String[] nomefix = nome.split(".");

            File inventario = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), nome + ".yml");
            YamlConfiguration inventariotaml = YamlConfiguration.loadConfiguration(inventario);

            file.put(UUID.fromString(nomefix[0]), inventario);
            yaml.put(UUID.fromString(nomefix[0]), inventariotaml);


        }*/





    }

    @Override
    public void onDisable() {

    }

    public YamlConfiguration getYaml(File file){


            YamlConfiguration inventariotaml = YamlConfiguration.loadConfiguration(file);

            return inventariotaml;




        }



    public File getFile(UUID u){

        File files = Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder();
        File[] filesx = files.listFiles();

        for(File s : filesx){
            String nome = s.getName();
            System.out.println(nome);
            String[] nomefix = nome.split(".");

            File inventario = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), nome + ".yml");
           // YamlConfiguration inventariotaml = YamlConfiguration.loadConfiguration(inventario);

            return inventario;




        }
        return null;

    }


    public void createZaino(Player p) throws IOException {


        File inventario = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), p.getUniqueId() + ".yml");

        if(!inventario.exists())
            inventario.createNewFile();

        YamlConfiguration inventariotaml = YamlConfiguration.loadConfiguration(inventario);


        if(!file.containsKey(p.getUniqueId())){
            file.put(p.getUniqueId(), inventario);
            yaml.put(p.getUniqueId(), inventariotaml);

            if(!inventariotaml.contains("slot")){
                inventariotaml.set("slot", 9);
                inventariotaml.save(inventario);
            }


            if(!inventariotaml.contains("name")){
                inventariotaml.set("name", "§7Zaino di " + p.getName() +" - §eLiv 1");
                inventariotaml.save(inventario);
            }



            if(!zaini.containsKey(p.getUniqueId())){






                    Inventory inv = Bukkit.createInventory(null, inventariotaml.getInt("slot"), inventariotaml.getString("name"));
                    zaini.put(p.getUniqueId(), inv);





                for(int x = 0; x < inventariotaml.getInt("slot"); x++){
                    if(inventariotaml.get("BackPackInv." + x) != null){
                        ItemStack i = inventariotaml.getItemStack("BackPackInv." + x);
                        zaini.get(p.getUniqueId()).setItem(x, i);
                    }
                }



            }


            if(zaini.containsKey(p.getUniqueId()) && inventariotaml.getInt("slot") != zaini.get(p.getUniqueId()).getSize()){


                for(int x = 0; x < inventariotaml.getInt("slot"); x++){

                    ItemStack i = zaini.get(p.getUniqueId()).getItem(x);

                    if(i != null){
                        inventariotaml.set("BackPackInv." + x, i);

                    } else {
                        inventariotaml.set("BackPackInv." + x, null);
                    }






                }


                inventariotaml.save(inventario);

            }




        }





    }




   /* public void openZaino(Player p){

        File pfile = file.get(p.getUniqueId());
        YamlConfiguration yamlfile = yaml.get(p.getUniqueId());
















        if(yamlfile.getInt("slot") == 9){
            Inventory inv = Bukkit.createInventory(null, yamlfile.getInt("slot"), "§7Zaino - §eLiv. 1");


            if(yamlfile.contains("BackPackInv")) {
                for (String s : yamlfile.getConfigurationSection("BackPackInv").getKeys(false)) {
                    ItemStack item = yamlfile.getItemStack("BackPackInv." + s);
                    inv.setItem(Integer.parseInt(s), item);
                }
                p.openInventory(inv);
                return;
            } else {
                p.openInventory(inv);
                return;
            }


            }



        } */


    public void openOtherZaino(Player p, Player admin){



        File pfile = file.get(p.getUniqueId());
        YamlConfiguration yamlfile = yaml.get(p.getUniqueId());






        if(yamlfile.getInt("slot") == 9){
            Inventory inv = Bukkit.createInventory(null, yamlfile.getInt("slot"), "§7Zaino di " + p.getName() + " - §eLiv. 1");
            if(yamlfile.contains("BackPackInv")) {
                for (String s : yamlfile.getConfigurationSection("BackPackInv").getKeys(false)) {
                    ItemStack item = yamlfile.getItemStack("BackPackInv." + s);
                    inv.setItem(Integer.parseInt(s), item);
                }
                admin.openInventory(inv);
                return;
            } else {
                admin.openInventory(inv);
                return;
            }


        }



    }


        public boolean checkForZaino(Player p){


        for(ItemStack is : p.getInventory().getContents()){

            if(is != null && is.getItemMeta().getDisplayName().contains("§7Zaino")){
                return true;
            }

        }



        return false;
        }



    }





















