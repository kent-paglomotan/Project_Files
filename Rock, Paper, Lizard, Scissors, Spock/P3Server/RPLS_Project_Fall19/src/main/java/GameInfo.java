import java.io.IOException;
import java.io.Serializable;

public class GameInfo implements Serializable {
    int p1Points;
    int p2Points;
    int roundNumber;
    String p1Plays;
    String p2Plays;

    //Constructor init default round;
    GameInfo(){
        this.p1Points = 0;
        this.p2Points = 0;
        this.roundNumber = 0;
        this.p1Plays = "";
        this.p2Plays = "";
    }

    public boolean bothPlayed() {
        if(p1Plays != "" && p2Plays != "")
            return true;
        else
            return false;
    }

    public String pointString(){
        return ("Player 1: "+this.p1Points+" points, Player 2: "+this.p2Points+" points");
    }

    public String whoWon (String p1Played, String p2Played){
        p1Played = p1Plays;
        p2Played = p2Plays;
        // Player 1 Won
        if ((p1Played.equals("ROCK")) && (p2Played.equals("SCISSORS"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("ROCK")) && (p2Played.equals("LIZARD"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("PAPER")) && (p2Played.equals("ROCK"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("PAPER")) && (p2Played.equals("SPOCK"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("SCISSORS")) && (p2Played.equals("PAPER"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("SCISSORS")) && (p2Played.equals("LIZARD"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("LIZARD")) && (p2Played.equals("PAPER"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("LIZARD")) && (p2Played.equals("SPOCK"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("SPOCK")) && (p2Played.equals("SCISSORS"))){
            return new String("PLAYER 1 WON");
        }
        else if ((p1Played.equals("SPOCK")) && (p2Played.equals("ROCK"))){
            return new String("PLAYER 1 WON");
        }
        // Player 2 WON
        else if ((p2Played.equals("ROCK")) && (p1Played.equals("SCISSORS"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("ROCK")) && (p1Played.equals("LIZARD"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("PAPER")) && (p1Played.equals("ROCK"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("PAPER")) && (p1Played.equals("SPOCK"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("SCISSORS")) && (p1Played.equals("PAPER"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("SCISSORS")) && (p1Played.equals("LIZARD"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("LIZARD")) && (p1Played.equals("PAPER"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("LIZARD")) && (p1Played.equals("SPOCK"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("SPOCK")) && (p1Played.equals("SCISSORS"))){
            return new String("PLAYER 2 WON");
        }
        else if ((p2Played.equals("SPOCK")) && (p1Played.equals("ROCK"))){
            return new String("PLAYER 2 WON");
        }
        //TIE
        else if ((p1Played.equals("ROCK")) && (p2Played.equals("ROCK"))){
            return new String("TIE");
        }
        else if ((p1Played.equals("PAPER")) && (p2Played.equals("PAPER"))){
            return new String("TIE");
        }
        else if ((p1Played.equals("SCISSORS")) && (p2Played.equals("SCISSORS"))){
            return new String("TIE");
        }
        else if ((p1Played.equals("LIZARD")) && (p2Played.equals("LIZARD"))){
            return new String("TIE");
        }
        else if ((p1Played.equals("SPOCK")) && (p2Played.equals("SPOCK"))){
            return new String("TIE");
        }
        else{
        return null;
        }
    }// END OF WHO WON

    public void evaluateRound (String roundResult){
        if (roundResult.equals("PLAYER 1 WON")){
            p1Points = p1Points + 1; // increment p1 points
            roundNumber = roundNumber + 1; // increment the round number
        }
        else if (roundResult.equals("PLAYER 2 WON")){
            p2Points = p2Points + 1;
            roundNumber = roundNumber + 1;
        }
        else if (roundResult.equals("TIE")){
            roundNumber = roundNumber + 1; // if its a tie then just increment the round number
        }
    }

    public Boolean gameOver(){
        if (p1Points == 3){
            return true;
        }
        else if (p2Points == 3){
            return  true;
        }
        else{
            return false;
        }
    }
}
