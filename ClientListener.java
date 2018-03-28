/**
 * ClientListener.java
 *
 * This class runs on the client end and just
 * displays any text received from the server.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.input.KeyCode.PLAY;


public class ClientListener implements Runnable {
  private Socket connectionSock = null;
  private Player player = null;

  ClientListener(Socket sock, Player player) {
    this.connectionSock = sock;
    this.player = player;
  }

  /**
   * Gets message from server and dsiplays it to the user.
   */
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          //System.out.println(serverText);
          parseResponse(serverText);

        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }

  private void parseResponse(String serverResponse){
    if(serverResponse.equals("PLAY")){
      player.play(serverResponse);
    }
    if(serverResponse.equals("SUCCESSFUL")){
      player.success();
    }
    if(serverResponse.equals("FAILURE")){
      player.failure();
    }
    if(serverResponse.equals("FINISHED")){  //Other Player leaves
      player.finished();
    }
    if(serverResponse.equals("WIN")){  //Other Player leaves
      player.win(serverResponse);
    }
    if(serverResponse.equals("LOSE")){  //Other Player leaves
      player.lose(serverResponse);

    }
    else
      System.out.println(serverResponse);

  }


} // ClientListener for Player
