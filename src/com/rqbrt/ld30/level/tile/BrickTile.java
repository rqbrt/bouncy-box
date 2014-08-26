package com.rqbrt.ld30.level.tile;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.rqbrt.ld30.Assets;
import com.rqbrt.ld30.level.Dimension;

public class BrickTile extends Tile {
	
	public BrickTile(Dimension dimension, int id, float x, float y) {
		super(dimension, id, Assets.BRICK, x, y);
		
		solid = true;
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set((x / 32) + 0.5f, (y / 32) + 0.5f);
		body = dimension.getLevel().getWorld().createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(0.5f, 0.5f);
		body.createFixture(box, 0);
		box.dispose();
		
		body.setUserData(this);
	}
}
