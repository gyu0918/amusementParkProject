package javaproject.chat.kim;

import java.io.Serializable;

enum Info {
    JOIN, EXIT, SEND,GET_FLAG,SET_FLAG;
}

class ChatDTO implements Serializable {
    private String nickName;
    private String message;
    private Info command;
    private boolean[] flag;
    private boolean[] checkAdmin;
    private int flagIndex;
    private int checkAdminIndex;
    private boolean checkFlag;

    public boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public int getFlagIndex() {
        return flagIndex;
    }

    public void setFlagIndex(int flagIndex) {
        this.flagIndex = flagIndex;
    }

    public int getCheckAdminIndex() {
        return checkAdminIndex;
    }

    public void setCheckAdminIndex(int checkAdminIndex) {
        this.checkAdminIndex = checkAdminIndex;
    }

    public boolean[] getFlag() {
        return flag;
    }

    public void setFlag(boolean[] flag) {
        this.flag = flag;
    }

    public boolean[] getCheckAdmin() {
        return checkAdmin;
    }

    public void setCheckAdmin(boolean[] checkAdmin) {
        this.checkAdmin = checkAdmin;
    }

    public String getNickName(){
        return nickName;
    }
    public Info getCommand(){
        return command;
    }
    public String getMessage(){
        return message;
    }
    public void setNickName(String nickName){
        this.nickName= nickName;
    }
    public void setCommand(Info command){
        this.command= command;
    }
    public void setMessage(String message){
        this.message= message;
    }
}
