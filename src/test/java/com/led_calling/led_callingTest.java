package com.led_calling;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class led_callingTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(led_callingPlugin.class);
		RuneLite.main(args);
	}
}