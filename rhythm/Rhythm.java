package rhythm;

//JComponent.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "sPress");
//JComponent.getActionMap().put("sPress",  javax.swing.Action);
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Rhythm extends JFrame {
	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon exit = new ImageIcon(Main.class.getResource("../images/exit.jpg"));
	private ImageIcon exit2 = new ImageIcon(Main.class.getResource("../images/exit2.jpg"));
	private ImageIcon startBasic = new ImageIcon(Main.class.getResource("../images/startbasic.png"));
	private ImageIcon startEntered = new ImageIcon(Main.class.getResource("../images/startenter.png"));
	private ImageIcon quitBasic = new ImageIcon(Main.class.getResource("../images/quitbasic.png"));
	private ImageIcon leftBasic = new ImageIcon(Main.class.getResource("../images/leftBasic.png"));
	private ImageIcon rightBasic = new ImageIcon(Main.class.getResource("../images/rightBasic.png"));
	private ImageIcon leftEntered = new ImageIcon(Main.class.getResource("../images/leftEnter.png"));
	private ImageIcon rightEntered = new ImageIcon(Main.class.getResource("../images/rightEnter.png"));
	private ImageIcon quitEntered = new ImageIcon(Main.class.getResource("../images/quitenter.png"));
	private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easybuttonenter.png"));
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easybuttonbasic.png"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardbuttonenter.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardbuttonbasic.png"));
	private ImageIcon backButtonBasic = new ImageIcon(Main.class.getResource("../images/leftbasic.png"));
	// backButton은 그냥 leftbutton이미지로 쓴다
	// ImageIcon은 getImage가 없고, Image는 getImage가 필요하다,.
	private Image background = new ImageIcon(Main.class.getResource("../images/title.jpg")).getImage();

	private JLabel menubar = new JLabel(new ImageIcon(Main.class.getResource("../images/menubar.jpg")));

	private JButton exitButton = new JButton(exit);
	private JButton startButton = new JButton(startBasic);
	private JButton quitButton = new JButton(quitBasic);
	private JButton leftButton = new JButton(leftBasic);
	private JButton rightButton = new JButton(rightBasic);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton backButton = new JButton(backButtonBasic);

	private int mouseX, mouseY;

	private boolean isMainScreen = false; // 처음엔 메인화면아니라서 false ->시작버튼 눌렸을 때 겜화면으로 변경
	private boolean isGameScreen = false; // 게임화면이미지

	ArrayList<Track> trackList = new ArrayList<Track>(); // 각각의 게임 곡을 담을 리스트

	private Music selectedMusic; // 노래선택에 대한 변수선언
	private Image selectedImage; // 바뀌므로 처음부터 선언해줄 필요 없음
	private Music intromusic = new Music("titlemusic.mp3", true); // true -intromusic무한실행
	private int nowSelected = 0; // 0 - 첫번째 곡의 번호, 인덱스로

	public static Game game; // 게임이 실행될 때 동시에 여러곡 실행되면 안되므로 public static

	public Rhythm() {		
		trackList.add(new Track("Game Set! Start.png", "Game Set! Background.png", "EK-07 - Game Set!.mp3", "EK-07 - Game Set!"));
		trackList.add(new Track("Now Start.png", "Now Background.png", "EK-07 - Now.mp3", "EK-07 - Now"));
		trackList.add(new Track("Wild Flower Start.png", "Wild Flower Background.png", "Wild Flower - Joakim Karud.mp3", "Wild Flower - Joakim Karud"));

		setUndecorated(true);// 처음시작시
		setTitle("Rhythm_PCE"); // 창제목설정
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 크기설정
		setResizable(false); // 임의적 사이즈변경이 불가능
		setLocationRelativeTo(null); // 실행시 게임창 중앙에
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 없으면 꺼져도 실행됨
		setVisible(true); // 눈에 보이게 함
		setBackground(new Color(0, 0, 0, 0)); // 하얀색
		setLayout(null);

		addKeyListener(new KeyListener()); // 키 리스너 이벤트 클래스
		// 어떠한 키를 눌렀을 때 이벤트처리 가능
		intromusic.start();

		// 생성자 순서대로 넣어주세유~ 곡들의 리스트 관리 가능(차례로 0번 1번 인덱스가 되는거임)

		menubar.setBounds(0, 0, 1280, 30); // 위치와 크기
		// 마우스의 X, Y값 받기
		menubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

		/* 종료버튼 이벤트 */
		exitButton.setBounds(1250, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exit);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
				Music mexit = new Music("Bbok.mp3", false);
				mexit.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exit2);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				/*
				 * 만약 종료할때도 소리를 넣고싶으면 보통 Thread를 사용한다. Music mexit2 = new Music("음악이름.mp3",
				 * false); mexit2.start(); try{ Thread.sleep(1000);//1초 있다가 음악 틀고 종료하기위해
				 * }catch(Exception e){ ex.printStackTrace(); }
				 * 
				 */
				System.exit(0);
			}
		});

		add(exitButton);

		/* 시작메뉴(버튼-시작하기) */
		startButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		startButton.setContentAreaFilled(false); // 버튼 채우기 안함
		startButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함

		startButton.setBounds(40, 200, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startEntered);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startBasic);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// 게임시작이벤트
				intromusic.close();
				enterMain();
			}
		});
		add(startButton);

		/* 종료메뉴 */
		// 아래 세 줄은 투명화를 위한 노력들..
		quitButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		quitButton.setContentAreaFilled(false); // 버튼 채우기 안함
		quitButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함

		quitButton.setBounds(40, 330, 400, 100);
		quitButton.setBorderPainted(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitEntered);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitBasic);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// 게임종료이벤트

				System.exit(0);
			}
		});

		add(quitButton);

		// 곡 선택 왼쪽버튼
		leftButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		leftButton.setContentAreaFilled(false); // 버튼 채우기 안함
		leftButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함

		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftEntered);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftBasic);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("button.mp3", false);
				buttonEnteredMusic.start();
				selectedLeft();
			}
		});

		add(leftButton);

		// 곡 선택 오른쪽버튼
		rightButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		rightButton.setContentAreaFilled(false); // 버튼 채우기 안함
		rightButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함

		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightEntered);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightBasic);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("button.mp3", false);
				buttonEnteredMusic.start();
				// 오른쪽버튼이벤트
				selectedRight();
			}
		});

		add(rightButton);

		// backbutton
		backButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		backButton.setContentAreaFilled(false); // 버튼 채우기 안함
		backButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함

		backButton.setVisible(false);
		backButton.setBounds(20, 50, 60, 60);
		backButton.setBorderPainted(false);
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(leftEntered);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(leftBasic);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("button.mp3", false);
				buttonEnteredMusic.start();
				// 게임 화면에서 메인화면으로 돌아가는 이벤트
				backMain();
			}
		});

		add(backButton);

		// easy button
		easyButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		easyButton.setContentAreaFilled(false); // 버튼 채우기 안함
		easyButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함
		easyButton.setVisible(false);
		easyButton.setBounds(375, 580, 250, 67);
		easyButton.setBorderPainted(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("button.mp3", false);
				buttonEnteredMusic.start();
				// 난이도 쉬움 이벤트
				gameStart(nowSelected, "easy");
			}
		});
		add(easyButton);

		// hard button
		hardButton.setBorderPainted(false); // 버튼의 외곽선 없애기
		hardButton.setContentAreaFilled(false); // 버튼 채우기 안함
		hardButton.setFocusPainted(false); // 선택됐을때 테두리 선택안함
		hardButton.setVisible(false);
		hardButton.setBounds(655, 580, 250, 67);
		hardButton.setBorderPainted(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardButtonEnteredImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));// 마우스 올라갔을 땐 손가락모양
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 안올라갔을땐 기본보양
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("button.mp3", false);
				buttonEnteredMusic.start();
				// 난이도 어려움 이벤트
				gameStart(nowSelected, "hard");
			}
		});
		add(hardButton);

		// 메뉴창 옮기기
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menubar);
	}

	// 화면에 사진출력(가장먼저)
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics(); // 그래픽 객체 얻어옴
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		// 곡선택에 따라 배경바뀌도록
		if (isMainScreen) {
			g.drawImage(selectedImage, 340, 100, null);
		}
		if (isGameScreen) {
			game.screenDraw(g); // Game클래스로 이동
		}
		// main frame에 add한 추가적요소가 그려지지는 것은 paintComponents로
		paintComponents(g); // JLabel같은것은 고정되기 때문에 paintComponents를 이용해줌
		try {
			Thread.sleep(5);
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	// 트랙 선택을 위한 함수 - Track과 연계
	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage()))
				.getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getGameMusic(), true);
		selectedMusic.start();
	}

	/* 시작화면에서 왼쪽선택 */
	public void selectedLeft() {
		// 맨 초기값이 0 인데 이것에서 왼쪽으로 가면 맨 뒤의 값이 나와야 하기 때문에 -1
		if (nowSelected == 0)
			nowSelected = trackList.size() - 1;
		// 위의 경우가 아니라면 단순히 1빼주면 된다
		else
			nowSelected--;
		selectTrack(nowSelected); // 인덱스 결과에 해당하는 트랙세팅
	}

	public void selectedRight() {
		// 맨 초기값이 0 인데 이것에서 왼쪽으로 가면 맨 뒤의 값이 나와야 하기 때문에 -1
		if (nowSelected == trackList.size() - 1) // trackList의 크기에서 -1 한다 는건 맨 뒤로 간다는 것
			nowSelected = 0;
		// 위의 경우가 아니라면 단순히 1더해주면 된다
		else
			nowSelected++;
		selectTrack(nowSelected); // 인덱스 결과에 해당하는 트랙
	}

	// real게임시작시 화면
	public void gameStart(int nowSelected, String difficulty) {
		if (selectedMusic != null) { // 음악이 이미 실행중이라면
			selectedMusic.close(); // 현재 재생되는 음악을 닫아준다
			isMainScreen = false; // 메인화면 없애고 게임화면으로
			isGameScreen = true; // 게임 시작 화면
			leftButton.setVisible(false);
			rightButton.setVisible(false);
			easyButton.setVisible(false);
			hardButton.setVisible(false);
			// 게임실행시 화면
			background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage()))
					.getImage();
			backButton.setVisible(true);
			game = new Game(trackList.get(nowSelected).getTitlename(),difficulty,trackList.get(nowSelected).getGameMusic());
			game.start(); //run함수 실행됨		
			setFocusable(true); //메인프레임에 고정, 키보드 오류 없애기 위함
			//requestFocus();
		}
	}

	// 게임화면에서 backButton눌렀을때
	public void backMain() {
		isGameScreen = false;
		isMainScreen = true; // 메인화면 띄우기
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		// 게임실행시 화면
		background = new ImageIcon(Main.class.getResource("../images/mainbackground.jpg")).getImage();
		backButton.setVisible(false);
		selectTrack(nowSelected); // 현재 셀렉된 곡을 보여줘라
		game.close(); //게임실행되던 음악과, 게임하던걸 종료
	}

	// 메인화면 들어갈 때 깔끔하게 정리
	public void enterMain() {
		background = new ImageIcon(Main.class.getResource("../images/mainbackground.jpg")).getImage();
		startButton.setVisible(false); // 시작버튼 보이지 않게
		isMainScreen = true; // 겜화면으로
		quitButton.setVisible(false);
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		intromusic.close();
		selectTrack(0); // 처음 시작시엔 맨 처음 곡 나오게 세팅
		// Track클래스 만들어서 아래부분 필요없음
		// Music selectedMusic = new Music("EK-07 - Game Set!.mp3", true); //곡이 바뀌지 않는
		// 이상 무한반복
		// selectedMusic.start();
	}
}
