/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.model.service;

import br.edu.utfpr.model.Music;
import br.edu.utfpr.model.dao.ComposerDAO;
import br.edu.utfpr.model.dao.MusicDAO;
import br.edu.utfpr.util.JPAUtil;
import java.util.List;

/**
 *
 * @author Roni
 */
public class MusicService extends AbstractService<Long, Music>{

    public MusicService() {
        dao = new MusicDAO();
    }
    
     public List<Music> listComposerMusic(Long composerID){
         List<Music> entity = null;
        try {
            JPAUtil.beginTransaction();
            entity = ((MusicDAO)dao).listComposerMusic(composerID);
            JPAUtil.commit();
        } catch (Exception e) {
            JPAUtil.rollBack();            
        } finally {
            JPAUtil.closeEntityManager();
        }        
        return entity;
     }
    
    
}
