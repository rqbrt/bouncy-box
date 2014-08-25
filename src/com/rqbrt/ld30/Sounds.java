package com.rqbrt.ld30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	public static final Sound SELECT = Gdx.audio.newSound(Gdx.files.internal("sounds/select.wav"));
	public static final Sound HURT = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"));
	public static final Sound JUMP = Gdx.audio.newSound(Gdx.files.internal("sounds/jump2.wav"));
	public static final Sound LEVEL_END = Gdx.audio.newSound(Gdx.files.internal("sounds/level_end.wav"));
	public static final Sound SWAP = Gdx.audio.newSound(Gdx.files.internal("sounds/swap.wav"));
	public static final Sound RESET = Gdx.audio.newSound(Gdx.files.internal("sounds/reset.wav"));
	
	public static void dispose() {
		SELECT.dispose();
		HURT.dispose();
		JUMP.dispose();
		LEVEL_END.dispose();
		SWAP.dispose();
		RESET.dispose();
	}
}
