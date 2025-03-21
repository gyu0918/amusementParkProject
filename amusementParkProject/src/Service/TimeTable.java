package javaproject.Service;

import javaproject.DAO.TimeTableDAO;
import javaproject.DTO.TimeTableDTO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class TimeTable extends JFrame {
    private List<TimeTableDTO> timeTableDTOList;
    private GridBagConstraints gbc;
    private GridBagLayout grid;

    public TimeTable() {

        getTimeTableInfo();
        setDisplay();
        showFrame();
    }

    public void getTimeTableInfo()  {
        try{
            TimeTableDAO timeTableDAO = new TimeTableDAO();
            timeTableDTOList = timeTableDAO.select();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // jcomponent인 jbutton의 객체에 x,y의 좌표의 시작점에서 w,h 크기의 단추를 만듭니다
    public void make(JComponent c,int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        //gridBagLayout의 GridBagConstraintsDML set하는 방법
        grid.setConstraints(c,gbc);
    }

    public void setDisplay() {


        JPanel panel = new JPanel();  //기존 판넬

        grid = new GridBagLayout();
        panel.setLayout(grid);
        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        //요일 라벨만들기
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};

        int x = 0;
        for (String day : days) {
            JLabel label = new JLabel(day,SwingConstants.CENTER);

            make(label, x++ , 0,1,1);
            panel.add(label);
        }

        // 요일별 데이터를 그룹화 Map 활용
        Map<Integer, List<TimeTableDTO>> groupedData = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            groupedData.put(i, new ArrayList<>());
        }

        // timeTableDTOList를 요일별로 그룹화
        for (TimeTableDTO dto : timeTableDTOList) {
            groupedData.get(dto.getTiDay()).add(dto);
        }

        // 요일별로 내용과 시간을 세로로 나열
        for (int i = 1; i <= 7; i++) {
            JPanel dayPanel = new JPanel();

            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS)); // 세로로 배치

            // 해당 요일의 행사들을 표시
            List<TimeTableDTO> dtos = groupedData.get(i);
            if (!dtos.isEmpty()) {
                for (TimeTableDTO dto : dtos) {
                    // 행사 내용 표시
                    JLabel contentLabel = new JLabel(dto.getTiContent());

                    contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(contentLabel);

                    // 행사 시간 표시
                    JLabel timeLabel = new JLabel(String.valueOf(dto.getTiTime()));

                    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    dayPanel.add(timeLabel);

                    //gridBag 부분    배경 넣을거면 생략?
                    make(dayPanel,  i - 1,1,1,4);
                }
            } else {
                JLabel noContentLabel = new JLabel("");
                noContentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //gridBag 부분     배경넣을거면 생략?
                make(noContentLabel,  i - 1,1,1,4);

                dayPanel.add(noContentLabel);
            }
            // 요일별로 패널에 추가
            panel.add(dayPanel);
        }

        add(panel);
    }

    public void showFrame() {
        setTitle("Banner");
        setBounds(100, 100, 800, 400);
        setVisible(true);
//        //이거 실행하면 전체 JFrame가 꺼짐 그래서 베너만 없에애되니까
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
