// if Eclipse IDE, and import the "Week11Homework.zip" archive  to the "src" direcotry,please uncomment the below line.
// package Week11Homework;

/*
 * This file uses UTF-8
 * 
 * 程序说明：
 * 
 * 本程序所必须的资源都在与本文件相同目录中。
 * 若程序运行不正确，请确认本程序引用的一些文件的路径是否正确。
 * 
 * 文件路径建议使用绝对路径。
 * 
 * 如果在命令行中编译本程序，需要包含播放音乐的组件 jl1.0.1.jar ，
 * 以及数据库 SQLite3 的组件 sqlitejdbc-v037-nested.jar 。
 * 
 * 在 Linux 下，进入本文件所在目录：
 * javac -cp jl1.0.1.jar:sqlitejdbc-v037-nested.jar: Week11Homework.java
 * java -cp jl1.0.1.jar:sqlitejdbc-v037-nested.jar: Week11Homework
 * 
 * 如果你使用的是 IDE，那么可将上面两个包导入到“BuildPath”中。
 * 如果你使用命令行，则在编译时要和上面两个 .jar 文件一起编译。
 * 
 * 本程序完成多媒体（音乐，mp3 文件）播放、多线程及 SQLite3 数据库的增删改查等功能。
 * 
 * 
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// 需要 jl1.0.1.jar 的支持
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

// 需要  sqlitejdbc-v037-nested.jar 的支持
import java.sql.*;

import java.awt.*;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Week11Homework extends JFrame {
	/**
	 * 
	 */
	// SQLite3 connection handler
	public static Connection conn = null;
	
	public static JTextField tfdKeyword;
	public static ContactsDetailDialog dialog = null;
	public static Week11Homework frame = null;
	
	Player player = null;
	static int peopleId = 0;
	static Thread musicPlayThread = null;

	public static JPanel pnNorth = null;
	public static JScrollPane scrollPane;
	public static JMenuItem mntmAddNewPeople = null;
	public static JMenu mnNewMenu = null;
	public static JMenuItem mntmModifyPeople = null;
	public static JMenuItem mntmRemovePeople = null;
	
	public static DefaultTableModel update_table = null;
	public static JTable table = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Week11Homework();
					frame.setVisible(true);
					
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Week11Homework() {
		setForeground(Color.RED);
		setFont(new Font("Dialog", Font.BOLD, 40));
		setTitle("我的通讯录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 524);
		
		pnNorth = new JPanel();
		getContentPane().add(pnNorth, BorderLayout.NORTH);
		pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JMenuBar menuBar = new JMenuBar();
		pnNorth.add(menuBar);
		
		mnNewMenu = new JMenu("通讯录管理");
		menuBar.add(mnNewMenu);
		
		mntmAddNewPeople = new JMenuItem("添加人员");
		mnNewMenu.add(mntmAddNewPeople);
		
		mntmModifyPeople = new JMenuItem("修改选中人员");
		mnNewMenu.add(mntmModifyPeople);
		
		mntmRemovePeople = new JMenuItem("删除选中人员");
		mnNewMenu.add(mntmRemovePeople);
		
		JLabel lblNewLabel = new JLabel("   查询(实时检索文本)：");
		pnNorth.add(lblNewLabel);
	}
	
	public void init() {
		
		// 完成数据库的初始化工作
		getDBConnection();
		
		// 关闭窗口，结束程序的运行
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				conn = null;
				System.exit(0);
			}
		});
		
		// 从数据库获取数据并填充到 JTable 中
		Vector<Vector<Object>> vecs = fetchAllRecordsFromDB();
		makeDataTable(vecs);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tfdKeyword = new JTextField();
		// 异步获取关键字
		tfdKeyword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String keyword = tfdKeyword.getText();
				// 从数据库获取根据关键字查询到的数据，
				// 并且将数据行返回给生成 JTable 的方法。
				Vector<Vector<Object>> vecs = retriveKeyword(keyword);
				makeDataTable(vecs);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
			}
		});
		pnNorth.add(tfdKeyword);
		tfdKeyword.setColumns(15);
		
		
		// 音乐模块
		JButton btnMusicBegin = new JButton("开始播放音乐");
		
		btnMusicBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (player == null) {
					// 这里需要用到多线程，否则整个程序会出问题。
					musicPlayThread = new Thread( () -> {
						try {
							File file = new File("./music.mp3");
							player = new Player(new FileInputStream(file));
							player.play();
							// 如果音乐播放停止
							if (player.isComplete()) {
								player.close();
								player = null;
								// 终止线程
								musicPlayThread.stop();
							}
						} catch ( FileNotFoundException eFNFE) {
							eFNFE.printStackTrace();
						} catch ( JavaLayerException eJLE) {
							eJLE.printStackTrace();
						} });
					musicPlayThread.start();
				}
			}
		});
		pnNorth.add(btnMusicBegin);
		
		JButton btnMusicEnd = new JButton("结束音乐的播放");
		btnMusicEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (player != null) {
					player.close();
					musicPlayThread.stop();
					player = null;
				}
			}
		});
		pnNorth.add(btnMusicEnd);
		
		// 往通讯录添加人员
		mntmAddNewPeople.addActionListener((ActionEvent e) -> {
			addNewPeople();
		});
		
		// 处理选中的记录(JTable)行
		if (table != null) {
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					JTable table = (JTable)e.getSource();
					int rowNum =table.getSelectedRow();
					String selectData = table.getValueAt(rowNum, 0).toString();
					peopleId = Integer.parseInt(selectData);
				}
				
				
			});
		}
		
		// 修改选中的行
		mntmModifyPeople.addActionListener((ActionEvent e) -> {
			if (peopleId > 0) {
				if (peopleIsExists()) {
					modifyPeopleInfo();
				}
			}
		});

		// 删除选中行
		mntmRemovePeople.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (peopleIsExists()) {
//					System.out.println(JOptionPane.showConfirmDialog(frame, "确认删除吗？"));
					if (JOptionPane.showConfirmDialog(frame, "确认删除 id 为 " + peopleId + " 的人员吗？") == 0) {
						removeRow();
						// 重新填充 JTable
						makeDataTable(fetchAllRecordsFromDB());
					}
				}
			}
		});
	}
	
	/**
	 * 
	 * 修改人员信息的操作方法
	 */
	private static void modifyPeopleInfo() {
		try {
			String sql = "select * from people where id = ?";
			PreparedStatement pSTMT = conn.prepareStatement(sql);
			pSTMT.setInt(1, peopleId);
			ResultSet rSet = pSTMT.executeQuery();
			Vector<Vector<Object>> vecs2 = new Vector<>(); 
			parseResultSet(vecs2, rSet);
			pSTMT.close();
		
			dialog = new ContactsDetailDialog("修改人员信息");
			dialog.txtName.setText((String)vecs2.get(0).get(1));
			dialog.txtBirthday.setText((String)vecs2.get(0).get(2));
			dialog.txtPhoneNumber.setText((String)vecs2.get(0).get(3));
			dialog.txtWechat.setText((String)vecs2.get(0).get(4));
			dialog.txtQq.setText((String)vecs2.get(0).get(5));
			dialog.txtEmail.setText((String)vecs2.get(0).get(6));
			dialog.txtCategory.setText((String)vecs2.get(0).get(7));
			dialog.txtOthers.setText((String)vecs2.get(0).get(8));
			dialog.setVisible(true);
		
			if (dialog.isOkPressed()) {
				try {
					String sqlUpdate = "UPDATE people set name = ?, birthday = ?, phonenumber = ?, wechat = ?, qq = ?, email = ?, category = ?, others = ? where id = ?;";
					pSTMT = conn.prepareStatement(sqlUpdate);
					pSTMT.setString(1, dialog.txtName.getText());
					pSTMT.setString(2, dialog.txtBirthday.getText());
					pSTMT.setString(3, dialog.txtPhoneNumber.getText());
					pSTMT.setString(4, dialog.txtWechat.getText());
					pSTMT.setString(5, dialog.txtQq.getText());
					pSTMT.setString(6, dialog.txtEmail.getText());
					pSTMT.setString(7, dialog.txtCategory.getText());
					pSTMT.setString(8, dialog.txtOthers.getText());
					pSTMT.setInt(9, peopleId);
					pSTMT.executeUpdate();
					// 重新填充 JTable
					makeDataTable(fetchAllRecordsFromDB());
					// 此程序凡是用到 PrepareStatement 的实例的地方就必须关闭该实例
					// 否则会出问题。
					pSTMT.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 添加人员的操作方法
	 */
	private static void addNewPeople() {
		dialog = new ContactsDetailDialog("添加人员信息");
		dialog.setVisible(true);
		
		if (dialog.isOkPressed()) {
			if (!dialog.txtName.getText().isEmpty()) {
				try {
					String sql = "INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement pSTMTP = conn.prepareStatement(sql);
					pSTMTP.setString(1, dialog.txtName.getText());
					pSTMTP.setString(2, dialog.txtBirthday.getText());
					pSTMTP.setString(3, dialog.txtPhoneNumber.getText());
					pSTMTP.setString(4, dialog.txtWechat.getText());
					pSTMTP.setString(5, dialog.txtQq.getText());
					pSTMTP.setString(6, dialog.txtEmail.getText());
					pSTMTP.setString(7, dialog.txtCategory.getText());
					pSTMTP.setString(8, dialog.txtOthers.getText());
					pSTMTP.executeUpdate();
					// 重新填充 JTable
					makeDataTable(fetchAllRecordsFromDB());
					pSTMTP.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}	
	}
	
	/**
	 * 
	 * 检查 peopleId 所指向的人员是否存在
	 * @return
	 */
	public static boolean peopleIsExists() {
		boolean isExists = true;
		try {
			String sql = "select * from people where id = " + peopleId + ";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) { // 不存在
				isExists = false;
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isExists;
	}
	
	public static void removeRow() {
		try {
			String sql = "delete from people where id = ?;";
			PreparedStatement pSTMT = conn.prepareStatement(sql);
			pSTMT.setInt(1, peopleId);
			pSTMT.executeUpdate();
			pSTMT.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void makeDataTable(Vector<Vector<Object>> vecs) {
		Vector<String> colume = new Vector<String>();
		colume.addAll(Arrays.<String> asList("ID", "姓名", "生日", "电话号码", "微信号", "QQ", "电子邮箱", "分类", "备注"));

		if (update_table == null) {
			update_table = new DefaultTableModel(vecs, colume);
			table = new JTable(update_table);
		} else {
			update_table.setDataVector(vecs, colume);
			table = new JTable(update_table);
		}
		  // Enable row selection (default)
	    table.setColumnSelectionAllowed(false);
	    table.setRowSelectionAllowed(true);
		scrollPane = new JScrollPane(table);
	}
	
	/**
	 * 
	 * 响应查询动作
	 * @param keyword
	 * @return
	 */
	private Vector<Vector<Object>> retriveKeyword(String keyword) {
		
		keyword = keyword.trim();
		
		Vector<Vector<Object>> vecs = null;
		try {
			String sql = "select * from people where name like ? or birthday like ? or phonenumber like ? or wechat like ? "
										+ "or qq like ? or email like ? or category like ? or others like ?";
			PreparedStatement pSTMT = conn.prepareStatement(sql);
			pSTMT.setString(1, "%" + keyword + "%");
			pSTMT.setString(2, "%" + keyword + "%");
			pSTMT.setString(3, "%" + keyword + "%");
			pSTMT.setString(4, "%" + keyword + "%");
			pSTMT.setString(5, "%" + keyword + "%");
			pSTMT.setString(6, "%" + keyword + "%");
			pSTMT.setString(7, "%" + keyword + "%");
			pSTMT.setString(8, "%" + keyword + "%");
			
			vecs = new Vector<Vector<Object>>();
			ResultSet rSet = pSTMT.executeQuery();
			parseResultSet(vecs, rSet);
			pSTMT.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vecs;
	}
	
	public static void parseResultSet(Vector<Vector<Object>> vecs, ResultSet rs) {
		try {
			Vector<Object> vec = null;
			while (rs.next()) {
				vec = new Vector<Object>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));
				vec.add(rs.getString(7));
				vec.add(rs.getString(8));
				vec.add(rs.getString(9));
				vecs.add(vec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据表中的所有记录
	 * 用于填充 JTable
	 * @return
	 */
	public static Vector<Vector<Object>> fetchAllRecordsFromDB() {
		Vector<Vector<Object>> vecs= null;
		try {

			String sql = "select * from people;";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			vecs = new Vector<Vector<Object>>();
			
			parseResultSet(vecs, rs);
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vecs;
	}

	/**
	 * 
	 * get sqlite3 connection
	 */
	public static void getDBConnection() {
		String driver = "org.sqlite.JDBC";
		// 会在当前项目目录寻找 db 文件
		// 若未找到，那么会在稍后新建数据表之时生成 sqlite db 文件
		String url = "jdbc:sqlite:./contacts.db";
		try {
			// 加载驱动程序
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// 连接 SQLite3 数据库
			conn = DriverManager.getConnection(url);
			conn.setAutoCommit(true);
			
			// 如果数据表不存在，那么新建一个
			String sqlCreate = "CREATE TABLE if not exists people (\n" + 
					"    id          INTEGER       PRIMARY KEY AUTOINCREMENT\n" + 
					"                              NOT NULL,\n" + 
					"    name        VARCHAR (512) NOT NULL,\n" + 
					"    birthday    VARCHAR (512),\n" + 
					"    phonenumber VARCHAR (512),\n" + 
					"    wechat      VARCHAR (512),\n" + 
					"    qq          VARCHAR (512),\n" + 
					"    email       VARCHAR (512),\n" + 
					"    category    VARCHAR (512),\n" + 
					"    others      VARCHAR (512) \n" + 
					");";
			conn.createStatement().execute(sqlCreate);
			
			// 往数据表填充数据
            /* Statement stmt = conn.createStatement(); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, 'XiaoMingLiao', '19991122', '982736785', '786823545', '89761255', 'mliao@122.com', '同学', '留学生');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '金毛狮王', '11150101', '9837493274', 'kjajkjjj', '293847928374', 'dasodfn@lsjaf.com', '大侠', '');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '张无忌', '10001202', '13800138123', 'zwjok2', '982374876', 'zwjok@1256.com', '武林高手', '');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '柳剑生', '19920111', '123987346687', 'Xsjddkjhf12334', '23894792374', 'ljs1992@1255.com', '朋友', '金融高手');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '徐云', '19900122', '132987397498', 'xuyun128', '928374932874', 'xuyun@hotemail.com', '朋友', '兵王');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '王大胆', '19990219', '13518972347', '1990wdd', '2389479837', 'wdd1990@1266.com', '朋友', '作战人员');"); */
            /* stmt.executeUpdate("INSERT INTO people (id, name, birthday, phonenumber, wechat, qq, email, category, others) VALUES (null, '黎南', '19901202', '138000138000', 'linan168', '89237437897', 'aaadkj@kk.com', '朋友', '隐世家族子弟');"); */
            /*  */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printResult(Vector<Vector<Object>> vecs) {
		for (Vector<Object> vec1 : vecs) {
			for (Object obj : vec1) {
				System.out.print((String)obj + " ");
			}
			System.out.println();
		}
	}
}

class ContactsDetailDialog extends JDialog {
	
	boolean okPressed = false;

	public JLabel lblName = null;
	public JTextField txtName = null;
	public JLabel lblBirthday = null;
	public JTextField txtBirthday = null;
	public JLabel lblPhoneNumber = null;
	public JTextField txtPhoneNumber = null;
	public JLabel lblEmail = null;
	public JTextField txtEmail = null;
	public JLabel lblWechat = null;
	public JTextField txtWechat = null;
	public JLabel lblQq = null;
	public JTextField txtQq = null;
	public JLabel lblCategory = null;
	public JTextField txtCategory = null;
	public JLabel lblOthers = null;
	public JTextPane txtOthers = null;

	private JPanel pnlInput = null;
	private JPanel pnlButtons = null;

	private JButton btnOk = null;
	private JButton btnCancel = null;

	public ContactsDetailDialog(String title) {
		setTitle(title);
		lblName = new JLabel("姓名 *(必填)");
		txtName = new JTextField();
		lblBirthday = new JLabel("生日");
		txtBirthday = new JTextField();
		lblPhoneNumber = new JLabel("电话号码");
		txtPhoneNumber = new JTextField();
		lblWechat = new JLabel("微信号");
		txtWechat = new JTextField();
		lblQq = new JLabel("QQ号");
		txtQq = new JTextField();
		lblEmail = new JLabel("Email");
		txtEmail = new JTextField();
		lblCategory = new JLabel("分类");
		txtCategory = new JTextField();
		lblOthers = new JLabel("备注");
		txtOthers = new JTextPane();
		
		ScrollPane scpnOthers = new ScrollPane();
		scpnOthers.add(txtOthers);
		
		pnlInput = new JPanel();
		pnlInput.setLayout(new GridLayout(9, 3));
		
		pnlInput.add(lblName);
		pnlInput.add(txtName);
		pnlInput.add(lblBirthday);
		pnlInput.add(txtBirthday);
		pnlInput.add(lblPhoneNumber);
		pnlInput.add(txtPhoneNumber);
		pnlInput.add(lblWechat);
		pnlInput.add(txtWechat);
		pnlInput.add(lblQq);
		pnlInput.add(txtQq);
		pnlInput.add(lblEmail);
		pnlInput.add(txtEmail);
		pnlInput.add(lblCategory);
		pnlInput.add(txtCategory);
		pnlInput.add(lblOthers);
		pnlInput.add(scpnOthers);

		pnlButtons = new JPanel();
		btnOk = new JButton("Ok");
		btnCancel = new JButton("Cancel");
		pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlButtons.add(btnOk);
		pnlButtons.add(btnCancel);
		
		JPanel pnlRight = new JPanel();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pnlInput, BorderLayout.CENTER);
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		getContentPane().add(pnlRight, BorderLayout.EAST);

		setSize(350, 300);
		setResizable(false);
		this.setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		btnOk.addActionListener(e -> {
			okPressed = true;
			dispose();
		});
		btnCancel.addActionListener(e -> {
			okPressed = false;
			dispose();
		});
	}

	public boolean isOkPressed() {
		return okPressed;
	}
}
