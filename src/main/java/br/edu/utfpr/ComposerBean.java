/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr;

import br.edu.utfpr.model.Composer;
import br.edu.utfpr.model.service.ComposerService;
import br.edu.utfpr.util.MessageUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Roni
 */
@ManagedBean
@SessionScoped
public class ComposerBean {
    
    private Composer composer;
    private List<Composer> composerList;
    private ComposerService composerService;
        
    public ComposerBean() {         
    }
    
    @PostConstruct
    public void init(){
        composer = new Composer();
        composerList = new ArrayList<>();
        composerService = new ComposerService();
    }

    public Composer getComposer() {
        return composer;
    }

    public void setComposer(Composer composer) {
        this.composer = composer;
    }   

    public List<Composer> getComposerList() {
        return composerList;
    }

    public void setComposerList(List<Composer> composerList) {
        this.composerList = composerList;
    }   

    public ComposerService getComposerService() {
        return composerService;
    }
    
    public void edit(Composer composer){
        this.composer = composer;        
    }
    
    public void delete(Composer composer){        
        boolean isSuccess = composerService.delete(composer);        
        if(isSuccess){
            this.composerList.remove(composer);
            MessageUtil.showMessage("Removido com sucesso", "", FacesMessage.SEVERITY_INFO);
        }
        this.composer = new Composer();
    }
    
    public void persist(){        
        
        /*
         * 
         * Verifica se o objeto tem id, se tiver, se trata de edição.
         * Caso contrário, o objeto ainda não existe no banco de dados, se tratando de uma inserção
         * 
         */
        if(composer.getId() == null){
            if(composerService.save(composer)){              
                this.composerList.add(composer);
                MessageUtil.showMessage("Persistido com sucesso", "", FacesMessage.SEVERITY_INFO);
            }
        }
        else{
            composerService.update(composer);
        }        
        
        this.composer = new Composer();
    }
    
    public List<Composer> findAll(){
        return composerService.findAll();
    }
}
