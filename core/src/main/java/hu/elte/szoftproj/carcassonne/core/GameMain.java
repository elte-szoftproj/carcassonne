package hu.elte.szoftproj.carcassonne.core;

import hu.elte.szoftproj.carcassonne.model.Board;
import hu.elte.szoftproj.carcassonne.model.Deck;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Square;
import hu.elte.szoftproj.carcassonne.model.impl.BasicBoard;
import hu.elte.szoftproj.carcassonne.model.impl.BasicDeck;

import java.util.HashMap;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class GameMain implements ApplicationListener {
	SpriteBatch batch;
	float elapsed;

	java.util.Map<String, Texture> tileTextures;
	
	// center position
	int px;
	int py;
	
	// game classes
	Deck gameDeck;
	Board gameBoard;
	
	@Override
	public void create () {
		px = 0; py = 0;
		batch = new SpriteBatch();
		tileTextures = new HashMap<>();
		
		// initialize game, for now just here
		gameDeck = new BasicDeck();
		gameBoard = new BasicBoard(gameDeck.getStarterTile(), Rotation.D0);
	}

	Texture getTextureFor(String name) {
		if(!tileTextures.containsKey(name)) {
			tileTextures.put(name, new Texture(Gdx.files.internal(name+".png")));
		}
		return tileTextures.get(name);
	}
	
	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render map
		batch.begin();
		for(int iy=gameBoard.getTopLeftPosition().getY();iy<=gameBoard.getBottomRightPosition().getY();iy++) {
			for(int ix=gameBoard.getTopLeftPosition().getX();ix<=gameBoard.getBottomRightPosition().getX();ix++) {
				Square s = gameBoard.getTileAt(ix, iy);
				batch.draw(getTextureFor(s.getTile().getName()), 100+90*ix, 100+90*iy);
			}
		}
		batch.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
