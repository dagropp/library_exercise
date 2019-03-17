/**
 * This class represents a library patron that has a name and assigns values to different literary aspects of books.
 *
 * @author dgropp
 */
class Patron {
    final String patronFirstName; // The first name of this patron.
    final String patronLastName; // The last name of this patron.
    final int comicTendency; // The weight this patron assigns to the comic aspects of books.
    final int dramaticTendency; // The weight this patron assigns to the dramatic aspects of books.
    final int educationalTendency; // The weight this patron assigns to the educational aspects of books.
    final int patronEnjoymentThreshold; // The minimal literary value a book must have for this patron to enjoy it.
    int borrowedBooks;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristics.
     *
     * @param patronFirstName          The first name of the patron.
     * @param patronLastName           The last name of the patron.
     * @param comicTendency            The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency         The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency      The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName,
           String patronLastName,
           int comicTendency,
           int dramaticTendency,
           int educationalTendency,
           int patronEnjoymentThreshold) {
        this.patronFirstName = patronFirstName;
        this.patronLastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.patronEnjoymentThreshold = patronEnjoymentThreshold;
        this.borrowedBooks = 0;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name, separated by a
     * single white space. For example, if the patron's first name is "Ricky" and his last name is "Bobby", this method
     * will return the String "Ricky Bobby".
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return this.patronFirstName + " " + this.patronLastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     *
     * @param book The book to asses.
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        // Multiplies patron's comic/dramatic/educational tendencies with the book's respective values.
        int totalComedy = this.comicTendency * book.getComicValue();
        int totalDrama = this.dramaticTendency * book.getDramaticValue();
        int totalEducation = this.educationalTendency * book.getEducationalValue();
        return totalComedy + totalDrama + totalEducation; // Sum of all multiplications.
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     *
     * @param book The book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return this.getBookScore(book) >= this.patronEnjoymentThreshold;
    }
}
