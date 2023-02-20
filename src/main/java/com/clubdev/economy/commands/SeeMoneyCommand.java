package com.clubdev.economy.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.clubdev.economy.EconomyRFE;

public class SeeMoneyCommand extends Command {
    EconomyRFE main;
    public SeeMoneyCommand(EconomyRFE main) {
        super("seemoney", "Посмотреть деньги у игрока");
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
            if(!commandSender.hasPermission("economy.seemoney")) {
                commandSender.sendMessage("У тебя нет прав!");
                return true;
            }
        }
        if(strings.length != 1) {
            commandSender.sendMessage("Вы неправильно используете комманду! Правильно: /seemoney никнейм");
            return true;
        }
        IPlayer target = main.getServer().getOfflinePlayer(strings[0]);
        if(target.getUniqueId() == null) {
            commandSender.sendMessage("Данного игрока не существует!");
            return true;
        }
        commandSender.sendMessage("У игрока" + strings[0] + " на балансе " + main.getMoneyManager().getMoney(target) + "Đ");
        return true;
    }
}
