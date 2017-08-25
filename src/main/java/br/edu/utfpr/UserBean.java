/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr;

import br.edu.utfpr.model.User;

import br.edu.utfpr.model.service.UserService;
import br.edu.utfpr.util.MessageUtil;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cabrito
 */
@ManagedBean
@RequestScoped
public class UserBean {

    private User user;
    private List<User> userList;
    private UserService userService;
    private Long cityId;

    @ManagedProperty(value = "#{userRoleBean}")
    private UserRoleBean userRoleBean;

    public UserBean() {
    }

    public UserBean(User user, List<User> userList, UserService userService) {
        this.user = user;
        this.userList = userList;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        user = new User();
        userList = new ArrayList<>();
        userService = new UserService();
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

    public UserRoleBean getUserRoleBean() {
        return userRoleBean;
    }

    public void setUserRoleBean(UserRoleBean userRoleBean) {
        this.userRoleBean = userRoleBean;
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
        if (user.getId() == null) {
            String passwordMd5 = gerarHashMD5(user.getPassword());
            user.setPassword(passwordMd5);
            if (userService.save(user)) {
                System.out.println("Ate aqui chegou");
                userRoleBean.getUserRole().setLogin(user.getLogin());
                userRoleBean.getUserRole().setRole("PENDING-USER");
                userRoleBean.persist();
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
