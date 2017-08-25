/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.jobs;

import java.util.TimerTask;

/**
 *
 * @author Luiz
 */
class MyTask extends TimerTask {

    public MyTask() {
        //Some stuffs
    }

    @Override
    public void run() {
        System.out.println("Hi see you after 10 seconds");
    }

}
