package rhythm.copy;
//11장부터 들으면 됌
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent e) {

		if(Rhythm.game == null) return;

		switch(e.getKeyCode()) {
		case KeyEvent.VK_S:
			Rhythm.game.pressS();
			break;
		case KeyEvent.VK_D:
			Rhythm.game.pressD();
			break;
		case KeyEvent.VK_F:
			Rhythm.game.pressF();
			break;
		case KeyEvent.VK_SPACE:
			Rhythm.game.pressSpace();
			break;
		case KeyEvent.VK_J:
			Rhythm.game.pressJ();
			break;
		case KeyEvent.VK_K:
			Rhythm.game.pressK();
			break;
		case KeyEvent.VK_L:
			Rhythm.game.pressL();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if(Rhythm.game == null) return;

		switch(e.getKeyCode()) {
		case KeyEvent.VK_S:
			Rhythm.game.releaseS();
			break;
		case KeyEvent.VK_D:
			Rhythm.game.releaseD();
			break;
		case KeyEvent.VK_F:
			Rhythm.game.releaseF();
			break;
		case KeyEvent.VK_SPACE:
			Rhythm.game.releaseSpace();
			break;
		case KeyEvent.VK_J:
			Rhythm.game.releaseJ();
			break;
		case KeyEvent.VK_K:
			Rhythm.game.releaseK();
			break;
		case KeyEvent.VK_L:
			Rhythm.game.releaseL();
			break;
		}
	}

}