//坦克大战3.0
package com.test4;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

public class Mytankegame4 extends JFrame implements ActionListener {

	Mypanel mp = null;
	MyStartPanel msp = null;
	JMenuBar jmb = null;
	JMenu jm = null;
	JMenuItem jmi1 = null;
	JMenuItem jmi2 = null;
	JMenuItem jmi3 = null;
	Recorder r = null;
	JMenuItem jmi4 = null;

	public static void main(String[] args) {
		Mytankegame4 mygame = new Mytankegame4();
	}

	// 坦克类
	public Mytankegame4() {
		msp = new MyStartPanel();
		r = new Recorder();
		// 创建菜单及菜单项
		jmb = new JMenuBar();
		jm = new JMenu("游戏（G）");
		// 设置快捷方式
		jm.setMnemonic('g');
		jmi1 = new JMenuItem("开始新游戏(n)");
		jmi1.setMnemonic('n');
		jmi2 = new JMenuItem("退出游戏（e）");
		jmi2.setMnemonic('e');
		jmi2.setActionCommand("exit");
		jmi2.addActionListener(this);
		// 注册监听
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgames");
		jmi3 = new JMenuItem("存盤退出（c）");
		jmi3.setMnemonic('c');
		jmi3.setActionCommand("games");
		jmi3.addActionListener(this);
		jmi4 = new JMenuItem("继续上局游戏(g)");
		jmi4.setActionCommand("goon");
		jmi4.addActionListener(this);
		jmb.add(jm);
		jm.add(jmi1);
		jm.add(jmi2);
		jm.add(jmi3);
		jm.add(jmi4);
		this.setJMenuBar(jmb);
		this.add(msp);
		Thread t = new Thread(msp);
		t.start();
		this.setBackground(Color.CYAN);
		this.setSize(600, 500);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("newgames")) {
			mp = new Mypanel("newgames");
			Thread t = new Thread(mp);
			t.start();
			// 注册监听
			this.remove(msp);
			this.addKeyListener(mp);
			this.add(mp);
			this.setVisible(true);
		} else if (e.getActionCommand().equals("exit")) {
			Recorder.keepRecording();
			System.exit(0);
		} else if (e.getActionCommand().equals("games")) {
			// 實現存盤的功能
			r.setEts(mp.ets);
			r.keepEnemytank();
			System.exit(0);
		} else if (e.getActionCommand().equals("goon")) {
			mp = new Mypanel("goon");
			Thread t = new Thread(mp);
			t.start();
			// 注册监听
			this.remove(msp);
			this.addKeyListener(mp);
			this.add(mp);
			this.setVisible(true);
		}
	}
}

// 做一个提示作用的面板
class MyStartPanel extends JPanel implements Runnable {
	int times = 0;

	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if (times % 2 == 0) {
			g.setColor(Color.yellow);
			Font myfont = new Font("华文新魏", Font.BOLD, 30);
			g.setFont(myfont);
			g.drawString("stage:1", 150, 150);
		}
	}

	@Override
	public void run() {
		while (true) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(500);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			times++;
			this.repaint();
		}
	}
}

// 我的面板
class Mypanel extends JPanel implements KeyListener, Runnable {
	// 定义我哥仪的坦克
	Hero hero = null;
	// 定义敌人的坦克
	Vector<Enemy> ets = new Vector<Enemy>();
	int ensize = 10;// 敌人坦克的数量
	// 定义三张图片
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	Enemy et = null;
	Vector<Bomb> bombs = new Vector<Bomb>();
	Vector<Node> nodes = new Vector<Node>();

	public Mypanel(String flag) {
		Recorder.getRecording();
		hero = new Hero(100, 100);
		// 初始化炸弹
		image1 = Toolkit.getDefaultToolkit().getImage(
				Panel.class.getResource("/bomb_1.gif"));
		image2 = Toolkit.getDefaultToolkit().getImage(
				Panel.class.getResource("/bomb_2.gif"));
		image3 = Toolkit.getDefaultToolkit().getImage(
				Panel.class.getResource("/bomb_3.gif"));
		if (flag.equals("newgames")) {
			for (int i = 0; i < ensize; i++) {
				// 创建一个敌人的坦克
				et = new Enemy((i + 1) * 50, 0);
				Thread t = new Thread(et);
				t.start();
				et.setEts(ets);
				shot s = new shot(et.x + 10, et.y + 30, 2);
				this.et.ss.add(s);
				Thread t1 = new Thread(s);
				t1.start();
				et.setColor(0);
				et.setDirect(2);
				ets.add(et);
				System.out.println("现在有" + ets.size() + "辆坦克");
				// 测试还剩的坦克数量
				if (et.isalive == false) {
					this.ets.remove(et);
				}
			}
		} else if (flag.equals("goon")) {
			  nodes = new Recorder().goonGame();
			for (int i = 0; i < nodes.size(); i++) {
				Node node = nodes.get(i);
				// 创建一个敌人的坦克
				et = new Enemy(node.x, node.y);
				Thread t = new Thread(et);
				t.start();
				et.setEts(ets);
				shot s = new shot(et.x + 10, et.y + 30, 2);
				this.et.ss.add(s);
				Thread t1 = new Thread(s);
				t1.start();
				et.setColor(0);
				et.setDirect(node.direct);
				ets.add(et);
				System.out.println("现在有" + ets.size() + "辆坦克");
				// 测试还剩的坦克数量
				if (et.isalive == false) {
					this.ets.remove(et);
				}
			}
		}
	}

