/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.model.dao;

import br.edu.utfpr.model.Music;
import br.edu.utfpr.util.JPAUtil;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Roni
 */
public class MusicDAO extends AbstractDAO<Long, Music>{
    
    public List<Music> listComposerMusic(Long composerID){
        this.entityManager = JPAUtil.getEntityManager();

        String queryString = "SELECT DISTINCT m FROM Music m where m.composer.id = :param";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("param", composerID);

        return query.getResultList();
    }
    
}
