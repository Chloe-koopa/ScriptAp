package moe.gensoukyo.scriptap;

import net.minecraftforge.fml.common.eventhandler.Event;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import org.bukkit.entity.Entity;
import org.serverct.ersha.jd.api.BaseAttribute;

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

    private IEntity<?> transformEntity(Entity entity) {
        return NpcAPI.Instance().getIEntity(EntityTransformer.transform(entity));
    }
}