	// 画出提示信息
	public void showInfo(Graphics g) {
		this.drawTank(80, 310, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnnum() + "", 110, 330);
		this.drawTank(130, 310, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife() + "", 165, 330);
		Font f = new Font("宋体", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("您的总成绩", 420, 30);
		this.drawTank(420, 40, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnemy() + "", 450, 60);

	}

	public void paint(Graphics g) {
		super.paint(g);
		// 画出提示信息坦克，该坦克不参与战斗

		g.fillRect(0, 0, 400, 300);
		this.showInfo(g);

		// g.draw3DRect(this.et.s.x, this.et.s.y, 1, 1,
		// false);
		if (hero.isalive) {
			this.drawTank(hero.getX(), hero.getY(), g, this.hero.getDirect(), 1);
		}
		for (int i = 0; i < ets.size(); i++) {
			Enemy enemy = ets.get(i);
			if (enemy.isalive) {
				this.drawTank(enemy.getX(), enemy.getY(), g, enemy.getDirect(),
						0);

				// g.draw3DRect(hero.ss.get(i).x, hero.ss.get(i).y, 1, 1,
				// false);

			}
			for (int j = 0; j < enemy.ss.size(); j++) {
				shot s = enemy.ss.get(j);
				if (s.islive) {
					g.draw3DRect(enemy.ss.get(j).x, enemy.ss.get(j).y, 1, 1,
							false);
				} else {
					enemy.ss.remove(s);
				}
			}

		}
		for (int i = 0; i < bombs.size(); i++) {
			Bomb b = bombs.get(i);
			if (b.life > 6) {
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 3) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			b.downlife();
			if (b.life == 0) {
				bombs.remove(b);
			}

		}

		// 实现子弹连发的功能
		for (int i = 0; i < this.hero.ss.size(); i++) {
			shot myshot = hero.ss.get(i);
			if (hero.s != null && hero.s.islive != false) {
				g.draw3DRect(hero.ss.get(i).x, hero.ss.get(i).y, 1, 1, false);
				// System.out.println("第" + i + "颗子弹");

			}
			if (myshot.islive == false) {
				this.hero.ss.remove(myshot);
			}

		}
	}

	public boolean hittank(shot s, Tank e) {
		boolean b2 = false;
		switch (e.direct) {
		case 0:
		case 2:
			if (s.x > e.x && s.x < e.x + 20 && s.y > e.y && s.y < e.y + 30) {
				s.islive = false;
				e.isalive = false;
				b2 = true;
				Bomb b = new Bomb(e.x, e.y);
				bombs.add(b);

			}

			break;
		case 1:
		case 3:
			if (s.x > e.x && s.x < e.x + 30 && s.y > e.y && s.y < e.y + 20) {
				s.islive = false;
				e.isalive = false;
				b2 = true;
				Bomb b = new Bomb(e.x, e.y);
				bombs.add(b);
			}

			break;
		}
		return b2;

	}

	public void hitEnemyTank() {
		// 判断何时消灭敌人坦克
		for (int i = 0; i < hero.ss.size(); i++) {
			shot s = hero.ss.get(i);
			for (int j = 0; j < ets.size(); j++) {
				Enemy enemy = ets.get(j);
				if (hittank(s, enemy)) {
					Recorder.reduceEnum();
					Recorder.addAlllife();
				}
			}
		}
	}

	// 判断敌人击中我
	public void hitMe() {
		for (int i = 0; i < ets.size(); i++) {
			// 取出敌人坦克
			Enemy et = ets.get(i);
			for (int j = 0; j < et.ss.size(); j++) {
				// 取出敌人的子弹
				shot s = et.ss.get(j);
				if (hero.isalive) {
					hittank(s, hero);
				}
			}
		}
	}

	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		// 判断是什么类型的坦克
		switch (type) {
		case 0:
			g.setColor(Color.CYAN);
			break;

		case 1:
			g.setColor(Color.yellow);
			break;
		}
		// 判断方向
		switch (direct) {
		case 0:

			g.fillRect(x, y, 5, 30);
			// 画出右边的矩形
			g.fillRect(x + 15, y, 5, 30);
			// 画出中间的矩形
			g.fillRect(x + 5, y + 5, 10, 20);
			// 画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			// 画出中间的直线
			g.drawLine(x + 10, y + 15, x + 10, y);
			break;
		case 1:// 炮筒向右
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			g.fillOval(x + 10, y + 5, 10, 10);
			g.drawLine(x + 15, y + 10, x + 30, y + 10);
			break;
		case 2:// 向下
			g.fillRect(x, y, 5, 30);
			// 画出右边的矩形
			g.fillRect(x + 15, y, 5, 30);
			// 画出中间的矩形
			g.fillRect(x + 5, y + 5, 10, 20);
			// 画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			// 画出中间的直线
			g.drawLine(x + 10, y + 15, x + 10, y + 30);
			break;
		case 3:// 向左
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			g.fillOval(x + 10, y + 5, 10, 10);
			g.drawLine(x + 15, y + 10, x, y + 10);
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// 设置我的坦克的方向
		// a表示方向向左，W表示方向向上，d表示方向向右，s表示方向向下
		if (e.getKeyCode() == KeyEvent.VK_W) {

			this.hero.moveup();
			this.hero.setDirect(0);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {

			this.hero.movedown();
			this.hero.setDirect(2);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {

			this.hero.setDirect(3);
			this.hero.moveleft();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {

			this.hero.moveright();
			this.hero.setDirect(1);
		}

		if (e.getKeyCode() == KeyEvent.VK_J)
			if (this.hero.ss.size() < 5)
				this.hero.shotenemy();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.hitEnemyTank();
			this.hitMe();
			this.repaint();
		}
	}

}
