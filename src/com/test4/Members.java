package com.test4;

import java.io.*;
import java.util.Vector;

class Node {
	int x;
	int y;
	int direct;

	Node(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

class Recorder {
	private static int ennum = 20;
	private static int myLife = 3;
	private static int allEnemy = 0;
	// ��¼��ǰ���ٵĵ��˵�̹������
	public static FileWriter fw = null;
	public static BufferedWriter bw = null;
	public static FileReader fr = null;
	public static BufferedReader br = null;

	public Vector<Enemy> ets = new Vector<Enemy>();
	public Vector<Node> nodes = new Vector<Node>();

	public Vector<Enemy> getEts() {
		return ets;
	}

	public void setEts(Vector<Enemy> ets1) {
		this.ets = ets1;

	}

	// 实现续上局的功能
	public Vector<Node> goonGame() {
		try {
			fr = new FileReader("d:/record.txt");
			br = new BufferedReader(fr);
			String s = " ";
			s=br.readLine();
			allEnemy=Integer.parseInt(s);
			while ((s = br.readLine()) != null) {
				String[] abc = s.split(" ");
				// System.out.println(abc[0]);
				Node node = new Node(Integer.parseInt(abc[0]),
						Integer.parseInt(abc[1]), Integer.parseInt(abc[2]));
				nodes.add(node);
			}

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
				fr.close();
			}
			catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return nodes;

	}

	// 实现存盘退出的功能
	public void keepEnemytank() {
		try {
			fw = new FileWriter("d:/record.txt");
			bw = new BufferedWriter(fw);
			bw.write(allEnemy + "\r\n");
			for (int i = 0; i < ets.size(); i++) {
				Enemy et = ets.get(i);
				if (et.isalive) {
					String s = et.x + " " + et.y + " " + et.direct;
					bw.write(s + "\r\n");
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
				fw.close();
			}
			catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	public static void getRecording() {
		try {
			fr = new FileReader("d:/record.txt");
			br = new BufferedReader(fr);
			String s = br.readLine();
			allEnemy = Integer.parseInt(s);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
				fr.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void keepRecording() {
		try {
			fw = new FileWriter("d:/record.txt");
			bw = new BufferedWriter(fw);
			bw.write(allEnemy + "\r\n");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
				fw.close();
			}
			catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	public static int getAllEnemy() {
		return allEnemy;
	}

	public static void setAllEnemy(int allEnemy) {
		Recorder.allEnemy = allEnemy;
	}

	public static int getEnnum() {
		return ennum;
	}

	public static void setEnnum(int ennum) {
		Recorder.ennum = ennum;
	}

	public static int getMyLife() {
		return myLife;
	}

	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}

	public static void reduceEnum() {
		ennum--;
	}

	public static void reduceMylife() {
		myLife--;
	}

	public static void addAlllife() {
		allEnemy++;
	}
}

class shot implements Runnable {
	int x;
	int y;
	int direct;
	int speed = 1;
	boolean islive = true;

	public shot(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);
				// System.out.println("x" + x + "y" + y);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			switch (direct) {
			case 0:
				y -= speed;
				break;
			case 1:
				x += speed;
				break;
			case 2:
				y += speed;
				break;
			case 3:
				x -= speed;
				break;
			}
			if (x < 0 || x > 400 || y < 0 || y > 300) {
				islive = false;
				break;
			}

		}
	}
}

class Bomb {
	int x;
	int y;
	int life = 9;
	boolean islive = true;

	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void downlife() {
		if (life > 0)
			life--;
		else
			islive = false;
	}
}

class Tank {
	// ��ʾ̹�˵����
	int x = 0;
	int y = 0;
	// ̹�˷���
	// 0��ʾ�ϣ�1��ʾ��2��ʾ��3��ʾ��
	int direct = 0;
	int speed = 3;
	int color = 0;
	boolean isalive = true;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

class Hero extends Tank {

	Vector<shot> ss = new Vector<shot>();
	shot s = null;

	public Hero(int x, int y) {
		super(x, y);// ��һ�俴������
		// ̹�˵��ƶ�
	}

	public void shotenemy() {
		switch (this.direct) {
		case 0:
			s = new shot(x + 10, y, 0);
			ss.add(s);
			break;
		case 1:
			s = new shot(x + 30, y + 10, 1);
			ss.add(s);
			break;
		case 2:
			s = new shot(x + 10, y + 30, 2);
			ss.add(s);
			break;
		case 3:
			s = new shot(x, y + 10, 3);
			ss.add(s);
			break;
		}
		Thread t = new Thread(s);
		t.start();
	}

	public void moveup() {
		if (y > 0) {
			y -= speed;
		}
	}

	public void movedown() {
		if (y <= 300) {
			y += speed;
		}
	}

	public void moveleft() {
		if (x > 0) {
			x -= speed;
		}
	}

	public void moveright() {
		if (x < 370) {
			x += speed;
		}
	}
}

class Enemy extends Tank implements Runnable {
	int times = 0;
	Vector<shot> ss = new Vector<shot>();
	Vector<Enemy> ets = new Vector<Enemy>();

	// shot s=null;
	public Enemy(int x, int y) {
		super(x, y);
	}

	public void setEts(Vector<Enemy> vv) {
		this.ets = vv;
	}

	public boolean isTouchEnemy() {
		boolean b = false;
		switch (this.direct) {
		case 0:
			// �ҵ�̹�˷�������
			// ȡ�����˵�����̹��
			for (int i = 0; i < ets.size(); i++) {
				Enemy et = ets.get(i);
				// �жϵ���̹�˲����Լ�
				if (et != this) {
					// �����˵�̹�˷������ϻ�������
					if (et.direct == 0 || et.direct == 2) {
						if (this.x >= et.x && this.x <= et.x + 20
								&& this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 20
								&& this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 1 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30
								&& this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 30
								&& this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		case 1:
			for (int i = 0; i < ets.size(); i++) {
				Enemy et = ets.get(i);
				// �жϵ���̹�˲����Լ�
				if (et != this) {
					// �����˵�̹�˷������ϻ�������
					if (et.direct == 0 || et.direct == 2) {
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 20
								&& this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 20
								&& this.y + 20 >= et.y
								&& this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 1 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30
								&& this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 30
								&& this.y + 20 >= et.y
								&& this.y + 20 <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		case 2:
			// �ҵ�̹�˷�������
			// ȡ�����˵�����̹��
			for (int i = 0; i < ets.size(); i++) {
				Enemy et = ets.get(i);
				// �жϵ���̹�˲����Լ�
				if (et != this) {
					// �����˵�̹�˷������ϻ�������
					if (et.direct == 0 || et.direct == 2) {
						if (this.x >= et.x && this.x <= et.x + 20
								&& this.y + 30 >= et.y
								&& this.y + 30 <= et.y + 30) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 20
								&& this.y + 30 >= et.y
								&& this.y + 30 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 1 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30
								&& this.y + 30 >= et.y
								&& this.y + 30 <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 30
								&& this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		case 3:
			// �ҵ�̹�˷�������
			// ȡ�����˵�����̹��
			for (int i = 0; i < ets.size(); i++) {
				Enemy et = ets.get(i);
				// �жϵ���̹�˲����Լ�
				if (et != this) {
					// �����˵�̹�˷������ϻ�������
					if (et.direct == 0 || et.direct == 2) {
						if (this.x >= et.x && this.x <= et.x + 20
								&& this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x >= et.x && this.x <= et.x + 20
								&& this.y + 20 >= et.y
								&& this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 1 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30
								&& this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x >= et.x && this.x <= et.x + 30
								&& this.y + 20 >= et.y
								&& this.y + 20 <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		}
		return b;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			switch (this.direct) {
			case 0:
				// ����

				for (int i = 0; i < 30; i++) {
					if (y > 0 && !this.isTouchEnemy()) {
						y -= speed;
						try {
							Thread.sleep(100);
						}
						catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					// s=new shot(x + 10, y, 0);
				}
				break;
			case 1:
				// ����

				for (int i = 0; i < 30; i++) {
					if (x < 400 && !this.isTouchEnemy()) {
						x += speed;
					}
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				break;

			case 2:
				// ����

				for (int i = 0; i < 30; i++) {
					if (y < 300 && !this.isTouchEnemy()) {
						y += speed;
					}
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				break;
			case 3:
				// ����
				for (int i = 0; i < 30; i++) {
					if (x > 0 && !this.isTouchEnemy()) {
						x -= speed;
					}
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				break;
			}
			times++;
			if (times % 2 == 0) {
				if (this.isalive) {
					if (ss.size() < 5) {
						shot s = null;
						switch (direct) {
						case 0:
							s = new shot(x + 10, y, 0);
							ss.add(s);
							break;
						case 1:
							s = new shot(x + 30, y + 10, 1);
							ss.add(s);
							break;
						case 2:
							s = new shot(x + 10, y + 30, 2);
							ss.add(s);
							break;
						case 3:
							s = new shot(x, y + 10, 3);
							ss.add(s);
							break;

						}
						Thread t = new Thread(s);
						t.start();
					}
				}
			}
			this.direct = (int) (Math.random() * 4);
			if (this.isalive == false) {
				break;
			}
		}
	}
}
