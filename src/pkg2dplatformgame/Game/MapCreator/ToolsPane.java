/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import static pkg2dplatformgame.Game.MapCreator.Interface.EDIT_FIELD;
import static pkg2dplatformgame.Game.MapCreator.Interface.PANEL_WIDTH;
import static pkg2dplatformgame.Game.MapCreator.Interface.SCREEN_X;
import static pkg2dplatformgame.Game.MapCreator.Interface.editFieldTransitionPaneled;

/**
 *
 * @author Jesus
 */
public class ToolsPane {
    
    private final int ITEMS_PANE = 0;
    private final int OPTIONS_PANE = 1;
    private final Image itemsPane,optionsPane;
    private float x;
    private float y;
    private final float width,height;
    private int selectedPane,selectedItem;
    private String[] imagesDirs; 
    private int[] itemsNames;    
    private List<Item> items;   
    private String item;
    private int bz;
    
    
    public ToolsPane(float x,float y,float width,float height,int block_size,String img1_src, String img2_src) throws SlickException{
        itemsPane = new Image(img1_src);
        optionsPane = new Image(img2_src);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        selectedPane = selectedItem = 0;
        items = new ArrayList();    
        item = "";
        this.initStrings();
        this.initItemsList();
        bz = block_size;
    }
    public void draw(Graphics g){
        switch(selectedPane){
            case ITEMS_PANE:
                this.drawItemsPane(g);
                break;
            case OPTIONS_PANE:
                this.drawOptionsPane();
                break;
        }
    }
    public void updatePosition(float newx,float newy){
        //Pane Position
        x = newx;
        y = newy;
        //Items Position
        for (int i = 0; i < items.size(); i++) {
            if(i < 5){
                items.get(i).updatePosition((editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH +10)), (i+1) * 100 + 20);
            }else{
                items.get(i).updatePosition((editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH +100)), (i - 4) * 100 + 20);
            }
        }        
    }
    public void setSelectedPane(int i){
        selectedPane = i;
    }
    public List<Item> getItemsList(){
        return items;
    }
    public int getSelectedPane(){
        return selectedPane;
    }
    public int getSelectedItem(){
        return selectedItem;
    }
    public String[] getImagesDir(){
        return imagesDirs;
    }
    public void checkSelectedItems(){
        for (int i = 0; i < 5; i++) {
            if(MouseCheck.isMouseHere(EDIT_FIELD + 10 , (i+1) * 100 + 20 , PANEL_WIDTH* 0.3f, PANEL_WIDTH* 0.3f)){
                switch(i){
                    case 0:
                        item = ""+items.get(i).getID();
                        selectedItem = i;
                        break;
                    case 1:
                        item = ""+items.get(i).getID();
                        selectedItem = i;
                        break;
                    case 2:
                        item = ""+items.get(i).getID();
                        selectedItem = i;
                        break;
                    case 3:
                        item = ""+items.get(i).getID();
                        selectedItem = i;
                        break;
                    case 4:
                        item = ""+items.get(i).getID();
                        selectedItem = i;
                        break;
                }
            }else if(MouseCheck.isMouseHere(EDIT_FIELD + 100, (i+1) * 100 + 20, PANEL_WIDTH* 0.3f, PANEL_WIDTH* 0.3f)){
                switch(i+5){
                    case 5:
                        item = ""+items.get(i+5).getID();
                        selectedItem = i + 5;
                        break;
                    case 6:
                        item = ""+items.get(i+5).getID();
                        selectedItem = i + 5;
                        break;
                    case 7:
                        item = ""+items.get(i+5).getID();
                        selectedItem = i + 5;
                        break;
                    case 8:
                        item = ""+items.get(i+5).getID();
                        selectedItem = i + 5;
                        break;                
                }
            }
        }    
    }    
    private void drawItemsPane(Graphics g){
        //Draw Pane
        itemsPane.draw(x, y, width, height);
        //Draw items
        for (int i = 0; i < items.size(); i++) {
            items.get(i).render(g);
        }        
        g.setColor(Color.white);
        g.drawString("Selected Item: "+item, (editFieldTransitionPaneled)+SCREEN_X - PANEL_WIDTH , 30);        
    }
    private void drawOptionsPane(){
        optionsPane.draw(x, y, width, height);
    }
    private void initItemsList() throws SlickException{

        for (int i = 0; i < 5; i++) {
            items.add(new Item( (int)(editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH +10)), (i+1) * 100 + 20,
                PANEL_WIDTH * 0.3f, PANEL_WIDTH * 0.3f, itemsNames[i],bz, imagesDirs[i]));
            
        }
        for (int i = 0; i < 5; i++) {
            items.add(new Item( (int)(editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH +100)), (i+1) * 100 + 20,
                PANEL_WIDTH * 0.3f, PANEL_WIDTH * 0.3f, itemsNames[i+5],bz, imagesDirs[i+5]));
        }
    }    
    private void initStrings(){
        itemsNames = new int[10];
        imagesDirs = new String[10];
        itemsNames[0] = 0;
        itemsNames[1] = 1;
        itemsNames[2] = 2;
        itemsNames[3] = 3;
        itemsNames[4] = 4;
        //
        itemsNames[5] = 5;
        itemsNames[6] = 6;
        itemsNames[7] = 7;
        itemsNames[8] = 8;
        itemsNames[9] = 9;
        //Images Dirs
        imagesDirs[0] = "Assets/basicBlock.png";
        imagesDirs[1] = "Assets/Coin.png";
        imagesDirs[2] = "Assets/Start.png";
        imagesDirs[3] = "Assets/End.png";
        imagesDirs[4] = "Assets/Enemy.png";
        imagesDirs[5] = "Assets/Colblock.png";
        imagesDirs[6] = "Assets/Stairs.png";
        imagesDirs[7] = "Assets/Chest.png";
        imagesDirs[8] = "Assets/SpikeTrap.png";
        imagesDirs[9] = "Assets/basicBlock.png";
    }    
}
