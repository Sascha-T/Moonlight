package net.mehvahdjukaar.moonlight.core;

import net.mehvahdjukaar.moonlight.api.fluids.SoftFluidRegistry;
import net.mehvahdjukaar.moonlight.api.fluids.SoftFluidTank;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class ClientConfigs {

    public static final Supplier<Boolean> MERGE_PACKS;
    public static final Supplier<Boolean> LAZY_MAP_DATA;
    public static final Supplier<Integer> MAPS_MIPMAP;

    public static final ConfigSpec CONFIG;

    static {
        ConfigBuilder builder = ConfigBuilder.create(Moonlight.MOD_ID, ConfigType.CLIENT);
        builder.push("general");
        MERGE_PACKS = builder.comment("Merge all dynamic resource packs from all mods that use this library into a single pack")
                .define("merge_dynamic_packs", true);
        LAZY_MAP_DATA = builder.comment("Prevents map texture from being upladed to GPU when only map markers have changed." +
                        "Could increase performance")
                .define("lazy_map_upload", true);
        MAPS_MIPMAP = builder.comment("Renders map textures using mipmap. Vastly improves look from afar as well when inside a Map Atlas from Map Atlases or similar. Set to 0 to have no mipmap like vanilla")
                .define("maps_mipmap", 3, 0, 4);
        builder.pop();
        CONFIG = builder.buildAndRegister();
        CONFIG.loadFromFile();
    }

    public static void init() {
    }
}
