package uz.Pdp.service.serviseUtil;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlUtil {
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static <T> void write(T t, String path) {
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            xmlMapper.writeValue(new File(path), t);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while reading from the file!");
        }
    }

    public static <T> T read(Class<T> tClass, String path) {
        try {
            return xmlMapper.readValue(new File(path), tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong when reading " + path);
        }
    }


}
