import database.UserDatabase;
import views.MainFrame;


public abstract class Main {
    public static void main(String[] args) {

        new MainFrame();

        UserDatabase.connect();

    }
}
