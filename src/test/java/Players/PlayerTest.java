package Players;
import org.junit.Assert;
import org.junit.Test;

import JCK.Risk.Players.Player;

import java.util.Random;

public class PlayerTest {
	@Test
	public void createPlayerTest() {
		Random random = new Random();
		
		String randomPlayer = "TestPlayer";
		Integer randomTurnRollValue = random.nextInt(6) + 1;
		
		Player player = new Player();
		player.createPlayer(randomPlayer, randomTurnRollValue);
		
		Assert.assertEquals(randomPlayer, player.getName());
		Assert.assertEquals(randomTurnRollValue, player.getTurnValue());
	}
}
