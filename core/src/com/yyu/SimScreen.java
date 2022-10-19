package com.yyu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

public class SimScreen implements Screen{
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    //Object that allwos us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    int currentlySelectedTile = -1;

    WumpusWorld myworld = new WumpusWorld();
    BitmapFont defaultFont = new BitmapFont();

    //runs one time, at the very beginning
    //all setup should happen here

    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();

        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    //this method runs as fast as it can, repeatedly, constantly looped

    public void render(float delta) {
        clearScreen();



        //all drawing of shapes MUST be in between begin/end
        shapeRenderer.begin();
        shapeRenderer.setColor(1,1,0,1);
        shapeRenderer.circle(30,30,30);
        shapeRenderer.end();

        //all drawing of graphics MUST be in between begin/end
        spriteBatch.begin();
        myworld.draw(spriteBatch);
        drawToolBar();
        drawDebug();
        HandleMouseClick();
        spriteBatch.end();
    }


    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    public void HandleMouseClick(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            //50X50 Each
            //(650,140)-(700,190) [Spider]
            int mouseX=Gdx.input.getX();
            int mouseY=Gdx.input.getY();
            if(mouseX>=650 && mouseX<=700 && mouseY>=140 &&mouseY<=190){
                currentlySelectedTile = WumpusWorld.SPIDER;

            }//inner if

            else if (mouseX>=650 && mouseX<=700 && mouseY >= 190 && mouseY <=240){
                currentlySelectedTile=WumpusWorld.PIT;
            }

            else if (mouseX>=650 && mouseX<=700 && mouseY >= 240 && mouseY <=290){
                currentlySelectedTile=WumpusWorld.WUMPUS;
            }

            else if (mouseX>=650 && mouseX<=700 && mouseY >= 290 && mouseY <=340){
                currentlySelectedTile=WumpusWorld.GOLD;
            }

            else if(currentlySelectedTile != -1){
                Location worldLoc= myworld.convertCoordsToRowCol(mouseX,mouseY);
                myworld.placeTile(currentlySelectedTile,worldLoc);
                currentlySelectedTile = -1;
            }
        }//outer if
    }

    public Point convertFromMouseToWorld(int x,int y){
        Point p = new Point();
        p.x = x;
        p.y= 600-y;


        return p;
    }

    public void drawToolBar(){
        defaultFont.draw(spriteBatch,"Toolbar",650,550);
        spriteBatch.draw(myworld.getGroundTile(),650,460);
        spriteBatch.draw(myworld.getSpiderTile(),650,410);
        spriteBatch.draw(myworld.getPitTile(),650,360);
        spriteBatch.draw(myworld.getWumpusTile(),650,310);
        spriteBatch.draw(myworld.getGoldTile(),650,260);


        //there is a selected tile
        if(currentlySelectedTile == WumpusWorld.SPIDER){
            Point p = convertFromMouseToWorld(Gdx.input.getX(), Gdx.input.getY());
            p.x -= myworld.getSpiderTile().getWidth()/2;
            p.y -= myworld.getSpiderTile().getHeight()/2;
            spriteBatch.draw(myworld.getSpiderTile(),p.x, p.y);
        }

        if(currentlySelectedTile == WumpusWorld.PIT){
            Point p = convertFromMouseToWorld(Gdx.input.getX(), Gdx.input.getY());
            p.x -= myworld.getSpiderTile().getWidth()/2;
            p.y -= myworld.getSpiderTile().getHeight()/2;
            spriteBatch.draw(myworld.getPitTile(),p.x, p.y);
        }

        if(currentlySelectedTile == WumpusWorld.WUMPUS){
            Point p = convertFromMouseToWorld(Gdx.input.getX(), Gdx.input.getY());
            p.x -= myworld.getSpiderTile().getWidth()/2;
            p.y -= myworld.getSpiderTile().getHeight()/2;
            spriteBatch.draw(myworld.getWumpusTile(),p.x, p.y);
        }

        if(currentlySelectedTile == WumpusWorld.GOLD){
            Point p = convertFromMouseToWorld(Gdx.input.getX(), Gdx.input.getY());
            p.x -= myworld.getSpiderTile().getWidth()/2;
            p.y -= myworld.getSpiderTile().getHeight()/2;
            spriteBatch.draw(myworld.getGoldTile(),p.x, p.y);
        }

    }

    public void drawDebug(){
        defaultFont.draw(spriteBatch,"x: "+ Gdx.input.getX(), 650,200);
        defaultFont.draw(spriteBatch,"Y: "+ Gdx.input.getY(), 650,150);
    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }

    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
