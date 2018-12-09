package office;

import back.Backend;
import front.Frontend;


public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend);

        frontend.start();
    }
}