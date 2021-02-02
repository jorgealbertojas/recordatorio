package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("senha")
    @Expose
    private String senha;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("perfil")
    @Expose
    private String perfil;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
