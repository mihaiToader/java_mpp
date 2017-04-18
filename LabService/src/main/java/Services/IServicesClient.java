package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Mihai on 03.04.2017.
 */
public interface IServicesClient extends Remote{

    void initializeMatchObservableList() throws RemoteException;
}
