package ua.lelpel.phonebook.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ua.lelpel.phonebook.data.dao.ContactDao;
import ua.lelpel.phonebook.data.entities.Contact;

/**
 * @author bruce
 */

@Database(version = 1, entities = Contact.class, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    abstract public ContactDao contactDao();
}
