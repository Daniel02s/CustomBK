package me.fanta.custombk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ZainoCmd implements CommandExecutor {

    Custombk main;

    public ZainoCmd(Custombk main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(args.length == 2){







            if(!(sender instanceof Player)) {


                if (args[0].equalsIgnoreCase("upgrade")) {
                    System.out.println("Comando upgrade lanciato!");

                    if(!Bukkit.getServer().getPlayerExact(args[1]).isOnline()){

                        System.out.println("ERRORE: Il player non è online");


                    }


                    Player target = Bukkit.getPlayer(args[1]);

                    File f = main.file.get(target.getUniqueId());
                    YamlConfiguration y = main.yaml.get(target.getUniqueId());

                    Inventory vecchioinv = main.zaini.get(target.getUniqueId());

                    if (y.getInt("slot") == 9) {
                        y.set("slot", 18);
                        y.set("name", "§7Zaino di " + target.getName() + " §6Liv - 2");
                        System.out.println("§7Zaino aggiornato al liv 2");

                    } else if (y.getInt("slot") == 18) {
                        y.set("slot", 27);
                        y.set("name", "§7Zaino di " + target.getName() + " §cLiv - 3");
                        System.out.println("§7Zaino aggiornato al liv 3");


                    } else if (y.getInt("slot") == 27) {
                        y.set("slot", 36);
                        y.set("name", "§7Zaino di " + target.getName() + " §4Liv - 4");
                        System.out.println("§7Zaino aggiornato al liv 4");

                    } else if (y.getInt("slot") == 36) {
                        y.set("slot", 45);
                        y.set("name", "§7Zaino di " + target.getName() + " §1Liv - 5");
                        System.out.println("§7Zaino aggiornato al liv 5");

                    } else if (y.getInt("slot") == 45) {
                        y.set("slot", 54);
                        y.set("name", "§7Zaino di " + target.getName() + " §9Liv - 6");
                        System.out.println("§7Zaino aggiornato al liv 6");

                    } else if (y.getInt("slot") == 54){
                        System.out.println("§7Il player ha raggiunto il massimo livello!");
                        return true;
                    }

                    try {
                        y.save(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    if (vecchioinv.getViewers().size() > 0) {

                        for (int x = 1; x < vecchioinv.getViewers().size(); x++) {
                            Player viewer = Bukkit.getPlayer(vecchioinv.getViewers().get(x).getName());
                            viewer.closeInventory();
                            viewer.sendMessage("§7Zaino aggiornato con successo!");

                        }
                    }

                    ItemStack[] vecchitem = vecchioinv.getContents();

                    Inventory nuovoinv = Bukkit.createInventory(null, y.getInt("slot"), y.getString("name"));
                    nuovoinv.setContents(vecchitem);


                    main.zaini.remove(target.getUniqueId());


                    main.zaini.put(target.getUniqueId(), nuovoinv);


                }


            } else {
                Player p = (Player) sender;
                p.sendMessage("§7Comando sconosciuto, se pensi sia un errore contatta lo Staff!");
                return true;
            }

        }




        if(sender instanceof Player){
            Player p = (Player) sender;





            if(args.length == 0){



                if(p.hasPermission("backpack.open")){
                    Inventory inv = main.zaini.get(p.getUniqueId());
                    p.openInventory(inv);
                    return true;
                } else {
                    p.sendMessage("§7Comando sconosciuto, se pensi sia un errore contatta lo Staff!");
                }


            }

















            if(args.length == 1 && p.hasPermission("zaino.admin")){

                if(Bukkit.getServer().getPlayerExact(args[0]) != null){
                    File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), p.getUniqueId() + ".yml");

                    YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                    if(main.zaini.containsKey(p.getUniqueId())) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Inventory inv = main.zaini.get(target.getUniqueId());
                        p.openInventory(inv);


                    }
                    } else {
                    if(main.zaini.containsKey(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId())){
                        Inventory inv = main.zaini.get(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId());
                        p.openInventory(inv);
                        return true;
                    } else {


                        if(main.getFile(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId()) != null ){






                            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Custombk").getDataFolder(), Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId() + ".yml");

                            if(!file.exists()){
                                p.sendMessage("§cQuesto player non è mai entrato nel server, non puoi aprire il suo zaino!");
                                return true;


                            }


                            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);



                            Inventory inv = Bukkit.createInventory(null, yaml.getInt("slot"), yaml.getString("name"));




                            for(int x = 0; x < yaml.getInt("slot"); x++){

                                ItemStack is = yaml.getItemStack("BackPackInv." + x);
                                if(is != null){
                                    inv.setItem(x, is);

                                }





                            }




                            main.zaini.put(Bukkit.getServer().getOfflinePlayer(args[0]).getUniqueId(), inv);
                            p.openInventory(inv);







                        }







                    }





                    }
                }




            }

return false;
        }


    }
