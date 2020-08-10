package moe.gensoukyo.scriptap;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.Event;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import org.bukkit.entity.Entity;
import org.serverct.ersha.jd.api.BaseAttribute;

import java.util.UUID;

/**
 * @implNote Not a event
 * @author Chloe_koopa
 */
public class ApAttributeEvent extends Event {
    public final IEntity<?> damager;
    public final IEntity<?> victim;
    public final BaseAttribute attr;
    public final double value;

    public ApAttributeEvent(Entity damager, Entity victim, BaseAttribute attr, double value) {
        this.damager = transformEntity(damager);
        this.victim = transformEntity(victim);
        this.attr = attr;
        this.value = value;
    }

    private static IEntity<?> transformEntity(Entity bukkitSide) {
        assert SERVER != null;
        UUID uuid = bukkitSide.getUniqueId();
        net.minecraft.entity.Entity result = null;
        for (WorldServer world : SERVER.worlds) {
            result = world.getEntityFromUuid(uuid);
            if (result != null) {
                break;
            }
        }
        return NpcAPI.Instance().getIEntity(result);
    }
    private static final MinecraftServer SERVER = NpcAPI.Instance().getIWorld(0).getMCWorld().getMinecraftServer();
}
