package moe.gensoukyo.scriptap;

import org.bukkit.entity.Entity;
import org.serverct.ersha.jd.api.AttributeType;
import org.serverct.ersha.jd.api.BaseAttribute;

/**
 * @author ersha
 */
@Deprecated
public class TestAttribute extends BaseAttribute {

    public TestAttribute() {
        super(AttributeType.DAMAGE, "Test", "test");
    }

    @Override
    public void run(Entity attacker, Entity entity, double attributeValue) {
        throw new RuntimeException("run!");
    }

    @Override
    public void run(Entity entity, double attributeValue) {
        throw new RuntimeException("run!");
    }
}
