package micdoodle8.mods.galacticraft.core.dimension;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.items.GCCoreItemParachute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 *  All rights reserved.
 *
 */
public class GCCoreTeleporter extends Teleporter
{
	private final Random random;
    private final WorldServer worldServer;

	public GCCoreTeleporter(WorldServer par1WorldServer)
	{
		super(par1WorldServer);
		this.worldServer = par1WorldServer;
		this.random = new Random();
	}

    @Override
    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
		if (this.placeInExistingPortal(par1Entity, par2, par4, par6, par8))
		{
			return;
		}
		else
		{
			return;
		}
	}
    
    @Override
    public boolean func_85188_a(Entity par1Entity)
    {
    	return false;
    }

    @Override
    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        final int var9 = MathHelper.floor_double(par1Entity.posX);
        final int var10 = MathHelper.floor_double(par1Entity.posY) - 1;
        final int var11 = MathHelper.floor_double(par1Entity.posZ);
        
        par1Entity.setLocationAndAngles(var9, 250, var11, par1Entity.rotationYaw, 0.0F);
        par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
        
        par1Entity.worldObj.markBlockForUpdate(var9, 30, var11);
        
		final ItemStack stack = GCCoreUtil.getPlayerBaseServerFromPlayer((EntityPlayer) par1Entity).playerTankInventory.getStackInSlot(4);
		
		if (stack != null && stack.getItem() instanceof GCCoreItemParachute)
		{
			GCCoreUtil.getPlayerBaseServerFromPlayer((EntityPlayer) par1Entity).setParachute(true);
		}
		else
		{
			GCCoreUtil.getPlayerBaseServerFromPlayer((EntityPlayer) par1Entity).setParachute(false);
		}

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
    		FMLClientHandler.instance().getClient().gameSettings.thirdPersonView = 1;
        }
		
		return true;
	}
}
