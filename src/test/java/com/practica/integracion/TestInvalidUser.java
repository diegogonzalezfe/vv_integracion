package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {

    @Mock
    private static AuthDAO mockAuthDao;
    @Mock
    private static GenericDAO mockGenericDao;

    @Test
    public void testStartRemoteSystemWithInValidUserAndSystem() throws Exception {
        User invalidUser = new User("1", "Ana", "Lopez", "Madrid", null);
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

        String invalidId = "12345";
        when(mockGenericDao.getSomeData(null, "where id=" + invalidId)).thenThrow(OperationNotSupportedException.class);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertThrows(SystemManagerException.class, () -> manager.startRemoteSystem(invalidUser.getId(), invalidId));

        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(null, "where id=" + invalidId);
    }

    @Test
    public void testStopRemoteSystemWithInValidUserAndSystem() throws Exception {
        User invalidUser = new User("1", "Ana", "Lopez", "Madrid", null);
        when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

        String invalidId = "12345";
        when(mockGenericDao.getSomeData(null, "where id=" + invalidId)).thenThrow(OperationNotSupportedException.class);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertThrows(SystemManagerException.class, () -> manager.stopRemoteSystem(invalidUser.getId(), invalidId));

        ordered.verify(mockAuthDao).getAuthData(invalidUser.getId());
        ordered.verify(mockGenericDao).getSomeData(null, "where id=" + invalidId);
    }

    @Test
    public void testAddRemoteSystemWithInvalidUserAndSystem() throws Exception {
        User validUser = new User("1", "Ana", "Lopez", "Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
        when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

        Object validRemote = "Hola";
        when(mockGenericDao.updateSomeData(validUser, validRemote)).thenReturn(false);

        InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
        SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

        assertThrows(SystemManagerException.class, () -> manager.addRemoteSystem(validUser.getId(), validRemote));

        ordered.verify(mockAuthDao).getAuthData(validUser.getId());
        ordered.verify(mockGenericDao).updateSomeData(validUser, validRemote);
    }
}
