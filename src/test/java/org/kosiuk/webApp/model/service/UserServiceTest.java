package org.kosiuk.webApp.model.service;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kosiuk.webApp.servletPaymentsApp.controller.dto.UserRegistrationDto;
import org.kosiuk.webApp.servletPaymentsApp.exception.DaoException;
import org.kosiuk.webApp.servletPaymentsApp.exception.NotCompatibleRolesException;
import org.kosiuk.webApp.servletPaymentsApp.exception.UsernameNotUniqueException;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoConnection;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.DaoFactory;
import org.kosiuk.webApp.servletPaymentsApp.model.dao.UserDao;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.Role;
import org.kosiuk.webApp.servletPaymentsApp.model.entity.User;
import org.kosiuk.webApp.servletPaymentsApp.model.service.UserService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

import java.util.Set;


@RunWith(PowerMockRunner.class)
@PrepareForTest( { UserService.class, Logger.class, DaoFactory.class } )
public class UserServiceTest {
    UserService userService;
    Logger logMock;
    DaoFactory daoFactoryMock;

    @Before
    public void prepareTest() {
        mockStatic(Logger.class);
        logMock = mock(Logger.class);
        when(Logger.getLogger(UserService.class)).thenReturn(logMock);

        mockStatic(DaoFactory.class);
        daoFactoryMock = mock(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactoryMock);

        DaoConnection daoConnectionMock = mock(DaoConnection.class);
        when(daoFactoryMock.getConnection()).thenReturn(daoConnectionMock);

        userService = new UserService();
    }

    @Test
    public void shouldRegisterUser() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        User user = initTestUser();
        UserRegistrationDto userRegDto = initUserRegDto();

        try {
            userService.registerUser(userRegDto);
            verify(userDaoMock, times(1)).create(user);
        } catch (DaoException | UsernameNotUniqueException e) {
            Assert.fail();
        }

    }

    @Test
    public void shouldGetUserById() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.getUserById(1);
            verify(userDaoMock, times(1)).findById(1);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetUserByUserName() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.getUserByUsername("Simba");
            verify(userDaoMock, times(1)).findByUsername("Simba");
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetUserByIdAsBasicDto() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.getUserByIdAsBasicDto(0);
            verify(userDaoMock, times(0)).findByUsername("Simba");
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetAllUsersPage() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.getAllUsersPage(2);
            verify(userDaoMock, times(1)).findAllPageable(2);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldGetNumberOfRecords() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.getNumberOfRecords();
            verify(userDaoMock, times(1)).getNumberOfRecords();
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldChangeRoles() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        Set<Role> userRoleSet = Set.of(Role.USER);

        try {
            userService.changeRoles(1, userRoleSet);
            verify(userDaoMock, times(1)).changeRoles(1, userRoleSet);
        } catch (DaoException | NotCompatibleRolesException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldIgnoreIncompatibleRolesChanging() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        Set<Role> incompatibleRolesSet = Set.of(Role.USER, Role.ADMIN);

        try {
            userService.changeRoles(1, incompatibleRolesSet);
            verify(userDaoMock, times(0)).changeRoles(1, incompatibleRolesSet);
        } catch (DaoException | NotCompatibleRolesException e) {
            Assert.assertSame(e.getClass(), NotCompatibleRolesException.class);
        }
    }

    @Test
    public void shouldBanUser() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.banUser(1);
            verify(userDaoMock, times(1)).banUser(1);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldUnbanUser() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.unbanUser(1);
            verify(userDaoMock, times(1)).unbanUser(1);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldDeleteUser() {
        UserDao userDaoMock = mock(UserDao.class);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);

        try {
            userService.deleteUser(1);
            verify(userDaoMock, times(1)).delete(1);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldFilterIncompatibleRoles() {
        Assert.assertTrue(!userService.isRolesCompatible(Set.of(Role.USER, Role.ADMIN)));
        Assert.assertTrue(userService.isRolesCompatible(Set.of(Role.USER)));
        Assert.assertTrue(userService.isRolesCompatible(Set.of(Role.ADMIN)));
    }

    private static User initTestUser() {
        return User.builder().
                initRegistrationDetails("Simba", "lion@gmail.com", "lion-king")
                .initFlagsDefault()
                .roles(Role.USER)
                .build();
    }

    private static UserRegistrationDto initUserRegDto() {
        return new UserRegistrationDto("Simba", "lion@gmail.com", "lion-king");
    }

}
