package com.clubdev.economy.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.clubdev.economy.EconomyRFE;

public class SetMoneyCommand extends Command {
    EconomyRFE main;
    public SetMoneyCommand(EconomyRFE main) {
        super("setmoney", "Установить деньги у игрока");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("money", CommandParamType.INT)
        });
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length != 2) {
            commandSender.sendMessage("Вы неправильно используете комманду! Правильно: /setmoney никнейм колличество");
            return true;
        }
        IPlayer target = main.getServer().getOfflinePlayer(strings[0]);
        if(target.getUniqueId() == null) {
            commandSender.sendMessage("Данного игрока не существует!");
            return true;
        }
        main.getMoneyManager().setMoney(target, Double.parseDouble(strings[1]));
        return true;
    }
}
