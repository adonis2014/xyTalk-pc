package xysoft.im.frames;

import xysoft.im.app.Launcher;
import xysoft.im.components.*;
import xysoft.im.db.model.Room;
import xysoft.im.entity.SelectUserData;
import xysoft.im.panels.SelectUserPanel;
import xysoft.im.utils.FontUtil;
import xysoft.im.utils.JID;
import xysoft.im.utils.OSUtil;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class AddOrRemoveMemberDialog extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6326533522141139458L;
	private JPanel editorPanel;
    private RCTextField searchTextField;

    private SelectUserPanel selectUserPanel;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JButton okButton;
    private List<SelectUserData> userList = new ArrayList<>();
    private List<SelectUserData> userListClone;

    public static final int DIALOG_WIDTH = 600;
    public static final int DIALOG_HEIGHT = 500;


    public AddOrRemoveMemberDialog(Frame owner, boolean modal, List<SelectUserData> userList)
    {
        super(owner, modal);
        this.userList = userList;
        userListClone = userList;

        initComponents();
        //initAllData();
        initView();
        setListeners();
    }
    
//	private void initAllData() {
//		userList.clear();
//		List<Room> singleUser = Launcher.roomService.findByType("s");
//		for (Room room : singleUser) {
//			userList.add(new SelectUserData(JID.usernameByJid(room.getRoomId()) + "-" + room.getName(), false));
//		}
//
//		selectUserPanel = new SelectUserPanel(DIALOG_WIDTH, DIALOG_HEIGHT - 100, userList);
//	}

    public AddOrRemoveMemberDialog(Frame owner, boolean modal, List<SelectUserData> usersLists,
			List<SelectUserData> allusers) {
        super(owner, modal);
        this.userList = usersLists;
        userListClone = allusers;

        initComponents();
        //initAllData();
        initView();
        setListeners();
	}

	private void initComponents()
    {
        int posX = MainFrame.getContext().getX();
        int posY = MainFrame.getContext().getY();

        posX = posX + (MainFrame.getContext().currentWindowWidth - DIALOG_WIDTH) / 2;
        posY = posY + (MainFrame.getContext().currentWindowHeight - DIALOG_HEIGHT) / 2;
        setBounds(posX, posY, DIALOG_WIDTH, DIALOG_HEIGHT);
        setUndecorated(true);

        getRootPane().setBorder(new LineBorder(Colors.LIGHT_GRAY));

        selectUserPanel = new SelectUserPanel(DIALOG_WIDTH, DIALOG_HEIGHT - 100, userList);

        // 输入面板
        editorPanel = new JPanel();
        searchTextField = new RCTextField();
        searchTextField.setPlaceholder("搜索");
        searchTextField.setPreferredSize(new Dimension(DIALOG_WIDTH / 2, 35));
        searchTextField.setFont(FontUtil.getDefaultFont(14));
        searchTextField.setForeground(Colors.FONT_BLACK);
        searchTextField.setMargin(new Insets(0, 15, 0, 0));


        // 按钮组
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        cancelButton = new RCButton("取消");
        cancelButton.setForeground(Colors.FONT_BLACK);

        okButton = new RCButton("创建", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        okButton.setBackground(Colors.PROGRESS_BAR_START);
    }


    private void initView()
    {
        editorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        editorPanel.add(searchTextField);

        buttonPanel.add(cancelButton, new GBC(0, 0).setWeight(1, 1).setInsets(15, 0, 0, 0));
        buttonPanel.add(okButton, new GBC(1, 0).setWeight(1, 1));


        /*setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, 0, 0, false, false));
        editorPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, 40));
        selectUserPanel.setPreferredSize(new Dimension(400, 200));
        buttonPanel.setPreferredSize(new Dimension(DIALOG_WIDTH, 40));
        add(editorPanel);
        add(selectUserPanel);
        add(buttonPanel);
*/

        add(editorPanel, BorderLayout.NORTH);
        add(selectUserPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setListeners()
    {
        cancelButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setVisible(false);

                super.mouseClicked(e);
            }
        });

        okButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });

        searchTextField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                searchUsers(searchTextField.getText());
                super.keyTyped(e);
            }
        });
    }
    
    
    private void searchUsers(String key) {
		 if (key == null || key.isEmpty())
	        {
	           
	            selectUserPanel.notifyDataSetChanged(null);
	            return;
	        }

	        key = key.toLowerCase();
	        List<SelectUserData> users = new ArrayList<>();

	        for (SelectUserData item : userListClone)
	        {
	            if (item.getUserName().toLowerCase().indexOf(key) > -1 && (!selectUserPanel.getSelectedUser().contains(item)))
	            {
	                users.add(item);
	            }
	        }

	        selectUserPanel.notifyDataSetChanged(users);
	}

//    private void searchUsers(String key)
//    {
//        if (key == null || key.isEmpty())
//        {
//            for (SelectUserData item : userListClone)
//            {
//                List<SelectUserData> selectUserDataList = selectUserPanel.getSelectedUser();
//                if (selectUserDataList.contains(item))
//                {
//                    item.setSelected(true);
//                }
//                else
//                {
//                    item.setSelected(false);
//                }
//            }
//            selectUserPanel.notifyDataSetChanged(userListClone);
//            return;
//        }
//
//        key = key.toLowerCase();
//        List<SelectUserData> users = new ArrayList<>();
//
//        for (SelectUserData item : userList)
//        {
//            if (item.getUserName().toLowerCase().indexOf(key) > -1 && (!selectUserPanel.getSelectedUser().contains(item)))
//    	    {
//                users.add(item);
//            }
//        }
//
//        selectUserPanel.notifyDataSetChanged(users);
//    }

    public List<SelectUserData> getSelectedUser()
    {
        return selectUserPanel.getSelectedUser();
    }

    public JButton getOkButton()
    {
        return okButton;
    }



}
