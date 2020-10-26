import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoopRequests {
    TV tv = new TV();
    public final String startProgram = "The program is started.";
    public final String finishProgram = "The program is finished.";
    public final String unknownCommand = "Unknown command: ";
    private final String TURN_ON = "TurnOn";
    private final String TURN_OFF = "TurnOff";
    private final String SELECT_CHANNEL = "SelectChannel";
    private final String INFO = "Info";
    private final String EXIT = "Exit";

    public void loopRequests() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(startProgram);
            String line;
            while (!(line = reader.readLine()).equals(EXIT)) {
                switch (line) {
                    case TURN_ON:
                        tv.setPower(true);
                        continue;
                    case TURN_OFF:
                        tv.setPower(false);
                        continue;
                    case INFO:
                        System.out.println(tv.getInfo());
                        continue;
                    default:
                        int isSelectChannel = isSelectChannel(line);
                        if (isSelectChannel > 0) {
                            tv.setChannel(isSelectChannel);
                            continue;
                        }
                        System.out.println(unknownCommand + line);
                }
            }
            System.out.println(finishProgram);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public int isSelectChannel(String line) {
        String[] lines = line.split("\\s");
        if (lines.length == 2 && lines[0].equals(SELECT_CHANNEL)) {
            try {
                return Integer.parseInt(lines[1]);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}