package hu.elte.szoftproj.carcassonne.core;

import hu.elte.szoftproj.carcassonne.model.Board;
import hu.elte.szoftproj.carcassonne.model.Deck;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Square;
import hu.elte.szoftproj.carcassonne.model.Tile;
import hu.elte.szoftproj.carcassonne.model.impl.BasicBoard;
import hu.elte.szoftproj.carcassonne.model.impl.BasicDeck;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameMain implements ApplicationListener {
	SpriteBatch batch;
	float elapsed;
	ShapeRenderer shapeRenderer;

	java.util.Map<String, TextureRegion> tileTextures;
	
	// center position
	int px;
	int py;
	
	// game classes
	Deck gameDeck;
	Board gameBoard;
	
	Tile nextTile;
	Rotation nextTileRotation;
	
	@Override
	public void create () {
		px = 0; py = 0;
		batch = new SpriteBatch();
		tileTextures = new HashMap<>();
		
		// initialize game, for now just here
		gameDeck = new BasicDeck();
		gameBoard = new BasicBoard(gameDeck.getStarterTile(), Rotation.D0);
		
		shapeRenderer = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				
				if (nextTile != null) {
					if (keycode == Keys.UP) py++;
					if (keycode == Keys.DOWN) py--;
					if (keycode == Keys.LEFT) px++;
					if (keycode == Keys.RIGHT) px--;
					if (keycode == (Keys.PAGE_UP)) {
						nextTileRotation = nextTileRotation.prev();
						System.out.println("Rotation: " + nextTileRotation);
					}
					if (keycode == (Keys.PAGE_DOWN)) {
						nextTileRotation = nextTileRotation.next();
						System.out.println("Rotation: " + nextTileRotation);
					}
				}
				
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				if (screenX < 1080) {
					if (gameBoard.canPlaceTileAt(unmapX(screenX), unmapY(screenY), nextTile, nextTileRotation)) {
						System.out.println("placing tile! :) " + unmapX(screenX) + " " + unmapY(screenY));
						gameBoard.placeTileAt(unmapX(screenX), unmapY(screenY), nextTile, nextTileRotation);
						nextTile = null;
					}
				}
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}

	TextureRegion getTextureFor(String name) {
		if(!tileTextures.containsKey(name)) {
			tileTextures.put(name, new TextureRegion(new Texture(Gdx.files.internal(name+".png")), 0, 0, 90, 90));
		}
		return tileTextures.get(name);
	}
	
	@Override
	public void resize (int width, int height) {
	}
	
	void updateLogic() {
		if (nextTile == null) {
			if (gameDeck.getRemainingPieceCount() == 0) {
				System.out.println("Game over! TODO: write scores");
				Gdx.app.exit();
			} else {
				nextTile = gameDeck.drawTile();
				nextTileRotation = Rotation.D0;
				System.out.println("Next tile: " + nextTile.getName());
			}
		}
	}
	
	int mapX(int x) {
		return (x+px)*90+(1080/2)-45;
	}
	
	int mapY(int y) {
		return (py-y)*90+(1024/2)-45;
	}
	
	int unmapX(int x) {
		return (int) (Math.floor((x+45-(1080/2)) / 90.0f)-px);
	}
	
	int unmapY(int y) {
		return (int) (Math.floor((y+45-(1024/2)) / 90.0f)+py);
	}

	@Override
	public void render () {
		
		updateLogic();
		
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render map
		batch.begin();
		for(int iy=gameBoard.getTopLeftPosition().getY()-1;iy<=gameBoard.getBottomRightPosition().getY()+1;iy++) {
			for(int ix=gameBoard.getTopLeftPosition().getX()-1;ix<=gameBoard.getBottomRightPosition().getX()+1;ix++) {
				Square s = gameBoard.getTileAt(ix, iy);
				if (s != null) {
					batch.draw(getTextureFor(s.getTile().getName()), mapX(ix), mapY(iy), 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, s.getTileRotation().getDegrees());
				} else {
					if (nextTile != null && gameBoard.canPlaceTileAt(ix, iy, nextTile, nextTileRotation)) {
						batch.setColor(1.0f, 1.0f, 0.5f, 0.3f);
						batch.draw(getTextureFor(nextTile.getName()), mapX(ix), mapY(iy), 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, nextTileRotation.getDegrees());
						batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
					}
				}
			}
		}
		
		batch.end();
		
		drawRightBar();
		
		
	}
	
	void drawRightBar() {

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(1280-200, 0, 200, 1024);
		shapeRenderer.end();
		
		batch.begin();
		
		if (nextTile != null) {
			batch.draw(getTextureFor(nextTile.getName()), 1280-145, 1024-140, 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, nextTileRotation.getDegrees());
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
