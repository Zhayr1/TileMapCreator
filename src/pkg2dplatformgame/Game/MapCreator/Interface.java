/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Jesus
 */
public class Interface extends BasicGame{
    
    public static final int SCREEN_X = 1000;
    public static final int SCREEN_Y = 600;
    public static final int PANEL_WIDTH = 200;
    public static final int EDIT_FIELD = (SCREEN_X - PANEL_WIDTH - 20);
    public int BLOCK_SIZE = 60;
    public int COLSH = Math.round((SCREEN_X - PANEL_WIDTH)/BLOCK_SIZE);
    public int COLSV = Math.round((SCREEN_Y)/BLOCK_SIZE);
    
    public final int BLOCK    = 0;
    public final int COIN     = 1;
    public final int START    = 2;
    public final int END      = 3;
    public final int ENEMY    = 4;
    public final int COLBLOCK = 5;
    public final int STAIRS   = 6;
    public final int CHEST    = 7;
    public final int TRAP     = 8;
    //public final int BLOQUE = 10;
    
    public final int ITEMPANE   = 0;
    public final int COLORPANE  = 1;
    public final int CONFIGPANE = 2;
    public float editFieldTransition;
    public static float editFieldTransitionPaneled;
    //Fin de Constantes y variables de la interfaz
    
    private ToolsPane toolsPane;
    private List<Item> mapItemsList;
    private int selectedItem;
    private boolean presionadoIzq,presionadoDer;
    private TextField blockSizeTextField;
    private float editField;
    private boolean drawGrid;
    
    public Interface(String a){
        super(a);
    }
    
