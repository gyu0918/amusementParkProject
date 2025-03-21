package javaproject.chat.kim;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientGUI extends JFrame {
    //gui 부분
    private JButton attractionButton;
    private JButton foodButton;
    private JButton ticketButton;
    private JPanel mainPanel;
    private Socket socket;
    private  boolean[] flagList;
    private boolean[] checkAdminList;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ClientGUI(String id) throws IOException, ClassNotFoundException {
        //서버에서  채팅방상황 플래그 받아오는것

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        attractionButton = new JButton("놀이기구 고객센터");
        attractionButton.setBounds(100, 100, 200, 50);
        attractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    socket = getFlagFromServer(1004);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "방풀입니다.", "gg", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("flagList  = " + flagList[0]);
                System.out.println("checkAdmin = " + checkAdminList[0]);

                if (flagList[0]) {
                    new ChatClient().service(socket,writer,reader,1004, false,  id);
                } else {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        });

        foodButton = new JButton("음식점 고객센터");
        foodButton.setBounds(350, 100, 200, 50);
        foodButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = getFlagFromServer(1005);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "방풀입니다.", "gg", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("flagList  = " + flagList[1]);
                System.out.println("checkAdmin = " + checkAdminList[1]);

                if (flagList[0]) {
                    new ChatClient().service(socket,writer,reader,1005, false, id);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "방풀입니다.", "gg", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });

        ticketButton = new JButton("티켓 고객센터");
        ticketButton.setBounds(600, 100, 200, 50);
        ticketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = getFlagFromServer(1006);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "방풀입니다.", "gg", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("flagList  = " + flagList[2]);
                System.out.println("checkAdmin = " + checkAdminList[2]);

                if (flagList[0]) {
                    new ChatClient().service(socket,writer,reader,1006, false,  id);
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "방풀입니다.", "gg", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        mainPanel.add(attractionButton);
        mainPanel.add(foodButton);
        mainPanel.add(ticketButton);

        add(mainPanel);

        setTitle("고겍센터");
        setBounds(100, 100, 900, 500);
        setVisible(true);

    }

    public Socket getFlagFromServer(int port) throws IOException, ClassNotFoundException {

        socket = new Socket("192.168.0.28", port); // 서버와 연결

        writer = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("요요요요요요요용");
        reader = new ObjectInputStream(socket.getInputStream());
        System.out.println("야야야ㅑㅇ야ㅑ");

        //서버로 flag요청
        ChatDTO dto = new ChatDTO();
        dto.setCommand(Info.GET_FLAG);
        writer.writeObject(dto);
        writer.flush();

        ChatDTO tempDTO = (ChatDTO) reader.readObject();

        flagList = tempDTO.getFlag();
        checkAdminList = tempDTO.getCheckAdmin();

        return socket;
    }

}
