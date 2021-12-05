package me.totalfreedom.totalfreedommod.services.impl;

import me.totalfreedom.totalfreedommod.command.impl.SitCMD;
import me.totalfreedom.totalfreedommod.services.AbstractService;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Sitter extends AbstractService
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e)
    {
        Entity dm = e.getDismounted();
        if (dm instanceof ArmorStand)
        {
            if (SitCMD.STANDS.contains(dm))
            {
                SitCMD.STANDS.remove(dm);
                dm.remove();
            }
        }
    }
}
