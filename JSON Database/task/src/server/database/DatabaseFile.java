package server.database;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class DatabaseFile implements Database<String> {
    private String databaseURI = System.getProperty("user.dir") + File.separator +
            "JSON Database" + File.separator +
            "task" + File.separator +
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator + "db.json";
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    @Override
    public Response set(Index<String> index, String value) {
        writeLock.lock();
        List<Row> rows = readUnsafe();
        Map<Index<String>, Row> keyValues = rows.stream().collect(Collectors.toMap(Row::getKey, r -> r));

        if (keyValues.containsKey(index)) {
            rows.remove(keyValues.get(index));
        }
        rows.add(new Row(index, value));
        writeUnsafe(rows);
        writeLock.unlock();
        return new Response("OK", "");
    }

    @Override
    public Response get(Index<String> index) {
        readLock.lock();
        List<Row> rows = readUnsafe();
        readLock.unlock();

        for (Row i : rows) {
            if (i.getKey().get().equals(index.get())) {
                return new Response(i.getKey().get(), i.getValue());
            }
        }
        return new Response("ERROR", "No such key");
    }

    @Override
    public Response delete(Index<String> index) {
        writeLock.lock();
        List<Row> rows = readUnsafe();
        Map<Index<String>, Row> keyValues = rows.stream().collect(Collectors.toMap(Row::getKey, r -> r));

        if (keyValues.containsKey(index)) {
            rows.remove(keyValues.get(index));
            writeUnsafe(rows);
            writeLock.unlock();
            return new Response("OK", "");
        }
        writeLock.unlock();
        return new Response("ERROR", "No such key");
    }


    private List<Row> readUnsafe() {
        Path path = new File(databaseURI).toPath();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            if (!validate(reader)) {
                return new ArrayList<>();
            }
            Row[] rows = new GsonBuilder().create().fromJson(reader, Row[].class);
            return new ArrayList<>(Arrays.asList(rows));
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writeUnsafe(List<Row> rows) {
        try (OutputStreamWriter isr = new OutputStreamWriter(new FileOutputStream(databaseURI), StandardCharsets.UTF_8)) {
            new Gson().toJson(rows, isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(Reader reader) throws IOException {
        reader.mark(1);
        if (reader.read() != '[') {
            reader.reset();
            return false;
        }
        reader.reset();
        return true;
    }
}
