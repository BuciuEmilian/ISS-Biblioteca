package iss.library.libraryiss1.model;

public class Librarian extends Registered {
    private int salary;

    public Librarian(String username, String password) {
        super("", username, password);
        this.salary = 0;
    }

    public Librarian(String name, String username, String password, int salary) {
        super(name, username, password);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
