package com.rqbrt.ld30.level.tile;

import com.badlogic.gdx.graphics.Color;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.Dimension;

public class SpawnTile extends BrickTile {

	public SpawnTile(Dimension dimension, int id, float x, float y) {
		super(dimension, id, x, y);
		
		allowJump = true;
		body.setUserData(this);
	}

	@Override
	public void render(Graphics g) {
		g.batch.begin();
		sprite.setColor(Color.GREEN);
		sprite.draw(g.batch);
		g.batch.end();
	}
}
