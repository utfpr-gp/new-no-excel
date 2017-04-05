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
import br.edu.utfpr.model.service.ComposerService;
import br.edu.utfpr.model.service.MusicService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Roni
 */
@ManagedBean
@SessionScoped
public class MusicBuyBean {
    
    private List<Composer> composerList;
    private Long composerIdSelected;
    private Long musicIdSelected;
    private Long artistIdSelected;
    /**
     * 
     * Músicas do compositor selecionado.
     * 
     */
    private List<Music> musicList;
    private List<Artist> artistList;
    
    private ComposerService composerService;
    private MusicService musicService;
    private ArtistService artistService;
    
    /**
     * 
     * Selecionados nos campos select
     * 
     */
    private Composer composer;
    private Music music;
    private Artist artist;    
    
    /**
     * Creates a new instance of MusicBuyBean
     */
    public MusicBuyBean() {
    }
    
    @PostConstruct
    public void init(){
        composerService = new ComposerService();
        musicService = new MusicService();
        artistService = new ArtistService();
    }

    public List<Composer> getComposerList() {
        return composerList;
    }

    public void setComposerList(List<Composer> composerList) {
        this.composerList = composerList;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public ComposerService getComposerService() {
        return composerService;
    }

    public void setComposerService(ComposerService composerService) {
        this.composerService = composerService;
    }

    public MusicService getMusicService() {
        return musicService;
    }

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    public ArtistService getArtistService() {
        return artistService;
    }

    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    public Composer getComposer() {
        return composer;
    }

    public void setComposer(Composer composer) {
        this.composer = composer;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }  

    public Long getComposerIdSelected() {
        return composerIdSelected;
    }

    public void setComposerIdSelected(Long composerIdSelected) {
        this.composerIdSelected = composerIdSelected;
    }

    public Long getMusicIdSelected() {
        return musicIdSelected;
    }

    public void setMusicIdSelected(Long musicIdSelected) {
        this.musicIdSelected = musicIdSelected;
    }

    public Long getArtistIdSelected() {
        return artistIdSelected;
    }

    public void setArtistIdSelected(Long artistIdSelected) {
        this.artistIdSelected = artistIdSelected;
    }
    
    /**
     * 
     * Retorna as músicas do compositor selecionado.
     * 
     * @return 
     */
    public void listCurrentComposerMusic(){              
        System.out.println("--------------------- ajax 11111111");
        musicList = musicService.listByProperty("composer.id", composerIdSelected);
    }
    
    public void listCurrentComposerMusic(AjaxBehaviorEvent e){    
        System.out.println("--------------------- ajax");
        musicList = musicService.listByProperty("composer.id", composerIdSelected);
    }
    
    public String doAction(){       
        
        return "relatorio?faces-redirect=true";
    }
    
}
