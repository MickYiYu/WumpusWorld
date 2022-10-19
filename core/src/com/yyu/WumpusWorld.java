package com.yyu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WumpusWorld {

    private final int xoffset=20, yoffset=500;
    private int world[][]={
            {0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0,0},
            {0,0,2,0,0,0,0,0,0,0},
            {0,0,0,3,0,0,0,0,0,0},
            {0,0,0,0,4,0,0,0,0,0},
            {0,11,0,0,0,0,0,0,0,0},
            {0,0,12,0,0,0,0,0,0,0},
            {0,0,0,13,0,0,0,0,0,0},
            {0,0,0,0,0,14,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };
    private Texture groundTile, spiderTile, pitTile, wumpusTile, goldTile,
    webTile, windTile, glitterTile, stinkTile,blackTile;
    public static final int GROUND=0,SPIDER=1,PIT=2,WUMPUS=3,GOLD=4,
    WEB=11,WIND=12,STINK=13,GLITTER=14;

    private final int tileWidth;

    public WumpusWorld(){
        groundTile = new Texture("groundTile.png");
        spiderTile = new Texture("spiderTile.png");
        pitTile = new Texture("pitTile.png");
        wumpusTile = new Texture("wumpusTile.png");
        goldTile =  new Texture("goldTile.png");
        webTile = new Texture("webTile.png");
        windTile = new Texture("windTile.png");
        glitterTile = new Texture("glitterTile.png");
        blackTile = new Texture("blackTile.png");
        stinkTile = new Texture("stinkTile.png");
        tileWidth = blackTile.getWidth();
    }

    public Location convertCoordsToRowCol(int x, int y){
        int row;//y
        int col;//x



        col = (x-xoffset)/50;
        row = (y-50)/50;//no sense but it works

        System.out.println("col is "+col);
        System.out.println("row is "+row);

        return new Location(row,col);
    }

    public boolean isValid(Location loc){
        return loc.getRow()>=0&& loc.getRow()<=world.length && loc.getCol()>=0 && loc.getCol()<=world[0].length;
    }

    public void placeTile(int tileId, Location loc){
        if(isValid(loc)){
        world[loc.getRow()][loc.getCol()]=tileId;
        }
    }

    public void draw(SpriteBatch spriteBatch){
        for(int row=0;row<world.length;row++){
            for (int col=0; col<world[row].length;col++){
                if(world[row][col]==GROUND){
                    spriteBatch.draw(groundTile,xoffset+col*tileWidth,yoffset-row*50);
                }
               else if(world[row][col]==SPIDER){
                    spriteBatch.draw(spiderTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==PIT){
                    spriteBatch.draw(pitTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==WUMPUS){
                    spriteBatch.draw(wumpusTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==GOLD){
                    spriteBatch.draw(goldTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==WEB){
                    spriteBatch.draw(webTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==WIND){
                    spriteBatch.draw(windTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==GLITTER){
                    spriteBatch.draw(glitterTile,xoffset+col*tileWidth,yoffset-row*50);
                }
                else if(world[row][col]==STINK){
                    spriteBatch.draw(stinkTile,xoffset+col*tileWidth,yoffset-row*50);
                }//end inner for
            }//end outer for
        }//end output
    }//end method

    public Texture getGroundTile() {
        return groundTile;
    }

    public Texture getSpiderTile() {
        return spiderTile;
    }

    public Texture getPitTile() {
        return pitTile;
    }

    public Texture getWumpusTile() {
        return wumpusTile;
    }

    public Texture getGoldTile() {
        return goldTile;
    }
}//end class
