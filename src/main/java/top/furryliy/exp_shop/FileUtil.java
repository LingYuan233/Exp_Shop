package top.furryliy.exp_shop;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class FileUtil {

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

    public static void write(File target, String text){
        try {
            if (!target.exists()){
                target.createNewFile();
            }
            FileWriter writer = new FileWriter(target);
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String SAMPLE_CONFIG = """
            [
              {
                "id": 1,
                "item": "minecraft:diamond",
                "exp": 30,
                "count": 1
              }
            ]
            """;

    public static void createConfigDirAndFillDefaultConfigIfItNotExits(){
        Path target = FabricLoader.getInstance().getConfigDir().resolve("ExpShop");
        File cfgDir = target.toFile();
        File sampleFile = target.resolve("sample.json").toFile();
        File configFile = target.resolve("ESConfig.json").toFile();
        if (!cfgDir.exists()){//如果配置文件夹不存在
            cfgDir.mkdir();
            File mainConfig = target.resolve("ESConfig.json").toFile();
            FileUtil.write(mainConfig, "[]");
            File sampleConfig = target.resolve("sample.json").toFile();
            FileUtil.write(sampleConfig, SAMPLE_CONFIG);
        }
        else {
            if (!sampleFile.exists()){
                FileUtil.write(sampleFile, SAMPLE_CONFIG);
            }
            if (!configFile.exists()){
                FileUtil.write(configFile, "[]");
            }
        }

    }

}