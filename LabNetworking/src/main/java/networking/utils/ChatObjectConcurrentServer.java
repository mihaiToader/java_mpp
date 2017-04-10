package networking.utils;


import Services.IServerServices;
import Services.IServicesUsers;
import networking.objectprotocol.ChatClientObjectWorker;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: grigo
 * Date: Mar 18, 2009
 * Time: 12:17:49 PM
 */
public class ChatObjectConcurrentServer extends AbsConcurrentServer {
    private IServerServices serverApp;
    public ChatObjectConcurrentServer(int port, IServerServices serverApp) {
        super(port);
        this.serverApp = serverApp;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ChatClientObjectWorker worker=new ChatClientObjectWorker(serverApp, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
