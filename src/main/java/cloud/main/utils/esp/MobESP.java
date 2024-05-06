package cloud.main.utils.esp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class MobESP {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void entityESPBox(Entity entity, int mode, float width) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        switch (mode) {
            case 0: // enemy
                GL11.glColor4f(
                        1-mc.thePlayer.getDistanceToEntity(entity) / 40,
                        mc.thePlayer.getDistanceToEntity(entity) / 40,
                        0,0.5f);
            case 1: // friend
                GL11.glColor4f(0, 0,1, 0.5f);
            case 2: // Other
                GL11.glColor4f(1, 1,0, 0.5f);
            case 3:// Target
                GL11.glColor4f(1, 0,0, 0.5f);
            case 4: // Team
                GL11.glColor4f(0, 1,0, 0.5f);


        }
        mc.getRenderManager();
        RenderGlobal.drawSelectionBoundingBox(
                new AxisAlignedBB(
                        entity.boundingBox.minX
                        - 0.05
                        - entity.posX
                        + (entity.posX - RenderManager.renderPosX),
                        entity.boundingBox.minY
                                - 0.05
                                - entity.posY
                                + (entity.posY - RenderManager.renderPosY),
                        entity.boundingBox.minZ
                                - 0.05
                                - entity.posZ
                                + (entity.posZ - RenderManager.renderPosZ),
                        entity.boundingBox.maxX
                        + 0.05
                        - entity.posX
                        + (entity.posX - RenderManager.renderPosX),
                        entity.boundingBox.maxY
                                + 0.1
                                - entity.posY
                                + (entity.posY - RenderManager.renderPosY),
                        entity.boundingBox.maxZ
                                + 0.05
                                - entity.posZ
                                + (entity.posZ - RenderManager.renderPosZ)));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
