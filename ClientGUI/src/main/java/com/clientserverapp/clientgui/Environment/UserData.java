package com.clientserverapp.clientgui.Environment;

import com.ClientServerApp.ClientApplication.ClientWorking.ClientWorking;
import com.clientserverapp.clientgui.LanguageSetting.Languages;

import java.nio.channels.SocketChannel;

public class UserData {
    public static SocketChannel socketChannel;
    public static String name;
    public static Languages language;
    public static ClientWorking clientWorking;
}
