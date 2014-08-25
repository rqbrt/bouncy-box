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
		bodyDef.position.set(x + 16, y + 16);
		body = dimension.getLevel().getWorld().createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(16, 16);
		body.createFixture(box, 0);
		box.dispose();
		
		body.setUserData(this);
	}
}
