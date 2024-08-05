package uz.Pdp.service.serviseUtil;

import lombok.experimental.UtilityClass;
import uz.Pdp.model.enums.XmlJson;
import uz.Pdp.model.wrapper.Cards;

import java.io.*;

@UtilityClass
public class XmlJsonUtil {
    private final String PATH = "src/test/xmlJson.txt";

    public String readWords() {
        String str = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                str += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str ;
    }

    public void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T read(Class<T> t, String PATH) {
        if (readWords().equals(XmlJson.Xml.getVal())) {
            return XmlUtil.read(t, PATH);
        } return JsonUtil.readJson(t, PATH);
    }

    public <T> void write(T t, String PATH) {
        if (readWords().equals(XmlJson.Xml.getVal())) {
            XmlUtil.write(t, PATH);
        } else {
            JsonUtil.writeGson(t, PATH);
        }

    }
}
