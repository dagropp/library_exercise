/**
 * This class represents a book, which has a title, author, year of publication and different literary aspects.
 *
 * @author dgropp
 */
public class Book {
    private final String TITLE; // The title of this book.
    private final String AUTHOR; //The name of the author of this book.
    private final int PUBLICATION_YEAR; // The year this book was published.
    private final int COMIC_VALUE; // The comic value of this book
    private final int DRAMATIC_VALUE; // The dramatic value of this book.
    private final int EDUCATIONAL_VALUE; // The educational value of this book.
    private static final int DEFAULT_NUM = -1; // Default num for empty slots and error values.
    private int currentBorrowerId = Book.DEFAULT_NUM; // The id of the current borrower of this book.

    /*----=  Constructors  =-----*/

    /**
     * Creates a new book with the given characteristic.
     *
     * @param title            The title of the book.
     * @param author           The name of the author of the book.
     * @param publicationYear  The year the book was published.
     * @param comicValue       The comic value of the book.
     * @param dramaticValue    The dramatic value of the book.
     * @param educationalValue The educational value of the book.
     */
    public Book(String title, String author,
                int publicationYear,
                int comicValue,
                int dramaticValue,
                int educationalValue) {
        this.TITLE = title;
        this.AUTHOR = author;
        this.PUBLICATION_YEAR = publicationYear;
        this.COMIC_VALUE = comicValue;
        this.DRAMATIC_VALUE = dramaticValue;
        this.EDUCATIONAL_VALUE = educationalValue;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the book, which is a sequence
     * of the title, author, year of publication and the total literary value of the book, separated by
     * commas, inclosed in square brackets. For example, if the book is
     * titled "Monkey Feet", was written by Ernie Douglas, published in 1987 and has a comic value of 7,
     * dramatic value of 3 and an educational value of 1, this method will return the string:
     * "[MonkeyFeet,Ernie Douglas,1987,11]"
     *
     * @return the String representation of this book.
     */
    String stringRepresentation() {
        return "[" + this.TITLE + "," + this.AUTHOR + "," + this.PUBLICATION_YEAR + "," + this.getLiteraryValue() + "]";
    }

    /**
     * Sets the given id as the id of the current borrower of this book, -1 if no patron is currently borrowing it.
     *
     * @param borrowerId Id number of the patron borrowing book.
     */
    void setBorrowerId(int borrowerId) {
        this.currentBorrowerId = borrowerId;
    }

    /**
     * @return the id of the current borrower of this book.
     */
    int getCurrentBorrowerId() {
        return this.currentBorrowerId;
    }

    /**
     * Marks this book as returned.
     */
    void returnBook() {
        this.setBorrowerId(Book.DEFAULT_NUM);
    }

    /**
     * @return the comic value of this book.
     */
    int getComicValue() {
        return this.COMIC_VALUE;
    }

    /**
     * @return the dramatic value of this book.
     */
    int getDramaticValue() {
        return this.DRAMATIC_VALUE;
    }

    /**
     * @return the educational value of this book.
     */
    int getEducationalValue() {
        return this.EDUCATIONAL_VALUE;
    }

    /**
     * @return the literary value of this book, which is defined as the sum of its comic value, its dramatic
     * value and its educational value.
     */
    private int getLiteraryValue() {
        return this.COMIC_VALUE + this.DRAMATIC_VALUE + this.EDUCATIONAL_VALUE;
    }
}
