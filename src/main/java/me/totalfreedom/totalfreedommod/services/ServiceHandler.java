package me.totalfreedom.totalfreedommod.services;

import me.totalfreedom.totalfreedommod.services.AbstractService;

import java.util.ArrayList;
import java.util.List;

public class ServiceHandler
{
    private final List<AbstractService> services;

    public ServiceHandler()
    {
        this.services = new ArrayList<>();
    }

    public void add(AbstractService service)
    {
        services.add(service);
    }

    public int getServiceAmount()
    {
        return services.size();
    }

    public void startServices()
    {
        for (AbstractService service : getServices())
        {
            try
            {
                service.onStart();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void stopServices()
    {
        for (AbstractService service : getServices())
        {
            try
            {
                service.onStop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<AbstractService> getServices()
    {
        return services;
    }
}