package io.github.learwin.journeymode.common.data;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import io.github.learwin.journeymode.JourneyUtil;
import io.github.learwin.journeymode.common.network.PacketHandler;
import io.github.learwin.journeymode.common.network.S2CAddUnlock;
import io.github.learwin.journeymode.common.network.S2CFullSyncUnlocks;

public class JourneyData {

    public static final String NBT_TAG = "journeyUnlocks";

    public static Set<String> getUnlocks(EntityPlayer player) {
        NBTTagCompound persisted = player.getEntityData()
            .getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        if (!persisted.hasKey(NBT_TAG)) {
            persisted.setTag(NBT_TAG, new NBTTagCompound());
        }

        NBTTagCompound unlocksTag = persisted.getCompoundTag(NBT_TAG);
        Set<String> result = new HashSet<>();

        for (String key : unlocksTag.func_150296_c()) {
            if (unlocksTag.getBoolean(key)) {
                result.add(key);
            }
        }

        return result;
    }

    public static boolean addUnlock(EntityPlayer player, ItemStack stack) {
        String key = JourneyUtil.getKey(stack);
        return addUnlock(player, key);
    }

    public static boolean addUnlock(EntityPlayer player, String key) {
        NBTTagCompound persisted = player.getEntityData()
            .getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagCompound unlocks = persisted.getCompoundTag(NBT_TAG);

        boolean alreadyUnlocked = unlocks.getBoolean(key);
        if (alreadyUnlocked) return false;

        unlocks.setBoolean(key, true);
        persisted.setTag(NBT_TAG, unlocks);
        player.getEntityData()
            .setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);

        return true;
    }

    public static void sendFullUnlocks(EntityPlayer player) {
        if (!(player instanceof EntityPlayerMP)) return;

        EntityPlayerMP mp = (EntityPlayerMP) player;
        Set<String> unlocks = JourneyData.getUnlocks(mp);
        PacketHandler.sendToPlayer(new S2CFullSyncUnlocks(unlocks), mp);
    }

    public static void sendAddUnlock(EntityPlayer player, String key) {
        if (!(player instanceof EntityPlayerMP)) return;

        EntityPlayerMP mp = (EntityPlayerMP) player;
        PacketHandler.sendToPlayer(new S2CAddUnlock(key), mp);
    }

}
