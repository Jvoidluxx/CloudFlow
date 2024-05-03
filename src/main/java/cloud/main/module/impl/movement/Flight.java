package cloud.main.module.impl.movement;

import cloud.main.events.Event;
import cloud.main.events.impl.EventMotion;
import cloud.main.events.impl.EventPacket;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.MoveUtil;
import cloud.main.utils.TimerUtil;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Flight extends Module {
    private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla");
    // made by me
    private NumberSetting speed = new NumberSetting("Speed", 0.1f, 5.0f, 1f, 0.001f);

    public Flight() {
        super("Flight", "Allows you to fly", 0, Category.MOVEMENT);
        addSettings(mode, speed);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMotion) {
            switch (mode.getMode()) {
                case "Vanilla": {
                    mc.thePlayer.capabilities.isFlying = true;
                    mc.thePlayer.capabilities.allowFlying = true;
                    MoveUtil.setSpeed(speed.getValueFloat());
                    break;

                }
                case "Vulcan": { // Doesnt work :sob:, tried testing
                    // get blocks in inventory
                    /*for (ItemStack item : mc.thePlayer.inventory.mainInventory) {
                        if (item == null) continue;
                        if (item.getItem() instanceof ItemBlock) {
                            ((EventMotion) e).setPitch(90f);
                            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(pos, EnumFacing.DOWN.ordinal(), item, pos.getX(), pos.getY() - 1, pos.getZ()));
                        }
                    }
                    if (MoveUtil.isMoving()) {
                        if (TimerUtil.HTE(100, true)) {
                            //((EventMotion) e).setY(mc.thePlayer.posY + 0.5f);
                            mc.thePlayer.posY = mc.thePlayer.posY + 1.5f;
                        } else {
                            //((EventMotion) e).setY(mc.thePlayer.posY - 0.5f);
                            mc.thePlayer.posY = mc.thePlayer.posY - 0.5f;
                            MoveUtil.setSpeed(0.3f);
                        }
                        break;
                    }*/
                }
            }
        }
    }
}
