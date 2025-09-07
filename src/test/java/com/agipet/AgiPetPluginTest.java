package com.agipet;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AgiPetPluginTest
{
	public static void main(String[] args) throws Exception
	{
        ExternalPluginManager.loadBuiltin(AgiPetPlugin.class);
        RuneLite.main(args);
	}
}