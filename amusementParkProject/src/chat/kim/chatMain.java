package javaproject.chat.kim;

import java.io.IOException;

public class chatMain {
    public static void main(String[] args) throws IOException {
        //서버는 처음 맵부분에 들어가기 시작할떄 만들어야 한다.
        new ChatServer();

    }
}
