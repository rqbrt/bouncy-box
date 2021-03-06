package com.rqbrt.ld30.level.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.Dimension;

public class SpikeTile extends Tile {

	private float angle = 0;
	
	public SpikeTile(Dimension dimension, int dir, int id, float x, float y) {
		super(dimension, id, null, x, y);
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x / 32, y / 32);
		
		angle = 0;
		if(dir == 1) angle = 180;
		if(dir == 2) angle = 90;
		if(dir == 3) angle = 270;
		
		if(dir == 1) bodyDef.position.set((x + width) / 32, (y + height) / 32);
		if(dir == 2) bodyDef.position.set((x + width) / 32, (y) / 32);
		if(dir == 3) bodyDef.position.set((x) / 32, (y + height) / 32);
		
		bodyDef.angle = (float) Math.toRadians(angle);
		
		body = dimension.getLevel().getWorld().createBody(bodyDef);
		
		PolygonShape triangle = new PolygonShape();
		
		triangle.set(new float[] {
				0, 0,
				width / 32, 0,
				(width / 2) / 32, height / 32
		});
		body.createFixture(triangle, 0);
		triangle.dispose();
		
		body.setUserData(this);
		
		Vector2 pos = body.getPosition();
		transform.translate(pos.x * 32, pos.y * 32, 0);
		transform.rotate(0, 0, 1, angle);
		transform.translate(-pos.x * 32, -pos.y * 32, 0);
	}
	
	@Override
	public void render(Graphics g) {
		Vector2 pos = body.getPosition();
		
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(Color.RED);
		g.shapeRenderer.setTransformMatrix(transform);
		g.shapeRenderer.triangle(pos.x * 32, pos.y * 32, (pos.x * 32) + width, pos.y * 32, (pos.x * 32) + width / 2, (pos.y * 32) + height);
		g.shapeRenderer.end();
		
		g.shapeRenderer.setTransformMatrix(new Matrix4());
	}
}
