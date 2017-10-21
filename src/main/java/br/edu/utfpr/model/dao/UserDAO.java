/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.model.dao;

import br.edu.utfpr.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Filipe
 */
public class UserDAO extends AbstractDAO<Long, User> {

    EntityManager em;

    public void teste() {

        TypedQuery<String> query = em.createQuery(
                "SELECT c FROM User c", String.class);
        List<String> results = query.getResultList();
    }

}
