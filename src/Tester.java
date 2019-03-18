public class Tester {
    public static void main(String[] args) {
//        Tester.testBook();
//        Tester.testPatron();
        int[] params = new int[args.length];
        for (int input = 0; input < args.length; input++)
            params[input] = Integer.parseInt(args[input]);
        Tester.testLibrary(params[0], params[1], params[2], params[3]);
    }

    private static int binarySearch(int[] list, int start, int end) {
        System.out.println("- Function called -");
        int mid = (start + end) / 2;
        if (end < start) {
            return -1;
        } else if (list[mid] != 0) {
            return binarySearch(list, mid + 1, end);
        } else if (list[mid - 1] != 0) {
            return mid;
        }
        return binarySearch(list, start, mid - 1);
    }

    private static void testBook() {
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 2002, 7, 10, 7);
        System.out.println(book1.stringRepresentation());
        System.out.println("Literary value: " + book1.getLiteraryValue());
        System.out.println("Borrower ID (before): " + book1.getCurrentBorrowerId());
        book1.setBorrowerId(3);
        System.out.println("Borrower ID (after): " + book1.getCurrentBorrowerId());
        book1.returnBook();
        System.out.println("Borrower ID (after return): " + book1.getCurrentBorrowerId());
    }

    private static void testLibrary(int maxBooks, int maxBorrowing, int maxPatrons, int testUser) {
        Book[] bookList = Tester.createBookList();
        Patron[] patronList = Tester.createPatronList();
        Library library = new Library(maxBooks, maxBorrowing, maxPatrons);
        for (Book item : bookList) {
            System.out.println(item.stringRepresentation());
            System.out.println("Book added, its ID: " + library.addBookToLibrary(item));
            System.out.println("again, its ID: " + library.getBookId(item));
        }
        Book bookTest = new Book("The Hobbit", "J.R.R. Tolkien", 1937, 8, 9, 9);
        System.out.println("Book test... Try to add: " + library.addBookToLibrary(bookTest));
        System.out.println("Book test... Try to get ID: " + library.getBookId(bookTest));
        System.out.println("Borrow 'The Two Towers': " + library.borrowBook(5, 3));
        System.out.println("Availability of 'The Two Towers': " + library.isBookAvailable(5));
        library.returnBook(5);
        library.returnBook(4);
        System.out.println("Return 'The Two Towers' ->");
        System.out.println("Availability of 'The Two Towers': " + library.isBookAvailable(5));
        System.out.println("Availability of 'The Fellowship of the Ring': " + library.isBookAvailable(4));
        System.out.println("Availability of non-existing book1: " + library.isBookAvailable(10));
        System.out.println("Availability of non-existing book2: " + library.isBookAvailable(-3));
        for (Patron person : patronList) {
            System.out.println(person.stringRepresentation());
            System.out.println("Patron added, its ID: " + library.registerPatronToLibrary(person));
            System.out.println("again, its ID:" + library.getPatronId(person));
        }
        System.out.println("Is patron ID 10 valid? " + library.isPatronIdValid(10));
        Patron patronTest = new Patron("Georg", "Chrysler", 10, 10, 10, 300);
        System.out.println("Patron test... Try to add: " + library.registerPatronToLibrary(patronTest));
        System.out.println("Patron test... Try to get ID: " + library.getPatronId(patronTest));
        System.out.println("Is patron ID 10 valid? " + library.isPatronIdValid(10));
        System.out.println("::ALL LIBRARY BOOKS::");
        for (Book item : library.libraryBooks)
            if (item != null)
                System.out.println("Book: " + item.stringRepresentation());
        System.out.println("::ALL LIBRARY PATRONS::");
        for (Patron person : library.libraryPatrons)
            if (person != null)
                System.out.println("Patron: " + person.stringRepresentation());
        System.out.println("::OVER BORROWING::");
        for (int book = 0; book < library.libraryBooks.length; book++) {
            library.borrowBook(book, testUser);
            Book book1 = library.libraryBooks[book];
            if (book1 == null)
                break;
            Patron patron = library.libraryPatrons[testUser];
            String bookName = book1.stringRepresentation();
            String borrowerName = patron.stringRepresentation();
            int borrowerID = book1.getCurrentBorrowerId();
            int enjoyment = patron.ENJOYMENT_THRESHOLD;
            int bookScore = patron.getBookScore(book1);
            boolean willEnjoy = patron.willEnjoyBook(book1);
            System.out.println("Tendency: " + enjoyment + ", Book score: " + bookScore + ", Will enjoy?" + willEnjoy);
            System.out.println("Book " + bookName + " was borrowed by " + borrowerName + ": " + borrowerID);
        }
        System.out.println("::BOOK SUGGESTION::");
        System.out.println("Patron's enjoyment: " + library.libraryPatrons[9].ENJOYMENT_THRESHOLD);
        for (Book item : library.libraryBooks) {
            if (item == null)
                break;
            Patron patron = library.libraryPatrons[9];
            int score = patron.getBookScore(item);
            boolean enjoy = patron.willEnjoyBook(item);
            boolean available = item.currentBorrowerId == -1;
            System.out.println(item.stringRepresentation() + " score: " + score + "; Will enjoy? " + enjoy + "; Is available? " + available);
        }
        Book suggestion = library.suggestBookToPatron(9);
        if (suggestion != null)
            System.out.println("Suggest book to bibi: " + suggestion.stringRepresentation());
        else
            System.out.println("No books found for bibi");
    }

    private static Book[] createBookList() {
        Book[] bookList = new Book[10];
        bookList[0] = new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997, 7, 10, 7);
        bookList[1] = new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", 1998, 7, 10, 7);
        bookList[2] = new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", 1999, 7, 10, 7);
        bookList[3] = new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", 2000, 7, 10, 7);
        bookList[4] = new Book("The Fellowship of the Ring", "J.R.R. Tolkien", 1954, 5, 10, 8);
        bookList[5] = new Book("The Two Towers", "J.R.R. Tolkien", 1954, 5, 10, 8);
        bookList[6] = new Book("The Return of the King", "J.R.R. Tolkien", 1955, 5, 10, 8);
        bookList[7] = new Book("Momo", "Michael Ende", 1973, 6, 8, 10);
        bookList[8] = new Book("Zorba the Greek", "Nikos Kazantzakis", 1946, 6, 8, 6);
        bookList[9] = new Book("One Hundred Years of Solitude", "Gabriel García Márquez", 1967, 8, 8, 8);
        return bookList;
    }

    private static void testPatron() {
        Patron[] patronList = Tester.createPatronList();
        for (Patron person : patronList) {
            System.out.println(person.stringRepresentation());
        }
    }

    private static Patron[] createPatronList() {
        Patron[] patronList = new Patron[10];
        patronList[0] = new Patron("Daniel", "Gropp", 10, 5, 7, 170);
        patronList[1] = new Patron("Shira", "Ben Ari", 7, 10, 5, 170);
        patronList[2] = new Patron("Amos", "Gropp", 10, 6, 7, 170);
        patronList[3] = new Patron("Jonathan", "Gropp", 8, 8, 10, 143);
        patronList[4] = new Patron("Yoav", "Cohen", 9, 7, 6, 180);
        patronList[5] = new Patron("Yaara", "Ben-Da", 8, 6, 9, 120);
        patronList[6] = new Patron("Hadas", "Golan", 7, 9, 8, 164);
        patronList[7] = new Patron("Mimi", "Huppert", 7, 6, 10, 143);
        patronList[8] = new Patron("Noga", "Frost", 6, 9, 10, 170);
        patronList[9] = new Patron("Benjamin", "Netanyahu", 3, 10, 4, 140);
        return patronList;
    }
}
