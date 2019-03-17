/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to be able
 * to check out books, if a copy of the requested book is available.
 *
 * @author dgropp
 */

class Library {

    final int maxBookCapacity; // The maximal number of books this library can hold.
    final int maxBorrowedBooks; // The maximal number of books this library allows a patron to borrow at the same time.
    final int maxPatronCapacity; // The maximal number of registered patrons this library can handle.
    Book[] libraryBooks; // Array of all library books and open book slots.
    Patron[] libraryPatrons; // Array of all library patrons and open patron slots.

    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow at the
     *                          same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity,
            int maxBorrowedBooks,
            int maxPatronCapacity) {
        this.maxBookCapacity = maxBookCapacity;
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.maxPatronCapacity = maxPatronCapacity;
        // Assign books array with null Book objects to match max nook capacity.
        this.libraryBooks = new Book[maxBookCapacity];
        // Assign patrons array with null Patron objects to match max patron capacity.
        this.libraryPatrons = new Patron[maxPatronCapacity];
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        return this.attemptInsert(book, this.libraryBooks); // Call attemptInsert() helper method.
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return idValid(bookId, this.libraryBooks); // Call idValid() helper method.
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        return this.getId(book, this.libraryBooks); // Call getId() helper method.
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        // Checks if Book ID is valid, and if so checks if is different than default book borrower num (-1).
        if (this.isBookIdValid(bookId)) {
            return this.libraryBooks[bookId].getCurrentBorrowerId() == -1;
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered,
     * a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        return this.attemptInsert(patron, this.libraryPatrons); // Call attemptInsert() helper method.
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return idValid(patronId, this.libraryPatrons); // Call idValid() helper method.
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        return this.getId(patron, this.libraryPatrons); // Call getId() helper method.
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is
     * available, the given patron isn't already borrowing the maximal number of books allowed, and if the patron
     * will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        // Before starting whole process, checks if book and patron ID's are valid.
        if (this.isBookIdValid(bookId) && this.isPatronIdValid(patronId)) {
            // Call patronCanBorrow() helper method to determine if patron can borrow book.
            boolean canBorrow = this.patronCanBorrow(patronId, 0, 0);
            // Call willEnjoyBook() method from Patron class to determine if patron will enjoy the book.
            boolean willEnjoy = this.libraryPatrons[patronId].willEnjoyBook(this.libraryBooks[bookId]);
            // Call isBookAvailable() method to determine if book is available to borrow.
            boolean isAvailable = this.isBookAvailable(bookId);
            // If all tests were true, mark book as borrowed to this patron, and return true.
            if (canBorrow && willEnjoy && isAvailable) {
                this.libraryBooks[bookId].setBorrowerId(patronId);
                return true;
            }
        }
        return false; // If at least 1 test failed.
    }

    /**
     * Return the given book.
     *
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (this.isBookIdValid(bookId)) {
            this.libraryBooks[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy,
     * if any such exist.
     *
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        // Before starting whole process, checks if patron ID is valid.
        if (this.isPatronIdValid(patronId)) {
            Patron patron = this.libraryPatrons[patronId];
            // Loop over all book list.
            for (int id = 0; id < this.libraryBooks.length; id++) {
                Book book = this.libraryBooks[id];
                // If book object is null, break loop.
                if (book == null) {
                    break;
                }
                // Calls Patron class method that determines if patron will enjoy book, and checks if it is available.
                else if (patron.willEnjoyBook(book) && this.isBookAvailable(id)) {
                    return book;
                }
            }
        }
        return null; // If no books found.
    }

    /**
     * Helper method to add books/patrons to library.
     *
     * @param input Object to insert to array.
     * @param list  Array to check and insert object input.
     * @return A non-negative id number for the insert if the input was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int attemptInsert(Object input, Object[] list) {
        int insertIdx = this.findFreeSlot(list, 0, list.length - 1); // Call findFreeSlot() helper method.
        // If found index slot is not default number (-1), assign input to this slot.
        if (insertIdx != -1) {
            list[insertIdx] = input;
        }
        return insertIdx; // Either insert index or return -1.
    }

    /**
     * Helper recursive method for attemptInsert() method, that uses binary search algorithm
     * to find the first array field that is null (if any).
     *
     * @param list  Array to find first field that is null (if any).
     * @param start First index of binary search (starting at 0).
     * @param end   Last index of binary search (starting at last list index).
     * @return A non-negative number with index of first null field if found, -1 if otherwise.
     */
    int findFreeSlot(Object[] list, int start, int end) {
        int mid = (start + end) / 2; // Assign middle number with average of start and end.
        // Recursion base: No empty slot found and reached last index. Return default number (-1).
        if (end < start) {
            return -1;
            // Recursion steps:
        } else if (list[mid] != null) {
            // If mid index not null, call method again with start as (mid + 1).
            return this.findFreeSlot(list, mid + 1, end);
        } else if (mid == 0 || list[mid - 1] != null) {
            // Mid index is null. If mid is first index or index before it is not null: empty slot found.
            return mid;
        }
        // Empty slot not found yet: call method again with end as (mid - 1).
        return this.findFreeSlot(list, start, mid - 1);
    }

    /**
     * Helper method to get book/patron ID.
     *
     * @param input Object to find ID of.
     * @param list  Array to find ID in.
     * @return A non-negative id number if found, -1 otherwise.
     */
    int getId(Object input, Object[] list) {
        // Loops over the object list.
        for (int id = 0; id < list.length; id++) {
            // If list entry matches input, returns its index.
            if (list[id] == input) {
                return id;
            }
        }
        return -1; // Nothing found.
    }

    /**
     * Helper method to find if ID in range of list and represents non-null value.
     *
     * @param id   To check if in range and not null.
     * @param list To check ID existence in.
     * @return True if in range and not null, false if otherwise.
     */
    boolean idValid(int id, Object[] list) {
        // Checks if id is in list range, and if not return false.
        if (id >= list.length || id < 0) {
            return false;
        }
        return list[id] != null; // boolean value if this id represents assigned object or null.
    }

    /**
     * Helper recursive method to find if patron can borrow book.
     *
     * @param patronId      Patron id to check.
     * @param idx           Index of library books list.
     * @param booksBorrowed Counter of how many borrowed books found so far.
     * @return True if patron can borrow more books, false if otherwise.
     */
    boolean patronCanBorrow(int patronId, int idx, int booksBorrowed) {
        // Recursion base: If null index found or index out of list range.
        if (idx >= this.libraryBooks.length || this.libraryBooks[idx] == null) {
            // Returns true if patron not over the max borrowed books, false otherwise.
            return booksBorrowed < this.maxBorrowedBooks;
        } else if (this.libraryBooks[idx].getCurrentBorrowerId() == patronId) { // Recursion steps:
            // If book has patron id marked as borrower, call method again with next index and add 1 to counter.
            return this.patronCanBorrow(patronId, idx + 1, booksBorrowed + 1);
        } else {
            // Book is not borrowed or has other borrower, call method again with next index.
            return this.patronCanBorrow(patronId, idx + 1, booksBorrowed);
        }
    }
}
