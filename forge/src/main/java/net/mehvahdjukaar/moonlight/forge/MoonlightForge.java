package net.mehvahdjukaar.moonlight.forge;

import net.mehvahdjukaar.moonlight.api.fluids.SoftFluidRegistry;
import net.mehvahdjukaar.moonlight.core.Moonlight;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: MehVahdJukaar
 */
@Mod(Moonlight.MOD_ID)
public class MoonlightForge {
    public static final String MOD_ID = Moonlight.MOD_ID;

    public MoonlightForge() {

        Moonlight.commonInit();

        MinecraftForge.EVENT_BUS.register(this);

    }

    @SubscribeEvent
    public void onDataLoad(OnDatapackSyncEvent event) {
        // if we're on the server, send syncing packets
        SoftFluidRegistry.onDataLoad();
    }
}

