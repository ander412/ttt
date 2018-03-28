/**
 * ClientHandler.java
 *
 * This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;


public class ClientHandler implements Runnable {
  private Socket connectionSock = null;
  private ArrayList<Socket> socketList;


  //Bailey
  boolean playing = false;
  boolean fail = false;
  int final row = 3;
  int final col = 3;
  public char[][] board = new char[row][col];

  public void initializeBoard(char[][] board1) {
    for (int row1 = 0; row1 < 3; ++row1) {
      for (int col1 = 0; col1 < 3; ++col1) {
        board1[row][col] = '-'
      }
    }

  }

// xORo == 0 if x, == 1 if o
  public boolean checkIfWin(char[][] board2, int xORo) {
    if (xORo == 0) {
      //left to right
      if (board2[0][0] == 'x' && board2[0][1] == 'x' && board2[0][2] == 'x') {
        return true;
      }
      else if (board2[1][0] == 'x' && board2[1][1] == 'x' && board2[1][2] == 'x') {
        return true;
      }
      else if (board2[2][0] == 'x' && board2[2][1] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      //going up and down
      else if (board2[0][0] == 'x' && board2[1][0] == 'x' && board2[2][0] == 'x') {
        return true;
      }
      else if (board2[0][1] == 'x' && board2[1][1] == 'x' && board2[2][1] == 'x') {
        return true;
      }
      else if (board2[0][2] == 'x' && board2[1][2] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      //going diagonal
      else if (board2[0][0] == 'x' && board2[1][1] == 'x' && board2[2][2] == 'x') {
        return true;
      }
      else if (board2[2][0] == 'x' && board2[1][1] == 'x' && board2[0][2] == 'x') {
        return true;
      }
    }
    if (xORo == 1) {
      //left to right
      if (board2[0][0] == 'o' && board2[0][1] == 'o' && board2[0][2] == 'o') {
        return true;
      }
      else if (board2[1][0] == 'o' && board2[1][1] == 'x' && board2[1][2] == 'o') {
        return true;
      }
      else if (board2[2][0] == 'o' && board2[2][1] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      //going up and down
      else if (board2[0][0] == 'o' && board2[1][0] == 'o' && board2[2][0] == 'o') {
        return true;
      }
      else if (board2[0][1] == 'o' && board2[1][1] == 'o' && board2[2][1] == 'o') {
        return true;
      }
      else if (board2[0][2] == 'o' && board2[1][2] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      //going diagonal
      else if (board2[0][0] == 'o' && board2[1][1] == 'o' && board2[2][2] == 'o') {
        return true;
      }
      else if (board2[2][0] == 'o' && board2[1][1] == 'o' && board2[0][2] == 'o') {
        return true;
      }
    }
  }

// xORo == 0 if x, == 1 if o
  public void playerMove(int move, char[][] gameboard, int xORo)
  {
    if (xORo == 0) {
      if (move == 1 && gameboard[0][0] != "-" && gameboard[0][0] != "o" && gameboard[0][0] != "x") {
        gameboard[0][0] = 'x'
      }
      else if (move == 2 && gameboard[0][1] != "-" && gameboard[0][1] != "o" && gameboard[0][1] != "x") {
        gameboard[0][1] = 'x'
      }
      else if (move == 3 && gameboard[0][2] != "-" && gameboard[0][2] != "o" && gameboard[0][2] != "x") {
        gameboard[0][2] = 'x'
      }
      else if (move == 4 && gameboard[1][0] != "-" && gameboard[1][0] != "o" && gameboard[1][0] != "x") {
        gameboard[1][0] = 'x'
      }
      else if (move == 5 && gameboard[1][1] != "-" && gameboard[1][1] != "o" && gameboard[1][1] != "x") {
        gameboard[1][1] = 'x'
      }
      else if (move == 6 && gameboard[1][2] != "-" && gameboard[1][2] != "o" && gameboard[1][2] != "x") {
        gameboard[1][2] = 'x'
      }
      else if (move == 7 && gameboard[2][0] != "-" && gameboard[2][0] != "o" && gameboard[2][0] != "x") {
        gameboard[2][0] = 'x'
      }
      else if (move == 8 && gameboard[2][1] != "-" && gameboard[2][1] != "o" && gameboard[2][1] != "x") {
        gameboard[2][1] = 'x'
      }
      else if (move == 9 && gameboard[2][2] != "-" && gameboard[2][2] != "o" && gameboard[2][2] != "x") {
        gameboard[2][2] = 'x'
      }
      else {
        fail = true;
      }
  }
  else if (xORo == 1) {
    if (move == 1 && gameboard[0][0] != "-" && gameboard[0][0] != "o" && gameboard[0][0] != "x") {
      gameboard[0][0] = 'o'
    }
    else if (move == 2 && gameboard[0][1] != "-" && gameboard[0][1] != "o" && gameboard[0][1] != "x") {
      gameboard[0][1] = 'o'
    }
    else if (move == 3 && gameboard[0][2] != "-" && gameboard[0][2] != "o" && gameboard[0][2] != "x") {
      gameboard[0][2] = 'o'
    }
    else if (move == 4 && gameboard[1][0] != "-" && gameboard[1][0] != "o" && gameboard[1][0] != "x") {
      gameboard[1][0] = 'o'
    }
    else if (move == 5 && gameboard[1][1] != "-" && gameboard[1][1] != "o" && gameboard[1][1] != "x") {
      gameboard[1][1] = 'o'
    }
    else if (move == 6 && gameboard[1][2] != "-" && gameboard[1][2] != "o" && gameboard[1][2] != "x") {
      gameboard[1][2] = 'o'
    }
    else if (move == 7 && gameboard[2][0] != "-" && gameboard[2][0] != "o" && gameboard[2][0] != "x") {
      gameboard[2][0] = 'o'
    }
    else if (move == 8 && gameboard[2][1] != "-" && gameboard[2][1] != "o" && gameboard[2][1] != "x") {
      gameboard[2][1] = 'o'
    }
    else if (move == 9 && gameboard[2][2] != "-" && gameboard[2][2] != "o" && gameboard[2][2] != "x") {
      gameboard[2][2] = 'o'
    }
    else {
      fail = true;
    }
  }

  public void openingMessage {

  }


  ClientHandler(Socket sock, ArrayList<Socket> socketList) {
    this.connectionSock = sock;
    this.socketList = socketList;  // Keep reference to master list
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      System.out.println("Connection made with socket " + connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {

        //Bailey

        initializeBoard(board);

        Socket playerX = socketList.get(0);
        Socket playerO = socketList.get(1);

        DataOutputStream PlayerXOutput = new DataOutputStream(playerX.getOutputStream());
        clientOutput.writeBytes("Welcome to Tic Tac Toe. You are Player X, so you will be Xs. You will go first.");

        DataOutputStream PlayerOOutput = new DataOutputStream(playerO.getOutputStream());
        clientOutput.writeBytes("Welcome to Tic Tac Toe. You are Player O, so you will be Os. Player X will go first.");

        //move
        playing = true;
        while (playing == true) {
          String r1 = board[0][0] + " " + board[0][1] + " " + board[0][2] + "\n";
          String r2 = board[1][0] + " " + board[1][1] + " " + board[1][2] + "\n";
          String r3 = board[2][0] + " " + board[2][1] + " " + board[2][2] + "\n";
          String play = "PLAY \n";
          String boardString = r1 + r2 + r3 + play;

          PlayerXOutput.writeBytes(boardString);

          BufferedReader PlayerXInput = new BufferedReader(
              new InputStreamReader(connectionSock.getInputStream()));

          String PlayerXMove = PlayerXInput.readline();
          try {
            int PlayerXMoveInt = Integer.parseInt(PlayerXMove);
          }
          catch (Exception e) {
            //fail
            PlayerXOutput.writeBytes(boardString);
            PlayerXOutput.writeBytes("FAILURE\n");
          }
          playerMove(PlayerXMoveInt, board, 0);
          if (fail == true) {
            //fail
            PlayerXOutput.writeBytes(boardString);
            PlayerXOutput.writeBytes("FAILURE\n");
          }
          if (checkIfWin(board, 0) == true) {
            PlayerXOutput.writeBytes("WIN");
            PlayerYOutput.writeBytes("LOSE\n");
          }
          playing = false;

          //update board
          r1 = board[0][0] + " " + board[0][1] + " " + board[0][2] + "\n";
          r2 = board[1][0] + " " + board[1][1] + " " + board[1][2] + "\n";
          r3 = board[2][0] + " " + board[2][1] + " " + board[2][2] + "\n";
          boardString = r1 + r2 + r3 + play;

          PlayerOOutput.writeBytes(boardString);

          BufferedReader PlayerOInput = new BufferedReader(
              new InputStreamReader(connectionSock.getInputStream()));

          String PlayerOMove = PlayerOInput.readline();
          try {
            int PlayerOMoveInt = Integer.parseInt(PlayerOMove);
          }
          catch (Exception e) {
            //fail
            PlayerOOutput.writeBytes(boardString);
            PlayerOOutput.writeBytes("FAILURE\n");
          }
          playerMove(PlayerOMoveInt, board, 0);
          if (fail == true) {
            //fail
            PlayerOOutput.writeBytes(boardString);
            PlayerOOutput.writeBytes("FAILURE\n");
          }
          else {
            PlayerOOutput.writeBytes("SUCCESS");
          }
          if (checkIfWin(board, 1) == true) {
            PlayerYOutput.writeBytes("WIN");
            PlayerXOutput.writeBytes("LOSE\n");
          }
          playing = false;
        }


        // Get data sent from a client
    //    String clientText = clientInput.readLine();
  //      if (clientText != null) {
    //      System.out.println("Received: " + clientText);
          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          /*
          for (Socket s : socketList) {
            if (s != connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
              clientOutput.writeBytes(clientText + "\n");
            }
          } */
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          socketList.remove(connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      socketList.remove(connectionSock);
    }
  }
} // ClientHandler for MtServer.java
