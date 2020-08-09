package rhythm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player; //다운받은 라이브러리 jlayer1.0.1 _Cdrive

public class Music extends Thread{
	private Player player;  //라이브러리받은거 사용
	private boolean isLoop; //한번or무한 재생 설정
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	//곡의 제목, 무한반복인지
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop; //isLoop초기화
			file = new File(Main.class.getResource("../music/" + name).toURI()); //toURI로 해당파일의 위치
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//음악 실행시간체크
	public int getTime() {
		if(player == null)
			return 0;
		return player.getPosition(); //시간분석
	}
	//언제 실행되던지 항상 종료할 수 있도록 함
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt(); //thread중지상태로
	}
	//thread상속받으면 거의 무조건 써줘야 함 run
	@Override
	public void run() {
		try {
			do {
				player.play(); //노래실행
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			}while(isLoop); //isLoop가 true라면 실행
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
