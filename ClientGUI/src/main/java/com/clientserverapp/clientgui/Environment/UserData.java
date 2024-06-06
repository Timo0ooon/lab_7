package com.clientserverapp.clientgui.Environment;

import com.ClientServerApp.ClientApplication.ClientWorking.ClientWorking;
import com.ClientServerApp.Model.HumanBeing.HumanBeing;
import com.clientserverapp.clientgui.Bundles.Languages;
import com.clientserverapp.clientgui.util.Localizer;
import javafx.scene.control.TableView;

import java.nio.channels.SocketChannel;

public class UserData {
    public static SocketChannel socketChannel;
    public static String name;
    public static Languages language;
    public static ClientWorking clientWorking;
    public static Localizer localizer;
    public static TableView<HumanBeing> mainTable;
}
