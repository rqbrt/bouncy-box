package com.rqbrt.ld30.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.graphics.Graphics;
import com.rqbrt.ld30.level.tile.AirTile;
import com.rqbrt.ld30.level.tile.BrickTile;
import com.rqbrt.ld30.level.tile.EndTile;
import com.rqbrt.ld30.level.tile.SpawnTile;
import com.rqbrt.ld30.level.tile.SpikeTile;
import com.rqbrt.ld30.level.tile.Tile;

public class Dimension {

	private Level level;
	public Tile[] tiles;
	
	public int spawnTileId = -1;
	public int endTileId = -1;
	
	public Dimension(Level level, String[] tileNames) {
		this.level = level;
		
		tiles = new Tile[tileNames.length];
		int x = (level.width * 32) - 32, y = 0;
		for(int i = tileNames.length - 1; i >= 0; i--) {
			if(x <= -32) {
				x = (level.width * 32) - 32;
				y += 32;
			}
			
			String name = tileNames[i];
			if(name.equalsIgnoreCase("a")) tiles[i] = new AirTile(this, i, x, y);
			else if(name.equalsIgnoreCase("s")) {
				tiles[i] = new SpawnTile(this, i, x, y);
				spawnTileId = i;
			}
			else if(name.equalsIgnoreCase("e")) {
				tiles[i] = new EndTile(this, i, x, y);
				endTileId = i;
			}
			else if(name.equalsIgnoreCase("w")) tiles[i] = new BrickTile(this, i, x, y);
			else if(name.equalsIgnoreCase("f")) {
				tiles[i] = new BrickTile(this, i, x, y);
				tiles[i].setAllowJump(true);
			}
			else if(name.equalsIgnoreCase("t")) tiles[i] = new SpikeTile(this, 0, i, x, y);
			else if(name.equalsIgnoreCase("y")) tiles[i] = new SpikeTile(this, 1, i, x, y);
			else if(name.equalsIgnoreCase("u")) tiles[i] = new SpikeTile(this, 2, i, x, y);
			else if(name.equalsIgnoreCase("i")) tiles[i] = new SpikeTile(this, 3, i, x, y);
			
			x -= 32;
		}
	}
	
	public void activate() {
		for(int i = 0; i < tiles.length; i++) {
			if(tiles[i] != null) {
				if(tiles[i].getBody() != null) tiles[i].getBody().setActive(true);
			}
		}
	}
	
	public void deactivate() {
		for(int i = 0; i < tiles.length; i++) {
			if(tiles[i] != null) {
				if(tiles[i].getBody() != null) tiles[i].getBody().setActive(false);
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < tiles.length; i++) {
			if(tiles[i] != null) tiles[i].update();
		}
	}
	
	public void render(Graphics g, Color background, Color foreground) {
		g.shapeRenderer.begin(ShapeType.Filled);
		g.shapeRenderer.setColor(background);
		
		int width = level.width * 32;
		if(width < Game.WIDTH) width = Game.WIDTH;
		int height = level.height * 32;
		if(height < Game.HEIGHT) height = Game.HEIGHT;
			
		g.shapeRenderer.rect(0, 0, width, height);
		g.shapeRenderer.end();
		
		for(int i = 0; i < tiles.length; i++) {
			if(tiles[i] != null) {
				if(tiles[i] instanceof BrickTile) tiles[i].sprite.setColor(foreground);
				tiles[i].render(g);
			}
		}
	}
	
	public Tile getTile(int id) {
		if(id >= 0 && id < tiles.length) {
			return tiles[id];
		} else {
			return null;
		}
	}
	
	public Level getLevel() {
		return level;
	}
}
