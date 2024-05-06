package cloud.main.module.impl.render;

import cloud.main.events.Event;
import cloud.main.events.impl.Event2D;
import cloud.main.events.impl.EventRender;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.esp.MobESP;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Module {
    public BooleanSetting mobs = new BooleanSetting("Mobs", true);
    public BooleanSetting players = new BooleanSetting("Players", true);
    public NumberSetting width = new NumberSetting("Width", 0.1f, 10.0f, 2.0f, 0.1f);
    public ESP() {
        super("ESP", "Shows you where entities are at", 0, Category.RENDER);
        addSettings(mobs, players, width);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            for (Entity p : mc.theWorld.loadedEntityList) {
                if (p == mc.thePlayer) continue;
                if (p instanceof EntityPlayer && players.isEnabled()) {
                    MobESP.entityESPBox(p, 0, width.getValueFloat());
                }
                if (p instanceof EntityMob && mobs.isEnabled()) {
                    MobESP.entityESPBox(p, 2, width.getValueFloat());
                }
            }
        }
    }
}
