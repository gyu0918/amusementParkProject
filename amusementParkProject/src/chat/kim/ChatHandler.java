package javaproject.chat.kim;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ChatHandler extends Thread {

    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;

    private List <ChatHandler> list;
    private boolean[] flag;
    private boolean[] checkAdmin;
    private  ChatDTO dto=null;

    //생성자
    public ChatHandler(Socket socketTemp, List <ChatHandler> list, boolean[] flag, boolean[] checkAdmin) throws IOException {

        this.socket = socketTemp;
        this.list = list;
        this.flag = flag;
        this.checkAdmin = checkAdmin;

        writer = new ObjectOutputStream(socketTemp.getOutputStream());
        reader = new ObjectInputStream(socketTemp.getInputStream());
        //순서가 뒤바뀌면 값을 입력받지 못하는 상황이 벌어지기 때문에 반드시 writer부터 생성시켜주어야 함!!!!!!

    }
    public void run(){
        String nickName;

        try{
            while(true){


                nickName = dto.getNickName();

                //System.out.println("배열 크기:"+ar.length);
                //사용자가 접속을 끊었을 경우. 프로그램을 끝내서는 안되고 남은 사용자들에게 퇴장메세지를 보내줘야 한다.
                if(dto.getCommand()==Info.EXIT){
                    ChatDTO sendDto = new ChatDTO();
                    //나가려고 exit를 보낸 클라이언트에게 답변 보내기
                    sendDto.setCommand(Info.EXIT);
                    writer.writeObject(sendDto);
                    writer.flush();
                    System.out.println(" 임시로 check  = " + dto.getCheckFlag());
                    if (dto.getCheckFlag()){
                        int index = dto.getFlagIndex();
                        checkAdmin[index] = false;
                    }

                    reader.close();
                    writer.close();
                    socket.close();
                    //남아있는 클라이언트에게 퇴장메세지 보내기
                    list.remove(this);

                    sendDto.setCommand(Info.SEND);
                    sendDto.setMessage(nickName+"님 퇴장하였습니다");
                    broadcast(sendDto);
                    break;
                } else if(dto.getCommand()==Info.JOIN){
                    //모든 사용자에게 메세지 보내기
                    //nickName = dto.getNickName();
                    //모든 클라이언트에게 입장 메세지를 보내야 함
                    ChatDTO sendDto = new ChatDTO();
                    sendDto.setCommand(Info.SEND);
                    sendDto.setMessage(nickName+"님 입장하였습니다");
                    broadcast(sendDto);
                } else if(dto.getCommand()==Info.SEND){
                    ChatDTO sendDto = new ChatDTO();
                    sendDto.setCommand(Info.SEND);
                    sendDto.setMessage("["+nickName+"]"+ dto.getMessage());
                    broadcast(sendDto);
                }else if (dto.getCommand()==Info.GET_FLAG){
                    System.out.println(" GET_FLAG 부분들어왔는지 체크 ");
                    //클라이언트 요청 받으면 flag 전달
                    ChatDTO sendDto = new ChatDTO();
//                    sendDto.setCommand(Info.SEND);
                    System.out.println(" secondDto.flag이전버전 " + this.flag[0]);
                    sendDto.setFlag(this.flag);
                    sendDto.setCheckAdmin(this.checkAdmin);
                    boolean[] check = sendDto.getFlag();
                    System.out.println("sendDto.flag = " + check[0]);
                    writer.writeObject(sendDto);   //클라이언트로 쏴주기
                    writer.flush();
                }else if (dto.getCommand() == Info.SET_FLAG){
                    //클라이언트 요청받으면 플래그 바꿔주기
                    System.out.println(" set 들어왔느지 체크 dto.getCheckFlag = " + dto.getCheckFlag());
                    if (dto.getCheckFlag()){
                        this.checkAdmin[dto.getCheckAdminIndex()] = true;
                        System.out.println(" 클라이언트에서 고친 checkAdmin= " + checkAdmin[dto.getFlagIndex()]);
                    }else {
                        this.checkAdmin[dto.getCheckAdminIndex()] = false;
                    }

                }
                dto = (ChatDTO) reader.readObject();
            }

        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    // 전체 메세지 보내주기
    public void broadcast(ChatDTO sendDto) throws IOException {

        for(ChatHandler handler: list){
            handler.writer.writeObject(sendDto); //핸들러 안의 writer에 값을 보내기
            handler.writer.flush();  //핸들러 안의 writer 값 비워주기
        }
    }


    public void getFlag() {
        try {
            dto = (ChatDTO) reader.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (dto.getCommand() == Info.SET_FLAG){
            //클라이언트 요청받으면 플래그 바꿔주기
            System.out.println(" set 들어왔느지 체크 dto.getCheckFlag = " + dto.getCheckFlag());
            if (dto.getCheckFlag()){
                this.checkAdmin[dto.getCheckAdminIndex()] = true;
                System.out.println(" 클라이언트에서 고친 checkAdmin= " + checkAdmin[dto.getFlagIndex()]);
            }else {
                this.checkAdmin[dto.getCheckAdminIndex()] = false;
            }

            try {
                dto = (ChatDTO) reader.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
