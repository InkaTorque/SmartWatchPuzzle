package com.leapgs.princess.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.leapgs.princess.Actors.PuzzlePieceActor;
import com.leapgs.princess.Actors.StaticImageActor;
import com.leapgs.princess.MainGame;
import com.leapgs.princess.Models.LevelData;
import com.leapgs.princess.Models.PuzzlePieceData;

import java.util.Random;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class GameplayScreen extends BaseScreen {

    private Label timerLabel;
    private Label.LabelStyle style;
    private int currentLevel;
    private float currentPoints,currentTime,resultScreenWaitTime = 1.5f;
    private LevelData currentLevelData;
    private Array<PuzzlePieceData> currentPieces;

    private boolean won;

    private PuzzlePieceActor actor;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        currentLevel = level;

        style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor= Color.WHITE;

        won = false;

        setUpLevelData(currentLevel);
    }

    private void setUpLevelData(int currentLevel) {

        /*The tiles for each level have to be stored in the following manner
            android\assets\sprites\levelTiles\level[X]sprites\piece[Y].png
            where X is the level number and Y is the tile number
            tiles get assigned their number from left to right , top to bottom
            1   2   3   4   5   6
            7   8   9   10  11  12
            14  15  16  17  18  19
          */

        //LevelData(levelNumber,levelTime,rows,colums,playgroundMarginLeft,playgroundMarginRight,
        //          playgroundMarginUp,playGroundMarginDown)

        FileHandle file = Gdx.files.local("levels/level"+currentLevel+".json");
        String levelString = file.readString();
        Json json = new Json();

        currentLevelData = json.fromJson(LevelData.class,levelString);
        currentLevelData.assignSectorsToTiles();

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
        currentTime = currentLevelData.getTime();
        currentPieces = currentLevelData.getPieces();
        createSquarePlayArea(currentLevelData.getPlayArea());
        spawnRandomPiece();
    }

    public void spawnRandomPiece() {
        if(currentPieces.size==0)
        {
            won=true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    endGame();
                }
            },resultScreenWaitTime);
        }
        else
        {
            Random ran = new Random();
            int index = ran.nextInt(currentPieces.size);
            System.out.println("piece is "+index);
            PuzzlePieceData ppd = currentPieces.get(index);
            stage.addActor(new PuzzlePieceActor(this,200,50,ppd));
            currentPieces.removeIndex(index);
        }

    }

    private void createSquarePlayArea(Rectangle playArea) {
        StaticImageActor playAreaTex = new StaticImageActor(game,"sprites/playArea.png",playArea.getX(),playArea.getY(),playArea.getWidth(),playArea.getHeight());
        stage.addActor(playAreaTex);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        currentTime -= delta;
        if(currentTime<=0)
        {
            currentTime=0;
            endGame();
        }
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


        game.goToResultsScreen(currentLevel,won);
        game.scorePrefs.flush();

    }
}
