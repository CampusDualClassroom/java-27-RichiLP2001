package com.campusdual.classroom;
//3º


import org.w3c.dom.Document;//-
import org.w3c.dom.Element;//-

import javax.xml.parsers.DocumentBuilder;//-
import javax.xml.parsers.DocumentBuilderFactory;//-
import javax.xml.parsers.ParserConfigurationException;//-
import javax.xml.transform.*;//-
import javax.xml.transform.dom.DOMSource;//-
import javax.xml.transform.stream.StreamResult;//-
import java.io.File;//-
import javax.xml.transform.TransformerConfigurationException;//-

//todo==============para el Json
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise27 {
    private static void createDocument () throws TransformerException,
            ParserConfigurationException {
      // 1º factoria: Solicitamos un constructor de documentos-->
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         //Ahora asignamos el constructor solicitado a DocumentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();
            //solicitamos a constructor -->devuelve un DOCUMENTO ABSTRACTO
        builder.newDocument();
            //Guardo el DOCUMENTO ABSTRACTO en una variable tipo Document
        Document document = builder.newDocument();
      // 3º --> creamos la rama central de donde cuelga todo  "NOMBRE DE LISTA"
        Element shoppinglist = document.createElement("shoppinglist");
            //todas las etiquetas empezaran a colgar de root =(shoppinlist)
        document.appendChild(shoppinglist);
            // todo "ITEMS" colgara de shoppinglist --> y los ITEM colgara de ITEMS
        Element items = document.createElement("items");
        shoppinglist.appendChild(items);

      //5º creamos elementos con el metodo createItem ---> todo CADA "ITEM" colgara de la rama "ITEMS"
        items.appendChild(createItem(document,"item","quantity","2","Manzana"));
        items.appendChild(createItem(document,"item","quantity","1","Leche"));
        items.appendChild(createItem(document,"item","quantity","3","Pan"));
        items.appendChild(createItem(document,"item","quantity","2","Huevo"));
        items.appendChild(createItem(document,"item","quantity","1","Queso"));
        items.appendChild(createItem(document,"item","quantity","1","Cereal"));
        items.appendChild(createItem(document,"item","quantity","4","Agua"));
        items.appendChild(createItem(document,"item","quantity","6","Yogur"));
        items.appendChild(createItem(document,"item","quantity","2","Arroz"));

     //6º.2) quiero que me escribas este documento en esta ruta-->
        writeToFile(document,"src/main/resources/shoppingList.xml");
    }

    //6º.1) crear metodo para dirigir la ruta de los documentos
private static void writeToFile(Document document, String pathFile)
        throws TransformerException{
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","3");
            //indicamos donde queremos crearlo
        File file = new File(pathFile);
        DOMSource source = new DOMSource(document);
        StreamResult result =new StreamResult(file);
        transformer.transform(source,result);
}


    //4º METODO - CREACION DE ARTICULOS(elementos)
    public static Element createItem (Document document, String tagName,
                                      String attribute, String attrValue,
                                      String textComponent){

        Element item = document.createElement(tagName);
        item.setAttribute(attribute, attrValue);
        item.setTextContent(textComponent);

        return item;
    }
//todo===INICIO, CREACION DEL JSON============================================================================================================
    private static void createFile (){
        JsonObject items = new JsonObject();
        JsonArray componentesArray = new JsonArray();

        //3ºUtilizamos METODO para agregar elementos al todo LISTADO JSON
        componentesArray.add(Exercise27.createItem(2,"Manzana"));
        componentesArray.add(Exercise27.createItem(1,"Leche"));
        componentesArray.add(Exercise27.createItem(3,"Pan"));
        componentesArray.add(Exercise27.createItem(2,"Huevo"));
        componentesArray.add(Exercise27.createItem(1,"Queso"));
        componentesArray.add(Exercise27.createItem(1,"Cereal"));
        componentesArray.add(Exercise27.createItem(4,"Agua"));
        componentesArray.add(Exercise27.createItem(6,"Yogur"));
        componentesArray.add(Exercise27.createItem(2,"Arroz"));
        //4º --> para terminar
            items.add("items", componentesArray);


        //todo 5º) Para IMPRIMIR CON FORMATO + ubicar Json en el almacenamiento:
            try (FileWriter fw= new FileWriter("src/main/resources/shoppingList.json")){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(items);
                fw.write(json);
                fw.flush();
            }catch (IOException e){
                e.printStackTrace();
            }




    }
    //2º METODO- CREACION DE ELEMENTOS/COMPONENTES EN LA LISTA JSON
    private static JsonObject createItem(int quantity, String items){
        JsonObject component = new JsonObject();
        component.addProperty("quantity",quantity);
        component.addProperty("text",items);
        return component;
    }


    public static void main(String[] args) throws TransformerException {
        // 2º creacion de objetos en otros elementos
        try {
            Exercise27.createDocument();
        } catch (TransformerConfigurationException
                 | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        //todo========================JSON====================================
        //creamos
        createFile();


    }
}
