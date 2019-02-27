/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Jesus
 */
public class Item extends Rectangle{
    
    private final int ID;
    private Image img;
    private int rx,ry,bz;
    
    public Item(float x, float y, float width, float height, int id,int bz, String imgDir) throws SlickException {
        super(x, y, width, height);
        ID = id;
        img = new Image(imgDir);
        this.bz = bz;
        rx = (int) (width/bz);
        ry = (int) (height/bz);
    }
    
    public int getID(){
        return ID;
    }
    public void render(Graphics g){
        img.draw(x, y, width, height);
    }
    public void render(Graphics g,int newx,int newy,int w,int h,int bz){
        img.draw(newx,newy,bz,bz);
        g.drawRect(newx, newy, w * bz, h * bz);

    }
    public void updatePosition(float newx, float newy){
        super.setX(newx);
        super.setY(newy);
    }
    @Override
    public String toString(){
        return "Item: "+ID+" X: "+super.getX()+" Y: "+super.getY() + " ID: "+ID;
    }
    public String toStringForSaveMap(){
        return ID+","+super.getX()+","+super.getY()+":";
    }
}
