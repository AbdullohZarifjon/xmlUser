package uz.Pdp.service.serviseUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uz.Pdp.model.Card;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void writeGson(T t, String path) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File(path), t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No cards found!");
        }
    }

//    public static <T> void writeGson(T t, String path) {
//        try {
//            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), t);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to write JSON to file: " + path);
//        }
//    }

//    public static <T> List<T> readGson(TypeReference<List<T>> t, String path) {
//        try {
//            return objectMapper.readValue(new File(path), t);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Card not read!");
//        }
//    }
    public static <T> T readJson(Class<T> tClass, String path) {
        try {
            return objectMapper.readValue(new File(path), tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON file: " + path);
        }
    }
//
//    public static <T> T readGson(Class<T> tClass, String path) {
//        try (FileReader reader = new FileReader(path)) {
//            return gson.fromJson(reader, tClass);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to read JSON file: " + path);
//        }
//    }
//   public static <T> List<T> readGson(Class<T> tClass, String path) {
//        try (FileReader reader = new FileReader(path)) {
//            Type listType = TypeToken.getParameterized(List.class, tClass).getType();
//            return gson.fromJson(reader, listType);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to read JSON file: " + path);
//        }
//    }
}
