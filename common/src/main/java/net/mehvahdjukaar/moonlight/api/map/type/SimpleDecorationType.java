package net.mehvahdjukaar.moonlight.api.map.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mehvahdjukaar.moonlight.api.map.markers.GenericMapBlockMarker;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.moonlight.core.Moonlight;
import net.mehvahdjukaar.moonlight.api.map.CustomMapDecoration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

//base type for simple data driven type. Basically a simple version of CustomDecorationType that can be serialized
public class SimpleDecorationType extends MapDecorationType<CustomMapDecoration, GenericMapBlockMarker<CustomMapDecoration>> {

    //using this and not block predicate since it requires a worldLevelGen...
    @Nullable
    private final RuleTest target;

    public static final Codec<SimpleDecorationType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RuleTest.CODEC.optionalFieldOf("target_block").forGetter(SimpleDecorationType::getTarget)
    ).apply(instance, SimpleDecorationType::new));


    public SimpleDecorationType(Optional<RuleTest> target) {
        this.target = target.orElse(null);
    }

    @Nullable
    public Optional<RuleTest> getTarget() {
        return Optional.ofNullable(target);
    }

    @Override
    public boolean hasMarker() {
        return target != null;
    }

    public ResourceLocation getId() {
        return Utils.getID(this);
    }

    @Nullable
    @Override
    public CustomMapDecoration loadDecorationFromBuffer(FriendlyByteBuf buffer) {
        try {
            return new CustomMapDecoration(this, buffer);
        } catch (Exception e) {
            Moonlight.LOGGER.warn("Failed to load custom map decoration for decoration type" + this.getId() + ": " + e);
        }
        return null;
    }

    @Nullable
    @Override
    public GenericMapBlockMarker<CustomMapDecoration> loadMarkerFromNBT(CompoundTag compound) {
        if (this.hasMarker()) {
            GenericMapBlockMarker<CustomMapDecoration> marker = new GenericMapBlockMarker<>(this);
            try {
                marker.loadFromNBT(compound);
                return marker;
            } catch (Exception e) {
                Moonlight.LOGGER.warn("Failed to load world map marker for decoration type" + this.getId() + ": " + e);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public GenericMapBlockMarker<CustomMapDecoration> getWorldMarkerFromWorld(BlockGetter reader, BlockPos pos) {
        if (this.target != null) {
            if (target.test(reader.getBlockState(pos), RandomSource.create())) {
                return new GenericMapBlockMarker<>(this, pos);
            }
        }
        return null;
    }
}
