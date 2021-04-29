import Checkers.network.CheckersNetworkHandler;
import Checkers.network.ConnectionHandler;
import Checkers.network.ConnectionListener;
import Checkers.network.Session;
import Checkers.ui.CheckerBoard;
import Checkers.ui.CheckersWindow;
import Checkers.ui.NetworkWindow;
import Checkers.ui.OptionPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
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
    public void testActionPerformedReadUpdateCommand() {
        ActionEvent aeMock = mock(ActionEvent.class);
        when(aeMock.getSource()).thenReturn(chMock);
        when(chMock.getSocket()).thenReturn(socketMock);

        try (MockedStatic<ConnectionListener> clMock = mockStatic(ConnectionListener.class)) {
            clMock.when(() -> ConnectionListener.read(socketMock)).thenReturn("UPDATE");
            Session session1 = mock(Session.class);
            Session session2 = mock(Session.class);
            when(checkersWindowMock.getSession1()).thenReturn(session1);
            when(checkersWindowMock.getSession2()).thenReturn(session2);
            CheckersNetworkHandler cnhSpy = spy(checkersNetworkHandler);

            try {
                cnhSpy.actionPerformed(aeMock);
            } catch (Exception e) {

            }
            verify(cnhSpy).handleUpdate("");
        }
    }

    @Test
    public void testActionPerformedReadConnectCommand() {
        ActionEvent aeMock = mock(ActionEvent.class);
        when(aeMock.getSource()).thenReturn(chMock);
        when(chMock.getSocket()).thenReturn(socketMock);

        try (MockedStatic<ConnectionListener> clMock = mockStatic(ConnectionListener.class)) {
            clMock.when(() -> ConnectionListener.read(socketMock)).thenReturn("CONNECT");
            Session session1 = mock(Session.class);
            Session session2 = mock(Session.class);
            when(checkersWindowMock.getSession1()).thenReturn(session1);
            when(checkersWindowMock.getSession2()).thenReturn(session2);
            CheckersNetworkHandler cnhSpy = spy(checkersNetworkHandler);

            try {
                cnhSpy.actionPerformed(aeMock);
            } catch (Exception e) {

            }
            verify(cnhSpy).handleConnect(socketMock, -1, false);
        }
    }

    @Test
    public void testActionPerformedReadGetStateCommand() {
        ActionEvent aeMock = mock(ActionEvent.class);
        when(aeMock.getSource()).thenReturn(chMock);
        when(chMock.getSocket()).thenReturn(socketMock);

        try (MockedStatic<ConnectionListener> clMock = mockStatic(ConnectionListener.class)) {
            clMock.when(() -> ConnectionListener.read(socketMock)).thenReturn("GET-STATE");
            Session session1 = mock(Session.class);
            Session session2 = mock(Session.class);
            when(session1.getSid()).thenReturn("");
            when(session2.getSid()).thenReturn("");
            when(checkersWindowMock.getSession1()).thenReturn(session1);
            when(checkersWindowMock.getSession2()).thenReturn(session2);

            try {
                checkersNetworkHandler.actionPerformed(aeMock);
            } catch (Exception e) {

            }
            InOrder inorder = inOrder(checkerBoardMock);
            inorder.verify(checkerBoardMock, times(1)).getGame();
        }
    }

    @Test
    public void testActionPerformedReadDisconnectCommand() {
        ActionEvent aeMock = mock(ActionEvent.class);
        when(aeMock.getSource()).thenReturn(chMock);
        when(chMock.getSocket()).thenReturn(socketMock);

        try (MockedStatic<ConnectionListener> clMock = mockStatic(ConnectionListener.class)) {
            clMock.when(() -> ConnectionListener.read(socketMock)).thenReturn("DISCONNECT");
            Session session1 = mock(Session.class);
            Session session2 = mock(Session.class);
            when(checkersWindowMock.getSession1()).thenReturn(session1);
            when(checkersWindowMock.getSession2()).thenReturn(session2);
            when(session1.getSid()).thenReturn("");
            when(session2.getSid()).thenReturn("");
            NetworkWindow nwMock = mock(NetworkWindow.class);
            when(optionPanelMock.getNetworkWindow1()).thenReturn(nwMock);

            try {
                checkersNetworkHandler.actionPerformed(aeMock);
            } catch (Exception e) {

            }
            InOrder inorder = inOrder(nwMock);
            inorder.verify(nwMock, times(1)).setCanUpdateConnect(true);
        }
    }

    @Test
    public void testHandleConnectDeniedUserAlreadyConnected() {
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        when(checkersWindowMock.getSession1()).thenReturn(session1);
        when(checkersWindowMock.getSession2()).thenReturn(session2);
        when(session1.getSid()).thenReturn("one");
        when(session2.getSid()).thenReturn("two");
        String s = checkersNetworkHandler.handleConnect(socketMock, 0, true);
        assertEquals(CheckersNetworkHandler.RESPONSE_DENIED + "\nError: user already connected.", s);
    }

    @Test
    public void testHandleConnectionDeniedNotValidConnection() {
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        when(checkersWindowMock.getSession1()).thenReturn(session1);
        when(checkersWindowMock.getSession2()).thenReturn(session2);
        when(session1.getSid()).thenReturn("");
        when(session2.getSid()).thenReturn("");
        String s = checkersNetworkHandler.handleConnect(socketMock, 0, true);
        assertEquals(CheckersNetworkHandler.RESPONSE_DENIED + "\nError: the other client is already "
                + "player " + "1.", s);
    }

    @Test
    public void testHandleConnectionDeniedClientCannotConnectToItself() {
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        when(checkersWindowMock.getSession1()).thenReturn(session1);
        when(checkersWindowMock.getSession2()).thenReturn(session2);
        when(session1.getSid()).thenReturn("");
        when(session2.getSid()).thenReturn("");
        when(session2.getSourcePort()).thenReturn(0);
        InetAddress iMock = mock(InetAddress.class);
        when(socketMock.getInetAddress()).thenReturn(iMock);
        when(iMock.getHostAddress()).thenReturn("127.0.0.1");

        String s = checkersNetworkHandler.handleConnect(socketMock, 0, false);

        assertEquals(CheckersNetworkHandler.RESPONSE_DENIED + "\nError: the client cannot connect "
                + "to itself.", s);
    }

    @Test
    public void testHandleConnectAccepted() {
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        when(checkersWindowMock.getSession1()).thenReturn(session1);
        when(checkersWindowMock.getSession2()).thenReturn(session2);
        when(session1.getSid()).thenReturn("");
        when(session2.getSid()).thenReturn("");
        when(session2.getSourcePort()).thenReturn(0);
        InetAddress iMock = mock(InetAddress.class);
        when(socketMock.getInetAddress()).thenReturn(iMock);
        when(iMock.getHostAddress()).thenReturn("123");
        NetworkWindow nwMock = mock(NetworkWindow.class);
        when(optionPanelMock.getNetworkWindow1()).thenReturn(nwMock);

        String s = checkersNetworkHandler.handleConnect(socketMock, 0, false);

        InOrder inorder = inOrder(session1, nwMock);
        inorder.verify(session1, times(1)).setSid(any());
        inorder.verify(session1, times(1)).setDestinationHost("123");
        inorder.verify(session1, times(1)).setDestinationPort(0);

        inorder.verify(nwMock, times(1)).setDestinationHost("123");
        inorder.verify(nwMock, times(1)).setDestinationPort(0);
        inorder.verify(nwMock,times(1)).setCanUpdateConnect(false);
        inorder.verify(nwMock, times(1)).setMessage("  Connected to " + "123" + ":" + "0" + ".");

        assertTrue(s.contains(CheckersNetworkHandler.RESPONSE_ACCEPTED));
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
