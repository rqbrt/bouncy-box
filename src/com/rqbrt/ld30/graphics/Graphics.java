package com.rqbrt.ld30.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rqbrt.ld30.Game;

public class Graphics {

	public OrthographicCamera camera;
	
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	
	public Graphics() {
		camera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		camera.position.set(Game.WIDTH / 2, Game.HEIGHT / 2, 0);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
	}
	
	public void update() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
	}
	
	public float textWidth(String text, float scale) {
		font.setScale(scale);
		return font.getBounds(text).width;
	}
	
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
