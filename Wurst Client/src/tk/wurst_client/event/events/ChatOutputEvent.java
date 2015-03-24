/*
 * Copyright � 2014 - 2015 | Alexander01998 | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.event.events;

public class ChatOutputEvent extends CancellableEvent
{
	private String message;
	
	public ChatOutputEvent(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	@Override
	public String getAction()
	{
		return "sending chat message";
	}
	
	@Override
	public String getComment()
	{
		return "Message: `" + message + "`";
	}
}