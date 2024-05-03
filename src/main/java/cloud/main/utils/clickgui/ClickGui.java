package cloud.main.utils.clickgui;

import cloud.main.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {
    // clickgui not made by me
    // https://www.youtube.com/watch?v=5SzDHWC4izc
    // added things that might make it look better
    public List<Frame> frames;
    public static final ClickGui INSTANCE = new ClickGui();
    public ClickGui() {
        frames = new ArrayList<>();
        int offset = 20;
        for (Category c : Category.values()) {
            frames.add(new Frame(c, offset, 20, 100, 20));
            offset += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Frame f : frames) {
            f.onRender(mouseX, mouseY, partialTicks);
            f.updatePos(mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame f : frames) {
            f.onMouseClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame f : frames) {
            f.onMouseRelease(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
