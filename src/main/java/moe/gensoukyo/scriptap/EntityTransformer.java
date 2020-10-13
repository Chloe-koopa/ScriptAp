package moe.gensoukyo.scriptap;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import noppes.npcs.api.NpcAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.UUID;

/**
 * {@link org.bukkit.entity.Entity}
 * to
 * {@link net.minecraft.entity.Entity}
 * 仅仅适配猫端
 * @author Chloe_koopa
 */
public class EntityTransformer {
    private static final MinecraftServer SERVER = NpcAPI.Instance().getIWorld(0).getMCWorld().getMinecraftServer();
    private static MethodHandle TRANSFORMER = null;
    private static final Logger LOGGER = LogManager
            .getLogger("ScriptAp Native Entity Transformer");

    static {
        try {
            TRANSFORMER = MethodHandles.lookup().findVirtual(
                    Class.forName("org.bukkit.craftbukkit.entity.CraftEntity"),
                    "getHandle",
                    MethodType.methodType(Entity.class)
            );
        } catch (Exception e) {
            LOGGER.error("Can't setup native Entity transformer!", e);
        }
    }

    public static Entity transform(org.bukkit.entity.Entity craftEntity) {
        if (TRANSFORMER != null){
            try {
                return (Entity) TRANSFORMER.bindTo(craftEntity).invoke();
            }
            catch (Throwable t) {
                LOGGER.error("What happened ?", t);
            }
        }
        return transformOld(craftEntity);
    }

    static Entity transformOld(org.bukkit.entity.Entity bukkitSide) {
        assert SERVER != null;
        UUID uuid = bukkitSide.getUniqueId();
        Entity result = null;
        for (WorldServer world : SERVER.worlds) {
            result = world.getEntityFromUuid(uuid);
            if (result != null) {
                break;
            }
        }
        return result;
    }
}