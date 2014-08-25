package com.rqbrt.ld30;

import com.badlogic.gdx.graphics.Texture;

public class Assets {

	public static final Texture BRICK = new Texture("textures/brick.png");
	
	public static void dispose() {
		BRICK.dispose();
	}
}
