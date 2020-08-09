package rhythm.copy;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED* Main.REACH_TIME); // y좌표가 580이라서 1초(1000_)가 지났을 때 정확히 -120에 도달하는것
																		// 이용(판정노트)
	private String noteType;

	public Note(String noteType) { // y좌표는 노트스피드와 노트가 쉬는시간에 따라 달라지기 때문에 불필
		switch(noteType) {
		case "S": x = 228; break;
		case "D": x = 332; break;
		case "F": x = 436; break;
		case "Space": x = 540; break;
		case "J": x = 744; break;
		case "K": x = 848; break;
		case "L": x = 952; break;
		}
		this.noteType = noteType;
	}

	public void screenDraw(Graphics2D g) {
		if (noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x + 100, y, null); // 스페이스 위해 가로길이 2배(+100)
		}

		else {
			g.drawImage(noteBasicImage, x, y, null);
			
		}
	}

	// 노트가 떨어지는 함수
	public void drop() {
		y += Main.NOTE_SPEED; // 아래쪽으로 7만큼
	}

	// 무한정 노트 떨어뜨리기
	@Override
	public void run() {
		try {
			while (true) {
				drop(); // 1초에 700픽셀만큼 아래로
				Thread.sleep(Main.SLEEP_TIME);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}