package com.clubdev.economy.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.clubdev.economy.EconomyRFE;

public class AddMoneyCommand extends Command {
    EconomyRFE main;
    public AddMoneyCommand(EconomyRFE main) {
        super("addmoney", "Дать деняк игроку");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("money", CommandParamType.INT)
        });
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player player) {
            if(!commandSender.hasPermission("economy.addmoney")) {
                commandSender.sendMessage("У тебя нет прав!");
                return true;
            }
        }
        if(strings.length != 2) {
            commandSender.sendMessage("Вы неправильно используете комманду! Правильно: /addmoney никнейм колличество");
            return true;
        }
        IPlayer target = main.getServer().getOfflinePlayer(strings[0]);
        if(target.getUniqueId() == null) {
            commandSender.sendMessage("Данного игрока не существует!");
            return true;
        }
        main.getMoneyManager().giveMoney(target, Double.parseDouble(strings[1]));
        return true;
    }
}
