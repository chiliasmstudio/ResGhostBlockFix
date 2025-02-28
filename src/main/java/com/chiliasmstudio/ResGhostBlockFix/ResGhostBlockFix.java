package com.chiliasmstudio.ResGhostBlockFix;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.chiliasmstudio.ResGhostBlockFix.Commands.FixGhost;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResGhostBlockFix extends JavaPlugin {

    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().getPlugin("Residence") == null) {
            getLogger().info("<red>Residence plugin is not installed!");
        }
        // Plugin startup logic
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
        commands.registrar().register(FixGhost.createCommand("fixghost"), "Fix ghost block");
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