    public void setBlockSize(int bz){
        BLOCK_SIZE = bz;
        COLSH = Math.round((SCREEN_X - PANEL_WIDTH)/BLOCK_SIZE);
        COLSV = Math.round((SCREEN_Y)/BLOCK_SIZE);
        mapItemsList.clear();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        mapItemsList = new ArrayList();
        presionadoIzq = false;
        presionadoDer = false;
        selectedItem = 0;
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	TrueTypeFont font = new TrueTypeFont(awtFont, false);
        blockSizeTextField = new TextField(gc, font, SCREEN_X - PANEL_WIDTH, 50, 100, 30);
        editField = 0;
        drawGrid = true;
        editFieldTransition = editField * SCREEN_X;
        editFieldTransitionPaneled = (editField * EDIT_FIELD);
        toolsPane = new ToolsPane((editFieldTransitionPaneled )+(SCREEN_X - PANEL_WIDTH - 20),
                                   0, PANEL_WIDTH + 20, SCREEN_Y, "Assets/ItemsPane.png", "Assets/OptionsPane.png");

    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        MouseCheck.updateMousePosition(SCREEN_Y);
        this.updateTransitionPosition();
        this.updateToolsPanePosition();
        //this.showMapItemsNames();
        selectedItem = toolsPane.getSelectedItem();
        if(editField < 0) editField = 0;
        if(editField > 5) editField = 5;
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.translate( -(editFieldTransitionPaneled), 0);
        g.setColor(Color.white);
        g.drawString("EDTP: "+editFieldTransitionPaneled+"\n"+editField+"\nMx: "+MouseCheck.getX()+"\nMy: "+MouseCheck.getY(), editFieldTransitionPaneled, 20);
        this.drawLines(g);
        this.drawMapItems(g);
        g.setColor(Color.white);
        this.drawPanel(g);
        blockSizeTextField.render(gc, g);
        g.setColor(Color.lightGray);
        g.fillRect(SCREEN_X - PANEL_WIDTH + 120 + (editFieldTransitionPaneled), 50, 50, 30);
    }
    @Override
    public void mouseClicked(int click, int i1, int i2, int i3) {
        if(click == 0){
            toolsPane.checkSelectedItems();
            try {
                this.checkPlacedItem(selectedItem);
            } catch (SlickException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(click == 1){
            this.removePlacedItem();
        }
    }
    @Override
    public void mouseReleased(int click, int x, int y) {       
    }
    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {
        if(presionadoIzq){
            try {
                this.checkPlacedItem(selectedItem);
            } catch (SlickException ex) {
                Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(presionadoDer){
            this.removePlacedItem();
        }        
    }
    @Override
    public void keyPressed(int i, char c) {
        switch(i){
            case Input.KEY_LSHIFT:
                presionadoIzq = true;
                break;
            case Input.KEY_LALT:
                presionadoDer = true;
                break;
            case Input.KEY_ENTER:
                if(blockSizeTextField.getText() != ""){
                    try{
                        this.setBlockSize(Integer.valueOf(blockSizeTextField.getText()));
                    }catch(Exception e){
                    }
                }
                break;
            case Input.KEY_RIGHT:
                editField++;
                break;
            case Input.KEY_LEFT:
                editField--;
                break;
            case Input.KEY_F8:
                drawGrid = !drawGrid;
                break;
            case Input.KEY_F5:
                SaveMap.createMapFile("TestMap.txt", mapItemsList, BLOCK_SIZE);
                break;
            case Input.KEY_F6:
                try {
                    mapItemsList = LoadMap.LoadMapFile("TestMap.txt", toolsPane.getImagesDir());
                } catch (SlickException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    @Override
    public void keyReleased(int i, char c) {
        switch(i){
            case Input.KEY_LSHIFT:
                presionadoIzq = false;
                break;
            case Input.KEY_LALT:
                presionadoDer = false;
                break;    
        }
    }    
    private void drawLines(Graphics g){
        if(drawGrid){
            for (int i = 1; i < COLSV; i++) {
                for (int j = 0; j < BLOCK_SIZE * COLSH; j+=5) {
                    g.drawLine( j + (editFieldTransitionPaneled) , i * BLOCK_SIZE , j + 2 + (editFieldTransitionPaneled) , i * BLOCK_SIZE );
                }
            }
            for (int i = 1; i < COLSH; i++) {
                for (int j = 0; j < BLOCK_SIZE * COLSH; j+=5) {
                    g.drawLine( i * BLOCK_SIZE + (editFieldTransitionPaneled), j , i * BLOCK_SIZE + (editFieldTransitionPaneled), j + 2 );
                }
            }    
        }
    }
    private void checkPlacedItem(int id) throws SlickException{
        for (int i = 0; i < COLSH; i++) {
            if(MouseCheck.isMouseHere(i * BLOCK_SIZE ,0, BLOCK_SIZE, BLOCK_SIZE)){
                for (int j = 0; j < mapItemsList.size(); j++) {
                    if(mapItemsList.get(j).getX() == i * BLOCK_SIZE + (editFieldTransitionPaneled) && mapItemsList.get(j).getY() == 0 ){
                        mapItemsList.remove(j);
                    }
                }
                mapItemsList.add(new Item(i * BLOCK_SIZE + (editFieldTransitionPaneled), 0 , BLOCK_SIZE,BLOCK_SIZE , id,toolsPane.getImagesDir()[id]));
            }
            for (int j = 0; j < COLSV; j++) {
                if(MouseCheck.isMouseHere(i * BLOCK_SIZE ,j * BLOCK_SIZE , BLOCK_SIZE, BLOCK_SIZE)){
                    for (int k = 0; k < mapItemsList.size(); k++) {
                        if(mapItemsList.get(k).getX() == i * BLOCK_SIZE + (editFieldTransitionPaneled) && mapItemsList.get(k).getY() == j * BLOCK_SIZE ){
                            mapItemsList.remove(k);
                       }
                    }
                    mapItemsList.add(new Item(i * BLOCK_SIZE + (editFieldTransitionPaneled), j * BLOCK_SIZE ,
                                                BLOCK_SIZE,BLOCK_SIZE,id,toolsPane.getImagesDir()[id]));
                }
            }
        }
    }
    private void removePlacedItem(){
        for (int i = 0; i < COLSH; i++) {
            if(MouseCheck.isMouseHere(i * BLOCK_SIZE ,0, BLOCK_SIZE, BLOCK_SIZE)){
                for (int j = 0; j < mapItemsList.size(); j++) {
                    if( mapItemsList.get(j).getX() == i * BLOCK_SIZE + (editFieldTransitionPaneled) && mapItemsList.get(j).getY() == 0 ){
                        mapItemsList.remove(j);
                    }    
                }
                
            }
            for (int j = 0; j < COLSV; j++) {
                if(MouseCheck.isMouseHere(i * BLOCK_SIZE ,j * BLOCK_SIZE , BLOCK_SIZE, BLOCK_SIZE)){
                    for (int k = 0; k < mapItemsList.size(); k++) {
                        if( mapItemsList.get(k).getX() == i * BLOCK_SIZE + (editFieldTransitionPaneled) && mapItemsList.get(k).getY() == j * BLOCK_SIZE ){
                            mapItemsList.remove(k);
                        }    
                    }
                }
            }
        }        
    }
    private void drawMapItems(Graphics g){
        for (int i = 0; i < mapItemsList.size(); i++) {
            switch(mapItemsList.get(i).getID()){
                case BLOCK:
                    if(mapItemsList.get(i).getX() >= EDIT_FIELD * editField && mapItemsList.get(i).getX() <= EDIT_FIELD * (editField + 1)){
                        mapItemsList.get(i).render(g);
                    }
                    break;
                case COIN:
                    mapItemsList.get(i).render(g);
                    break;
                case START:
                    mapItemsList.get(i).render(g);
                    break;
                case END:
                    mapItemsList.get(i).render(g);
                    break;
                case ENEMY:
                    mapItemsList.get(i).render(g);
                    break;
                case COLBLOCK:
                    mapItemsList.get(i).render(g);
                    break;
                case STAIRS:
                    mapItemsList.get(i).render(g);
                    break;
                case CHEST:
                    mapItemsList.get(i).render(g);
                    break;                    
                case TRAP:
                    mapItemsList.get(i).render(g);
                    break;
            }
        }
    }
    private void drawPanel(Graphics g){
                toolsPane.draw(g);
    }
    private void showMapItemsNames(){
        System.out.println("<=========================================>");
        mapItemsList.forEach((mapItemsList1) -> {
            System.out.println(mapItemsList1.toString());
        });
    }
    private void updateTransitionPosition(){
        editFieldTransition = editField * SCREEN_X;
        editFieldTransitionPaneled = editField * EDIT_FIELD;
    }
    private void updateToolsPanePosition(){
        toolsPane.updatePosition(editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH - 20), 0);
    }
}
