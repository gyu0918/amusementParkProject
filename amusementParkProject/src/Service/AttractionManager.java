package javaproject.Service;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.attribute.AclEntry.Builder;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javaproject.DAO.AttractionDAO;
import javaproject.DTO.AttractionDTO;

public class AttractionManager extends JFrame{
	AttractionDAO dao=new AttractionDAO();
	String[] col={"아이디","이름","이미지","최대인원","운영여부"};
	DefaultTableModel model=new DefaultTableModel(col,0){
        // Jtable 내용 편집 안되게
        public boolean isCellEditable(int i, int c) {
            return false;
        }
    };;
	JTable t=new JTable(model);
	
	JLabel l1=new JLabel("아이디");
	JLabel l2=new JLabel("이름");
	JLabel l3=new JLabel("이미지");
	JLabel l4=new JLabel("최대인원");
	JLabel l5=new JLabel("운영여부");
	
	JLabel id=new JLabel("아이디");
	JLabel name=new JLabel("이름");
	JTextField image=new JTextField(15);
	JTextField max=new JTextField(5);
	
	JButton update=new JButton("수정");
	
	boolean flag=true;
	ButtonGroup g_radio=new ButtonGroup();
	JRadioButton on=new JRadioButton("on");
	JRadioButton off=new JRadioButton("off");
	
	public AttractionManager(JFrame before) {
		this.setSize(500, 400);
		this.setTitle("시설관리");
		
		modelset();
		
		JScrollPane p=new JScrollPane(t);
		p.setSize(500,200);
		add(p);
		t.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
        t.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		t.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setting(e);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setting(e);
			}}
			);

		
		
		g_radio.add(on);
		g_radio.add(off);
		JPanel radio=new JPanel();
		radio.setLayout(new GridLayout());
		radio.add(on);
		radio.add(off);
		
		on.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	flag=true;
            	
            			
            }
        });
		off.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	flag=false;
            	
            }
        });
		
		JPanel p2=new JPanel();
		p2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		p2.add(l1,c);
		p2.add(l2,c);
		p2.add(l3,c);
		p2.add(l4,c);
		p2.add(l5,c);

		c.gridx = 0;
        c.gridy = 1;
        p2.add(id,c);
        c.gridx = 1;
        p2.add(name,c);
        c.gridx = 2;
        p2.add(image,c);
        c.gridx = 3;
        p2.add(max,c);
        c.gridx = 4;
        p2.add(radio,c);
        c.gridy=2;
        c.gridx = 4;
        p2.add(update,c);

        update.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	try {
            		AttractionDTO att = AttractionDTO.builder()
            				.atId(id.getText())
            				.atName(name.getText())
            				.atUrl(image.getText())
            				.atMax(Integer.parseInt(max.getText()))
            				.atOnoff(flag ? 1:0)
            				.build();
            		if(dao.updateat(att))
            			JOptionPane.showMessageDialog(null, "수정완료.");
            		else
            			JOptionPane.showMessageDialog(null, "수정실패.");  
            		modelset();
      
            	}catch (Exception e) {
            		JOptionPane.showMessageDialog(null, "정확히 입력해주세요.");  
				}
            	
            			
            }
        });
		
		add("South",p2);
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 화면 중앙 배치

		addWindowListener (
			    new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	before.setEnabled(true);
			        }
			    }
			);
	}

	protected void setting(MouseEvent e) {
		JTable t=(JTable)e.getSource();
		id.setText((String)model.getValueAt(t.getSelectedRow(), 0));
		name.setText((String)model.getValueAt(t.getSelectedRow(), 1));
		image.setText((String)model.getValueAt(t.getSelectedRow(), 2));
		max.setText((int)model.getValueAt(t.getSelectedRow(), 3)+"");
		if(model.getValueAt(t.getSelectedRow(), 4).toString().equals("true")) {
			flag=true;
			on.setSelected(true);
		}else {
			flag=false;
			off.setSelected(true);
		}
		
	}

	private void modelset() {
		model.setRowCount(0);
		List<AttractionDTO> alist =dao.selectAll();

		for(AttractionDTO a:alist) {
			model.addRow(new Object[] {
				a.getAtId(),a.getAtName(),a.getAtUrl(),a.getAtMax(),a.getAtOnoff()==0 ? false:true
			});
		}
		
	}
}
