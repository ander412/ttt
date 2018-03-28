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
  private boolean running;

  ClientListener(Socket sock, Player player) {
    this.connectionSock = sock;
    this.player = player;
    this.running = true;
  }

  /**
   * Gets message from server and displays it to the user.
   */
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (running) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          System.out.println("DEBUG: " + serverText);
          parseResponse(serverText);
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }

  private void parseResponse(String serverResponse){
    String[] serverResponseLines = serverResponse.split("\n");
    String board = "";

    if(serverResponseLines[0].equals("PLAY")){  //Client turn
      board = parseBoardFromResponse(serverResponseLines);
      player.play(board);
    }
    if(serverResponseLines[0].equals("SUCCESS")){ //Action was valid
      player.success();
    }
    if(serverResponseLines[0].equals("FAILURE")){  //Action was invalid
      board = parseBoardFromResponse(serverResponseLines);
      player.failure(board);
    }
    if(serverResponseLines[0].equals("FINISHED")){  //Other Player leaves
      player.finished();
      running = false;
    }
    if(serverResponseLines[0].equals("WIN")){  //You win
      player.win();
      running = false;
    }
    if(serverResponseLines[0].equals("LOSE")){  //You lose
      board = parseBoardFromResponse(serverResponseLines);
      player.lose(board);
      running = false;
    }
    else
      System.out.println(serverResponse);
  }

  private String parseBoardFromResponse(String[] serverResponseLines){
    String board = "";
    for(int i = 1; i < serverResponseLines.length; i++){
      board += serverResponseLines[i];
    }
    return board;
  }

} // ClientListener for Player
