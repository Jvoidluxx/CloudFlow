package cloud.main.module.impl.render;

import cloud.main.events.Event;
import cloud.main.events.impl.Event2D;
import cloud.main.events.impl.EventRender;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.esp.BlockESPUtils;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.tileentity.TileEntityChest;

public class ChestESP extends Module {
    public NumberSetting width = new NumberSetting("Width", 0.1f, 10.0f, 2.0f, 0.1f);
    public ChestESP() {
        super("ChestESP", "Shows you the location of chests", 0, Category.RENDER);
        addSetting(width);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRender) {
            for (Object o : mc.theWorld.loadedTileEntityList) {
                if (o instanceof TileEntityChest) {
                    BlockESPUtils.blockESPBox(((TileEntityChest) o).getPos(), width.getValueFloat());
                }
            }
        }
    }
}
