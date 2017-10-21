/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr;

import br.edu.utfpr.model.User;
import br.edu.utfpr.model.UserRole;
import br.edu.utfpr.model.dao.UserDAO;
import br.edu.utfpr.model.service.UserRoleService;
import java.util.Calendar;
import br.edu.utfpr.model.service.UserService;
import static br.edu.utfpr.util.JPAUtil.getEntityManager;
import br.edu.utfpr.util.MessageUtil;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 *
 * @author cabrito
 */
@ManagedBean
@RequestScoped
@PersistenceUnit
public class UserBean {

    private User user;
    private List<User> userList;
    private UserService userService;
    private UserRoleService userRoleService;
    private Long cityId;

//    @ManagedProperty(value = "#{userRoleBean}")
//    private UserRoleBean userRoleBean;
    public UserBean() {
    }

    @PostConstruct
    public void init() {
        user = new User();
        userList = new ArrayList<>();
        userService = new UserService();
        userRoleService = new UserRoleService();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void edit(User user) {
        this.user = user;
    }

    public void delete(User user) {
        boolean isSuccess = userService.delete(user);
        if (isSuccess) {
            this.userList.remove(user);
            MessageUtil.showMessage("Removido com sucesso", "", FacesMessage.SEVERITY_INFO);
        } else {
            MessageUtil.showMessage("Falha na remoção", "", FacesMessage.SEVERITY_ERROR);
        }
        this.user = new User();
    }

    public void persist() {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        System.out.println("TIMEEEE");

        System.out.println("PASSOUAQUI");
        long time = cal.getTimeInMillis();
        System.out.println("PASSOU");
        if (userService.getByProperty("email", user.getEmail()) != null) {
            MessageUtil.showMessage("Erro ao cadastrar, email ja informado", "", FacesMessage.SEVERITY_ERROR);
        } else if (userService.getByProperty("login", user.getLogin()) != null) {
            MessageUtil.showMessage("Erro ao cadastrar, cpf ja informado", "", FacesMessage.SEVERITY_ERROR);
        } else if (user.getId() == null) {
            String passwordMd5 = gerarHashMD5(user.getPassword());
            user.setPassword(passwordMd5);
            UserRole userRole = new UserRole(user.getLogin(), UserRole.USER_PENDING);
            //user.getRoles().add(userRole);
            user.setTime(time);

            //TODO Cadastrar o user e role em apenas uma transaçao - criar um novo método save em algum service
            if (userService.save(user) && userRoleService.save(userRole)) {
                System.out.println("Ate aqui chegou");
                MessageUtil.showMessage("Cadastrado com sucesso", "", FacesMessage.SEVERITY_INFO);
            } else {
                MessageUtil.showMessage("Falha ao cadastrar", "", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            if (userService.update(user)) {
                MessageUtil.showMessage("Alterado com sucesso", "", FacesMessage.SEVERITY_INFO);
            } else {
                MessageUtil.showMessage("Falha na alteração", "", FacesMessage.SEVERITY_ERROR);
            }

        }
        //tirei o this abaixo
        user = new User();
    }

    public List<User> findAll() {

        return userList = userService.findAll();

    }

    public String gerarHashMD5(String passwordRaw) {
        byte[] b;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.reset();
            b = md.digest(passwordRaw.getBytes());

            return new BigInteger(1, b).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

}
