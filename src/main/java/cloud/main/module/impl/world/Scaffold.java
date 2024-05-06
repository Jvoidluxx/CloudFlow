package cloud.main.module.impl.world;

import cloud.main.events.Event;
import cloud.main.events.impl.EventMotion;
import cloud.main.events.impl.EventPacket;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.PacketUtil;
import cloud.main.utils.PlayerUtil;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    // settings from me
    // https://www.youtube.com/watch?v=1N0NmZXetek
    // scaffold from superblaubeere27
    public ModeSetting tower = new ModeSetting("Tower", "Legit", "Legit", "Vanilla", "NCP");
    private NumberSetting timer = new NumberSetting("Timer", 0.1, 10, 1.0, 0.1);
    public BooleanSetting placeChests = new BooleanSetting("PlaceChests", false);
    public Scaffold() {
        super("Scaffold", "Bridges for you", Keyboard.KEY_G, Category.WORLD);
        addSettings(tower, timer, placeChests);
    }
    private BlockPos currentPos;
    private EnumFacing currentFacing;

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMotion || e instanceof EventPacket) {
            mc.timer.timerSpeed = timer.getValueFloat();
            currentPos = null;
            currentFacing = null;
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                setBlockAndFacing(pos);
                if (currentPos != null) {
                    float[] facing = getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);
                    float yaw = facing[0];
                    float pitch = Math.max(90, facing[1]);

                    mc.thePlayer.renderYawOffset = yaw;
                    mc.thePlayer.rotationYawHead = yaw;
                    mc.thePlayer.rotationPitchHead = pitch;

                    if (e instanceof EventMotion) {
                        ((EventMotion)e).setYaw(yaw);
                        ((EventMotion)e).setPitch(pitch);
                    }


                    if (currentPos != null) {
                        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                            ItemBlock chest = (ItemBlock) mc.thePlayer.getCurrentEquippedItem().getItem();
                            if (chest.getBlock() instanceof BlockChest) return;
                            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), currentPos, currentFacing, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()))) {
                                switch (tower.getMode()) {
                                    case "Legit":
                                        break;
                                    case "Vanilla":
                                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                                            mc.thePlayer.jump();
                                        }
                                        break;
                                    case "NCP":
                                        if (mc.gameSettings.keyBindJump.isKeyDown() && PlayerUtil.blockNear(2)) {
                                            PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));
                                            if (Math.abs(mc.thePlayer.posY - Math.floor(mc.thePlayer.posY)) <= 0.00153598) {
                                                mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                                                mc.thePlayer.motionY = 0.42F;
                                            } else if (Math.abs(mc.thePlayer.posY - Math.floor(mc.thePlayer.posY)) < 0.1 && mc.thePlayer.offGroundTicks != 0) {
                                                mc.thePlayer.motionY = 0;
                                                mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                                            }
                                        }
                                        break;
                                }
                                mc.thePlayer.swingItem();
                            }
                        }
                    }
                }
            }
        }
    }
    private void setBlockAndFacing(BlockPos var1) {
        if (mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        }  else if (mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air){
            this.currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.SOUTH;
        } else {
            currentPos = null;
            currentFacing = null;
        }
    }

    private float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(mc.theWorld);
        var4.posX = (double) var0 + 0.5D;
        var4.posY = (double) var1 + 0.5D;
        var4.posZ = (double) var2 + 0.5D;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25D;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity(var4);
    }
    private float[] getDirectionToEntity(Entity var0) {
        return new float[] { getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch };
    }
    private float getYaw(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5;
        if (var3 < 0.0D && var1 < 0.0D) {
            var5 = 90.0D + Math.toDegrees(Math.atan(var3 / var1));
        } else if (var3 < 0.0D && var1 > 0.0D) {
            var5 = -90.0D + Math.toDegrees(Math.atan(var3 / var1));
        }else{
            var5 = Math.toDegrees(-Math.atan(var1 / var3));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) var5));
    }
    private float getPitch(Entity var0) {
        double var1 = var0.posX - mc.thePlayer.posX;
        double var3 = var0.posZ - mc.thePlayer.posZ;
        double var5 = var0.posY - 1.6D + (double) var0.getEyeHeight() - mc.thePlayer.posY;
        double var7 = (double) MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) var9);
    }
}
