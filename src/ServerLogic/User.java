package ServerLogic;

import Logic.GameLogic;

public class User implements Comparable{
    private String name;
    private Game myCurrrentGame;

    public User(String i_Name) {
        name = i_Name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User) o).name);
    }

    public String getName() {
        return name;
    }
}
