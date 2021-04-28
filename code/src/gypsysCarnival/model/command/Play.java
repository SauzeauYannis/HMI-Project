package gypsysCarnival.model.command;

import gypsysCarnival.model.character.Player;

// This class is a subclass of Command
public class Play extends Command {

    /***************
     * Constructor *
     ***************/

    public Play() {
        super("play",
                "| play : To play the game if your in good place");
    }

    /**********
     * Method *
     **********/

    @Override
    public void executeCommand(Player player, String[] args) {
        player.playGame();
    }
}
