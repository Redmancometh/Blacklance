package com.strongholdmc.blcore.main;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.strongholdmc.blcore.listeners.DropListeners;
import com.strongholdmc.blcore.listeners.PlayerListeners;
import com.strongholdmc.blcore.listeners.WeaponListener;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.DBUtil;
import com.strongholdmc.blcore.storage.DataSource;
import com.strongholdmc.blcore.storage.RPGPlayers;

@SuppressWarnings("deprecation")
public class BlackLance extends JavaPlugin
{
	BukkitTask connect;
	public static Plugin pl;
	private static DataSource dataSource;
	public void onEnable()
	{
		try
		{
			dataSource = DataSource.getInstance();
			DBUtil.setup(dataSource.getConnection());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		PluginManager pm = Bukkit.getPluginManager();
		pl = this;
		File configFile = new File(this.getDataFolder(), "config.yml");
		if (!configFile.exists())
		{
			this.saveDefaultConfig();
		}
		pm.registerEvents(new PlayerListeners(this, configFile), this);
		pm.registerEvents(new WeaponListener(), this);
		pm.registerEvents(new DropListeners(), this);
	}

	public void onDisable()
	{
		Bukkit.getScheduler().cancelAllTasks();
		for (Player p : Bukkit.getOnlinePlayers())
		{
			RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
			try
			{
				DBUtil.saveDataByID(rp.getUID(), rp);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void saveIt()
	{
		this.saveConfig();
	}

	public static Connection getConnection()
	{
		try
		{
			return dataSource.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Plugin getPlugin()
	{
		return pl;
	}
}