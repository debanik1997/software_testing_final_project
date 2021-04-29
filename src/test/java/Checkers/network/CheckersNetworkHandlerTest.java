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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

public class CheckersNetworkHandlerTest {

    private ConnectionHandler chMock;
    private CheckersWindow checkersWindowMock;
    private CheckerBoard checkerBoardMock;
    private OptionPanel optionPanelMock;
    private CheckersNetworkHandler checkersNetworkHandler;
    private Socket socketMock;
    private OutputStream outputMock;

    @BeforeEach
    void setup() {
        chMock = mock(ConnectionHandler.class);
        checkersWindowMock = mock(CheckersWindow.class);
        checkerBoardMock = mock(CheckerBoard.class);
        optionPanelMock = mock(OptionPanel.class);
        checkersNetworkHandler = new CheckersNetworkHandler(true, checkersWindowMock, checkerBoardMock, optionPanelMock);
        socketMock = mock(Socket.class);
        outputMock = mock(OutputStream.class);
    }

    @Test
    public void testConnectionHandlerMocksSendResponseBehaviorSocketIsClosedAndGetSocketWhenSocketIsClosed() {
        when(chMock.getSocket()).thenReturn(socketMock);
        when(socketMock.isClosed()).thenReturn(true);

        CheckersNetworkHandler.sendResponse(chMock, "response");
        InOrder inOrder = inOrder(socketMock, chMock);
        inOrder.verify(chMock, times(1)).getSocket();
        inOrder.verify(socketMock, times(1)).isClosed();
    }

    @Test
    public void testConnectionHandlerMocksSendResponseBehaviorSocketIsClosedAndGetSocketWhenSocketIsNotClosed() throws IOException {
        when(chMock.getSocket()).thenReturn(socketMock);
        when(socketMock.isClosed()).thenReturn(false);
        when(socketMock.getOutputStream()).thenReturn(outputMock);

        CheckersNetworkHandler.sendResponse(chMock, "response");
        InOrder inOrder = inOrder(socketMock, chMock, outputMock);
        inOrder.verify(chMock, times(1)).getSocket();
        inOrder.verify(socketMock, times(1)).isClosed();
        inOrder.verify(socketMock, times(1)).getOutputStream();
        inOrder.verify(outputMock, times(1)).write(any());
        inOrder.verify(outputMock, times(1)).flush();
        inOrder.verify(socketMock, times(1)).close();
    }

    @Test
    public void testConnectionHandlerMocksSendResponseBehaviorIOException() throws IOException {
        doThrow(IOException.class).when(outputMock).write(any());
        when(chMock.getSocket()).thenReturn(socketMock);
        when(socketMock.isClosed()).thenReturn(false);
        when(socketMock.getOutputStream()).thenReturn(outputMock);

        CheckersNetworkHandler.sendResponse(chMock, "response");

        verify(outputMock, never()).flush();
    }

}
