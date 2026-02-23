package main.java.com.hit.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T>
{
    protected final String filePath; /*the path that will lead us to the file that we need*/
    private String fileName;

    public AbstractDao(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public List<T> getAll()
    {
        File file = new File(filePath, fileName);
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
        if (list.remove(item)) {
            saveListToFile(list);
        }
    }

    /*writes all the information that she get in the list to the file*/
    private void saveListToFile(List<T> list) throws IOException
    {
        File file = new File(filePath, fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(list);
        }
    }
}
