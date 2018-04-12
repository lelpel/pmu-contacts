package ua.lelpel.phonebook.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.lelpel.phonebook.data.entities.Contact;

/**
 * Created by bruce on 30.03.2018.
 */

@Dao
public interface ContactDao {
    @Insert
    void add(Contact item);

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Delete
    void delete(Contact item);

    @Update
    void edit(Contact item);

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(long id);
}
