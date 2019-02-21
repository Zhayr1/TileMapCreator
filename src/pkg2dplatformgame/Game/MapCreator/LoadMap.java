/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Jesus
 */
public class LoadMap{
    //ID - X - Y :
    public static List<Item> LoadMapFile(String filename,String[] imagesDir) throws SlickException{
        int screenX,screenY,blockSize;
        String[] items = LoadMap.readMapFile(filename);
        List<Item> itList = new ArrayList();
        blockSize = 60;
        for (int i = 0; i < items.length; i++) {
            if(i == 0){
                String[] aux = items[0].split(",");
                screenX = Integer.valueOf(aux[0]);
                screenY = Integer.valueOf(aux[1]);
                blockSize = Integer.valueOf(aux[2]);
            }else{
                String[] aux = items[i].split(",");
                itList.add(new Item(Float.valueOf(aux[1]),Float.valueOf(aux[2]),blockSize,blockSize,
                           Integer.valueOf(aux[0]),imagesDir[Integer.valueOf(aux[0])]));
            }
        }
                            //mapItemsList.add(new Item(i * BLOCK_SIZE + (editFieldTransitionPaneled), j * BLOCK_SIZE ,
                              //                  BLOCK_SIZE,BLOCK_SIZE,id,toolsPane.getImagesDir()[id]));
        
        return itList;
    }
    
    private static String[] readMapFile(String fileName){
        //La variable 'pos' indicará la posición del puntero del fichero,
        //  señalando el lugar donde se leerá o escribirá
        int pos = 0;
        String nombreFichero = fileName;
        RandomAccessFile raf = null;
        String mapString = "";
        try
        {
            //Se abre el fichero para permitir lectura y escritura
            raf = new RandomAccessFile(nombreFichero,"r");
            //Posicionar el puntero del fichero en la posición indicada
            raf.seek(pos);
                //Leer un carácter y avanzar puntero al siguiente carácter
                //Los caracteres leídos se toman de tipo entero
                mapString = raf.readLine();

        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("Error de lectura/escritura en el fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(raf != null)
                    raf.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }        
        String[] mapItems = mapString.split(":");
        System.out.println("Mapa Cargado Exitosamente");
        return mapItems;
    }
}
