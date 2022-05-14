package bb.love_letter.game.characters;


import bb.love_letter.game.GameEvent;
import bb.love_letter.game.Player;

/*
    Strength: 3
    AmountInDeck: 2
    Effects: Player may choose another player and privately compare hands.
    The player with the lower-value card is eliminated from the round.
 */
public class Baron extends Cards {
    private final String name = "BARON";
    private final int cardPoints = 3;
    private final String cardAction = "Compare Hands privately.Lower value card is eliminated";

    @Override
    public String getCardName() {
        return name;
    }

    @Override
    public String getCardAction() {
        return cardAction;
    }

    @Override
    public int getCardPoints() {
        return cardPoints;
    }

    //when player looses::
    // sourcePlayer + targetPlayer private message with Info :: You played Card+Value - target played Card + Value
    // sourceCard + targetCard arrayList <Card> history add.
    // playersInRound delete user

    @Override
    public void useAction(Player sourcePlayer, Player targetPlayer) {
        /* compare hands with another player, lower number is out */
        Cards sourcePlayerCard1 = sourcePlayer.getCard1();
        Cards targetPlayerCard1 = targetPlayer.getCard1();

        int sourceCardValue = sourcePlayerCard1.getCardPoints();
        int targetCardValue = targetPlayerCard1.getCardPoints();

        if (sourceCardValue > targetCardValue) {


        }
        else if (sourceCardValue < targetCardValue){

        }
        else {
            // ignore effect play on; cards to history
        }

    }
}
