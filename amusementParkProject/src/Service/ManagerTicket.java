package javaproject.Service;

import javaproject.DAO.TicketDAO;
import javaproject.DTO.TicketDTO;
import javaproject.DTO.TimeTableDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class ManagerTicket extends JFrame implements MouseListener {
    //setDisplay 부분
    private List<TicketDTO> ticketDTOList;
    private TicketDAO ticketDAO;
    private DefaultTableModel model;
    private JTable table;

    //등록버튼 부분
    private JFrame newFrame;
    private JTextField ticketNumTextField;
    private JTextField tickNameTextField;
    private JTextField ticketPriceTextField;

    public ManagerTicket() throws SQLException {
        getTimeTableInfo();
        setDisplay();
        showFrame();
    }

    public void getTimeTableInfo() throws SQLException {
        ticketDAO = new TicketDAO();
        ticketDTOList = ticketDAO.select();
    }

    public void setDisplay() {
        //조회 필요없고 등록, 삭제, 수정

        //JTable 사용
        String[] columnType = {"이용권번호", "이용권 이름", "이용권 가격"};

        String[][] dataList = new String[ticketDTOList.size()][columnType.length];
        for (int i = 0; i < ticketDTOList.size(); i++) {
            TicketDTO ticketDTO = ticketDTOList.get(i);
            dataList[i][0] = ticketDTO.getTPass();
            dataList[i][1] = ticketDTO.getTName();
            dataList[i][2] = String.valueOf(ticketDTO.getTPrice());
        }
        // JTable 관리자 생성
        model = new DefaultTableModel(dataList, columnType);

        table = new JTable(model);
        //JScrollPane처리를 해줘야 헤더 까지 나옴
        add(new JScrollPane(table));

        //테이블에 MouseListener등록
        table.addMouseListener(this);

        //레이아웃 추가
        setLayout(new GridLayout(2, 1));

        JPanel panel = new JPanel(new GridLayout(3, 3));

        //아래 위로 빈칸 넣기
        for (int i = 0; i < 4; i++) {
            JLabel noContentLabel = new JLabel("");
            panel.add(noContentLabel);
        }

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //등록 버튼
        JButton insertBtn = new JButton("등록");
        insertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //새창에서 클릭버튼이랑 textfield등등 만들어주기.
                newFrame = new JFrame();

                JPanel newpanel = new JPanel(new GridLayout(4, 2));

                JLabel ticketNumJLabel = new JLabel("이용권 번호");
                ticketNumTextField = new JTextField(10);
                newpanel.add(ticketNumJLabel);
                newpanel.add(ticketNumTextField);

                JLabel ticketNameJLabel = new JLabel("이용권 이름");
                tickNameTextField = new JTextField(10);
                newpanel.add(ticketNameJLabel);
                newpanel.add(tickNameTextField);

                JLabel ticketPriceJLabel = new JLabel("이용권 가격");
                ticketPriceTextField = new JTextField(10);
                newpanel.add(ticketPriceJLabel);
                newpanel.add(ticketPriceTextField);

                JLabel noContentLabel = new JLabel("");
                newpanel.add(noContentLabel);

                JButton insertButton = new JButton("등록하기");
                insertButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        insert();
                        newFrame.setVisible(false);
                    }
                });
                newpanel.add(insertButton);

                newFrame.add(newpanel);

                newFrame.setTitle("등록-관리자");
                newFrame.setBounds(300, 300, 400, 200);
                newFrame.setVisible(true);

            }
        });

        centerPanel.add(insertBtn);

        //삭제
        JButton deleteBtn = new JButton("삭제");
        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        centerPanel.add(deleteBtn);

        //수정
        JButton updateBtn = new JButton("수정");
        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        centerPanel.add(updateBtn);


        panel.add(centerPanel);
        add(panel);

        //아래 위로 빈칸 넣기
        for (int i = 0; i < 4; i++) {
            JLabel noContentLabel = new JLabel("");
            panel.add(noContentLabel);
        }

    }

    public void insert() {
        //텍스트필드 값
        String ticketNum = ticketNumTextField.getText();
        String ticketName = tickNameTextField.getText();
        String ticketPrice = ticketPriceTextField.getText();


        // 값이 없을떄 경고창
        if (ticketNum.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "이용권 번호를 입력해 주세요.", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ticketName.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "이용권 이름을 입력하세요. ", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ticketPrice.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "이용권 가격을 입력하세요 ", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            int tPrice = Integer.parseInt(ticketPrice);

            TicketDTO ticketDTO = TicketDTO.builder()
                    .tPass(ticketName)
                    .tName(ticketName)
                    .tPrice(tPrice)
                    .build();

            //db넣기
            ticketDAO.insert(ticketDTO);

            ticketDTOList.add(ticketDTO);

            String[][] updatedData = listToArr();

            //Jtable 부분 바꾼다.
            model.setDataVector(updatedData, new String[]{"이용권 번호", "이용권 이름", "이용권 가격"});
        }
    }

    public String[][] listToArr() {
        String[] columnType = {"이용권 번호", "이용권 이름", "이용권 가격"};

        String[][] dataList = new String[ticketDTOList.size()][columnType.length];
        for (int i = 0; i < ticketDTOList.size(); i++) {
            TicketDTO ticketDTO = ticketDTOList.get(i);
            dataList[i][0] = ticketDTO.getTPass();
            dataList[i][1] = ticketDTO.getTName();
            dataList[i][2] = String.valueOf(ticketDTO.getTPrice());
        }

        return dataList;
    }

    public void delete() {
        int index = table.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요.", "메세지", JOptionPane.WARNING_MESSAGE);
        } else {
            //db도 지워준다.
            ticketDAO.delete(ticketDTOList.get(index).getTPass());

            ticketDTOList.remove(index);

            model.removeRow(index);

            String[][] updatedData = listToArr();

            //Jtable 부분 바꾼다.
            model.setDataVector(updatedData, new String[]{"이용권 번호", "이용권 이름", "이용권 가격"});
        }
    }

    public void update(){
        int rowIndex = table.getSelectedRow();
        int columnIndex = table.getSelectedColumn();

        TicketDTO newTicketDTO = null;

        String value = null;

        String id = null;

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "수정할 행을 선택하세요.", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            switch (columnIndex) {
                case 0:
                    value = table.getValueAt(rowIndex, 0).toString();

                    id = ticketDTOList.get(rowIndex).getTPass();

                    newTicketDTO = TicketDTO.builder()
                            .tPass(value)
                            .tName(ticketDTOList.get(rowIndex).getTName())
                            .tPrice(ticketDTOList.get(rowIndex).getTPrice())
                            .build();
                    //db처리
                    ticketDAO.update(newTicketDTO,id);

                    break;
                case 1:
                    value = table.getValueAt(rowIndex, 1).toString();

                    id = ticketDTOList.get(rowIndex).getTPass();

                    newTicketDTO = TicketDTO.builder()
                            .tPass(ticketDTOList.get(rowIndex).getTPass())
                            .tName(value)
                            .tPrice(ticketDTOList.get(rowIndex).getTPrice())
                            .build();
                    //db처리
                    ticketDAO.update(newTicketDTO,id);
                    break;
                case 2:
                    value = table.getValueAt(rowIndex, 2).toString();

                    id = ticketDTOList.get(rowIndex).getTPass();

                    newTicketDTO = TicketDTO.builder()
                            .tPass(ticketDTOList.get(rowIndex).getTPass())
                            .tName(ticketDTOList.get(rowIndex).getTName())
                            .tPrice(Integer.parseInt(value))
                            .build();
                    //db처리
                    ticketDAO.update(newTicketDTO,id);
                    break;
            }
        }

        ticketDTOList.set(rowIndex,newTicketDTO);
    }

    public void showFrame() {
        setTitle("티켓-관리자");
        setBounds(100, 100, 900, 500);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
