/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.features.commands;

import java.util.Iterator;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.StringUtils;
import tk.wurst_client.alts.Alt;
import tk.wurst_client.files.ConfigFiles;
import tk.wurst_client.gui.alts.GuiAltList;
import tk.wurst_client.utils.ChatUtils;

@Cmd.Info(
	description = "Adds a player or all players on a server to your alt list.",
	name = "addalt",
	syntax = {"<player>", "all"})
public final class AddAltCmd extends Cmd
{
	@Override
	public void execute(String[] args) throws CmdError
	{
		if(args.length != 1)
			syntaxError();
		if(args[0].equals("all"))
		{
			int alts = 0;
			Iterator itr = mc.getNetHandler().getPlayerInfoMap().iterator();
			while(itr.hasNext())
			{
				NetworkPlayerInfo info = (NetworkPlayerInfo)itr.next();
				String crackedName =
					StringUtils.stripControlCodes(info.getPlayerNameForReal());
				if(crackedName.equals(mc.player.getName())
					|| crackedName.equals("Alexander01998") || GuiAltList.alts
						.contains(new Alt(crackedName, null, null)))
					continue;
				GuiAltList.alts.add(new Alt(crackedName, null, null));
				alts++;
			}
			if(alts == 1)
				ChatUtils.message("Added 1 alt to the alt list.");
			else
				ChatUtils.message("Added " + alts + " alts to the alt list.");
			GuiAltList.sortAlts();
			ConfigFiles.ALTS.save();
		}else if(!args[0].equals("Alexander01998"))
		{
			GuiAltList.alts.add(new Alt(args[0], null, null));
			GuiAltList.sortAlts();
			ConfigFiles.ALTS.save();
			ChatUtils.message("Added \"" + args[0] + "\" to the alt list.");
		}
	}
}
