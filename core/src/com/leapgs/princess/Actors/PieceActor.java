package com.leapgs.princess.Actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.leapgs.princess.Screens.GameplayScreen;

/**
 * Created by Leap-Pancho on 6/19/2017.
 */

public class PieceActor extends Actor {

    private Texture pieceTex;
    private GameplayScreen screen;

    public PieceActor(final GameplayScreen screen, float x , float y) {
        this.screen = screen;
        this.pieceTex = new Texture("sprites/piece.png");
        setSize(pieceTex.getWidth(),pieceTex.getHeight());
        setPosition(x-pieceTex.getWidth()/2,y-pieceTex.getHeight()/2);
        setTouchable(Touchable.enabled);
        addListener(new DragListener(){

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector3 touchPos = new Vector3(Gdx.input.getX(pointer),Gdx.input.getY(pointer),0.0f), piecePos;
                piecePos = screen.camera.unproject(touchPos);
                setPosition(piecePos.x - pieceTex.getWidth()/2,piecePos.y - pieceTex.getHeight()/2);
                System.out.println(getX()+","+getY());
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pieceTex,getX(),getY());
    }
}
