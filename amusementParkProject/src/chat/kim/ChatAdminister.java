package javaproject.chat.kim;

import javaproject.DAO.AdminDAO;
import javaproject.DTO.AdminDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatAdminister {
    private AdminDAO adminDAO;
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ChatAdminister(String id) throws IOException, ClassNotFoundException {
        adminDAO = new AdminDAO();
        AdminDTO adminDTO = adminDAO.select(id);
        int port = Integer.parseInt(adminDTO.getAName());

        // 서버와 연결된 소켓을 한 번만 열어두고 재사용
        try {
            socket = new Socket("192.168.0.28", port);  // 소켓을 한 번만 열기
            writer = new ObjectOutputStream(socket.getOutputStream());
            try{
                reader = new ObjectInputStream(socket.getInputStream());
            }catch (Exception e){
                return ;
            }


            // 포트별로 관리자 설정
            switch (port) {
                case 1004:
                    setFlagFromClient(writer, 0);
                    break;
                case 1005:
                    setFlagFromClient(writer, 1);
                    break;
                case 1006:
                    setFlagFromClient(writer, 2);
                    break;
                default:
                    return;
            }

            new ChatClient().service(socket,writer,reader,port, true, id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    // 서버에서 플래그 정보를 받는 메서드
//    public ChatDTO getFlagFromServer(ObjectInputStream reader,ObjectOutputStream writer ) throws IOException, ClassNotFoundException {
//        // 서버로 flag 요청
//        ChatDTO dto = new ChatDTO();
//        dto.setCommand(Info.GET_FLAG);
//        writer.writeObject(dto); // 이 부분을 추가하여 서버에 GET_FLAG 요청을 보냅니다.
//        writer.flush();
//
//        // 서버로부터 하나의 객체를 받습니다.
//        ChatDTO flagDTO = (ChatDTO) reader.readObject();  // 하나의 객체만 받도록 수정
//        return flagDTO;
//    }

    // 서버로 플래그를 보내는 메서드
    public void setFlagFromClient(ObjectOutputStream writer, int checkAdminIndex) throws IOException {
        // 서버로 플래그 설정 요청
        System.out.println(" fffffffffffffffffffffffffffffff");
        ChatDTO dto = new ChatDTO();
        dto.setCommand(Info.SET_FLAG);
        dto.setCheckAdminIndex(checkAdminIndex);
        dto.setCheckFlag(true);
        writer.writeObject(dto);
        writer.flush();
    }
}
