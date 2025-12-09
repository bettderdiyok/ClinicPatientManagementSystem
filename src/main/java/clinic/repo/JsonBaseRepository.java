package clinic.repo;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public abstract class JsonBaseRepository<T> {
   private final Path filePath;
   private final Gson gson;
   private final Type listType;

    protected JsonBaseRepository(Path filePath, Gson gson, Type listType) {
        this.filePath = filePath;
        this.gson = gson;
        this.listType = listType;
    }

    protected void writeAllInternal(List<T> items) {
        try(BufferedWriter newBufferWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, CREATE, TRUNCATE_EXISTING)) {
            gson.toJson(items, listType, newBufferWriter);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write json file: " + filePath, e);
        }
   }

   protected List<T> readAllInternal() {
        if (Files.notExists(filePath)) {
            return List.of();
   }
        try(BufferedReader bufferedReader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)){
            List<T> result = gson.fromJson(bufferedReader, listType);
            return result != null ? result : List.of();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read json file: " + filePath, e);
        }
   }
}

