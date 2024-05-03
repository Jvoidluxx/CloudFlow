package cloud.main.module.impl.combat;

import cloud.main.events.Event;
import cloud.main.events.impl.EventMotion;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.RotationUtil;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BowAimBot extends Module {
    private NumberSetting range = new NumberSetting("Range", 5, 10, 20, 1);
    public BowAimBot() {
        super("Bow AimBot", "Makes you aim at the nearest target with a bow", 0, Category.COMBAT);
    }

    @Override
    public void onEvent(Event event) { // VERY VERY BUGGY
        if (!(event instanceof EventMotion))
            return;

        EventMotion motionEvent = (EventMotion) event;
        List<EntityLivingBase> targets = mc.theWorld.loadedEntityList.stream()
                .filter(EntityLivingBase.class::isInstance)
                .map(EntityLivingBase.class::cast)
                .filter(entity -> entity != mc.thePlayer && entity.isEntityAlive())
                .filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < range.getValue() && entity.getHealth() > 0)
                .sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)))
                .collect(Collectors.toList());

        //List<Item> bows = getBowsInInventory();

        //if (bows.isEmpty())
            //return;

        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
            // check if he is using the bow
            if (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
                if (!targets.isEmpty()) {
                    EntityLivingBase target = targets.get(0);
                    float[] angles = RotationUtil.getBowAngles(target); // get angles to hit the player
                    mc.thePlayer.rotationYawHead = angles[0];
                    mc.thePlayer.renderYawOffset = angles[0];
                    motionEvent.setYaw(angles[0]);
                    motionEvent.setPitch(angles[1]);
                }
            }
        }
    }


    /*private List<Item> getBowsInInventory() {
        List<Item> bows = new ArrayList<>();
        for (ItemStack itemStack : mc.thePlayer.inventory.mainInventory) {
            if (itemStack != null && itemStack.getItem() instanceof ItemBow) {
                bows.add(itemStack.getItem());
            }
        }
        return bows;
    }*/
}
