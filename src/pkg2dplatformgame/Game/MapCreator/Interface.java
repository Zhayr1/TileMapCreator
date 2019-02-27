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
import javax.swing.JOptionPane;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Jesus
 */
public class Interface extends BasicGameState{
    
    public static final int SCREEN_X = 1000;
    public static final int SCREEN_Y = 600;
    public static final int PANEL_WIDTH = 200;
    public static final int ID = 0;
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
    private List<Item> itemList;
    private int selectedItem;
    private boolean presionadoIzq,presionadoDer;
    private TextField blockSizeTextField;
    private float editField;
    private boolean drawGrid,drawHelp;
    private int itemw,itemh;
    private String aux;
    private Image HelpText,OkButton;
    
    
    public void setBlockSize(int bz){
        BLOCK_SIZE = bz;
        COLSH = Math.round((SCREEN_X - PANEL_WIDTH)/BLOCK_SIZE);
        COLSV = Math.round((SCREEN_Y)/BLOCK_SIZE);
        mapItemsList.clear();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        mapItemsList = new ArrayList();
        presionadoIzq = false;
        presionadoDer = false;
        selectedItem = 0;
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	TrueTypeFont font = new TrueTypeFont(awtFont, false);
        blockSizeTextField = new TextField(gc, font, SCREEN_X - PANEL_WIDTH, 50, 100, 30);
        editField = 0;
        drawGrid = false;
        drawHelp = true;
        itemw = itemh = 1;
        HelpText = new Image("Assets/HelpText.png");
        OkButton = new Image("Assets/OkButton.png");
        editFieldTransition = editField * SCREEN_X;
        editFieldTransitionPaneled = (editField * EDIT_FIELD);
        toolsPane = new ToolsPane((editFieldTransitionPaneled )+(SCREEN_X - PANEL_WIDTH - 20),
        0, PANEL_WIDTH + 20, SCREEN_Y,BLOCK_SIZE, "Assets/ItemsPane.png", "Assets/OptionsPane.png");
        itemList = toolsPane.getItemsList();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        MouseCheck.updateMousePosition(SCREEN_Y);
        this.updateTransitionPosition();
        this.updateToolsPanePosition();
        //this.showMapItemsNames();
        selectedItem = toolsPane.getSelectedItem();
        if(editField < 0) editField = 0;
        if(editField > 5) editField = 5;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.translate( -(editFieldTransitionPaneled), 0);
        g.setColor(Color.white);
        this.drawLines(g);
        this.drawMapItems(g);
        itemList.get(selectedItem).render(g,(int)MouseCheck.getX(),(int)MouseCheck.getY(),itemw,itemh,BLOCK_SIZE);
        g.setColor(Color.white);
        this.drawPanel(g);
        blockSizeTextField.render(gc, g);
        OkButton.draw(SCREEN_X - PANEL_WIDTH + 120 + (editFieldTransitionPaneled),50,50,30);
        if(drawHelp){
            HelpText.draw(0, 0);
        }else{
            g.setColor(Color.white);
            g.drawString("EDTP: "+editFieldTransitionPaneled+"\n"+editField+"\nMx: "+MouseCheck.getX()+"\nMy: "+MouseCheck.getY(), editFieldTransitionPaneled, 20);
        }
    }
    @Override
    public void mouseClicked(int click, int i1, int i2, int i3) {
        if(click == 0){
            toolsPane.checkSelectedItems();
            this.checkButtonsClick();
            try {
                this.checkPlacedItem(selectedItem);
            } catch (SlickException ex) {
                System.out.println("Error: "+ex);
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
            case Input.KEY_RIGHT:
                editField++;
                break;
            case Input.KEY_LEFT:
                editField--;
                break;
            case Input.KEY_F8:
                drawGrid = !drawGrid;
                break;
            case Input.KEY_F1:
                drawGrid = !drawGrid;
                drawHelp = !drawHelp;
                break;
            case Input.KEY_F5:
                aux = JOptionPane.showInputDialog("Ingrese el nombre para guardar el mapa");
                if(!aux.contains(".tmc")){
                    aux += ".tmc";
                }
                SaveMap.createMapFile(aux, mapItemsList, BLOCK_SIZE);
                break;
            case Input.KEY_F6:
                aux = JOptionPane.showInputDialog("Ingrese el nombre del mapa que desea cargar");
                if(!aux.contains(".tmc")){
                    aux += ".tmc";
                }
                try {
                    mapItemsList = LoadMap.LoadMapFile(aux, toolsPane.getImagesDir());
                } catch (SlickException ex) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case Input.KEY_NUMPAD4:
                System.out.println("itemw"+itemw);
                if(itemw < 2){
                    itemw = 1;
                }else{
                    itemw --;
                }
                break;
            case Input.KEY_NUMPAD6:
                if(itemw > Math.floor(EDIT_FIELD/BLOCK_SIZE)){
                    itemw = (int)Math.floor(EDIT_FIELD/BLOCK_SIZE);
                }else{
                    itemw ++;
                }
                break;        
            case Input.KEY_NUMPAD2:
                if(itemh > SCREEN_Y/BLOCK_SIZE){
                    itemh = SCREEN_Y/BLOCK_SIZE;
                }else{
                    itemh ++;
                }
                break;     
            case Input.KEY_NUMPAD8:
                if(itemh < 2){
                    itemh = 1;
                }else{
                    itemh --;
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
                mapItemsList.add(new Item(i * BLOCK_SIZE + (editFieldTransitionPaneled), 0 , itemw * BLOCK_SIZE
                        ,itemh * BLOCK_SIZE , id,BLOCK_SIZE,toolsPane.getImagesDir()[id]));
            }
            for (int j = 0; j < COLSV; j++) {
                if(MouseCheck.isMouseHere(i * BLOCK_SIZE ,j * BLOCK_SIZE , BLOCK_SIZE, BLOCK_SIZE)){
                    for (int k = 0; k < mapItemsList.size(); k++) {
                        if(mapItemsList.get(k).getX() == i * BLOCK_SIZE + (editFieldTransitionPaneled) && mapItemsList.get(k).getY() == j * BLOCK_SIZE ){
                            mapItemsList.remove(k);
                       }
                    }
                    mapItemsList.add(new Item(i * BLOCK_SIZE + (editFieldTransitionPaneled), j * BLOCK_SIZE,
                                                itemw * BLOCK_SIZE,itemh * BLOCK_SIZE ,id,BLOCK_SIZE,toolsPane.getImagesDir()[id]));
                }
            }
        }
    }
    private void checkButtonsClick(){
        if(MouseCheck.isMouseHere(SCREEN_X - PANEL_WIDTH + 120, 50, 50, 30)){
                if(blockSizeTextField.getText() != ""){
                    try{
                        int auxi = Integer.valueOf(blockSizeTextField.getText());
                        if(auxi != 0){
                            this.setBlockSize(auxi);
                        }
                    }catch(Exception e){
                        System.out.println("Error: "+e);
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
    private void updateTransitionPosition(){
        editFieldTransition = editField * SCREEN_X;
        editFieldTransitionPaneled = editField * EDIT_FIELD;
    }
    private void updateToolsPanePosition(){
        toolsPane.updatePosition(editFieldTransitionPaneled + (SCREEN_X - PANEL_WIDTH - 20), 0);
 
    }
    @Override
    public int getID() {
        return ID;
    }
}
