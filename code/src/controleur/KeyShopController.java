package controleur;

import modele.character.Player;
import modele.command.Interpreter;

public class KeyShopController {

    private Player player;

    public void buyCopperKey() {
        Interpreter.interpretCommand(this.player, "take copper");
    }

    public void buyGoldKey() {
        Interpreter.interpretCommand(this.player, "take gold");
    }

    public void buyPlatinumKey() {
        Interpreter.interpretCommand(this.player, "take platinum");
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
