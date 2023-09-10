package com.led_calling;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Old School RuneScape LED calling tool"
)
public class led_callingPlugin extends Plugin
{
	private int threshold = 73;
	private int previous;
	@Inject
	private Client client;

	@Inject
	private led_callingConfig config;

	@Override
	protected void startUp() throws Exception
	{
		previous = client.getBoostedSkillLevel(Skill.PRAYER);
		//log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	led_callingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(led_callingConfig.class);
	}
	@Subscribe
	public void onGameTick(GameTick event) {
		checkPrayerPoints();
	}

	private void checkPrayerPoints() {
		int prayerPoints = client.getBoostedSkillLevel(Skill.PRAYER);
		if (prayerPoints != previous && prayerPoints < threshold) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "IMissMe", "increase your prayer" , null);
			previous = prayerPoints;
		}
	}
}
