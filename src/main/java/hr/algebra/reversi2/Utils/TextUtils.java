package hr.algebra.reversi2.Utils;

import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.List;

public class TextUtils {
    public static List<String> createListFromString(TextArea ta){
        String allText = ta.getText();
        String[] messagesArray = allText.split("\n");

        return Arrays.asList(messagesArray);
    }

    public static String getStringFromList(List<String> stringList) {
        return String.join("\n", stringList);
    }
}
