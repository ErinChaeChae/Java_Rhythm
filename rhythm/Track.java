package rhythm;

/*게임 시작 -왼/오버튼 클릭 - 변경되어야 하는 것들*/
public class Track {
	// private String titleImage; // 제목 부분 이미지. 난 곡+곡이름 하나로함
	private String startImage; // 게임 선택 창 표지 이미지
	private String gameImage; // 해당 곡 실행시 표지 이미지
	private String gameMusic; // 해당 곡을 실행했을 때 음악
	private String titlename;
	// private String startMusic; // 게임 선택 창 음악(하이라이트 30초음악 따로 만들지않음)

	// 마우스 우클릭-source-Generate getters and setters - 자동생성
	public String getStartImage() {
		return startImage;
	}
	// Track이라는 클래스 안에서 새로운 변수를 만들어 줄 때
	// 한번에 내부 변수들 초기화해주는 메소드 (생성자)
	// source - select generate fields

	public Track(String startImage, String gameImage, String gameMusic, String titlename) {
		super();
		this.startImage = startImage;
		this.gameImage = gameImage;
		this.gameMusic = gameMusic;
		this.titlename = titlename;
	}

	public void setStartImage(String startImage) {
		this.startImage = startImage;
	}

	public String getGameImage() {
		return gameImage;
	}

	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}

	public String getGameMusic() {
		return gameMusic;
	}

	public void setGameMusic(String gameMusic) {
		this.gameMusic = gameMusic;
	}

	public String getTitlename() {
		return titlename;
	}

	public void setTitlename(String titlename) {
		this.titlename = titlename;
	}
}
