package com.rqbrt.ld30.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.Sounds;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.factory.LevelFactory;

public class MainMenu extends Menu {
	
	private Color background;
	
	private String pressEnter = "Press enter to start...";
	
	private String controlsHeader = "Controls:";
	private String jump = "W to jump";
	private String move = "A to move left and D to move right";
	private String rotate = "LEFT ARROW to rotate left and RIGHT ARROW to rotate right";
	private String tab = "SPACE to swap the dimension";
	private String back = "BACKSPACE to restart the current level";
	
	private String objectiveHeader = "Objective:";
	private String objective = "Get from the green tile to the blue tile.";
	
	private long startTime;
	private float delay = 0.5f;

	public MainMenu(Game game) {
		super(game);
		
		background = new Color(0.4f, 0.2f, 0.5f, 1.0f);
		startTime = System.nanoTime();
	}
	
	@Override
	public void update() {
		if((System.nanoTime() - startTime) / 100000000 > delay) {
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				Sounds.SELECT.play();
				game.setMenu(null);
				game.setLevel(LevelFactory.createLevel(game, "levels/1.json"));
			}
		}
	}
	
	@Override
	public void render(Graphics g) {		
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(background);
		g.shapeRenderer.rect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.shapeRenderer.end();
		
		g.batch.begin();
		g.font.setScale(1);
		g.font.setColor(new Color(1, 1, 1, 1));
		g.font.draw(g.batch, Game.TITLE, (Game.WIDTH - g.textWidth(Game.TITLE, 1)) / 2, Game.HEIGHT - 100);
		g.font.draw(g.batch, pressEnter, (Game.WIDTH - g.textWidth(pressEnter, 1)) / 2, Game.HEIGHT - 200);
		
		g.font.draw(g.batch, controlsHeader, (Game.WIDTH - g.textWidth(controlsHeader, 1)) / 2, (Game.HEIGHT / 2) - 25);
		g.font.draw(g.batch, move, (Game.WIDTH - g.textWidth(move, 1)) / 2, (Game.HEIGHT / 2) - 50);
		g.font.draw(g.batch, rotate, (Game.WIDTH - g.textWidth(rotate, 1)) / 2, (Game.HEIGHT / 2) - 75);
		g.font.draw(g.batch, jump, (Game.WIDTH - g.textWidth(jump, 1)) / 2, (Game.HEIGHT / 2) - 100);
		g.font.draw(g.batch, tab, (Game.WIDTH - g.textWidth(tab, 1)) / 2, (Game.HEIGHT / 2) - 125);
		g.font.draw(g.batch, back, (Game.WIDTH - g.textWidth(back, 1)) / 2, (Game.HEIGHT / 2) - 150);
		
		g.font.draw(g.batch, objectiveHeader, (Game.WIDTH - g.textWidth(objectiveHeader, 1)) / 2, (Game.HEIGHT / 2) - 200);
		g.font.draw(g.batch, objective, (Game.WIDTH - g.textWidth(objective, 1)) / 2, (Game.HEIGHT / 2) - 225);
		g.batch.end();
		
		super.render(g);
	}
}
