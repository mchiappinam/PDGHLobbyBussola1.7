package me.mchiappinam.pdghlobbybussola;

import java.util.ArrayList;
import java.util.List;

import me.mchiappinam.pdghlobbybussola.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin implements PluginMessageListener {
	public static Inventory menu = Bukkit.createInventory(null, 9, "§e» §9§lPDGH Network§e«");
	public int playerscriativo = 0;
	public int playersdayz = 0;
	public int playersfullpvp = 0;
	public int playerslobby = 0;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
	    setScoreBoard();
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyBussola] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyBussola] §2Acesse: http://pdgh.com.br/");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyBussola] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobbyBussola] §2Acesse: http://pdgh.com.br/");
	}
	
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
       
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
       
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playerCount = in.readInt();
            if(server.contains("criativo")) {
            	playerscriativo=playerCount;
            }else if(server.contains("dayz")) {
            	playersdayz=playerCount;
            }else if(server.contains("fullpvp")) {
            	playersfullpvp=playerCount;
            }
        }
       
    }
	
	public void setScoreBoard() {
		final Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		final Objective stats = score.registerNewObjective("1", "2");
		stats.setDisplaySlot(DisplaySlot.SIDEBAR);
		stats.setDisplayName("§3§l» PDGH Network « §e§l(0)");

		final Score criativo = stats.getScore("§e§lCriativo§c:");
		final Score dayz = stats.getScore("§e§lPDGH DayZ§c:");
		final Score lobby = stats.getScore("§e§lLobby§c:");
		final Score fullpvp = stats.getScore("§e§lFull PvP§c:");
		final Score aedf = stats.getScore("§e§lAEDF§c:");

		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			//@SuppressWarnings("deprecation")
			public void run() {
				getCount("criativo");
				getCount("dayz");
				getCount("fullpvp");
				playerslobby=getServer().getOnlinePlayers().size();
				int total = playerscriativo+playersdayz+playerslobby;
				stats.setDisplayName("§3§l» PDGH Network « §e§l("+total+")");
				criativo.setScore(playerscriativo);
				dayz.setScore(playersdayz);
				lobby.setScore(playerslobby);
				fullpvp.setScore(playersfullpvp);
				aedf.setScore(9999);
			}
		}, 0, 4*20);
		
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {
				for(Player p : getServer().getOnlinePlayers())
					p.setScoreboard(score);
			}
		}, 0, 1*20);
	}
   
    public void getCount(String server) {
       
        if (server == null) {
            server = "ALL";
        }
       
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        getServer().sendPluginMessage(this, "BungeeCord", out.toByteArray());
        
    }
   
    public ItemStack bussola() {
		ItemStack a0 = new ItemStack(Material.COMPASS, 1);
		List<String> l0 = new ArrayList<String>();
	    ItemMeta b0 = a0.getItemMeta();
	    b0.setDisplayName("§aClique para escolher o servidor");
	    b0.setLore(l0);
	    a0.setItemMeta(b0);
		return a0;
    }
   
    public ItemStack cerca() {
		ItemStack a0 = new ItemStack(Material.IRON_FENCE, 1);
		List<String> l0 = new ArrayList<String>();
	    ItemMeta b0 = a0.getItemMeta();
	    b0.setDisplayName(" ");
	    b0.setLore(l0);
	    a0.setItemMeta(b0);
		return a0;
    }
   
    public ItemStack book() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)book.getItemMeta();
        List<String> pages = new ArrayList<String>();
        pages.add("§9\nOlá, seja bem vindo aos servidores da\n§9§lPDGH Network§9\n\n\n§c§l>> Guia Básico <<\n\n§3Para entrar em algum servidor da §lPDGH§3, clique na bússola que se encontra no seu inventário.");
        pages.add("§3\nDepois de entrar em algum servidor, você terá diversos recursos.\n\nTemos em nossos servidores, um sistema de ajuda básica ao jogador. Para ver como funciona, digite o comando §l/ajuda");
        pages.add("§c§l>> Links Uteis <<\n\n§3Site: pdgh.com.br\n\nFórum: pdgh.com.br/forum\n\nChangeLog: pdgh.com.br/changelog\n\nTeamSpeak: pdgh.com.br/teamspeak");
        pages.add("§c§l> Algumas Regras <\n\n§3Abuso de BUG = ban permanente.\n\nConta fake = ban permanente em ambas contas.\n\nDivulgação = ban permanente.");
        pages.add("§3Uso de hacker = ban permanente.\n\nOfensa = ban por 1200 minutos.\n\nFlood = ban por 600 minutos.\n\nVeja todas as regras no site: pdgh.com.br/regras");
        pages.add("§9\nTemos mais servidores do que esses que se encontram na lista da bússola. Veja todos os nossos servidores em nosso site: §lwww.pdgh.com.br§9\n\nTenha um excelente jogo!");
        meta.setTitle("§aBem vindo aos nossos servidores§f");
        meta.setAuthor("§c§lPDGH Minecraft Servers");
        meta.setPages(pages);
        book.setItemMeta(meta);
		return book;
    }

	protected void tags() {
		ItemStack a0 = new ItemStack(Material.DIAMOND_BLOCK, 1);
	    ItemMeta b0 = a0.getItemMeta();
		List<String> l0 = new ArrayList<String>();
	    b0.setDisplayName("§a["+playerscriativo+"] §e§lPDGH Criativo§f §7§l[1.8-1.12]");
	    l0.add(" ");
	    l0.add("§6Clique para entrar no servidor");
	    b0.setLore(l0);
	    a0.setItemMeta(b0);
	    menu.setItem(1, a0);

		ItemStack a2 = new ItemStack(Material.IRON_SWORD, 1);
	    ItemMeta b2 = a2.getItemMeta();
	    b2.setDisplayName("§a["+playersfullpvp+"] §e§lPDGH Full PvP§f §7§l[1.8-1.12]");
	    b2.setLore(l0);
	    a2.setItemMeta(b2);
	    menu.setItem(3, a2);

		ItemStack a3 = new ItemStack(Material.BEACON, 1);
	    ItemMeta b3 = a3.getItemMeta();
	    b3.setDisplayName("§e§lPDGH §d§lAEDF§f §7§l[MODPACK]");
		List<String> l1 = new ArrayList<String>();
	    l1.add(" ");
	    l1.add("§e§lPara entrar neste servidor é necessário");
	    l1.add("§e§lbaixar nosso ModPack no site:");
	    l1.add("§a§lwww.PDGH.com.br/minecraft");
	    l1.add(" ");
	    l1.add("§e§lCaso já tenha baixado, entre com o IP:");
	    l1.add("§faedf.pdgh.com.br");
	    b3.setLore(l1);
	    a3.setItemMeta(b3);
	    menu.setItem(5, a3);

		ItemStack a1 = new ItemStack(Material.IRON_AXE, 1);
	    ItemMeta b1 = a1.getItemMeta();
	    b1.setDisplayName("§a["+playersdayz+"] §e§lPDGH Day§c§lZ§f §7§l[1.8-1.12]");
	    b1.setLore(l0);
	    a1.setItemMeta(b1);
	    menu.setItem(7, a1);
	}

	public void sendCriativo(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("criativo");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendDayZ(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("dayz");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendFullPvP(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("fullpvp");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	public void sendAEDF(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("aedf");
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}
	
}