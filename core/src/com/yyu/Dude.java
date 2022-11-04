package com.yyu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Dude {
    private Location loc;
    private WumpusWorld myWorld;
    private Texture texture;
    private boolean hasGold = false;
    private int totalSteps = 0;
    private boolean killedWumpus = false;




    static Stack<Location> stack = new Stack<Location>();//backtrack


    private int danger[][] = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };


    public Dude(Location loc, WumpusWorld myWorld) {
        this.loc = loc;
        this.myWorld = myWorld;
        texture = new Texture("guy.png");
        myWorld.makeVisible(loc);
    }

    public void randomAISolution() {
        int choice = (int)(1 + Math.random() * 4);

        if(loc.getRow()!=9&&loc.getCol()!=0&&loc.getRow()!=0&&loc.getCol()!=9) {
            if (choice == 1 && danger[loc.getRow() + 1][loc.getCol()] < 1 && myWorld.isValid(new Location(loc.getRow() + 1, loc.getCol())))
                moveDown();
            else if (choice == 2 && danger[loc.getRow() - 1][loc.getCol()] < 1 && myWorld.isValid(new Location(loc.getRow() - 1, loc.getCol())))
                moveUp();
            else if (choice == 3 && danger[loc.getRow()][loc.getCol() + 1] < 1 && myWorld.isValid(new Location(loc.getRow(), loc.getCol() + 1)))
                moveRight();
            else if (choice == 4 && danger[loc.getRow()][loc.getCol() - 1] < 1 && myWorld.isValid(new Location(loc.getRow(), loc.getCol() - 1)))
                moveLeft();
        }
        else{
            if(choice==1)
                moveDown();
            else if (choice==2)
                moveUp();
            else if(choice==3)
                moveRight();
            else
                moveLeft();
        }

    }


    public void dodge() {

        if(loc.getRow()!=9&&loc.getCol()!=0&&loc.getRow()!=0&&loc.getCol()!=9) {

            if (myWorld.getTileId(loc) == 11 || myWorld.getTileId(loc) == 12 || myWorld.getTileId(loc) == 13) {
                danger[loc.getRow()][loc.getCol()] = 0;
                danger[loc.getRow() + 1][loc.getCol() + 1] += 1;
                danger[loc.getRow() + 1][loc.getCol()] += 1;
                danger[loc.getRow() + 1][loc.getCol() - 1] += 1;
                danger[loc.getRow()][loc.getCol() + 1] += 1;
                danger[loc.getRow()][loc.getCol() - 1] += 1;
                danger[loc.getRow() - 1][loc.getCol() + 1] += 1;
                danger[loc.getRow() - 1][loc.getCol()] += 1;
                danger[loc.getRow() - 1][loc.getCol() - 1] += 1;
            }

            if (myWorld.getTileId(new Location(loc.getRow() - 1, loc.getCol())) == 0) {
                danger[loc.getRow() - 1][loc.getCol()] = 0;
            } else if (myWorld.getTileId(new Location(loc.getRow() + 1, loc.getCol())) == 0) {
                danger[loc.getRow() + 1][loc.getCol()] = 0;
            } else if (myWorld.getTileId(new Location(loc.getRow(), loc.getCol() - 1)) == 0) {
                danger[loc.getRow()][loc.getCol() - 1] = 0;
            } else if (myWorld.getTileId(new Location(loc.getRow(), loc.getCol() + 1)) == 0) {
                danger[loc.getRow()][loc.getCol() + 1] = 0;
            }
        }
        }

    public ArrayList<Location> SafeLocList(){
        ArrayList<Location> SafeLoc = new ArrayList<Location>();
        for(int i=0;i<danger.length;i++){
            for (int j=0;j<danger[i].length;j++){
                SafeLoc.add(new Location(i,j));
                if(danger[i][j]>3){
                    SafeLoc.remove(new Location(i,j));
                }
            }
        }
        return SafeLoc;
    }

    public void printDangerLoc(){
        for (int i=0;i<danger.length;i++) {
            System.out.println(" ");
            for (int j=0;j<danger[i].length;j++) {
                System.out.print("["+ danger[i][j]+"], ");
            }
        }
    }



    //this method makes ONE step
    public void step() {
        dodge();
        if(loc.getRow()==0)
            moveUp();
        randomAISolution();
        System.out.println(" ");
        System.out.println("How Dangerous known places are: ");
        printDangerLoc();
        DudeDebug();

    }


    public boolean killedWumpus() {
        return killedWumpus;
    }

    public void moveRight() {
        if(loc.getCol()+1 < myWorld.getNumCols()) {
            loc.setCol(loc.getCol() + 1);
            myWorld.makeVisible(loc);
            totalSteps++;
        }
    }

    public void moveLeft() {
        if(loc.getCol()-1 >= 0) {
            loc.setCol(loc.getCol() - 1);
            myWorld.makeVisible(loc);
            totalSteps++;
        }
    }

    public void moveUp() {
        if(loc.getRow() - 1 >= 0) {
            loc.setRow(loc.getRow()-1);
            myWorld.makeVisible(loc);
            totalSteps++;
        }
    }

    public void moveDown() {
        if(loc.getRow() + 1 < myWorld.getNumRows()) {
            loc.setRow(loc.getRow()+1);
            myWorld.makeVisible(loc);
            totalSteps++;
        }
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public Location getLoc() {
        return loc;
    }

    public void reset(Location loc) {
        this.loc = loc;
        myWorld.makeVisible(loc);
        totalSteps = 0;
        killedWumpus = false;
        for (int i =0;i<danger.length;i++){
            for (int j =0;j<danger[i].length;j++){
                danger[i][j]=0;
            }
        }
    }

    public boolean hasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public void draw(SpriteBatch spriteBatch) {
        Point myPoint = myWorld.convertRowColToCoords(loc);
        spriteBatch.draw(texture,(int)myPoint.getX(),(int)myPoint.getY());
    }

    public void DudeDebug(){
       /* for (int i = 0; i< SafeLocList().size(); i++){
            System.out.println(SafeLocList().get(i).getRow()+", "+ SafeLocList().get(i).getCol());
        }

        */
    }


}
