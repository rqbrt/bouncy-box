package com.rqbrt.ld30.level;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.rqbrt.ld30.graphics.Graphics;

public class Entity {
	
	public float x, y;
	public float width, height;
	public float speed = 2;
	
	protected boolean solid = false;
	protected boolean allowJump = false;
	protected boolean delete = false;
	
	protected BodyDef bodyDef;
	protected FixtureDef fixtureDef;
	protected Body body;
	
	protected Matrix4 transform;
	
	public Entity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		transform = new Matrix4();
	}
	
	public void update() {
		if(delete) {
			body.getWorld().destroyBody(body);
		}
	}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean allowsJump() {
		return allowJump;
	}
	
	public void setAllowJump(boolean b) {
		allowJump = b;
	}
	
	public Body getBody() {
		return body;
	}
	
	public boolean delete() {
		return delete;
	}
	
	public void setDelete(boolean b) {
		delete = b;
	}
}
