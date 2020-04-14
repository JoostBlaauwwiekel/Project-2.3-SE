package project.gamedata;

import project.gameframework.CommunicationChannel;

import java.util.HashSet;

/**
 * There seems to be a issue with either the server or the TCP connection where after some time of inactivity
 * the connection drops, or is dropped by the server. This class is used to send a request to the server once
 * every 2 seconds, to make sure our connection stays up.
 */
public class AntiTimeout implements Runnable{

    private boolean isInGame;
    private CommunicationChannel communicationChannel;

    /**
     * Constructor that sets the necessary variables.
     * @param communicationChannel communication channel to the server.
     * @param isInGame if client is in a game.
     */
    public AntiTimeout(CommunicationChannel communicationChannel, boolean isInGame){
        this.isInGame = isInGame;
        this.communicationChannel = communicationChannel;
    }

    /**
     * Runnable that makes a request to the server once every 2 seconds.
     */
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isInGame){
                communicationChannel.antiTimeout();
            }
        }
    }
}
