package top.furryliy.exp_shop.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static ArrayList<Base> getList() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("ExpShop").resolve("ESConfig.json");
        String cfg = read(path);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Base>>(){}.getType();
        ArrayList<Base> list = gson.fromJson(cfg, type);
        return list;
    }
    public static String read(Path path){
        File file = path.toFile();
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = reader.readLine()) != null){
                buffer.append(text).append(System.getProperty("line.separator"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
