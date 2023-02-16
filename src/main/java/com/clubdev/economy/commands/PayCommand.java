package com.clubdev.economy.commands;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.clubdev.economy.EconomyRFE;

public class PayCommand extends Command {
    EconomyRFE main;
    public PayCommand(EconomyRFE main) {
        super("pay", "Перевести деньги другому игроку");
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
            if(strings.length != 2) {
                player.sendChat("Вы неправильно используете комманду! Правильно: /pay никнейм колличество");
                return true;
            }
            if(main.getMoneyManager().getMoney(player) < Integer.parseInt(strings[1])) {
                player.sendMessage("Нельзя перевести большую сумму, чем вы имеете на своем балансе!");
                return true;
            }
            IPlayer target = main.getServer().getOfflinePlayer(strings[0]);
            if(target.getUniqueId() == null) {
                player.sendMessage("Данного игрока не существует!");
                return true;
            }
            if(player == target.getPlayer()) {
                player.sendMessage("Нельзя перевести деньги самому себе!");
                return true;
            }
            main.getMoneyManager().reduceMoney(player, Integer.parseInt(strings[1]));
            main.getMoneyManager().giveMoney(target, Integer.parseInt(strings[1]));
            player.sendMessage("Вы перевели " + strings[1] + "Đ" + " игроку " + strings[0]);
            if(target.isOnline()) {
                target.getPlayer().sendMessage(player.getName() + " перевел вам " + strings[1] + "Đ" + ", теперь на вашем балансе " + main.getMoneyManager().getMoney(target) + "Đ");
            }
            return true;
        }
        commandSender.sendMessage("Данную команду можно использовать только в игре.");
        return false;
    }
}
