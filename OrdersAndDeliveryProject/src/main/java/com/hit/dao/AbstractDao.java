package main.java.com.hit.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T>
{
    protected final String filePath;
    private String fileName;

    public AbstractDao(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public List<T> getAll()
    {
        File file = new File(filePath, fileName);
        if (!file.exists()) return new ArrayList<>(); // אם הקובץ לא קיים, נחזיר רשימה ריקה

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public abstract T find(long id) throws IOException;

    @Override
    public void save(T item) throws IOException {
        List<T> list = getAll();
        list.add(item);
        saveListToFile(list);
    }

    @Override
    public void delete(T item) throws IOException
    {
        List<T> list = getAll();
        // במקום להסתמך רק על equals, נשתמש ב-findToRemove שממומש במחלקות היורשות
        T toRemove = findToRemove(list, item);
        if (toRemove != null) {
            list.remove(toRemove);
            saveListToFile(list);
        }
    }

    // מתודה מופשטת חדשה שתעזור לנו למצוא את האובייקט למחיקה מתוך הרשימה
    protected abstract T findToRemove(List<T> list, T item);

    private void saveListToFile(List<T> list) throws IOException
    {
        File file = new File(filePath, fileName);
        // יצירת תיקיות אם הן לא קיימות
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(list);
        }
    }
}