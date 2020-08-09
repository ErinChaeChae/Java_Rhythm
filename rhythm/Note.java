package rhythm;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED* Main.REACH_TIME); // y좌표가 580이라서 1초(1000_)가 지났을 때 정확히 -120에 도달하는것
																		// 이용(판정노트)
	private String noteType;
	private boolean proceeded = true; //현재 노트가 진행중인가?
	public String getNoteType() {
		return noteType;
	}
	public boolean isProceeded() { //맞으면 ㄱㄱ
		return proceeded;
	}
	public void close() { //닫으면 노트 더이상 이동x
		proceeded = false;
		
	}
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
		if(y > 620) { //엄청 아래로 내려갔다면(노트 판정바 miss야기)
			System.out.println("Miss");
			close();
		}
	}

	// 무한정 노트 떨어뜨리기
	@Override
	public void run() {
		try {
			while (true) {
				drop(); // 1초에 700픽셀만큼 아래로
				if(proceeded) { //노트 나오면 쉬면서 나오면 됨
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt(); //스레드가 멈추도록 interrupt
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//판정함수 - Game
	public void judge() {
		if(y >= 613) {
			System.out.println("Late");
			close();
		}
		else if(y >= 600) {
			System.out.println("Good");
			close();
		}
		else if(y >= 587) {
			System.out.println("Great");
			close();
		}
		else if(y >= 573) {
			System.out.println("Perfect");
			close();
		}
		else if(y >= 565) {
			System.out.println("Great");
			close();
		}
		else if(y >= 550) {
			System.out.println("Good");
			close();
		}
		else if(y >= 535) {
			System.out.println("Early");
			close();
		}
	}
}