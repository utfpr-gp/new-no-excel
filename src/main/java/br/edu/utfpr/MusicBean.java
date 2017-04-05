/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr;

import br.edu.utfpr.model.Composer;
import br.edu.utfpr.model.Music;
import br.edu.utfpr.model.service.ComposerService;
import br.edu.utfpr.model.service.MusicService;
import br.edu.utfpr.util.MessageUtil;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class MusicBean {

    private Long composerId;
    private Music music;
    private List<Music> musicList;

    private transient MusicService musicService;
    private transient ComposerService composerService;

    /**
     * Creates a new instance of MusicBean
     */
    public MusicBean() {
    }

    @PostConstruct
    public void init() {
        this.musicService = new MusicService();
        this.composerService = new ComposerService();
        this.music = new Music();
        this.musicList = new ArrayList<>();
    }

    public Long getComposerId() {
        return composerId;
    }

    public void setComposerId(Long composerId) {
        this.composerId = composerId;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public void edit(Music music) {
        this.music = music;
    }

    public void delete(Music music) {
        boolean isSuccess = musicService.delete(music);
        if (isSuccess) {
            this.musicList.remove(music);
            MessageUtil.showMessage("Removido com sucesso", "", FacesMessage.SEVERITY_INFO);
        } else {
            MessageUtil.showMessage("Falha na remoção", "", FacesMessage.SEVERITY_ERROR);
        }
        this.music = new Music();
    }

    public void persist() {

        Composer composer = composerService.getById(composerId);
        music.setComposer(composer);

        /*
         * 
         * Verifica se o objeto tem id, se tiver, se trata de edição.
         * Caso contrário, o objeto ainda não existe no banco de dados, se tratando de uma inserção
         * 
         */
        if (music.getId() == null) {
            if (musicService.save(music)) {
                this.musicList.add(music);
                
                //salva de forma bi-direcional
                /*
                composer = composerService.getById(composerId);
                music = musicService.getById(music.getId());
                composer.getMusicList().add(music);
                composerService.save(composer);
                */
                MessageUtil.showMessage("Persistido com sucesso", "", FacesMessage.SEVERITY_INFO);
            } else {
                MessageUtil.showMessage("Falha ao persistir", "", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            if (composerService.update(composer)) {
                MessageUtil.showMessage("Alterado com sucesso", "", FacesMessage.SEVERITY_INFO);
            } else {
                MessageUtil.showMessage("Falha na alteração", "", FacesMessage.SEVERITY_ERROR);
            }
        }

        this.music = new Music();
    }

    public List<Music> findAll() {
        return musicList = musicService.findAll();
    }

}
