public class ClearConsoleService {
    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
            else
            {
                System.out.print("\033[H\033[2J");
                System.out.flush();

                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        }
        catch (final Exception e) {
            //  Handle any exceptions.
        }
    }
}