package com.seuprojeto.projeto_web.requests;

import com.seuprojeto.projeto_web.enums.Gravity;

public class SinisterRequest {

    private String identification;
    private Gravity gravity;

    public SinisterRequest() {}

    public SinisterRequest(String identification, Gravity gravity) {
        this.identification = identification;
        this.gravity = gravity;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Gravity getGravity() {
        return gravity;
    }
    
    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

}
