package rhythm.copy;

public class Main {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int NOTE_SPEED = 3;  //노트 떨어지는 속도
	public static final int SLEEP_TIME = 10; //노트가 멈췄다가 무한정반복
	public static final int REACH_TIME = 2;  //노트가 생성되고나서 판정바까지 가는 시간(곡마다 처음이 다르니까)
	
	public static void main(String[] args) {
		
		new Rhythm();
		
	}

}
