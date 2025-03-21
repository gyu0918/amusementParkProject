package javaproject;

import javaproject.Service.LoginG;
import javaproject.Service.MemG;
import javaproject.Service.Reservation;
import javaproject.Service.ReservationG;
import javaproject.chat.kim.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class Map extends JFrame implements ActionListener {

    private Image image = new ImageIcon("resource\\images\\kkk.jpg").getImage();
    private String id;
    private LoginG LoginG;
    private JPanel jpImage = new JPanel() {
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            this.setOpaque(false);
            super.paintComponents(g);
        }
    };
    Map m =this;



    public Map(String id,LoginG loginG) throws IOException {
        this.LoginG = loginG;
        this.id = id;
        this.setTitle("정규랜드 지도");
        this.setSize(1200, 7000);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jpImage);
        jpImage.setLayout(null);

        reservationBnt();
        logManageBnt();
        ticketBnt();
        logoutBnt();
        chatBnt();
        this.setVisible(true);
        add();
        mouseCheck();

    }
    public void add(){
        System.out.println("oooooo");
        addButton2("",0,0,1,1);
        addButton2("",2,2,1,1);
        addButton2("",3,3,1,1);
        addButton2("",6,6,1,1);
        addButton2("",8,8,1,1);
        addButton2("",10,10,1,1);



        addButton2("t익스",396,81,130,195);
        addButton2("롤링",707,380,67,120);
        addButton2("콜롬버스",950,366,94,120);
        addButton2("범퍼",712,284,60,50);
        addButton2("이솝",828,343,80,70);
        addButton2("썬더폴스",862,138,90,130);
        addButton2("사파리",209,514,140,100);
        addButton2("lost",282,641,110,70);
        addButton2("더블락",664,514,45,60);
        addButton2("버드",587,695,60,60);
        addButton2("판다",599,526,44,52);
        addButton2("애니멀원더 월드",527,596,70,80);
        addButton2("씨라이언",449,675,57,57);
        addButton2("애니멀원더 스테이지",504,456,56,100);
        addButton2("카니발광장",479,381,62,57);

    }
    public void reservationBnt(){
        JButton rbnt = new JButton("예약현황");
        rbnt.setBounds(400,10,100,26);


        rbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new ReservationG();
            }
        });
        jpImage.add(rbnt);
    }
    public void logManageBnt(){
        JButton lbnt = new JButton("회원정보수정");
        lbnt.setBounds(500,10,110,26);

        lbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                new MemG(id);
            };
        });

        jpImage.add(lbnt);
    }
    public void logoutBnt(){
        JButton lobnt = new JButton("로그아웃");
        lobnt.setBounds(610,10,100,26);

        lobnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                dispose();
                LoginG.setVisible(true);
            }
        });

        jpImage.add(lobnt);
    }
    public void ticketBnt(){
        JButton tbnt = new JButton("티켓구매");
        tbnt.setBounds(710,10,100,26);
        tbnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("연결해야댐");
                try {
                    new ticketGUI(id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        jpImage.add(tbnt);
    }
    //chat 임시 만들기 --------------------------------------------------------
    public void chatBnt(){
        JButton cbnt  = new JButton("고객센터");
        cbnt.setBounds(810,10,100,26);
        cbnt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ClientGUI(id);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jpImage.add(cbnt);
    }


    public void mouseCheck(){
        jpImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                System.out.println("마우스 클릭 좌표: X = " + mouseX + ", Y = " + mouseY);
            }
        });
    }

    public void addButton2(String name , int x , int y , int width , int height){
        JButton jbnt = new JButton();
        jbnt.setText("");
        jbnt.setBounds(x,y,width,height);
        jbnt.setBorder(BorderFactory.createEmptyBorder());
        jbnt.setContentAreaFilled(false);
        jpImage.add(jbnt);
        jbnt.addActionListener(createButtonListener(name));


    }
    private ActionListener createButtonListener(String rideName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 클릭된 버튼을 가져옴
                JButton clickedButton = (JButton) e.getSource();  // 클릭된 버튼 객체
                System.out.println("클릭된 버튼: " + clickedButton);  // 버튼 객체

                System.out.println("놀이기구 이름: " + rideName );
                try {
                    System.out.println("ddddddddd");
                    new Reservation(m,rideName,id);
                } catch (SQLException ex) {
                    System.out.println("aaaaaa");
                    throw new RuntimeException(ex);
                }

//               임시 sendDataToTeamMember(rideName, rideId, clickedButton);  // 정진형한테 전달
            }
        };
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws IOException {
        new Map("ddd", new LoginG());
    }
}
