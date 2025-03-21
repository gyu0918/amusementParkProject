package javaproject.Service;

import javaproject.DAO.TimeTableDAO;
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

public class ManagerTimeTable extends JFrame implements MouseListener {
    private TimeTableDAO timeTableDAO;
    private List<TimeTableDTO> timeTableDTOList;
    private JTable table;
    private DefaultTableModel model;

    private JTextField searchTextField;
    private String[] items = {"요일", "시간"};
    private JComboBox<String> combo;

    //등록부분
    private JTextField idTextField;
    private JTextField dayTextField;
    private JTextField timeTextField;
    private JTextField paradeTextField;
    private JFrame newFrame;

    public ManagerTimeTable() throws SQLException {
        getTimeTableInfo();
        setDisplay();
        showFrame();
    }


    public void getTimeTableInfo() {
        try {
            timeTableDAO = new TimeTableDAO();
            timeTableDTOList = timeTableDAO.select();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String changeDay(int dayNum) {
        String day = "";
        String[] dayList = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};

        for (int i = 1; i < dayList.length + 1; i++) {
            if (i == dayNum) {
                day = dayList[i - 1];
                break;
            }
        }

        return day;
    }

    public int changeDayToInt(String day) {
        int dayNum = 0;
        String[] dayList = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};

        for (int i = 0; i < dayList.length + 1; i++) {
            if (dayList[i].equals(day)) {
                dayNum = i + 1;
                break;
            }
        }

        return dayNum;
    }

