/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import org.lwjgl.input.Mouse;

/**
 *
 * @author Jesus
 */
public class MouseCheck {
    
    private static float mx,my;
    
    public static void updateMousePosition(int screenY){
        mx = Mouse.getX();
        my = Mouse.getY();
        my = Math.abs( my - screenY);
    }
    public static boolean isMouseHere(float x1,float y1,float width,float height){
        return mx > x1 && mx < x1+width && my > y1 && my < y1+height;
    }
    public static float getX(){
        return mx;
    }
    public static float getY(){
        return my;
    }
}
