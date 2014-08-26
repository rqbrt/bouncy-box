package com.rqbrt.ld30.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.Sounds;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.factory.LevelFactory;
import com.rqbrt.ld30.level.tile.EndTile;
import com.rqbrt.ld30.level.tile.SpikeTile;
import com.rqbrt.ld30.level.tile.Tile;
import com.rqbrt.ld30.menu.EndMenu;

public class Level {
	
	private Game game;
	public String name;
	public int id;
	public int width, height;
	
	private byte dimension = 0;
	private Dimension dim1, dim2;
	
	private Color background;
	private Color foreground;
	private boolean transition = false;
	
	private boolean tabPressed = false;
	private boolean backSpacePressed = false;
	
	private World world;
	private Player player;
	
	private boolean complete = false;
	
	private String deathCount = "Deaths: ";
	
	private String time = "Time: ";
	private long startTime, endTime;
	
	private String levelCompleted;
	private String cont = "Press enter to continue...";
	
	public Level(Game game, String name, int id, int width, int height, String[] dimension_1, String[] dimension_2) {
		this.game = game;
		this.name = name;
		this.id = id;
		this.width = width;
		this.height = height;
		
		background = new Color(0, 0, 0, 1);
		foreground = new Color(1, 1, 1, 1);
		
		world = new World(new Vector2(0, -9.8f), true);
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				try {
					Entity a = (Entity)contact.getFixtureA().getBody().getUserData();
					Entity b = (Entity)contact.getFixtureB().getBody().getUserData();
					
					if(a.allowsJump() && b instanceof Player) {
						if(a.getBody().getPosition().y < b.getBody().getPosition().y) player.setFalling(false);
					}
					
					if(a instanceof EndTile) {
						if(!complete) {
							Sounds.LEVEL_END.play();
							complete = true;
							endTime = System.nanoTime();
							setStats();
						}
					}
					
					if(a instanceof SpikeTile) {
						player.setDead(true);
					}
				} catch(Exception e) {
					return;
				}
			}
			@Override
			public void endContact(Contact contact) {
				
			}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
			}
		});
		
		dim1 = new Dimension(this, dimension_1);
		dim2 = new Dimension(this, dimension_2);
		
		dim1.activate();
		dim2.deactivate();
		
		Tile spawnTile = dim1.getTile(dim1.spawnTileId);
		player = new Player(this, spawnTile.x + 4, spawnTile.y + 32);
		
		startTime = System.nanoTime();
		levelCompleted = name + " Completed!";
		
		/*debugRenderer = new Box2DDebugRenderer();
		box2dCam = new OrthographicCamera(game.getGraphics().camera.viewportWidth / 32, game.getGraphics().camera.viewportHeight / 32);
		box2dCam.position.set(box2dCam.viewportWidth / 2, box2dCam.viewportHeight / 2, 0);
		box2dCam.update();*/
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && !backSpacePressed) {
			backSpacePressed = true;
			Sounds.RESET.play();
			complete = false;
			world.step(0, 0, 0);
			world.destroyBody(player.getBody());
			
			dimension = 0;
			Tile spawn = getDimension().getTile(getDimension().spawnTileId);
			player = new Player(this, spawn.x + 4, spawn.y + 32);
			startTime = System.nanoTime();
			
			background = new Color(0, 0, 0, 1);
			foreground = new Color(1, 1, 1, 1);
			
			dim2.deactivate();
			dim1.activate();
		} else if(!Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
			backSpacePressed = false;
		}
		
		if(!complete) {			
			world.step(1 / 60.0f, 6, 2);
		
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !tabPressed) {
				Sounds.SWAP.play();
				tabPressed = true;
				if(dimension == 0) dimension = 1;
				else dimension = 0;
				
				if(dimension == 0) {
					dim2.deactivate();
					dim1.activate();
				} else {
					dim1.deactivate();
					dim2.activate();
				}
				
				transition = true;
			} else if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				tabPressed = false;
			}
			
			if(transition) {
				if(dimension == 0) {
					// set background to black; foreground to white
					background.r -= 0.1f;
					background.g -= 0.1f;
					background.b -= 0.1f;
					
					if(background.r < 0) background.r = 0;
					if(background.g < 0) background.g = 0;
					if(background.b < 0) background.b = 0;
					
					foreground.r += 0.1f;
					foreground.g += 0.1f;
					foreground.b += 0.1f;
					
					if(foreground.r > 1) foreground.r = 1;
					if(foreground.g > 1) foreground.g = 1;
					if(foreground.b > 1) foreground.b = 1;
					
					if(background.r == 0 && background.g == 0 && background.b == 0 && foreground.r == 1 && foreground.g == 1 && foreground.b == 1) transition = false;
				} else if(dimension == 1) {
					// set background to white; foreground to black
					background.r += 0.1f;
					background.g += 0.1f;
					background.b += 0.1f;
					
					if(background.r > 1) background.r = 1;
					if(background.g > 1) background.g = 1;
					if(background.b > 1) background.b = 1;
					
					foreground.r -= 0.1f;
					foreground.g -= 0.1f;
					foreground.b -= 0.1f;
					
					if(foreground.r < 0) foreground.r = 0;
					if(foreground.g < 0) foreground.g = 0;
					if(foreground.b < 0) foreground.b = 0;
					
					if(background.r == 1 && background.g == 1 && background.b == 1 && foreground.r == 0 && foreground.g == 0 && foreground.b == 0) transition = false;
				}
			}
			
			player.update();
			dim1.update();
			dim2.update();
		} else {
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				Sounds.SELECT.play();
				dispose();
			}
		}
	}
	
	public void render(Graphics g) {		
		if(dimension == 0) dim1.render(g, background, foreground);
		else if(dimension == 1) dim2.render(g, background, foreground);
		
		g.batch.begin();
		g.font.setColor(foreground);
		g.font.setScale(1);
		g.font.draw(g.batch, name, (Game.WIDTH - g.textWidth(name, 1)) / 2, Game.HEIGHT - 10);
		g.font.draw(g.batch, deathCount + player.getDeathCount(), 10, Game.HEIGHT - 10);
		if(!complete) {
			g.font.draw(g.batch, time + ((System.nanoTime() - startTime) / 1000000000) + "s", (Game.WIDTH - g.textWidth(time + ((System.nanoTime() - startTime) / 1000000000) + "s", 1)) - 10, Game.HEIGHT - 10);
		} else {
			g.font.draw(g.batch, time + ((endTime - startTime) / 1000000000) + "s", (Game.WIDTH - g.textWidth(time + ((endTime - startTime) / 1000000000) + "s", 1)) - 10, Game.HEIGHT - 10);
		}
		g.batch.end();
		
		player.render(g);
		
		//debugRenderer.render(world, box2dCam.combined);
		if(complete){
			g.batch.begin();
			g.font.setColor(foreground);
			g.font.draw(g.batch, levelCompleted, (Game.WIDTH - g.textWidth(levelCompleted, 1)) / 2, (Game.HEIGHT / 2) + 15);
			g.batch.end();
			
			g.batch.begin();
			g.font.setColor(foreground);
			g.font.draw(g.batch, cont, (Game.WIDTH - g.textWidth(cont, 1)) / 2, Game.HEIGHT / 2);
			g.batch.end();
		}
	}
	
	public void dispose() {
		world.step(0, 0, 0);
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		
		if(bodies.size > 0) {
			for(int i = 0; i < bodies.size; i++) {
				((Entity)bodies.get(i).getUserData()).setDelete(true);
			}
			
			player.update();
			dim1.update();
			dim2.update();
		} else {
			world.dispose();
			try {
				game.setLevel(null);
				game.setLevel(LevelFactory.createLevel(game, "levels/" + (id + 1) + ".json"));
			} catch(Exception e) {
				game.setLevel(null);
				game.setMenu(new EndMenu(game));
			}
		}
	}
	
	private void setStats() {
		game.stats[id - 1][0] = String.valueOf(player.getDeathCount());
		game.stats[id - 1][1] = ((endTime - startTime) / 1000000000) + "s";
	}
	
	public World getWorld() {
		return world;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Dimension getDimension() {
		if(dimension == 0) return dim1;
		else return dim2;
	}
}
