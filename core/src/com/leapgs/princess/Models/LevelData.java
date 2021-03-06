package com.leapgs.princess.Models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class LevelData {

    private Array<PuzzlePieceData> pieces;
    private int rows,colums,level;
    private float marginLeft, marginRight, marginUp, marginDown,time;
    private Rectangle playArea;

    private static String levelTilesFolderPrefix = "sprites/levelTiles/";

    public LevelData(int level,float time, int rows, int colums , float marginL, float marginR, float marginU, float marginD ) {

        this.time = time;
        this.rows=rows;
        this.colums=colums;
        this.marginLeft = marginL;
        this.marginRight = marginR;
        this.marginUp = marginU;
        this.marginDown = marginD;
        this.level = level;

        assignSectorsToTiles();
    }

    public LevelData() {

    }

    public void assignSectorsToTiles() {

        Array<Rectangle> sectors = createSectorList();
        pieces = new Array<PuzzlePieceData>();

        for(int i=0;i<rows*colums;i++)
        {
            pieces.add(new PuzzlePieceData(sectors.get(i),levelTilesFolderPrefix+"level"+level+"Sprites/piece"+(i+1)+".png"));
        }
        sectors.clear();
    }

    private Array<Rectangle> createSectorList() {

        playArea = new Rectangle();

        Array<Rectangle> sectors = new Array<Rectangle>();
        float xArea,yArea,xOrigin,yOrigin,xStep,yStep;

        xArea = 400 - marginLeft - marginRight;
        yArea = 400 - marginUp - marginDown;

        xStep = xArea/colums;
        yStep = yArea/rows;

        xOrigin = marginLeft;
        yOrigin = 400 - marginUp;

        playArea.width = xArea;
        playArea.height = yArea;
        playArea.x = marginLeft+ xArea/2;
        playArea.y = marginDown + yArea/2;

        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<colums;j++)
            {
                sectors.add(new Rectangle(xOrigin + (xStep*j),yOrigin - (yStep*i),xStep,yStep));
            }
        }
        return sectors;

    }

    public Array<PuzzlePieceData> getPieces() {
        return pieces;
    }

    public int getRows() {
        return rows;
    }

    public int getColums() {
        return colums;
    }

    public int getLevel() {
        return level;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public float getMarginRight() {
        return marginRight;
    }

    public float getMarginUp() {
        return marginUp;
    }

    public float getMarginDown() {
        return marginDown;
    }

    public float getTime() {
        return time;
    }

    public Rectangle getPlayArea() {
        return playArea;
    }

    public static String getLevelTilesFolderPrefix() {
        return levelTilesFolderPrefix;
    }
}
