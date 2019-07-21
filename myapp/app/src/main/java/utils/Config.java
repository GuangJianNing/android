package utils;

public class Config {

    public static final String URL = "http://119.3.184.10:8000/Server/OrderServlet";

    public static final String ACTION = "action";
    public static final String ACTION_FIND_ALL = "selectall";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_INSERT = "insert";

    //弹出菜单子项id定义
    public static final int MENU_DELETE = 0;
    public static final int MENU_MODIFY = 1;

    //handler消息状态码定义
    public  static  final int MESSAGE_INIT=0;
    public  static  final int MESSAGE_DELETE_OK=1;
    public  static  final int MESSAGE_DELETE_FAIL=2;
    public  static  final int MESSAGE_UPDATE_OK=3;
    public  static  final int MESSAGE_UPDATE_FAIL=4;




}
