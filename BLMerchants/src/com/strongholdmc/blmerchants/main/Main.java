package com.strongholdmc.blmerchants.main;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.strongholdmc.blmerchants.caching.CachedVars;
import com.strongholdmc.blmerchants.cfg.CfgUtil;
import com.strongholdmc.blmerchants.listeners.MerchantListener;
import com.strongholdmc.blmerchants.trait.BLMerchant;
import com.strongholdmc.blmerchants.trait.CitizenUtil;
public class Main extends JavaPlugin
{
    private static CachedVars cacheProcessor;
    private static CfgUtil conf;
    public void onEnable()
    {
	PluginManager pm = Bukkit.getPluginManager();
	cacheProcessor = new CachedVars();
	File configFile = new File(this.getDataFolder(), "config.yml");
	if (!configFile.exists())
	{
	    this.saveDefaultConfig();
	}
	FileConfiguration config = this.getConfig();
	net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(BLMerchant.class).withName("BLMerchant"));
	conf = new CfgUtil(config, cacheProcessor);
	pm.registerEvents(new MerchantListener(cacheProcessor), this);
	conf.setAllInventories();
	CitizenUtil.spawnAllMerchants();
    }
    public static CfgUtil getCfg()
    {
	return conf;
    }
    public static CachedVars getCache()
    {
	return cacheProcessor;
    }
}
