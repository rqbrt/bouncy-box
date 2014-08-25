package com.rqbrt.ld30.level.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.Dimension;
import com.rqbrt.ld30.level.Entity;

public class Tile extends Entity {
	
	protected Dimension dimension;
	public int id;
	public Sprite sprite;
	
	public Tile(Dimension dimension, int id, Texture texture, float x, float y) {
		super(x, y, 32, 32);
		this.dimension = dimension;
		this.id = id;
		
		bodyDef = new BodyDef();
		
		if(texture != null) {
			sprite = new Sprite(texture);
			sprite.setPosition(x, y);
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(sprite != null) {
			g.batch.begin();
			sprite.draw(g.batch);
			g.batch.end();
		}
	}
}
