import Checkers.network.CheckersNetworkHandler;
import Checkers.network.ConnectionHandler;
import Checkers.ui.CheckerBoard;
import Checkers.ui.CheckersWindow;
import Checkers.ui.OptionPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CheckersNetworkHandlerTest {

    @Test
    public void testMock() {
        ConnectionHandler ch = mock(ConnectionHandler.class);
        CheckersWindow window = mock(CheckersWindow.class);
        CheckerBoard board = mock(CheckerBoard.class);
        OptionPanel panel = mock(OptionPanel.class);
        CheckersNetworkHandler handler = new CheckersNetworkHandler(true, window, board, panel);
        Socket s = mock(Socket.class);
        when(ch.getSocket()).thenReturn(s);
        when(s.isClosed()).thenReturn(true);

        CheckersNetworkHandler.sendResponse(ch, "response");
        InOrder inOrder = inOrder(s);
        inOrder.verify(s, times(1)).isClosed();
        verifyNoInteractions(s, ch);


    }


}
