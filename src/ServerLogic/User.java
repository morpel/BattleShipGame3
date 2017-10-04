package ServerLogic;

public class User implements Comparable{
    private String name;

    public User(String i_Name) {
        name = i_Name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User) o).name);
    }
}
