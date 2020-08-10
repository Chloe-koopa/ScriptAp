package moe.gensoukyo.scriptap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.serverct.ersha.jd.api.AttributeType;
import org.serverct.ersha.jd.api.BaseAttribute;

import java.util.*;

/**
 * @author Chloe_koopa
 */
@SuppressWarnings("unused")
public class ScriptApPlugin extends JavaPlugin {
    private final List<BaseAttribute> addedAttrs = new LinkedList<>();

    public static final String PATH_OF_VALUES = "attrs";

    @Override
    public void onEnable() {
        this.onReload();
        super.onEnable();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void onReload() {
        this.reloadConfig();
        addedAttrs.clear();
        ConfigurationSection section = this.getConfig()
                .getConfigurationSection(PATH_OF_VALUES);
        if (section == null) {
            return;
        }
        Map<String, String> values = (Map<String, String>) (Map)
                section.getValues(false);
        values.forEach((name, type) -> {
            try {
                BaseAttribute attr = new ScriptAttribute(AttributeType.valueOf(type), name);
                attr.registerAttribute();
                addedAttrs.add(attr);
            } catch (IllegalArgumentException e) {
                this.getLogger().warning(String.format("Unknown type for attribute %s : %s", name, type));
            }
        });
    }

    @Override
    public void onDisable() {
        saveToConfig();
        super.onDisable();
    }

    protected void saveToConfig() {
        ConfigurationSection values = this.getConfig().createSection(PATH_OF_VALUES);
        addedAttrs.forEach(attribute ->
                values.set(attribute.getAttributeName(), attribute.getAttributeType().name())
        );
        saveConfig();
    }

    private static final String CMD_DEFINE_ATTR = "defineattr";
    private static final String CMD_PLUGIN = "scriptap";
    private static final String CMD_RELOAD_ARG2 = "reload";
    private static final String CMD_LIST_ATTRS = "list";
    private static final String CMD_SAVE = "save";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CMD_DEFINE_ATTR.equalsIgnoreCase(command.getName())) {
            return onDefineAttr(sender, args);
        } else if (CMD_PLUGIN.equalsIgnoreCase(command.getName())) {
            return onPluginCommand(sender, args);
        }
        return super.onCommand(sender, command, label, args);
    }

    private boolean onDefineAttr(CommandSender sender, String[] args) {
        if ((args.length < 1) || (args.length > 2)) {
            return false;
        }

        final String name = args[0];
        final String type = (args.length >= 2) ? args[1] : AttributeType.DAMAGE.name();

        //判断是否重复
        if (addedAttrs.stream().anyMatch(attribute ->
                (attribute.getAttributeName().equals(name))
                        && (attribute.getAttributeType().name().equals(type))
        )) {
            sender.sendMessage("Attribute already defined!");
        }

        try {
            ScriptAttribute attribute = new ScriptAttribute(AttributeType.valueOf(type), name);
            attribute.registerAttribute();
            addedAttrs.add(attribute);
            sender.sendMessage(String.format("Attribute %s(%s) has been defined!", name, type));
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cUnknown attribute type: " + type);
        }
        return true;
    }

    private boolean onPluginCommand(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return false;
        }
        switch (args[0]) {
            //reload
            case CMD_RELOAD_ARG2:
                this.onReload();
                sender.sendMessage("§aConfig reloaded!");
                return true;
            //list
            case CMD_LIST_ATTRS:
                addedAttrs.forEach(attribute -> sender.sendMessage(
                        String.format("§a\"%s\": %s",
                                attribute.getAttributeName(),
                                attribute.getAttributeType().name())));
                return true;
            //save
            case CMD_SAVE:
                this.saveToConfig();
                sender.sendMessage("§aAttributes saved.");
                return true;
            default:
                return false;
        }
    }
}