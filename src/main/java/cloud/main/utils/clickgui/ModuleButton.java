package cloud.main.utils.clickgui;

import cloud.main.module.Module;
import cloud.main.utils.RenderUtil;
import cloud.main.utils.clickgui.Frame;
import cloud.main.utils.clickgui.components.CheckBox;
import cloud.main.utils.clickgui.components.Component;
import cloud.main.utils.clickgui.components.ModeBox;
import cloud.main.utils.clickgui.components.Slider;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import cloud.main.utils.settings.Setting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    public Module module;
    public int offset;
    public Frame frame;
    public List<Component> components;
    public boolean extended;

    public ModuleButton(Module module, int offset, Frame frame) {
        this.module = module;
        this.offset = offset;
        this.frame = frame;
        this.extended = false;

        int setOffset = frame.height;
        this.components = new ArrayList<>();
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, setOffset, this));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, setOffset, this));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, setOffset, this));
            }
            setOffset += frame.height;
        }
    }
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRectBetter(frame.x, frame.y+offset, frame.x+frame.width, frame.y+frame.height+offset, 0xFF4F4F4F);
        frame.fr.drawString(module.getName(), frame.x+2, frame.y+offset+2, module.isToggled() ? -1 : -5592406);
        if (extended) {
            for (Component c : components) {
                c.onRender(mouseX, mouseY, partialTicks);
            }
        }
    }
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            module.toggle();
        }
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            extended = !extended;
            frame.updateButtons();
        }
        for (Component c : components) {
            c.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (extended) {
            for (Component c : components) {
                c.onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }
    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > frame.x && mouseX < frame.x+frame.width && mouseY > frame.y + offset && mouseY < frame.y+frame.height + offset;
    }
}
