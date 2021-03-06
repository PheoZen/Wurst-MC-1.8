/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.features.mods;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import tk.wurst_client.events.listeners.RenderListener;
import tk.wurst_client.features.Feature;
import tk.wurst_client.utils.RenderUtils;

@Mod.Info(description = "Allows you to see mobs through walls.",
	name = "MobESP",
	tags = "mob esp",
	help = "Mods/MobESP")
@Mod.Bypasses
public final class MobEspMod extends Mod implements RenderListener
{
	private static final AxisAlignedBB MOB_BOX =
		new AxisAlignedBB(-0.5, 0, -0.5, 0.5, 1, 0.5);
	
	@Override
	public Feature[] getSeeAlso()
	{
		return new Feature[]{wurst.mods.playerEspMod, wurst.mods.itemEspMod,
			wurst.mods.prophuntEspMod};
	}
	
	@Override
	public void onEnable()
	{
		wurst.events.add(RenderListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(RenderListener.class, this);
	}
	
	@Override
	public void onRender(float partialTicks)
	{
		// GL settings
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(2);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glPushMatrix();
		GL11.glTranslated(-mc.getRenderManager().renderPosX,
			-mc.getRenderManager().renderPosY,
			-mc.getRenderManager().renderPosZ);
		
		// draw boxes
		for(Entity entity : mc.world.loadedEntityList)
		{
			if(!(entity instanceof EntityLiving))
				continue;
			
			if(!wurst.special.targetSpf.invisibleMobs.isChecked()
				&& entity.isInvisible())
				continue;
			
			GL11.glPushMatrix();
			
			// set color
			float factor = mc.player.getDistanceToEntity(entity) / 20F;
			if(factor > 2)
				factor = 2;
			GL11.glColor4f(2 - factor, factor, 0, 0.5F);
			
			// set position
			GL11.glTranslated(
				entity.prevPosX
					+ (entity.posX - entity.prevPosX) * partialTicks,
				entity.prevPosY
					+ (entity.posY - entity.prevPosY) * partialTicks,
				entity.prevPosZ
					+ (entity.posZ - entity.prevPosZ) * partialTicks);
			
			// set size
			double boxWidth = entity.width + 0.1;
			double boxHeight = entity.height + 0.1;
			GL11.glScaled(boxWidth, boxHeight, boxWidth);
			
			// draw box
			RenderUtils.drawOutlinedBox(MOB_BOX);
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		
		// GL resets
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
}
