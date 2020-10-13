package moe.gensoukyo.scriptap;

import noppes.npcs.api.entity.IEntity;
import noppes.npcs.controllers.ScriptController;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffectType;
import org.serverct.ersha.jd.api.AttributeType;
import org.serverct.ersha.jd.api.BaseAttribute;

/**
 * @author Chloe_koopa
 */
public class ScriptAttribute extends BaseAttribute {
    public ScriptAttribute(AttributeType attributeType,
                           String funcName) {
        super(attributeType, funcName, funcName);
    }

    public static final String FUNC_PREFIX = "ap_";

    @Override
    public void run(Entity damager, Entity entity, double value) {
        ApAttributeEvent event = new ApAttributeEvent(damager, entity, this, value);
        ScriptController.Instance.forgeScripts.runScript(
                FUNC_PREFIX + this.getAttributeName(), event);

        super.run(damager, entity, value);
    }

    @Override
    public void run(Entity entity, double attributeValue) {
        this.run(null, entity, attributeValue);
    }

    //保护方法公开化

    @Override
    public void setPotion(PotionEffectType potion) {
        super.setPotion(potion);
    }

    @Override
    public void setProbabilityAttribute(boolean b){
        super.setProbabilityAttribute(b);
    }

    @Override
    protected void setEntityMessage(String string) {
        super.setEntityMessage(string);
    }

    @Override
    protected void setDamagerMessage(String string) {
        super.setDamagerMessage(string);
    }
}
