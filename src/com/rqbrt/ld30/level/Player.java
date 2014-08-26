package com.rqbrt.ld30.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.Sounds;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.tile.Tile;

public class Player extends Entity {
	
	private Level level;
	private Color color = Color.MAGENTA;
	
	private boolean falling = true;
	private boolean dead = false;
	private int deathCount = 0;
	
	public Player(Level level, float x, float y) {
		super(x, y, 24, 24);
		this.level = level;
		
		solid = true;
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set((x + width / 2) / 32, (y + height / 2) / 32);
		
		body = level.getWorld().createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox((width / 2) / 32, (height / 2) / 32);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;
		
		body.createFixture(fixtureDef);
		body.setUserData(this);
		
		box.dispose();
	}
	
	@Override
	public void update() {
		super.update();
		
		if(dead) {
			Sounds.HURT.play();
			deathCount++;
			dead = false;
			Tile spawn = level.getDimension().getTile(level.getDimension().spawnTileId);
			body.setTransform((spawn.x + width) / 32, (spawn.y + 48) / 32, 0);
			body.setLinearDamping(0);
			body.setLinearVelocity(0, 0);
			body.setAngularVelocity(0);
		}
		
		Vector2 pos = body.getPosition();
		
		if(pos.x < 0 || pos.x + (width / 32) >= Game.WIDTH / 32) dead = true;
		if(pos.y < 0 || pos.y + (height / 32) >= Game.HEIGHT / 32) dead = true;
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			body.applyLinearImpulse(0.08f, 0, pos.x, pos.y, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			body.applyLinearImpulse(-0.08f, 0, pos.x, pos.y, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(!falling) {
				Sounds.JUMP.play();
				falling = true;
				body.setTransform(pos.x, pos.y + 0.01f, body.getAngle());
				body.applyLinearImpulse(0, 1.3f, pos.x, pos.y, true);
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			body.applyAngularImpulse(0.01f, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			body.applyAngularImpulse(-0.01f, true);
		}
		
		transform.idt();
		transform.translate(pos.x * 32, pos.y * 32, 0);
		transform.rotateRad(new Vector3(0, 0, 1), body.getAngle());
		transform.translate(-pos.x * 32, -pos.y * 32, 0);
		
		//falling = true;
	}
	
	@Override
	public void render(Graphics g) {
		Vector2 pos = body.getPosition();
		g.shapeRenderer.begin(ShapeType.Line);
		g.shapeRenderer.setColor(color);
		g.shapeRenderer.setTransformMatrix(transform);
		g.shapeRenderer.rect((pos.x * 32) - width / 2, (pos.y * 32) - height / 2, width, height);
		g.shapeRenderer.end();
		
		g.shapeRenderer.setTransformMatrix(new Matrix4());
	}
	
	public void setFalling(boolean b) {
		falling = b;
	}
	
	public void setDead(boolean b) {
		dead = b;
	}
	
	public int getDeathCount() {
		return deathCount;
	}
}
