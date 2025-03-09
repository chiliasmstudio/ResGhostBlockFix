package com.chiliasmstudio.ResGhostBlockFix.Commands;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FixGhost {
    public static LiteralCommandNode<CommandSourceStack> createCommand(final String command) {
        return Commands.literal(command).executes(ctx -> {
                    final CommandSender sender = ctx.getSource().getSender();
                    if (!sender.hasPermission("ResGhostBlockFix.FixGhost")) {
                        sender.sendRichMessage("<red>no permission</red>");
                        return 0;
                    } else if (!(sender instanceof Player)) {
                        sender.sendRichMessage("<red>Only players can do this</red>");
                        return 0;
                    }
                    Player player = (Player) sender;
                    Location loc = player.getLocation().getBlock().getLocation();
                    Block block = loc.getBlock();
                    Material blockType = block.getType();
                    sender.sendRichMessage(String.format("<red>Replace block: %s (X: %d, Y: %d, Z: %d)",
                            blockType, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                    ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(loc);
                    if(res!=null){
                        ResidencePlayer rPlayer = Residence.getInstance().getPlayerManager().getResidencePlayer(((Player) sender).getPlayer());
                        boolean canBreak= rPlayer.canBreakBlock(block, true);
                        if(!canBreak){
                            sender.sendRichMessage("<red>no permission</red>");
                            return 0;
                        }
                    }
                    if(blockType == Material.BEDROCK || blockType == Material.VOID_AIR){
                        sender.sendRichMessage("<red>Illegal operation</red>");
                        return 0;
                    }
                    block.setType(Material.SHORT_GRASS);
                    sender.sendRichMessage(String.format("<green>Replace Done!"));
                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
