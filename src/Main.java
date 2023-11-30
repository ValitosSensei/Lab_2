import java.util.ArrayList;
import java.util.List;

abstract class Item {
    private String title;
    private String uniqueID;
    private boolean isBorrowed;

    public Item(String title, String uniqueID) {
        this.title = title;
        this.uniqueID = uniqueID;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    abstract void borrowItem();

    abstract void returnItem();
}

class Book extends Item {
    private String author;

    public Book(String title, String uniqueID, String author) {
        super(title, uniqueID);
        this.author = author;
    }

    @Override
    void borrowItem() {
        setBorrowed(true);
    }

    @Override
    void returnItem() {
        setBorrowed(false);
    }
}

class DVD extends Item {
    private int duration;

    public DVD(String title, String uniqueID, int duration) {
        super(title, uniqueID);
        this.duration = duration;
    }

    @Override
    void borrowItem() {
        setBorrowed(true);
    }

    @Override
    void returnItem() {
        setBorrowed(false);
    }
}

class Patron {
    private String name;
    private String ID;
    private List<Item> borrowedItems;

    public Patron(String name, String ID) {
        this.name = name;
        this.ID = ID;
        this.borrowedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    public void borrow(Item item) {
        borrowedItems.add(item);
        item.borrowItem();
    }

    public void returnItem(Item item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}

interface IManageable {
    void add(Item item);

    void remove(Item item);

    void listAvailable();

    void listBorrowed();
}

class Library implements IManageable {
    private List<Item> items;
    private List<Patron> patrons;

    public Library() {
        this.items = new ArrayList<>();
        this.patrons = new ArrayList<>();
    }

    @Override
    public void add(Item item) {
        items.add(item);
    }

    @Override
    public void remove(Item item) {
        items.remove(item);
    }

    @Override
    public void listAvailable() {
        System.out.println("Available Items:");
        for (Item item : items) {
            if (!item.isBorrowed()) {
                System.out.println(item.getTitle() + " (ID: " + item.getUniqueID() + ")");
            }
        }
    }

    @Override
    public void listBorrowed() {
        System.out.println("Borrowed Items:");
        for (Item item : items) {
            if (item.isBorrowed()) {
                System.out.println(item.getTitle() + " (ID: " + item.getUniqueID() + ")");
            }
        }
    }

    public void registerPatron(Patron patron) {
        patrons.add(patron);
    }

    public void lendItem(Patron patron, Item item) {
        if (!item.isBorrowed()) {
            patron.borrow(item);
            System.out.println(item.getTitle() + " has been borrowed by " + patron.getName());
        } else {
            System.out.println("Item is already borrowed.");
        }
    }

    public void returnItem(Patron patron, Item item) {
        if (patron.getBorrowedItems().contains(item)) {
            patron.returnItem(item);
            System.out.println(item.getTitle() + " has been returned by " + patron.getName());
        } else {
            System.out.println("Patron did not borrow this item.");
        }
    }

    // Test functionality
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("Book 1", "B001", "Author 1");
        Book book2 = new Book("Book 2", "B002", "Author 2");
        DVD dvd1 = new DVD("DVD 1", "D001", 120);

        library.add(book1);
        library.add(book2);
        library.add(dvd1);

        Patron patron1 = new Patron("Patron 1", "P001");
        Patron patron2 = new Patron("Patron 2", "P002");

        library.registerPatron(patron1);
        library.registerPatron(patron2);

        library.listAvailable();

        library.lendItem(patron1, book1);
        library.lendItem(patron2, dvd1);

        library.listBorrowed();
    }
}
