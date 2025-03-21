package javaproject.Service;

import javaproject.DAO.AdminDAO;
import javaproject.DAO.MemDAO;
import javaproject.DTO.AdminDTO;
import javaproject.DTO.MemDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdG extends JFrame implements ActionListener {
    private JPanel labelJPanel = new JPanel();
    private String[] labelName = {"아이디", "비밀번호", "이름", "성별", "생년월일", "직책"};
    private JPanel textFJPanel = new JPanel();
    private JRadioButton radioMan = new JRadioButton("남자");
    private JRadioButton radioWoman = new JRadioButton("여자");
    private ButtonGroup group = new ButtonGroup();
    private JComboBox yearCom;
    private JComboBox monthCom;
    private JComboBox dayCom;
    private JButton editPass = new JButton("수정");
    private JButton editName = new JButton("수정");
    private JButton editGender = new JButton("수정");
    private JButton editBirth = new JButton("수정");
    private JButton editPos = new JButton("수정");
    private JPanel buttonJPanel = new JPanel();
    private JLabel aId = new JLabel();
    private JTextField aPass = new JTextField();
    private JTextField aName = new JTextField();
    private JTextField aPos = new JTextField();
    String id ;
    public AdG(String id) {
        this.id = id;
        setTitle("관리자정보");
        setBounds(10, 10, 400, 300);
        setLayout(null);

        setLabelJPanel();
        labelJPanel.setBounds(10, 10, 80, 200);
        add(labelJPanel);

        setTextFieldsJPanel();
        textFJPanel.setBounds(100, 10, 210, 200);
        add(textFJPanel);

        setButtonJPanel();
        buttonJPanel.setBounds(310, 10, 60, 200);
        add(buttonJPanel);

        editPass.addActionListener(this);
        editName.addActionListener(this);
        editGender.addActionListener(this);
        editBirth.addActionListener(this);
        editPos.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setButtonJPanel() {
        GridLayout a = new GridLayout(6, 1);
        a.setVgap(2);
        buttonJPanel.setLayout(a);
        buttonJPanel.add(new JLabel(""));
        buttonJPanel.add(editPass);
        buttonJPanel.add(editName);
        buttonJPanel.add(editGender);
        buttonJPanel.add(editBirth);
        buttonJPanel.add(editPos);
    }

    private void setTextFieldsJPanel() { // 테스트용 id = "A1001"
        textFJPanel.setLayout(new GridLayout(6, 1));
        AdminDAO adminDAO = new AdminDAO();
        AdminDTO adminDTO = adminDAO.select(id);
        aId = new JLabel(adminDTO.getAId());
        textFJPanel.add(aId);
        aPass = new JTextField(adminDTO.getAPass(), 10);
        aPass.setSize(150, 20);
        aPass.setEditable(true);
        textFJPanel.add(aPass);
        aName = new JTextField(adminDTO.getAName(), 10);
        aName.setSize(150, 20);
        aName.setEditable(true);
        textFJPanel.add(aName);
        JPanel aGender = makeGenderPanel(adminDTO.getAGender());
        textFJPanel.add(aGender);
        JPanel aBirth = makerBirthPanel(adminDTO);
        textFJPanel.add(aBirth);
        aPos = new JTextField(adminDTO.getAPosition());
        textFJPanel.add(aPos);

    }

    private void setLabelJPanel() {
        labelJPanel.setLayout(new GridLayout(6, 1));
        for (int i = 0; i < labelName.length; i++) {
            StringBuilder labelN = new StringBuilder(labelName[i]);
            while (labelN.length() < 5) {
                labelN.append(" ");
            }
            JLabel label = new JLabel(labelN.toString());
            labelJPanel.add(label);
        }
    }

    private JPanel makeGenderPanel(int a) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(radioMan);
        panel.add(radioWoman);
        if (a == 0) {
            radioMan.setSelected(true);
        } else if (a == 1) {
            radioWoman.setSelected(true);
        }
        group.add(radioMan);
        group.add(radioWoman);

        return panel;
    }

    private JPanel makerBirthPanel(AdminDTO adminDTO) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] year = new String[46];
        String[] month = new String[12];
        String[] day = new String[31];
        for (int i = 0; i < year.length; i++) {
            year[i] = String.valueOf(i + 1980);
        }
        for (int i = 0; i < month.length; i++) {
            month[i] = String.valueOf(i + 1);
        }
        for (int i = 0; i < day.length; i++) {
            day[i] = String.valueOf(i + 1);
        }
        yearCom = new JComboBox(year);
        yearCom.setBackground(Color.WHITE);
        monthCom = new JComboBox(month);
        monthCom.setBackground(Color.WHITE);
        dayCom = new JComboBox(day);
        dayCom.setBackground(Color.WHITE);
        panel.add(yearCom);
        panel.add(new JLabel("년"));
        panel.add(monthCom);
        panel.add(new JLabel("월"));
        panel.add(dayCom);
        panel.add(new JLabel("일"));

        int BYear = Integer.parseInt(adminDTO.getYear());
        int BMonth = Integer.parseInt(adminDTO.getMonth());
        int BDay = Integer.parseInt(adminDTO.getDay());

        yearCom.setSelectedIndex(BYear - 1980);
        monthCom.setSelectedIndex(BMonth - 1);
        dayCom.setSelectedIndex(BDay - 1);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AdminDAO adminDAO = new AdminDAO();
        String newId = aId.getText();
        if (e.getSource() == editPass) {
            String newPass = aPass.getText();
            adminDAO.edit(1, newPass, newId);
        }
        if (e.getSource() == editName) {
            String newName = aName.getText();
            adminDAO.edit(2, newName, newId);
        }
        if (e.getSource() == editGender) {
            if (radioMan.isSelected()) {
                adminDAO.edit(3, "0", newId);
            } else {
                adminDAO.edit(3, "1", newId);
            }
        }
        if (e.getSource() == editBirth) {
            String newBirth = yearCom.getSelectedItem().toString() + "-" + monthCom.getSelectedItem().toString() + "-" + dayCom.getSelectedItem().toString();
            System.out.println(newBirth);
            adminDAO.edit(4, newBirth, newId);
        }
        if (e.getSource() == editPos) {
            String newPos = aPos.getText();
            System.out.println(newPos);
            adminDAO.edit(5, newPos, newId);
        } else {
            return;
        }
        dispose();
        new AdG(id);
    }

}
