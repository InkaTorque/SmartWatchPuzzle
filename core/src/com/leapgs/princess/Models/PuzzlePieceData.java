package com.leapgs.princess.Models;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Leap-Pancho on 6/20/2017.
 */

public class PuzzlePieceData {

    public Rectangle sector;
    public String tileSpritePath;

    public PuzzlePieceData(Rectangle rect,String tileSpriteName) {
        this.sector = rect;
        this.tileSpritePath = tileSpriteName;
    }
}