    public void setDisplay() {

        String[] columnType = {"Id", "요일", "시간", "퍼레이드이름"};

        //등록, 삭제 버튼 필요 수정은 알아서 textBox안에서 바꿀수 있도록 해야한다.
        String[][] dataList = new String[timeTableDTOList.size()][columnType.length];
        for (int i = 0; i < timeTableDTOList.size(); i++) {
            TimeTableDTO timeTableDTO = timeTableDTOList.get(i);
            dataList[i][0] = timeTableDTO.getTiId();
            dataList[i][1] = changeDay(timeTableDTO.getTiDay());
            dataList[i][2] = timeTableDTO.getTiTime() + "";
            dataList[i][3] = timeTableDTO.getTiContent();
        }
        // JTable, JTable 관리자 생성하기**
        model = new DefaultTableModel(dataList, columnType);


        table = new JTable(model);
        add(new JScrollPane(table));


        //테이블에 MouseListener 등록
        table.addMouseListener(this);


        //버튼추가
        setLayout(new GridLayout(2, 1));

        JPanel panel = new JPanel(new GridLayout(3, 3));

        //아래 위로 빈칸 넣기
        for (int i = 0; i < 4; i++) {
            JLabel noContentLabel = new JLabel("");
            panel.add(noContentLabel);
        }

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //검색 -----------------------------------------------------------------------------------------
        JButton searchBtn = new JButton("검색");
        combo = new JComboBox<>(items);
        searchTextField = new JTextField(10);

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        JButton prtAll = new JButton("전체보기");
        prtAll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 기존 데이터를 새로 갱신된 데이터로 교체
                String[][] dataList = listAll();
                model.setDataVector(dataList, new String[]{"Id", "요일", "시간", "퍼레이드이름"});
            }
        });
        centerPanel.add(combo);
        centerPanel.add(searchTextField);
        centerPanel.add(searchBtn);
        centerPanel.add(prtAll);

        //등록 ----------------------------------------------------------------------------------------
        JButton insertBtn = new JButton("등록");
        insertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //새창에서 클릭버튼이랑 textfield등등 만들어주기.
                newFrame = new JFrame();

                JPanel newpanel = new JPanel(new GridLayout(5,2));

                JLabel idJLabel = new JLabel("id");
                idTextField = new JTextField(10);
                newpanel.add(idJLabel);
                newpanel.add(idTextField);

                JLabel dayJLabel = new JLabel("요일");
                dayTextField = new JTextField(10);
                newpanel.add(dayJLabel);
                newpanel.add(dayTextField);

                JLabel timeJLabel = new JLabel("시간");
                timeTextField = new JTextField(10);
                newpanel.add(timeJLabel);
                newpanel.add(timeTextField);

                JLabel paradeJLabel = new JLabel("퍼레이드이름");
                paradeTextField = new JTextField(10);
                newpanel.add(paradeJLabel);
                newpanel.add(paradeTextField);

                JLabel noContentLabel = new JLabel("");
                newpanel.add(noContentLabel);

                JButton insertButton = new JButton("등록");
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

        //삭제 ----------------------------------------------------------------------------------------
        JButton deleteBtn = new JButton("삭제");
        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delete();

            }
        });
        centerPanel.add(deleteBtn);

        //수정 ---------------------------------------------------------------------------------------
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

    public void showFrame() {
        setTitle("일정표-관리자");
        setBounds(100, 100, 900, 500);
        setVisible(true);
    }

    public void updateTable() {
        // 테이블 데이터 갱신
        String[][] updatedDataList = new String[timeTableDTOList.size()][4];
        for (int i = 0; i < timeTableDTOList.size(); i++) {
            TimeTableDTO timeTableDTO = timeTableDTOList.get(i);
            updatedDataList[i][0] = timeTableDTO.getTiId();
            updatedDataList[i][1] = changeDay(timeTableDTO.getTiDay());
            updatedDataList[i][2] = timeTableDTO.getTiTime() + "";
            updatedDataList[i][3] = timeTableDTO.getTiContent();
        }

        // 기존 데이터를 새로 갱신된 데이터로 교체
        model.setDataVector(updatedDataList, new String[]{"Id", "요일", "시간", "퍼레이드이름"});
    }

    public void delete() {
        int index = table.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요.", "메세지", JOptionPane.WARNING_MESSAGE);
        } else {
            //db도 지워준다.
            timeTableDAO.delete(timeTableDTOList.get(index).getTiId());

            timeTableDTOList.remove(index);

            model.removeRow(index);

            updateTable();
        }
    }

    public boolean checkId(String id) {
        String[][] dataList = listAll();
        for (int i = 0; i < timeTableDTOList.size(); i++) {
            if (dataList[i][0].equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void insert() {

        //텍스트필드 값
        String idWord = idTextField.getText();
        String dayWord = dayTextField.getText();
        String timeWord = timeTextField.getText();
        String paradeWord = paradeTextField.getText();


        // 값이 없을떄 경고창
        if (idWord.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "id를 입력해 주세요.", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (dayWord.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "요일을 입력하세요. ", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (timeWord.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "시간을 입력하세요 ", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (paradeWord.equals("")) {
            JOptionPane.showMessageDialog(newFrame, "퍼레이드 이름을 입력하세요. ", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (checkId(idWord)) {
            JOptionPane.showMessageDialog(newFrame, "이미 존재하는 id 입니다. ", "메세지", JOptionPane.WARNING_MESSAGE);
        } else {
            int dayNum = changeDayToInt(dayWord);

            Time time = Time.valueOf(timeWord);

            TimeTableDTO timeTableDTO = TimeTableDTO.builder()
                    .tiId(idWord)
                    .tiDay(dayNum)
                    .tiTime(time)
                    .tiContent(paradeWord)
                    .build();

            //db넣기
            timeTableDAO.insert(timeTableDTO);

            timeTableDTOList.add(timeTableDTO);

            String[][] updatedDataList = listAll();


            model.setDataVector(updatedDataList, new String[]{"Id", "요일", "시간", "퍼레이드이름"});
        }

    }

    public String[][] listAll() {
        String[] columnType = {"Id", "요일", "시간", "퍼레이드이름"};
        String[][] dataList = new String[timeTableDTOList.size()][columnType.length];
        for (int i = 0; i < timeTableDTOList.size(); i++) {
            TimeTableDTO timeTableDTO = timeTableDTOList.get(i);
            dataList[i][0] = timeTableDTO.getTiId();
            dataList[i][1] = changeDay(timeTableDTO.getTiDay());
            dataList[i][2] = timeTableDTO.getTiTime() + "";
            dataList[i][3] = timeTableDTO.getTiContent();
        }
        return dataList;
    }

    public void search() {
        int index = -1;


        //콤보 박스 값 알아내기 - 검색
        String field = (String) combo.getSelectedItem();
        if (field.equals("요일")) {
            index = 1;

        } else if (field.equals("시간")) {
            index = 2;
        }

        //텍스트필드 값
        String word = searchTextField.getText();

        //기존 리스트 가지고 오기
        String[][] dataList = listAll();

        // 테이블 데이터 갱신
        int count = 0;
        int checkIndex = 0;
        String[][] updatedDataList = new String[timeTableDTOList.size()][4];
        for (int i = 0; i < timeTableDTOList.size(); i++) {
            if (dataList[i][index].equals(word)) {
                TimeTableDTO timeTableDTO = timeTableDTOList.get(i);
                updatedDataList[checkIndex][0] = timeTableDTO.getTiId();
                updatedDataList[checkIndex][1] = changeDay(timeTableDTO.getTiDay());
                updatedDataList[checkIndex][2] = timeTableDTO.getTiTime() + "";
                updatedDataList[checkIndex][3] = timeTableDTO.getTiContent();
                count++;
                checkIndex++;
            }
        }
        if (count > 0) {
            // 기존 데이터를 새로 갱신된 데이터로 교체
            model.setDataVector(updatedDataList, new String[]{"Id", "요일", "시간", "퍼레이드이름"});
        } else {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다. ", "메세지", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void update() {
        int rowIndex = table.getSelectedRow();
        int columnIndex = table.getSelectedColumn();

        TimeTableDTO newTimeTableDTO = null;

        String value = null;

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "수정할 행을 선택하세요.", "메세지", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            switch (columnIndex) {
                case 0:
                    value = table.getValueAt(rowIndex, 0).toString();
                    String id = timeTableDTOList.get(rowIndex).getTiId();
                    newTimeTableDTO = TimeTableDTO.builder()
                            .tiId(value)
                            .tiDay(timeTableDTOList.get(rowIndex).getTiDay())
                            .tiTime(timeTableDTOList.get(rowIndex).getTiTime())
                            .tiContent(timeTableDTOList.get(rowIndex).getTiContent())
                            .build();

                    timeTableDAO.update(newTimeTableDTO,id);


                    break;
                case 1:
                    value = table.getValueAt(rowIndex, 1).toString();
                    int result = changeDayToInt(value);
                    String id2 = timeTableDTOList.get(rowIndex).getTiId();
                    newTimeTableDTO = TimeTableDTO.builder()
                            .tiId(timeTableDTOList.get(rowIndex).getTiId())
                            .tiDay(result)
                            .tiTime(timeTableDTOList.get(rowIndex).getTiTime())
                            .tiContent(timeTableDTOList.get(rowIndex).getTiContent())
                            .build();
                    timeTableDAO.update(newTimeTableDTO,id2);
                    break;
                case 2:
                    Time value2 = Time.valueOf(table.getValueAt(rowIndex, 2).toString());
                    String id3 = timeTableDTOList.get(rowIndex).getTiId();
                    newTimeTableDTO = TimeTableDTO.builder()
                            .tiId(timeTableDTOList.get(rowIndex).getTiId())
                            .tiDay(timeTableDTOList.get(rowIndex).getTiDay())
                            .tiTime(value2)
                            .tiContent(timeTableDTOList.get(rowIndex).getTiContent())
                            .build();
                    timeTableDAO.update(newTimeTableDTO,id3);
                    break;
                case 3:
                    value = table.getValueAt(rowIndex, 3).toString();
                    String id4 = timeTableDTOList.get(rowIndex).getTiId();
                    newTimeTableDTO = TimeTableDTO.builder()
                            .tiId(timeTableDTOList.get(rowIndex).getTiId())
                            .tiDay(timeTableDTOList.get(rowIndex).getTiDay())
                            .tiTime(timeTableDTOList.get(rowIndex).getTiTime())
                            .tiContent(value)
                            .build();
                    timeTableDAO.update(newTimeTableDTO,id4);
                    break;
            }
        }

        timeTableDTOList.set(rowIndex,newTimeTableDTO);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
