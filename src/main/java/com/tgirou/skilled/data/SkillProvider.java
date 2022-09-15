package com.tgirou.skilled.data;

import com.tgirou.skilled.skills.ProgressionManager;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ProgressionManager> SKILL_MANAGER = CapabilityManager.get(new CapabilityToken<>(){});

    private ProgressionManager skill = null;
    private final LazyOptional<ProgressionManager> opt = LazyOptional.of(this::createSkill);

    @Nonnull
    private ProgressionManager createSkill() {
        if (skill == null) {
            skill = new ProgressionManager();
        }
        return skill;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == SKILL_MANAGER) {
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        createSkill().saveNBT(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSkill().loadNBT(nbt);
    }
}
