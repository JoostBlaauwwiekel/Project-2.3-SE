package project.gamedata;

import project.gameframework.CommunicationChannel;

import java.util.HashSet;

public class tempFix implements Runnable{

    private boolean isInGame;
    private CommunicationChannel communicationChannel;
    private HashSet<String> playerSet;

    public tempFix(CommunicationChannel communicationChannel, boolean isInGame){
        this.isInGame = isInGame;
        this.communicationChannel = communicationChannel;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isInGame){
                communicationChannel.help();
                System.out.println("test");
            }
        }
    }
}
