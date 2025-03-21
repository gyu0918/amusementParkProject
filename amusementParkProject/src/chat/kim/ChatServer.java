package javaproject.chat.kim;

import java.io.IOException;
import java.net.ServerSocket;


public class ChatServer {
   private ServerSocket[] serverSocket;
   private boolean[] flagList = {true,true,true};    //고객센터 채팅방 상태 확인용
   private boolean[] checkAdmin = {false,false,false};  //관리자가 들어왔는지 안들어왔는지 확인용

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket[3];
        int port = 1004;
        for (int i = 0; i < 3; i++) {
            serverSocket[i] = new ServerSocket(port);
            ServerTool serverTool = new ServerTool(serverSocket[i],flagList,i,checkAdmin);
            serverTool.start();
            port++;
        }
    }


}
