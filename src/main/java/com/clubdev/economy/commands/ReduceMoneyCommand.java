package com.clubdev.economy.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.clubdev.economy.EconomyRFE;

public class ReduceMoneyCommand extends Command {
    EconomyRFE main;
    public ReduceMoneyCommand(EconomyRFE main) {
        super("reducemoney", "Забрать деняк у игрока");
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
            if(!commandSender.hasPermission("economy.reducemoney")) {
                commandSender.sendMessage("У тебя нет прав!");
                return true;
            }
        }
        if(strings.length != 2) {
            commandSender.sendMessage("Вы неправильно используете комманду! Правильно: /reducemoney никнейм колличество");
            return true;
        }
        IPlayer target = main.getServer().getOfflinePlayer(strings[0]);
        if(target.getUniqueId() == null) {
            commandSender.sendMessage("Данного игрока не существует!");
            return true;
        }
        if(main.getMoneyManager().getMoney(target) < Integer.parseInt(strings[1])) {
            main.getMoneyManager().setMoney(target, 0);
            return true;
        }
        main.getMoneyManager().reduceMoney(target, Double.parseDouble(strings[1]));
        return true;
    }
}
