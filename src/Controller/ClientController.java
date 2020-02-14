package Controller;

import Model.Constants;
import Model.Player;
import Model.PlayerHelper;
import View.GameView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * The type Client controller.
 */
public class ClientController implements Constants {

    private Socket aSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private GameView gameView;
    private PlayerHelper playerHelper;
    private boolean yourTurn = false;

    /**
     * Instantiates a new Client controller.
     *
     * @param serverName the server name
     * @param portNumber the port number
     */
    public ClientController(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            gameView = new GameView();
            output = new ObjectOutputStream(aSocket.getOutputStream());
            input = new ObjectInputStream(aSocket.getInputStream());
            gameView.createActionListener(new ClickListener());
            gameView.setTextArea("Waiting for Opponent to join. Please Wait");

            while (true) {
                try {
                playerHelper = (PlayerHelper) input.readObject();
                serverResponse(playerHelper.getResponseNumber());
                } catch (EOFException e) {
                    // ... this is fine
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Server Error");
        }
    }

    /**
     * Server response.
     *
     * @param option the option
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public void serverResponse(int option) throws IOException {
        if(option ==1)
            gameView.setTextArea(playerHelper.getLine());
        if(option ==2) {
            playerHelper.getPlayer().setMark(playerHelper.getPlayer().getMark());
            gameView.setPlayerText(playerHelper.getPlayer().getMark() + "");
            gameView.setPlayerNameText(playerHelper.getPlayer().getName());
        }
        if(option == 3) {
            yourTurn = true;
            this.gameView.setTextArea(playerHelper.getLine());
            //write xPlayerHelper object with X's row and col
        }
        if(option ==4) {
            this.gameView.setTextArea(playerHelper.getLine());
        }
        if(option ==5) {
            this.gameView.setTextArea(playerHelper.getLine());
        }
        if(option == 6) { // marking for your own board when the mark has been validated by server
            gameView.addMarkOnView(playerHelper.getPosition()[0], playerHelper.getPosition()[1], playerHelper.getPlayer().getMark());
            this.gameView.setTextArea(playerHelper.getLine());
            // disable actionlister for this client
        }
        if(option ==7) { // to tell the player move is invalid and listener starts wirting the object again
            this.gameView.setTextArea(playerHelper.getLine());
            yourTurn = true;
        }
        if(option ==8) {
            this.gameView.setTextArea(playerHelper.getLine());
            gameView.createMessageDialog("THE GAME IS OVER IN TIE");
            close();
        }
        if(option ==9) {
                this.gameView.setTextArea(playerHelper.getLine());
                gameView.createMessageDialog("THE GAME IS OVER: " + playerHelper.getPlayer().getName() + " you are winner!");
                close();
        }
        if(option ==12) {
            this.gameView.setTextArea(playerHelper.getLine());
            gameView.createMessageDialog("THE GAME IS OVER YOU LOST!");
            close();
        }
        if(option == 10) { // marking for O when x is playing
            System.out.println(playerHelper.getLine());
            gameView.addMarkOnView(playerHelper.getPosition()[0], playerHelper.getPosition()[1], LETTER_X);
            this.gameView.setTextArea(playerHelper.getLine());
        }
        if(option == 11) { // marking for X when O is playing
            System.out.println(playerHelper.getLine());
            gameView.addMarkOnView(playerHelper.getPosition()[0], playerHelper.getPosition()[1], LETTER_O);
            this.gameView.setTextArea(playerHelper.getLine());
        }
        if(option == 13) {
            playerHelper = new PlayerHelper(new Player(gameView.getPlayersName("Enter Player's Name"), 'Z'), null, null, 0);
            output.writeObject(playerHelper);
            output.flush();
            resetObject();
        }
    }

    /**
     * Reset object.
     */
    public void resetObject() {
        if (playerHelper != null) {
            playerHelper = new PlayerHelper(playerHelper.getPlayer(), null, null, 0);
        }
    }

    /**
     * Close.
     */
    public void close() {
        try {
            input.close();
            output.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetObject();
            int[] position = gameView.getPositionOfButtons((JButton) e.getSource());
            if (yourTurn) {
                try {
                    playerHelper.setPosition(position);
                    output.writeObject(playerHelper);
                    yourTurn = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        ClientController aClient = new ClientController("localhost", 9806);
    }
}

