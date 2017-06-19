package com.leapgs.princess.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.leapgs.princess.Actors.PieceActor;
import com.leapgs.princess.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class GameplayScreen extends BaseScreen {

    private Label timerLabel;
    private Label.LabelStyle style;
    private int currentLevel;
    private float currentPoints,currentTime;

    private PieceActor actor;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        currentLevel = level;

        style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor= Color.WHITE;

        setUpLevelData();
    }

    private void setUpLevelData() {

    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);

        timerLabel = new Label("",style);

        addLabelToStage(timerLabel,0,350,150,50,Color.WHITE);

        currentPoints = 0;

        stage.setDebugAll(true);
        setUpCurrentLevel();

    }

    private void addLabelToStage(Label label, float x , float y , float width , float height , Color color) {
        label.setAlignment(Align.center);
        label.setColor(color);
        label.setSize(width,height);
        label.setPosition(x,y);
        stage.addActor(label);
    }

    private void setUpCurrentLevel() {
        stage.addActor(new PieceActor(this,200,200));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        timerLabel.setText("Time Left = "+currentTime);

    }

    private void endGame() {

        if (game.scorePrefs.getFloat("highScore"+currentLevel, -1000) == -1000)
        {
            game.scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
        }
        else
        {
            if(game.scorePrefs.getFloat("highScore"+currentLevel)<currentPoints)
            {
                game.scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
            }
        }
        game.scorePrefs.putFloat("currentScore"+currentLevel,currentPoints);


        game.goToResultsScreen(currentLevel);

    }
}
