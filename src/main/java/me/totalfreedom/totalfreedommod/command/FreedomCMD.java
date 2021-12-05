package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import org.bukkit.command.Command;

public abstract class FreedomCMD extends Command
{
    protected final TotalFreedomMod plugin = TotalFreedomMod.plugin();

    private final CommandParameters parameters;
    private final CommandPermissions permissions;

    public FreedomCMD() {
        super("");

        this.parameters = this.getClass().getDeclaredAnnotation(CommandParameters.class);
        this.permissions = this.getClass().getDeclaredAnnotation(CommandPermissions.class);

        this.setName(parameters.name());
        this.setLabel(parameters.name());
    }

}
