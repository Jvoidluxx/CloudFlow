package cloud.main.module.impl.render;

import cloud.Cloud;
import cloud.main.events.Event;
import cloud.main.events.impl.Event2D;
import cloud.main.module.Category;
import cloud.main.module.Manager;
import cloud.main.module.Module;
import cloud.main.utils.BlurUtils;
import cloud.main.utils.RenderUtil;
import cloud.main.utils.fontutils.FontRenderer;
import cloud.main.utils.settings.BooleanSetting;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class HUD extends Module {
    // settings from me
    // HUD from https://www.youtube.com/watch?v=_t2j5XoF0Pc&t=591s
    public static FontRenderer fr = new FontRenderer("Product Sans Bold", 20, Font.PLAIN, true, true);
    public BooleanSetting arraylist = new BooleanSetting("ArrayList", true);
    public BooleanSetting watermark = new BooleanSetting("Watermark", true);

    public HUD() {
        super("HUD", "Shows the ArrayList", Keyboard.KEY_NONE, Category.RENDER);
        addSettings(arraylist, watermark);
        toggled = true;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof Event2D) {
            if (watermark.isEnabled()) {
                drawWaterMark();
            }
            if (arraylist.isEnabled()) {
                drawList();
            }
        }
    }

    public void drawWaterMark() {
        fr.drawString(Cloud.name + " " + Cloud.version, 4, 4, -1);
    }

    private void drawList() {
        ScaledResolution sr = new ScaledResolution(mc);
        int yCount = 8;
        ArrayList<Module> mod = new ArrayList<>();
        for (Module m : Manager.modules) {
            if (m.isToggled()) {
                mod.add(m);
            }
        }
        mod.sort(Comparator.comparing(m -> -fr.getStringWidth(m.getName())));
        for (Module m : mod) {
            //RenderUtil.drawRoundedRectSkid3(sr.getScaledWidth()-30, yCount +2, sr.getScaledWidth()+40, yCount - 4, 5, 0xFF1F1F1F);
            fr.drawStringWithShadow(m.getName(), sr.getScaledWidth() - fr.getStringWidth(m.getName()), yCount - 2, -1);
            yCount += 12;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
