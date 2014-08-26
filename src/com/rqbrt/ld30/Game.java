package com.rqbrt.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.Level;
import com.rqbrt.ld30.menu.MainMenu;
import com.rqbrt.ld30.menu.Menu;

public class Game extends ApplicationAdapter {
	
	public static final String TITLE = "Bouncy Box";
	public static final int WIDTH = 960;
	public static final int HEIGHT = 544;
	
	private Graphics graphics;
	private Menu menu;
	private Level level;
	
	public String[][] stats = new String[][] {
			new String[]{"0", "0s"},		// level 1
			new String[]{"0", "0s"},		// level 2
			new String[]{"0", "0s"},		// level 3
			new String[]{"0", "0s"},		// level 4
			new String[]{"0", "0s"},		// level 5
			new String[]{"0", "0s"},		// level 6
	};
	
	@Override
	public void create() {
		graphics = new Graphics();
		menu = new MainMenu(this);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		graphics.update();
		
		if(menu != null) menu.update();
		if(level != null) level.update();
		if(level != null) level.render(graphics);
		if(menu != null) menu.render(graphics);
	}
	
	@Override
	public void dispose() {
		Assets.dispose();
		Sounds.dispose();
		graphics.dispose();
		super.dispose();
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
}
