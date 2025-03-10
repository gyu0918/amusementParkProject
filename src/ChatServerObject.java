import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class ChatServerObject {
    private ServerSocket serverSocket;
    private List<ChatHandlerObject> chatHandlerObjectList;

    public ChatServerObject(){
        try{
            serverSocket = new ServerSocket(9500);
            System.out.println(" 서버 준비 완료");
            chatHandlerObjectList = new ArrayList<>();

            while (true){
                Socket socket = serverSocket.accept();
                ChatHandlerObject handler = new ChatHandlerObject(socket, chatHandlerObjectList);
                handler.start();    //스레드 시작
                chatHandlerObjectList.add(handler); // 이 리스트의 개수가 클라이언트의 숫자의미
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new ChatServerObject();
    }

}
