import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

import static org.junit.Assert.*;

public class LoopRequestsTest {
    LoopRequests loop = new LoopRequests();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(stream);
    PrintStream originalOutputStream = System.out;

    @Before
    public void changeOutputStream() {
        System.setOut(out);
    }

    @After
    public void returnStandardOutputStream() {
        System.setOut(originalOutputStream);
    }

    @Test
    public void emptyInput_ShouldCorrectlyFinishLoop() {
        ByteArrayInputStream in = new ByteArrayInputStream(("Exit").getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnOnTV_ShouldShowInfoThatTVIsOn() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is on. Current channel is 1.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnOnTV_TurnOff_ShouldShowInfoThatTVIsOff() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nTurnOff\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is off.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void doubleTurnOnTV_ShouldShowInfoThatTVIsOn() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nTurnOn\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is on. Current channel is 1.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void nothing_ShouldShowInfoThatTVIsOff() {
        ByteArrayInputStream in = new ByteArrayInputStream("Info\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is off.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void doubleTurnOffTV_ShouldShowInfoThatTVIsOff() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOff\r\nTurnOff\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is off.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnOnTV_Set100Channel_ShouldShowInfoThatChannelIsNotChange() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nSelectChannel 100\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is on. Current channel is 1.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnOnTV_Set99Channel_ShouldShowInfoThatChannelIsChange() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nSelectChannel 99\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is on. Current channel is 99.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnOnTV_SetNegativeChannel_ShouldShowInfoThatUnknownCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nSelectChannel -1\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "Unknown command: SelectChannel -1\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void setChannel_ShouldShowInfoThatTVIsOff() {
        ByteArrayInputStream in = new ByteArrayInputStream("SelectChannel 2\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is off.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }

    @Test
    public void turnTV_SelectChannel_TurnOffTV_turnTV_Info_ShouldShowRememberChannel() {
        ByteArrayInputStream in = new ByteArrayInputStream("TurnOn\r\nSelectChannel 2\r\nTurnOff\r\nTurnOn\r\nInfo\r\nExit".getBytes());
        System.setIn(in);
        loop.loopRequests();
        assertEquals(loop.startProgram + "\r\n" + "TV is on. Current channel is 2.\r\n" + loop.finishProgram + "\r\n", stream.toString());
    }



    @Test
    public void nonSelectChannel_ShouldReturn0() {
        assertEquals(loop.isSelectChannel("SelectChannel"), 0);
    }

    @Test
    public void nonSelectChannel2_ShouldReturn0() {
        assertEquals(loop.isSelectChannel("S 2"), 0);
    }

    @Test
    public void selectChannel_ShouldReturnSelectChannelNumber() {
        assertEquals(loop.isSelectChannel("SelectChannel 12"), 12);
    }

    @Test
    public void selectChannel_ShouldReturnSelectChannelNumberEvenNonRight() {
        assertEquals(loop.isSelectChannel("SelectChannel -1"), -1);
    }
}