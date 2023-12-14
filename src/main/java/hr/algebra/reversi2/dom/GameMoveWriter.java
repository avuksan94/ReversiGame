package hr.algebra.reversi2.dom;

import hr.algebra.reversi2.enums.PlayerRole;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.List;

//https://www.baeldung.com/java-xerces-dom-parsing
//https://www.javaguides.net/2020/01/java-dom-tutorial.html
//https://stackoverflow.com/questions/54047580/writing-out-a-dom-as-an-xml-file

public class GameMoveWriter {

    private static GameMoveWriter instance;

    private GameMoveWriter() {}

    public static synchronized GameMoveWriter getInstance() {
        if (instance == null) {
            instance = new GameMoveWriter();
        }
        return instance;
    }

    public void writeGameMove(String filePath, PlayerRole currentPlayer, List<Point> sandwichedDisks) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("GameMove");
            doc.appendChild(rootElement);

            // player element
            Element playerElement = doc.createElement("Player");
            playerElement.appendChild(doc.createTextNode(currentPlayer.name()));
            rootElement.appendChild(playerElement);

            // sandwichedDisks element
            Element sandwichedDisksElement = doc.createElement("SandwichedDisks");
            rootElement.appendChild(sandwichedDisksElement);

            for (Point p : sandwichedDisks) {
                Element diskElement = doc.createElement("Disk");
                diskElement.setAttribute("x", String.valueOf(p.x));
                diskElement.setAttribute("y", String.valueOf(p.y));
                sandwichedDisksElement.appendChild(diskElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}