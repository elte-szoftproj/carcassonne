package hu.elte.szoftproj.carcassonne.core;

import hu.elte.szoftproj.carcassonne.model.Area;
import hu.elte.szoftproj.carcassonne.model.Board;
import hu.elte.szoftproj.carcassonne.model.Deck;
import hu.elte.szoftproj.carcassonne.model.Place;
import hu.elte.szoftproj.carcassonne.model.Rotation;
import hu.elte.szoftproj.carcassonne.model.Side;
import hu.elte.szoftproj.carcassonne.model.Slot;
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
	
	class AreaPointer {
		int dx;
		int dy;
		int gx, gy;
		Place place;
		Square square;
		Area area;
		
		
		public AreaPointer(int x, int y) {
			super();
			gx = unmapX(x);
			gy = unmapY(y);
			dx = x - mapX(gx);
			dy = y - mapY(-gy);
			
			place = decidePlace(dx, dy);
			
			square = gameBoard.getTileAt(gx, gy);
			
			System.out.println("Area pointer: (" + gx + ", " + gy + ", " + place + ") " + dy);
			
			if (square != null) {
				System.out.println(" -D " + square.getTile());
				area = square.getSlotAt(place).getArea();				
				
				System.out.println("Selected area: " + area);
				System.out.println("Slot count: " + area.getSlots().size());
				for(Slot s: area.getSlots()) {
					System.out.println(" - " + s.getSquare().getX() + ", " + s.getSquare().getY() + "[" + s.getPlaces() + "]");
				}
			}
		}


		private Place decidePlace(int dx2, int dy2) {
			// TODO Auto-generated method stub
			int mx = dx2 / 30;
			int my = dy2 / 30;
			
			int rx = dx2 % 30;
			int ry = dy2 % 30;
			
			System.out.println(dx2);
			System.out.println(dy2);			
			System.out.println(mx);
			System.out.println(my);
			System.out.println(rx);
			System.out.println(ry);
			
			if (mx == 1) {
				switch(my) {
				case 0: return Place.TOP;
				case 1: return Place.CENTER;
				case 2: return Place.BOTTOM;
				}
			}
			if (mx == 0) {
				switch(my) {
				case 0: return rx > ry ? Place.TOP_LEFT_TOP : Place.TOP_LEFT_LEFT;
				case 1: return Place.LEFT; 
				case 2: return rx > 30 - ry ? Place.BOTTOM_LEFT_BOTTOM : Place.BOTTOM_LEFT_LEFT;
				}
			}
			if (mx == 2) {
				switch(my) {
				case 0: return 30 - rx > ry ? Place.TOP_RIGHT_TOP : Place.TOP_RIGHT_RIGHT;
				case 1: return Place.LEFT; 
				case 2: return rx > ry ? Place.BOTTOM_RIGHT_RIGHT : Place.BOTTOM_RIGHT_BOTTOM;
				}
			}
			return null;
		}
		
		void rendereOverlay(ShapeRenderer sr) {
			
			if (area == null) return;
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(1, 0, 0, 0.5f));
			
			for(Slot s: area.getSlots()) {
				renderSlot(sr, s);
			}
			shapeRenderer.end();
		}


		private void renderSlot(ShapeRenderer sr, Slot s) {
			int tx = mapX(s.getSquare().getX());
			int ty = mapY(s.getSquare().getY());
			
			for(Place p: s.getPlaces()) {
				renderPlace(sr, tx, ty, p);
			}
		}


		private void renderPlace(ShapeRenderer sr, int tx, int ty, Place p) {
			switch(p) {
			case CENTER: sr.rect(tx+30, ty+30, 30, 30); break;
			case LEFT: sr.rect(tx+0, ty+30, 30, 30); break;
			case RIGHT: sr.rect(tx+60, ty+30, 30, 30); break;
			case TOP: sr.rect(tx+30, ty+60, 30, 30); break;
			case BOTTOM: sr.rect(tx+30, ty+0, 30, 30); break;
			
			case TOP_LEFT_TOP: sr.triangle(tx, ty+90, tx+30, ty+90, tx+30, ty+60); break;
			case TOP_LEFT_LEFT: sr.triangle(tx, ty+90, tx+30, ty+60, tx, ty+60); break;
			
			case TOP_RIGHT_TOP: sr.triangle(tx+90, ty+90, tx+60, ty+90, tx+60, ty+60); break;
			case TOP_RIGHT_RIGHT: sr.triangle(tx+90, ty+90, tx+60, ty+60, tx+90, ty+60); break;
			
			case BOTTOM_LEFT_BOTTOM: sr.triangle(tx, ty+0, tx+30, ty+0, tx+30, ty+30); break;
			case BOTTOM_LEFT_LEFT: sr.triangle(tx, ty+0, tx+30, ty+30, tx, ty+30); break;
			
			case BOTTOM_RIGHT_BOTTOM: sr.triangle(tx+90, ty+0, tx+60, ty+0, tx+60, ty+30); break;
			case BOTTOM_RIGHT_RIGHT: sr.triangle(tx+90, ty+0, tx+60, ty+30, tx+90, ty+30); break;
			}
		}
	}
	
	AreaPointer areaPointer;
	
	@Override
	public void create () {
		px = 0; py = 0;
		batch = new SpriteBatch();
		tileTextures = new HashMap<>();
		
		// initialize game, for now just here
		gameDeck = new BasicDeck();
		gameBoard = new BasicBoard(gameDeck.getStarterTile(), Rotation.D0);
		
		areaPointer = null;
		
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
				if (button == 0) {
				if (screenX < 1080) {
					if (gameBoard.canPlaceTileAt(unmapX(screenX), unmapY(screenY), nextTile, nextTileRotation)) {
						System.out.println("placing tile! :) " + unmapX(screenX) + " " + unmapY(screenY) + " b: " + button);
						gameBoard.placeTileAt(unmapX(screenX), unmapY(screenY), nextTile, nextTileRotation);
						nextTile = null;
					}
				}
				}
				if (button == 1) { // right click
					areaPointer = new AreaPointer(screenX, screenY);
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
		return (py+y)*90+(1024/2)-45;
	}
	
	int unmapX(int x) {
		return (int) (Math.floor((x+45-(1080/2)) / 90.0f)-px);
	}
	
	int unmapY(int y) {
		return (int) (Math.floor((y+45-(1024/2)) / 90.0f)+py)*-1;
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
					batch.draw(getTextureFor(s.getTile().getName()), mapX(ix), mapY(iy), 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, 360-s.getTileRotation().getDegrees());
				} else {
					if (nextTile != null && gameBoard.canPlaceTileAt(ix, iy, nextTile, nextTileRotation)) {
						batch.setColor(1.0f, 1.0f, 0.5f, 0.3f);
						batch.draw(getTextureFor(nextTile.getName()), mapX(ix), mapY(iy), 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, 360-nextTileRotation.getDegrees());
						batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
					}
				}
			}
		}
		
		batch.end();
		
		drawRightBar();
		
		
		
		if (areaPointer != null) {
			
			Gdx.gl.glEnable(GL10.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			areaPointer.rendereOverlay(shapeRenderer);
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}
		
	}
	
	void drawRightBar() {

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(1280-200, 0, 200, 1024);
		shapeRenderer.end();
		
		batch.begin();
		
		if (nextTile != null) {
			batch.draw(getTextureFor(nextTile.getName()), 1280-145, 1024-140, 45.0f, 45.0f, 90.0f, 90.0f, 1.0f, 1.0f, 360-nextTileRotation.getDegrees());
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
