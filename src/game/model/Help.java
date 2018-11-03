package game.model;

public class Help {
    // This is a purely static class
    private Help() {}

    public static String GO_USAGE = "go <area>";
    public static String INSPECT_USAGE = "inspect <item>";
    public static String LOOK_USAGE = "look";

    public static void handleHelp() {
        World.print(
            GO_USAGE, " - go to area\n",
            INSPECT_USAGE, " - inspect an item\n",
            LOOK_USAGE, " - look at the area you're in\n",
            "help - print this helpful message again!\n"
        );
    }
}
