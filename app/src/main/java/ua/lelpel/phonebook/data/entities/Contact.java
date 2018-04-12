package ua.lelpel.phonebook.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.time.LocalDateTime;

/**
 * Created by bruce on 30.03.2018.
 */

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)

    private long id;

    public Contact(String firstName, String lastName, String phoneNumber, String email, String imagePath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imagePath = imagePath;
    }

    private String firstName;
    private String lastName;

    private String phoneNumber;
    private String email;

    private String imagePath;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }
}
