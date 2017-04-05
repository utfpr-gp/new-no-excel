/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr;

import br.edu.utfpr.model.Artist;
import br.edu.utfpr.model.Composer;
import br.edu.utfpr.model.Music;
import br.edu.utfpr.model.service.ArtistService;
import br.edu.utfpr.util.MessageUtil;
import java.util.ArrayList;
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
public class ArtistBean {
    
    private Artist artist;
    private List<Artist> artistList;
    private ArtistService artistService;

    /**
     * Creates a new instance of ArtistBean
     */
    public ArtistBean() {
    }
    
    @PostConstruct
    public void init(){
        artist = new Artist();
        artistList = new ArrayList<>();
        artistService = new ArtistService();        
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }
        
    public void edit(Artist artist){
        this.artist = artist;        
    }
    
    public void delete(Artist artist){
        boolean isSuccess = artistService.delete(artist);        
        if(isSuccess){
            this.artistList.remove(artist);
            MessageUtil.showMessage("Removido com sucesso", "", FacesMessage.SEVERITY_INFO);
        }
        else{
            MessageUtil.showMessage("Falha na remoção", "", FacesMessage.SEVERITY_ERROR);
        }
        this.artist = new Artist();        
    }
    
    public void persist(){
        
        /*
         * 
         * Verifica se o objeto tem id, se tiver, se trata de edição.
         * Caso contrário, o objeto ainda não existe no banco de dados, se tratando de uma inserção
         * 
         */
        if(artist.getId() == null){
            if(artistService.save(artist)){              
                this.artistList.add(artist);
                MessageUtil.showMessage("Persistido com sucesso", "", FacesMessage.SEVERITY_INFO);
            }
            else{
                MessageUtil.showMessage("Falha ao persistir", "", FacesMessage.SEVERITY_ERROR);
            }
        }
        else{
            if(artistService.update(artist)){
                MessageUtil.showMessage("Alterado com sucesso", "", FacesMessage.SEVERITY_INFO);
            }
            else{
                MessageUtil.showMessage("Falha na alteração", "", FacesMessage.SEVERITY_ERROR);
            }
        }        
        
        this.artist = new Artist();        
    }
    
    public List<Artist> findAll(){
        return artistList = artistService.findAll();
    }
}
