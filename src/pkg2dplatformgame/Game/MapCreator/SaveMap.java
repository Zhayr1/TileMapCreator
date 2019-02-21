/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dplatformgame.Game.MapCreator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Jesus
 */
public class SaveMap {
    
    public static void createMapFile(String mapName,List<Item> itemList,int BlockSize){
        String texto = ""+Interface.SCREEN_X+","+Interface.SCREEN_Y+","+BlockSize+":";
        for (int i = 0; i < itemList.size(); i++) {
            texto += itemList.get(i).toStringForSaveMap();
        }
        String nombreFichero = mapName;
        BufferedWriter bw = null;
        try {
            //Crear un objeto BufferedWriter. Si ya existe el fichero, 
            //  se borra automáticamente su contenido anterior.
            bw = new BufferedWriter(new FileWriter(nombreFichero));
            //Escrbir en el fichero el texto con un salto de línea
            bw.write(texto);
        }
        catch(Exception e) {
           System.out.println("Error de escritura del fichero");
           System.out.println(e.getMessage());
        }
        finally {
            try {
                //Cerrar el buffer
                if(bw != null)
                    bw.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }        
        System.out.println("Mapa Guardado Exitosamente");
    }      
}
