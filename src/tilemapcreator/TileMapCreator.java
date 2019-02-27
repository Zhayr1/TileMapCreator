/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemapcreator;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import pkg2dplatformgame.Game.MapCreator.Interface;

/**
 *
 * @author Jesus
 */
public class TileMapCreator extends StateBasedGame{

    public static void main(String args[]){
        try {
            AppGameContainer app = new AppGameContainer(new TileMapCreator("TMC"));
            app.setDisplayMode(1000, 600, false);
            app.setShowFPS(true);
            app.start();
        } catch(SlickException e) {
            System.out.println("Ex: "+e);
        }
    }

    public TileMapCreator(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new Interface());
    }
    
}
