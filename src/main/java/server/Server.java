package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
//    public static void main(String[] args) {
//        Spark.port(9000);
//        Router.init();
//        DebugScreen.enableDebugScreen();
//    }
    public static void main(String[] args) {
        Spark.port(getPort());
        Router.init();
        DebugScreen.enableDebugScreen();
    }

    private static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 9000;
    }

}