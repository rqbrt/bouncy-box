package com.rqbrt.ld30.level.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.rqbrt.ld30.Game;
import com.rqbrt.ld30.level.Level;

public class LevelFactory {

	public String name;
	public int id;
	public int width;
	public int height;
	
	public String[] dimension_1;
	public String[] dimension_2;
	
	public static Level createLevel(Game game, String path) {		
		Json json = new Json();
		LevelFactory factory = json.fromJson(LevelFactory.class, Gdx.files.internal(path));
		return new Level(game, factory.name, factory.id, factory.width, factory.height, factory.dimension_1, factory.dimension_2);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name: " + name + "\n").append("Size: " + width + "x" + height + "\n");		
		return builder.toString();
	}
}
