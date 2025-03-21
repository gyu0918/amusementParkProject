package javaproject;

import javaproject.DAO.MemDAO;
import javaproject.DAO.TicketDAO;
import javaproject.DTO.TicketDTO;
import java.util.List;
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ticketGUI extends JFrame implements ActionListener {

    private String userId; // 로그인한 사용자id
    private TicketDAO ticketDAO = TicketDAO.getInstance();



    private MemDAO memDAO = new MemDAO();
    public ticketGUI(String id) throws SQLException {
        this.userId = id;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setTitle("티켓 구매");


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);


        List<TicketDTO> tickets = ticketDAO.getTicketList();


        for (TicketDTO ticket : tickets) { //여기 수정이요!@!@!@
            JButton ticketButton = new JButton("<html><center><b>" + ticket.getTName() +
                    "</b><br>가격: " + ticket.getTPrice() + "원</center></html>");
            ticketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            ticketButton.setPreferredSize(new Dimension(350, 50));
            ticketButton.addActionListener(e -> purchaseTicket(ticket.getTName()));
            panel.add(ticketButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }


        panel.setPreferredSize(new Dimension(380, tickets.size() * 70));


        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.WHITE);

        this.add(scrollPane);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void purchaseTicket(String ticket){
        if(memDAO.select(userId).getTPass()!=null){
            JOptionPane.showMessageDialog(this, "이미 티켓을 구매하셨습니다!");
            return;
        }
//        if(ticket.equals("노멀패스")){
//            memDAO.edit(5,"노멀패스",userId);
//        }else{
//            memDAO.edit(5,"매직패스",userId);
//        }
        memDAO.edit(5,ticket,userId); //여기서 넘겨줌@!@!@!
        JOptionPane.showMessageDialog(this, ticket + "가 성공적으로 구매되었습니다");
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
