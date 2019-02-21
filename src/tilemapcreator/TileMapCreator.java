/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemapcreator;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import pkg2dplatformgame.Game.MapCreator.Interface;

/**
 *
 * @author Jesus
 */
public class TileMapCreator {

    public static void main(String args[]){
        try {
            AppGameContainer app = new AppGameContainer(new Interface("2D Game"));
            app.setDisplayMode(1000, 600, false);
            app.setShowFPS(false);
            app.start();
        } catch(SlickException e) {
            System.out.println("Ex: "+e);
        }
    }
    
}
