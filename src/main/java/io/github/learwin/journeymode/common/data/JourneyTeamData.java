package io.github.learwin.journeymode.common.data;

import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;
import com.gtnewhorizon.gtnhlib.eventbus.Phase;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import io.github.learwin.journeymode.Constants;
import io.github.learwin.journeymode.JourneyUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import serverutils.events.team.*;
import serverutils.lib.data.ForgeTeam;
import serverutils.lib.data.TeamData;
import serverutils.lib.data.Universe;
import serverutils.lib.util.FileUtils;
import serverutils.lib.util.NBTUtils;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber
public class JourneyTeamData extends TeamData {

    private final Set<String> unlocks = new HashSet<>();

    public JourneyTeamData(ForgeTeam t) {
        super(t);
    }

    public static JourneyTeamData get(ForgeTeam team) {
        return team.getData().get(Constants.MOD_ID);
    }

    public static boolean unlock_for_team(EntityPlayer player, String id) {
        if (!MinecraftServer.getServer().isDedicatedServer())
            return false;

        var fp = Universe.get().getPlayer(player);

        if (!fp.hasTeam())
            return false;

        get(fp.team).add(id);
        return true;
    }

    public static Set<String> all_team_unlocks(EntityPlayer player) {
        var fp = Universe.get().getPlayer(player);

        if (!fp.hasTeam())
            return new HashSet<>(0);

        return new HashSet<>(get(fp.team).unlocks);
    }

    public void add(String id) {
        if (this.unlocks.add(id))
            this.unlock_for_members(id);
    }

    @SubscribeEvent
    public static void onTeamLoaded(ForgeTeamLoadedEvent event) {
        var team = event.getTeam();
        var data = get(team);
        var root = NBTUtils.readNBT(team.getDataFile(Constants.MOD_ID));
        var nbt = root.getCompoundTag(JourneyData.NBT_TAG);

        data.unlocks.addAll(nbt.func_150296_c());
    }

    @SubscribeEvent
    public static void registerTeamData(ForgeTeamDataEvent event) {
        event.register(new JourneyTeamData(event.getTeam()));
    }
    @SubscribeEvent
    public static void onTeamPlayerJoined(ForgeTeamPlayerJoinedEvent event) {
        get(event.getTeam()).merge_player(event.getPlayer().getPlayer());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP) || event.player.worldObj.isRemote)
            return;

        var player = Universe.get().getPlayer(event.player);

        if (player.hasTeam())
            get(player.team).merge_player(player.getPlayer());
    }

    @SubscribeEvent
    public static void onTeamSaved(ForgeTeamSavedEvent event) {
        ForgeTeam team = event.getTeam();
        NBTUtils.writeNBTSafe(team.getDataFile(Constants.MOD_ID), get(team).to_nbt());
    }

    @SubscribeEvent
    public static void onTeamDeleted(ForgeTeamDeletedEvent event) {
        FileUtils.delete(event.getTeam().getDataFile(Constants.MOD_ID));
    }

    @Override
    public String getId() {
        return Constants.MOD_ID;
    }

    private void merge_player(EntityPlayerMP player) {
        this.unlocks.addAll(JourneyData.getUnlocks(player));

        for (var id : this.unlocks)
            this.unlock_for_members(id);
    }

    private void unlock_for_members(String id) {
        for (var member : this.team.getOnlineMembers())
            if (JourneyData.addUnlock(member, id))
                JourneyData.sendAddUnlock(member, id);
    }

    private NBTTagCompound to_nbt() {
        var root = new NBTTagCompound();
        var nbt = new NBTTagCompound();

        for (var id : this.unlocks)
            nbt.setBoolean(id, true);

        root.setTag(JourneyData.NBT_TAG, nbt);

        return root;
    }
}
