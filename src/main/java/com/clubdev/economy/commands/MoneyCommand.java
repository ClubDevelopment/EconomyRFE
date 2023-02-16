package com.clubdev.economy.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import com.clubdev.economy.EconomyRFE;

public class MoneyCommand extends Command {
    private final EconomyRFE main;
    public MoneyCommand(EconomyRFE main) {
        super("money", "Посмотреть свои деньги");
        this.main = main;
    }
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player player) {
            player.sendMessage("У вас на балансе " + main.getMoneyManager().getMoney(player) + "Đ");
            return true;
        }
        commandSender.sendMessage("Данную команду можно использовать только в игре.");
        return false;
    }
}
