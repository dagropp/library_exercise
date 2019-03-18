/**
 * This class represents a library patron that has a name and assigns values to different literary aspects of books.
 *
 * @author dgropp
 */
class Patron {
    private final String FIRST_NAME; // The first name of this patron.
    private final String LAST_NAME; // The last name of this patron.
    private final int COMIC_TENDENCY; // The weight this patron assigns to the comic aspects of books.
    private final int DRAMATIC_TENDENCY; // The weight this patron assigns to the dramatic aspects of books.
    private final int EDUCATIONAL_TENDENCY; // The weight this patron assigns to the educational aspects of books.
    private final int ENJOYMENT_THRESHOLD; // The minimal literary value a book must have for this patron to enjoy it.

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristics.
     *
     * @param firstName           The first name of the patron.
     * @param lastName            The last name of the patron.
     * @param comicTendency       The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency    The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns to the educational aspects of books.
     * @param enjoymentThreshold  The minimal literary value a book must have for this patron to enjoy it.
     */
    public Patron(String firstName,
                  String lastName,
                  int comicTendency,
                  int dramaticTendency,
                  int educationalTendency,
                  int enjoymentThreshold) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.COMIC_TENDENCY = comicTendency;
        this.DRAMATIC_TENDENCY = dramaticTendency;
        this.EDUCATIONAL_TENDENCY = educationalTendency;
        this.ENJOYMENT_THRESHOLD = enjoymentThreshold;
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
        return this.FIRST_NAME + " " + this.LAST_NAME;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     *
     * @param book The book to asses.
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        // Multiplies patron's comic/dramatic/educational tendencies with the book's respective values.
        int totalComedy = this.COMIC_TENDENCY * book.getComicValue();
        int totalDrama = this.DRAMATIC_TENDENCY * book.getDramaticValue();
        int totalEducation = this.EDUCATIONAL_TENDENCY * book.getEducationalValue();
        return totalComedy + totalDrama + totalEducation; // Sum of all multiplications.
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     *
     * @param book The book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return this.getBookScore(book) >= this.ENJOYMENT_THRESHOLD;
    }
}
