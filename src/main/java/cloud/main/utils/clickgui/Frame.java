package cloud.main.utils.clickgui;

import cloud.main.module.Category;
import cloud.main.module.Manager;
import cloud.main.module.Module;
import cloud.main.utils.RenderUtil;
import cloud.main.utils.clickgui.components.Component;
import cloud.main.utils.fontutils.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    public static FontRenderer fr = new FontRenderer("Product Sans Bold", 20, Font.PLAIN, true, true);
    public int x, y, width, height, dragX, dragY;
    public Category category;
    public boolean dragging, extended;
    public List<ModuleButton> buttons;
    public Frame(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dragging = false;
        extended = false;

        buttons = new ArrayList<>();
        int offset = height;
        for (Module mb : Manager.getModuleInCategory(category)) {
            buttons.add(new ModuleButton(mb, offset, this));
            offset += height;
        }

    }
    public void onRender(int mouseX, int mouseY, float partialTicks) {
        if (extended) {
            RenderUtil.drawRoundedRectSkid3(x, y, x+width, y+height, 5, 0xFFFF4F4F);
        }else {
            RenderUtil.drawRoundedRectSkid2(x, y, width, height, 5, 0xFFFF4F4F);
        }
        fr.drawString(category.displayName, x+2, y+2, -1);
        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.onRender(mouseX, mouseY, partialTicks);
            }
        }
    }
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        } else if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            extended = !extended;
        }
        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.onMouseClick(mouseX, mouseY, mouseButton);
            }
        }
    }
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && dragging) {
            dragging = false;
        }
        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }
    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height;
    }
    public void updatePos(int mouseX, int mouseY) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
    }

    public void updateButtons() {
        int offset = height;
        for (ModuleButton mb : buttons) {
            mb.offset = offset;
            offset += height;
            if (mb.extended) {
                for (Component component : mb.components) {
                    if (component.setting.isVisible()) offset+=height;
                }
            }
        }
    }
}
