package com.leapgs.princess.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.leapgs.princess.Models.PuzzlePieceData;
import com.leapgs.princess.Screens.GameplayScreen;

import java.security.cert.CertificateNotYetValidException;

/**
 * Created by Leap-Pancho on 6/19/2017.
 */

public class PuzzlePieceActor extends Actor {

    private Texture pieceTex;
    private GameplayScreen screen;
    private Rectangle asignedSector;
    private Vector2 spawnPosition;

    public PuzzlePieceActor(final GameplayScreen screen, float x, float y, PuzzlePieceData ppd) {

        this.screen = screen;
        this.pieceTex = new Texture(ppd.tileSpritePath);
        this.asignedSector = ppd.sector;

        setSize(pieceTex.getWidth(), pieceTex.getHeight());
        setCenteredPosition(x,y);
        spawnPosition = new Vector2(x,y);

        addListeners();



    }

    private void addListeners()
    {
        addListener(new DragListener() {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0.0f), piecePos;
                piecePos = screen.camera.unproject(touchPos);
                setCenteredPosition(piecePos.x,piecePos.y);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event,x,y,pointer,button);
                checkSectorBelonging();
            }
        });
    }

    private void setCenteredPosition(float x, float y) {

        setPosition(x - (getWidth() / 2), y - (getHeight() / 2));
    }

    private void checkSectorBelonging() {

        Vector2 center = new Vector2(getX() + getWidth()/2, getY() + getHeight()/2);

        if ((center.x < asignedSector.x + asignedSector.width && center.x > asignedSector.x) &&
                (center.y < asignedSector.y && center.y > asignedSector.y - asignedSector.height))
        {
            snapToPosition();
        }
        else{
            setCenteredPosition(spawnPosition.x,spawnPosition.y);
        }


    }

    private void snapToPosition() {
        float sectorCenterX,sectorCenterY;
        sectorCenterX = asignedSector.x + (asignedSector.width/2);
        sectorCenterY = asignedSector.y - (asignedSector.height/2);
        setCenteredPosition(sectorCenterX,sectorCenterY);
        screen.spawnRandomPiece();
        clearListeners();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pieceTex, getX(), getY());
    }
}
