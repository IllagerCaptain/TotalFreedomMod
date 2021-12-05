package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.services.AbstractService;
import me.totalfreedom.totalfreedommod.util.FLog;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommandLoader extends AbstractService {
    private final List<FreedomCommand> commands;

    public CommandLoader() {
        commands = new ArrayList<>();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    public void add(Class<? extends FreedomCommand> command) {
        FreedomCommand cmd = null;
        try {
            Constructor<?> constructor = command.getDeclaredConstructor();
            constructor.setAccessible(true);
            cmd = (FreedomCommand) constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


        if (cmd != null) {
            commands.add(cmd);
            cmd.register();
        }
    }

    public FreedomCommand getByName(String name) {
        for (FreedomCommand command : commands) {
            if (name.equals(command.getName())) {
                return command;
            }
        }
        return null;
    }

    public boolean isAlias(String alias) {
        for (FreedomCommand command : commands) {
            if (Arrays.asList(command.getAliases().split(",")).contains(alias)) {
                return true;
            }
        }
        return false;
    }

    public void loadCommands() {
        Reflections commandDir = new Reflections("me.totalfreedom.totalfreedommod.command");

        Set<Class<? extends FreedomCommand>> commandClasses = commandDir.getSubTypesOf(FreedomCommand.class);

        for (Class<? extends FreedomCommand> commandClass : commandClasses) {
            try {
                add(commandClass);
            } catch (ExceptionInInitializerError ex) {
                FLog.warning("Failed to register command: /" + commandClass.getSimpleName().replace("CMD", ""));
            }
        }

        FLog.info("Loaded " + commands.size() + " commands");
    }

    public List<FreedomCommand> getCommands() {
        return commands;
    }
}