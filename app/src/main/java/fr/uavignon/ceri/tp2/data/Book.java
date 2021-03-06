package fr.uavignon.ceri.tp2.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book  {

    public static final String TAG = Book.class.getSimpleName();

    public static final long ADD_ID = -1;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookId")
    private long id;


    @ColumnInfo(name = "bookTitle")
    private String title;

    @ColumnInfo(name = "bookAuthors")
    private String authors;

    @ColumnInfo(name = "bookYear")
    private String year; // publication year

    @ColumnInfo(name = "bookGenres")
    private String genres;

    @ColumnInfo(name = "bookPublisher")
    private String publisher;


    public Book(String title, String authors, String year, String genres, String publisher) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.genres = genres;
        this.publisher = publisher;
    }


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenres() { return genres;}

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return this.title+"("+this.authors+")";
    }



    public static Book[] books = {new Book("Rouge Br??sil", "J.-C. Rufin", "2003", "roman d'aventure, roman historique", "Gallimard"),
            new Book("Guerre et paix", "L. Tolsto??", "1865-1869", "roman historique", "Gallimard"),
            new Book("Fondation", "I. Asimov", "1957", "roman de science-fiction", "Hachette"),
            new Book("Du c??t?? de chez Swan", "M. Proust", "1913", "roman", "Gallimard"),
            new Book("Le Comte de Monte-Cristo", "A. Dumas", "1844-1846", "roman-feuilleton", "Flammarion"),
            new Book("L'Iliade", "Hom??re", "8e si??cle av. J.-C.", "roman classique", "L'??cole des loisirs"),
            new Book("Histoire de Babar, le petit ??l??phant", "J. de Brunhoff", "1931", "livre pour enfant", "??ditions du Jardin des modes"),
            new Book("Le Grand Pouvoir du Chninkel", "J. Van Hamme et G. Rosi??ski", "1988", "BD fantasy", "Casterman"),
            new Book("Ast??rix chez les Bretons", "R. Goscinny et A. Uderzo", "1967", "BD aventure", "Hachette"),
            new Book("Monster", "N. Urasawa", "1994-2001", "manga policier", "Kana Eds"),
            new Book("V pour Vendetta", "A. Moore et D. Lloyd", "1982-1990", "comics", "Hachette")};
}
