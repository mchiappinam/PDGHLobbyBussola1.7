package me.mchiappinam.pdghlobbybussola;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener  {
	
	private Main plugin;
	public Listeners(Main main) {
		plugin=main;
	}
	
	@EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)||e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){
                e.setCancelled(true);
                plugin.tags();
                e.getPlayer().openInventory(Main.menu);
            }
        }
	}
	
	@EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
	    if((e.getPlayer().getWorld()==plugin.getServer().getWorld("world"))&&(!e.getPlayer().hasPermission("pdgh.op"))) {
	    	e.setCancelled(true);
			e.getPlayer().sendMessage(" ");
			e.getPlayer().sendMessage(ChatColor.RED+"➩Escolha um servidor para jogar!");
			e.getPlayer().sendMessage(" ");
	    }
    }
	
	@EventHandler(priority=EventPriority.MONITOR)
    public void onItemDrop(PlayerDropItemEvent e) {
	    if(e.getPlayer().getWorld()==plugin.getServer().getWorld("world")) {
			e.getItemDrop().remove();
			e.setCancelled(true);
	        e.getPlayer().getInventory().setItem(0, plugin.bussola());
	        e.getPlayer().getInventory().setItem(1, plugin.cerca());
	        e.getPlayer().getInventory().setItem(2, plugin.cerca());
	        e.getPlayer().getInventory().setItem(3, plugin.cerca());
	        e.getPlayer().getInventory().setItem(4, plugin.cerca());
	        e.getPlayer().getInventory().setItem(5, plugin.cerca());
	        e.getPlayer().getInventory().setItem(6, plugin.cerca());
	        e.getPlayer().getInventory().setItem(7, plugin.cerca());
	        e.getPlayer().getInventory().setItem(8, plugin.book());
	    }
    }
	
	@EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
	    if(e.getPlayer().getWorld()!=plugin.getServer().getWorld("world")) {
	        World w = plugin.getServer().getWorld("world");
	        if (w != null) {
	        	e.getPlayer().teleport(w.getSpawnLocation());
	        	e.getPlayer().sendMessage("§9[ⒸⒻ] §cVocê desconectou no cf e foi teleportado para o spawn.");
	        }else{
	        	e.getPlayer().sendMessage("§cOcorreu um erro. Notifique alguém da STAFF.");
	        }
	    }
        e.getPlayer().getInventory().setItem(0, plugin.bussola());
        e.getPlayer().getInventory().setItem(1, plugin.cerca());
        e.getPlayer().getInventory().setItem(2, plugin.cerca());
        e.getPlayer().getInventory().setItem(3, plugin.cerca());
        e.getPlayer().getInventory().setItem(4, plugin.cerca());
        e.getPlayer().getInventory().setItem(5, plugin.cerca());
        e.getPlayer().getInventory().setItem(6, plugin.cerca());
        e.getPlayer().getInventory().setItem(7, plugin.cerca());
        e.getPlayer().getInventory().setItem(8, plugin.book());
    }

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
    	//e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
	    ItemStack clicked = e.getCurrentItem();
	    Inventory inventory = e.getInventory();
	    if (inventory.getName().equals(Main.menu.getName())) {
	    	e.setCancelled(true);
	    	if ((e.getCurrentItem() != null)&&(!e.getCurrentItem().getType().equals(Material.AIR))) {
		    	if (clicked.getType() == Material.DIAMOND_BLOCK) { //Criativo
			    	p.closeInventory();
			    	plugin.sendCriativo(p);
				    return;
			    }else if (clicked.getType() == Material.IRON_SWORD) { //DayZ
			    	p.closeInventory();
			    	plugin.sendFullPvP(p);
				    return;
			    }else if (clicked.getType() == Material.BEACON) { //AEDF
			    	p.closeInventory();
			    	p.sendMessage("§e§lPara entrar neste servidor é necessário baixar nosso ModPack no site: §a§lwww.PDGH.com.br/minecraft");
			    	p.sendMessage("§e§lCaso já tenha baixado, entre no servidor com o IP: §faedf.pdgh.com.br");
				    return;
			    }else if (clicked.getType() == Material.IRON_AXE) { //DayZ
			    	p.closeInventory();
			    	plugin.sendDayZ(p);
				    return;
			    }
	    	}
		    return;
	    }
	}
	
}