package micdoodle8.mods.galacticraft.callisto.client;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.GCCoreConfigManager;
import micdoodle8.mods.galacticraft.core.GCCoreUtil;
import micdoodle8.mods.galacticraft.mars.dimension.GCMarsWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 *  All rights reserved.
 *
 */
public class GCCallistoSkyProvider extends IRenderHandler
{
	public int starGLCallList = GLAllocation.generateDisplayLists(3);
	public int glSkyList;
	public int glSkyList2;
	
	public GCCallistoSkyProvider()
	{
		GL11.glPushMatrix();
		GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
		this.renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		final Tessellator tessellator = Tessellator.instance;
		this.glSkyList = this.starGLCallList + 1;
		GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
		final byte byte2 = 10;
		final int i = 256 / byte2 + 2;
		float f = 3F;

		for (int j = -byte2 * i; j <= byte2 * i; j += byte2) {
			for (int l = -byte2 * i; l <= byte2 * i; l += byte2) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j + 0, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + byte2);
				tessellator.addVertex(j + 0, f, l + byte2);
				tessellator.draw();
			}
		}

		GL11.glEndList();
		this.glSkyList2 = this.starGLCallList + 2;
		GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
		f = -16F;
		tessellator.startDrawingQuads();

		for (int k = -byte2 * i; k <= byte2 * i; k += byte2) {
			for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
				tessellator.addVertex(k + byte2, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + byte2);
				tessellator.addVertex(k + byte2, f, i1 + byte2);
			}
		}

		tessellator.draw();
		GL11.glEndList();
	}

	/*
	 * public void changeWorld(World world) { theWorld = world; renderStars();
	 * super.changeWorld(world); }
	 */
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		GCMarsWorldProvider gcProvider = null;
		
		if (world.provider instanceof GCMarsWorldProvider)
		{
			gcProvider = (GCMarsWorldProvider)world.provider;
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        final Vec3 var2 = this.getCustomSkyColor();
        float var3 = (float)var2.xCoord * (1 - world.getStarBrightness(partialTicks) * 2);
        float var4 = (float)var2.yCoord * (1 - world.getStarBrightness(partialTicks) * 2);
        float var5 = (float)var2.zCoord * (1 - world.getStarBrightness(partialTicks) * 2);
        float var8;

        if (mc.gameSettings.anaglyph)
        {
            final float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            final float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
            var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
            var3 = var6;
            var4 = var7;
            var5 = var8;
        }

        GL11.glColor3f(var3, var4, var5);
        final Tessellator var23 = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(var3, var4, var5);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        final float var9;
        float var10;
        float var11;
        float var12;

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glPushMatrix();
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
        GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        var12 = 8.0F;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/terrain/sun.png"));
        var23.startDrawingQuads();
        var23.addVertexWithUV(-var12, 150.0D, -var12, 0.0D, 0.0D);
        var23.addVertexWithUV(var12, 150.0D, -var12, 1.0D, 0.0D);
        var23.addVertexWithUV(var12, 150.0D, var12, 1.0D, 1.0D);
        var23.addVertexWithUV(-var12, 150.0D, var12, 0.0D, 1.0D);
        var23.draw();
        GL11.glPopMatrix();
        // HOME:
        GL11.glPushMatrix();
        var12 = 0.4F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/micdoodle8/mods/galacticraft/core/client/planets/overworld.png"));
        final int var28 = world.getMoonPhase(partialTicks);
        final int var30 = var28 % 4;
        final int var29 = var28 / 4 % 2;
        GL11.glRotatef(GCCoreUtil.calculateEarthAngleFromOtherPlanet(world.getWorldTime(), partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-40F, 0.0F, 0.0F, 1.0F);
        var23.startDrawingQuads();
        var23.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
        var23.addVertexWithUV(var12, -100.0D, var12, 1, 1);
        var23.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
        var23.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
        var23.draw();
        GL11.glPopMatrix();
        // PHOBOS:
        if (gcProvider != null)
        {
            GL11.glPushMatrix();
            var12 = 5F;
            GL11.glScalef(0.6F, 0.6F, 0.6F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/micdoodle8/mods/galacticraft/mars/client/planets/phobos.png"));
            GL11.glRotatef(gcProvider.calculatePhobosAngle(world.getWorldTime(), partialTicks), 1.0F, 1.0F, 0.0F);
            GL11.glRotatef(-70F, 0.0F, 0.0F, 1.0F);
            var23.startDrawingQuads();
            var23.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
            var23.addVertexWithUV(var12, -100.0D, var12, 1, 1);
            var23.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
            var23.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
            var23.draw();
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
	        var12 = 2.0F;
	        GL11.glScalef(0.6F, 0.6F, 0.6F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/micdoodle8/mods/galacticraft/mars/client/planets/deimos.png"));
	        GL11.glRotatef(-gcProvider.calculateDeimosAngle(world.getWorldTime(), partialTicks) - 100F, 1.0F, 1.0F, 0.0F);
	        var23.startDrawingQuads();
	        var23.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
	        var23.addVertexWithUV(var12, -100.0D, var12, 1, 1);
	        var23.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
	        var23.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
	        var23.draw();
	        GL11.glPopMatrix();
        }
        // DEIMOS:
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        final float var20 = world.getStarBrightness(partialTicks) + 0.2F;

        if (var20 > 0.0F)
        {
            GL11.glColor4f(var20, var20, var20, var20);
            GL11.glCallList(this.starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        final double var25 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

        if (var25 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var25 + 65.0D));
            var12 = -var10;
            var23.startDrawingQuads();
            var23.setColorRGBA_I(0, 255);
            var23.addVertex(-var10, var11, var10);
            var23.addVertex(var10, var11, var10);
            var23.addVertex(var10, var12, var10);
            var23.addVertex(-var10, var12, var10);
            var23.addVertex(-var10, var12, -var10);
            var23.addVertex(var10, var12, -var10);
            var23.addVertex(var10, var11, -var10);
            var23.addVertex(-var10, var11, -var10);
            var23.addVertex(var10, var12, -var10);
            var23.addVertex(var10, var12, var10);
            var23.addVertex(var10, var11, var10);
            var23.addVertex(var10, var11, -var10);
            var23.addVertex(-var10, var11, -var10);
            var23.addVertex(-var10, var11, var10);
            var23.addVertex(-var10, var12, var10);
            var23.addVertex(-var10, var12, -var10);
            var23.addVertex(-var10, var12, -var10);
            var23.addVertex(-var10, var12, var10);
            var23.addVertex(var10, var12, var10);
            var23.addVertex(var10, var12, -var10);
            var23.draw();
        }

        GL11.glColor3f(70F / 256F, 0F / 256F, 0F / 256F);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -((float)(var25 - 16.0D)), 0.0F);
        GL11.glCallList(this.glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
	}

    private void renderStars()
    {
    	final Random var1 = new Random(10842L);
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();

        for (int var3 = 0; var3 < (GCCoreConfigManager.moreStars ? 20000 : 6000); ++var3)
        {
            double var4 = var1.nextFloat() * 2.0F - 1.0F;
            double var6 = var1.nextFloat() * 2.0F - 1.0F;
            double var8 = var1.nextFloat() * 2.0F - 1.0F;
            final double var10 = 0.15F + var1.nextFloat() * 0.1F;
            double var12 = var4 * var4 + var6 * var6 + var8 * var8;

            if (var12 < 1.0D && var12 > 0.01D)
            {
                var12 = 1.0D / Math.sqrt(var12);
                var4 *= var12;
                var6 *= var12;
                var8 *= var12;
                final double var14 = var4 * (GCCoreConfigManager.moreStars ? var1.nextDouble() * 100D + 150D : 100.0D);
                final double var16 = var6 * (GCCoreConfigManager.moreStars ? var1.nextDouble() * 100D + 150D : 100.0D);
                final double var18 = var8 * (GCCoreConfigManager.moreStars ? var1.nextDouble() * 100D + 150D : 100.0D);
                final double var20 = Math.atan2(var4, var8);
                final double var22 = Math.sin(var20);
                final double var24 = Math.cos(var20);
                final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
                final double var28 = Math.sin(var26);
                final double var30 = Math.cos(var26);
                final double var32 = var1.nextDouble() * Math.PI * 2.0D;
                final double var34 = Math.sin(var32);
                final double var36 = Math.cos(var32);

                for (int var38 = 0; var38 < 4; ++var38)
                {
                    final double var39 = 0.0D;
                    final double var41 = ((var38 & 2) - 1) * var10;
                    final double var43 = ((var38 + 1 & 2) - 1) * var10;
                    final double var47 = var41 * var36 - var43 * var34;
                    final double var49 = var43 * var36 + var41 * var34;
                    final double var53 = var47 * var28 + var39 * var30;
                    final double var55 = var39 * var28 - var47 * var30;
                    final double var57 = var55 * var22 - var49 * var24;
                    final double var61 = var49 * var22 + var55 * var24;
                    var2.addVertex(var14 + var57, var16 + var53, var18 + var61);
                }
            }
        }

        var2.draw();
    }

    private Vec3 getCustomSkyColor()
    {
        return Vec3.vec3dPool.getVecFromPool(0D, 0D, 0.0D);
    }
    
    public float getSkyBrightness(float par1)
    {
        final float var2 = FMLClientHandler.instance().getClient().theWorld.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.sin(var2 * (float)Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 1F;
    }
}
