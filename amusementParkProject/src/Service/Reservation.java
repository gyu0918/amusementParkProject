package javaproject.Service;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javaproject.DAO.AttractionDAO;
import javaproject.DAO.MemDAO;
import javaproject.DAO.ReservationDAO;
import javaproject.DAO.TicketDAO;
import javaproject.DTO.AttractionDTO;
import javaproject.DTO.ReservationDTO;
import javaproject.Map;

public class Reservation extends JFrame{
	String mId=null;
	AttractionDAO adao=new AttractionDAO();
	ReservationDAO rdao=new ReservationDAO();
	MemDAO mdao=new MemDAO();
	TicketDAO tdao=new TicketDAO();
	
	JPanel p=new JPanel();
	JLabel name=new JLabel();
	JLabel chaos=new JLabel();
	JButton submit=new JButton("예약하기");
	
	public Reservation(Map before, String atname, String id) throws SQLException {
		
		
		this.setTitle("기구 예약");
		System.out.println("atname =  " + atname);
		AttractionDTO attract=adao.getAttract(atname);
		
		if(attract==null) {
			before.setEnabled(true);
			this.dispose();
			return;
		}
		String atId=attract.getAtId();
		mId=id; //회원 아이디
		String tpass=mdao.select(mId).getTPass();
		String tname=tdao.selectti(tpass).getTName();
		
		int count= rdao.selectatt(atId); //현재 대기인원
		if(tname.equals("vip권")) {
			count=rdao.selectvip(atId); //현재 대기인원
			System.out.println(count);
		}
		
		int vcount=0;

		ImageIcon icon = new ImageIcon(attract.getAtUrl());
		this.setSize(icon.getIconWidth()+100,icon.getIconHeight()+40); //프레임 크기를 이미지에 맞추기
		
		JLabel lb1 = new JLabel(icon, JLabel.CENTER); //가운데로 수평정렬
		this.add(lb1);

		
		p.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
        
		name.setText("기구명: "+attract.getAtName());
		p.add(name,c);
		
		
		c.gridy=1;
		chaos.setText("대기 인원 비율: "+(float)count/attract.getAtMax()*100+"%");

		p.add(chaos,c);
		
		c.gridy=2;
		p.add(submit,c);
		
		this.add("East",p);
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 화면 중앙 배치
		
		submit.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	ReservationDTO r=new ReservationDTO();
            	r.setNo(rdao.getcount(mId));
            	r.setAtId(attract.getAtId());
            	r.setMId(mId);
            	r.setTPass(tpass);
            	if(rdao.insertres(r)) {
            		JOptionPane.showMessageDialog(null, "예약되었습니다.");     
            		before.setEnabled(true);
            		dispose();
            	}
            }
        });

		
		addWindowListener (
			    new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	before.setEnabled(true);
			        }
			    }
			);
	}
}
