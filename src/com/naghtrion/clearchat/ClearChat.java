package com.naghtrion.clearchat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearChat extends JavaPlugin {

    @Override
    public void onEnable() {
        // Salva cria a config padrão
        saveDefaultConfig();
        getLogger().info("plugin ativado com sucesso! | Autor:  Naghtrion");
    }

    @Override
    public void onDisable() {
        // Salva a config
        saveConfig();
        getLogger().info("plugin desativado com sucesso! | Autor:  Naghtrion");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // É um player?
            if (sender instanceof Player) {
                for (int i = 0; i < 25; i += 1) {
                    sender.sendMessage("");
                }
                sender.sendMessage(getConfig().getString("clear-chat").replace("&", "§").replace("{player}", sender.getName()));
                return true;
            }
            // então é um console :)
            for (int i = 0; i < 100; i += 1) {
                sender.sendMessage("");
            }
            sender.sendMessage(getConfig().getString("clear-chat").replace("&", "§").replace("{player}", sender.getName()));
            return true;
        }

        // ta afim de limpar o chat de todos?
        if (args[0].equalsIgnoreCase("todos") || args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("global")) {
            // Tem permissao pra isso?
            if (!sender.hasPermission("clearchat.geral") || !sender.isOp()) {
                sender.sendMessage(getConfig().getString("sem-permissao").replace("&", "§").replace("{player}", sender.getName()));
                return false;
            }
            for (int i = 0; i < 25; i += 1) {
                Bukkit.getServer().broadcastMessage("");
            }
            Bukkit.getServer().broadcastMessage(getConfig().getString("clear-chat-all").replace("&", "§").replace("{player}", sender.getName()));
            return true;
        }

        // Só pode estar querendo limpar o chat de outro player...
        // Tem permissao pra isso?
        if (!sender.hasPermission("clearchat.players") || !sender.isOp()) {
            sender.sendMessage(getConfig().getString("sem-permissao").replace("&", "§").replace("{player}", sender.getName()));
            return false;
        }

        Player otherplayer = Bukkit.getPlayer(args[0]);
        if (otherplayer != null) {
            // Limpa o chat de outro player
            for (int i = 0; i < 25; i += 1) {
                otherplayer.sendMessage("");
            }
            otherplayer.sendMessage(getConfig().getString("clear-chat-player").replace("&", "§").replace("{player}", sender.getName()));
            sender.sendMessage(getConfig().getString("clear-chat-sender").replace("&", "§").replace("{player}", otherplayer.getName()));
            return true;
        }
        sender.sendMessage(getConfig().getString("player-off").replace("&", "§").replace("{player}", args[0]));
        return false;
    }
}