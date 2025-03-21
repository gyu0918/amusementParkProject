package javaproject;

import javaproject.Service.*;
import javaproject.chat.kim.ChatAdminister;
import javaproject.chat.kim.ChatServer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class AdminMenu1 extends JFrame {
    JPanel jp = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JButton food = new JButton("고객센터");
    JButton attraction = new JButton("어트랙션");
    JButton reservation = new JButton("예약관리");
    JButton schedule = new JButton("일정표 관리");
    JButton member = new JButton("회원");
    JButton ticket = new JButton("티켓");
    JButton update = new JButton("관리자정보수정/로그아웃");
    LoginG LoginG;
    String id;

    //채팅
    private ChatServer chatServer;

    public AdminMenu1(LoginG loginG,String id) {
        this.id = id;
        this.LoginG = loginG;
        this.setSize(1000, 650);
        this.setLocation(0, 0);
        this.setTitle("관리자 메뉴");
        setting();

        //chat부분
        chatServer = chatServer;

        this.setVisible(true);


    }

    public void setting() {
        this.setLayout(new GridLayout(2, 1));
        this.add(jp);
        this.add(jp2);
        jp.setLayout(new GridLayout(1, 2));
        jp.add(food);
        jp.add(attraction);
        jp2.setLayout(new GridLayout(1, 3));
        jp2.add(jp3);
        jp2.add(reservation);
        jp2.add(jp4);
        jp3.setLayout(new GridLayout(2, 1));
        jp4.setLayout(new GridLayout(2, 1));
        jp3.add(member);
        jp3.add(ticket);
        jp4.add(schedule);
        jp4.add(update);

        styleButton(food, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(attraction, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(reservation, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(schedule, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(member, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(ticket, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 25), Color.BLACK);
        styleButton(update, Color.LIGHT_GRAY, new Font("Serif", Font.BOLD, 18), Color.BLACK);
        seting2();
        this.setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor, Font font, Color fgColor) {
        button.setBorder(new LineBorder(Color.black, 5));
        button.setBackground(bgColor);
        button.setFont(font);
        button.setForeground(fgColor);
        button.setFocusPainted(false);  // 포커스 효과 없애기

    }

    public void seting2() {
        food.addActionListener(e -> {
            try {
                FoodClick();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        attraction.addActionListener(_ -> attractClick());
        reservation.addActionListener(e -> resCLick());
        schedule.addActionListener(e -> {
            scheduleClick();
        });
        member.addActionListener(e -> memberClick());
        ticket.addActionListener(e -> {
            ticketClick();
        });
        update.addActionListener(e -> adminUpdate());
    }

    private void FoodClick() throws IOException, ClassNotFoundException {
        System.out.println("고객센터 연결");
        new ChatAdminister(id);

    }

    private void attractClick() {
        System.out.println("연결");
        new AttractionManager(this);

    }

    private void resCLick() {
        System.out.println("연결");
        new ReservationG();
    }

    private void scheduleClick() {
        try {
            new ManagerTimeTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void memberClick() {
        System.out.println("연결");
        new MemAdG();
    }

    private void ticketClick()  {
        try {
            new ManagerTicket();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void adminUpdate() {
        System.out.println("연결");
         new AdG(id);
    }


}




