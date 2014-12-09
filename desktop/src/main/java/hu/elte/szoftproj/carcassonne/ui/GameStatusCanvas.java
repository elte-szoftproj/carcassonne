package hu.elte.szoftproj.carcassonne.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.CurrentPlayer;
import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.screen.CurrentGameInterface;

import java.util.HashMap;
import java.util.Map;

public class GameStatusCanvas {

    public final int topX;
    public final int topY;
    public final int bottomX;
    public final int bottomY;

    private final BitmapFont font;

    private final CurrentGameInterface currentGame;

    public GameStatusCanvas(int topX, int topY, int bottomX, int bottomY, CurrentGameInterface currentGame) {
        this.topX = topX;
        this.topY = topY;
        this.bottomX = bottomX;
        this.bottomY = bottomY;

        this.font = new BitmapFont();

        this.currentGame = currentGame;
    }

    public void draw(SpriteBatch batch, CarcassonneGame gameObject) {
        font.draw(batch, buildStatusMessage(gameObject), topX+10, topY);
        font.draw(batch, buildScores(gameObject), topX+10, topY-25);
        font.draw(batch, buildFollowerInfo(gameObject), topX+10, topY-50);
    }

    protected String buildStatusMessage(CarcassonneGame gameObject) {
        StringBuilder builder = new StringBuilder();
        builder.append("Game status: " + gameObject.getStatus());
        if (!gameObject.isFinished() && gameObject.getCurrentPlayer().isPresent()) {
            CurrentPlayer c = gameObject.getCurrentPlayer().get();
            builder.append(" current player: " + c.getPlayer().getName() + " [" + c.getAction() + "]");

            if (currentGame.getCurrentPlayerName().equals(c.getPlayer().getName())) {
                builder.append("   - IT'S YOUR TURN!");
            }
        }



        return builder.toString();
    }

    protected String buildScores(CarcassonneGame gameObject) {
        StringBuilder builder = new StringBuilder();

        for(Player p: gameObject.getPlayersByScore()) {
            builder.append(p.getName() + ": " + p.getScore() + "     ");
        }

        return builder.toString();
    }


    protected CharSequence buildFollowerInfo(CarcassonneGame gameObject) {

        Player p = gameObject.getPlayerByName(currentGame.getCurrentPlayerName());

        HashMap<String, Integer> followerCount = new HashMap<>();

        for (Follower f: p.getFollowers()) {
            if (!followerCount.containsKey(f.getName())) {
                followerCount.put(f.getName(), 0);
            }

            followerCount.put(f.getName(), followerCount.get(f.getName()) + 1);
        }

        StringBuilder builder = new StringBuilder();

        builder.append("Idle followers: ");

        for (Map.Entry<String, Integer> e : followerCount.entrySet()) {
            builder.append(e.getKey() + ": " + e.getValue() + "    ");
        }

        return builder.toString();
    }
}
