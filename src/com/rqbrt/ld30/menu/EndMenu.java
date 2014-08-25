package com.rqbrt.ld30.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.Sounds;
import com.rqbrt.ld30.graphics.Graphics;

public class EndMenu extends Menu {
	
	private String msg1 = "You weren't supposed to beat all of my torture chambers! >:(";
	
	private String stats = "Stats:";
	
	private String pressEnter = "Press enter to return to the main menu.";
	private String msg2 = "Thanks for playing!";
	
	private long startTime;
	private float delay = 0.5f;

	public EndMenu(Game game) {
		super(game);
		startTime = System.nanoTime();
	}
	
	@Override
	public void update() {
		if((System.nanoTime() - startTime) / 1000000000 > delay) {
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				Sounds.SELECT.play();
				game.setMenu(new MainMenu(game));
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.batch.begin();
		g.font.setColor(new Color(1, 1, 1, 1));
		g.font.setScale(1);
		g.font.draw(g.batch, msg1, (Game.WIDTH - g.textWidth(msg1, 1)) / 2, Game.HEIGHT - 10);
		
		g.font.draw(g.batch, stats, (Game.WIDTH - g.textWidth(stats, 1)) / 2, Game.HEIGHT - 100);
		
		float levelY = Game.HEIGHT - 125;
		for(int i = 0; i < game.stats.length; i++) {
			g.font.draw(g.batch, "Level " + (i + 1) + ": " + game.stats[i][0] + " deaths in " + game.stats[i][1], 400, levelY);
			levelY -= 20;
		}
		
		g.font.draw(g.batch, msg2, (Game.WIDTH - g.textWidth(msg2, 1)) / 2, 30);
		g.font.draw(g.batch, pressEnter, (Game.WIDTH - g.textWidth(pressEnter, 1)) / 2, 15);
		g.batch.end();
	}
}
