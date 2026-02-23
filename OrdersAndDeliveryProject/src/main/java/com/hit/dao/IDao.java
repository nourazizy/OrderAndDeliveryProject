package main.java.com.hit.dao;

import java.io.IOException;
import java.util.List;

public interface IDao<T>
{
    /*return all the information that the file contains*/
    List<T> getAll() ;

    /*search for something specific in the file*/
    T find(long id) throws IOException;

    /*update the information that we have in the file by adding new data*/
    void save(T item) throws IOException;

    /*update the information that we have in the file by removing new data*/
    void delete(T item) throws IOException;
}
