package com.example.jorgealberto.researchmobile.comunication;

import com.example.jorgealberto.researchmobile.model.Aluno;
import com.example.jorgealberto.researchmobile.model.Controle_fim;
import com.example.jorgealberto.researchmobile.model.Controle_inicio;
import com.example.jorgealberto.researchmobile.model.Escola;
import com.example.jorgealberto.researchmobile.model.Pergunta;
import com.example.jorgealberto.researchmobile.model.Pesquisa;
import com.example.jorgealberto.researchmobile.model.Resposta;
import com.example.jorgealberto.researchmobile.model.Turma;
import com.example.jorgealberto.researchmobile.model.Usuario;
import com.example.jorgealberto.researchmobile.model.configuracao;
import com.example.jorgealberto.researchmobile.model.opcao;
import com.example.jorgealberto.researchmobile.model.pesquisa_usuario;

/**
 * Created by sspbr on 11/05/2016.
 */
public class ClasseADO {
    public opcao nOpcao;
    public Pergunta nPergunta;
    public configuracao nconfiguracao;
    public Resposta nResposta;
    public Aluno nAluno;
    public Escola nEscola;
    public Turma nTurma;
    public Pesquisa nPesquisa;
    public Controle_inicio nControle_inicio;
    public Controle_fim nControle_fim;
    public Usuario nUsuario;
    public pesquisa_usuario npesquisa_usuario;



    public Object IntanciarClasse(String Nome){

        Object nObjecto = null;

        if (Nome.equals("opcao") == true) {
            nOpcao = new opcao();
            nObjecto = nOpcao;
        }
        else if (Nome.equals("Pergunta") == true) {
            nPergunta = new Pergunta();
            nObjecto = nPergunta;
        }
        else if (Nome.equals("configuracao") == true) {
            nconfiguracao = new configuracao();
            nObjecto = nconfiguracao;
        }
        else if (Nome.equals("Resposta") == true) {
            nResposta = new Resposta();
            nObjecto = nResposta;
        }
        else if (Nome.equals("Aluno") == true) {
            nAluno = new Aluno();
            nObjecto = nAluno;
        }
        else if (Nome.equals("Escola") == true) {
            nEscola = new Escola();
            nObjecto = nEscola;
        }
        else if (Nome.equals("Turma") == true) {
            nTurma = new Turma();
            nObjecto = nTurma;
        }
        else if (Nome.equals("Pesquisa") == true) {
            nPesquisa = new Pesquisa();
            nObjecto = nPesquisa;
        }
        else if (Nome.equals("Controle_inicio") == true) {
            nControle_inicio = new Controle_inicio();
            nObjecto = nControle_inicio;
        }
        else if (Nome.equals("Controle_fim") == true) {
            nControle_fim = new Controle_fim();
            nObjecto = nControle_fim;
        }
        else if (Nome.equals("Usuario") == true) {
            nUsuario = new Usuario();
            nObjecto = nUsuario;
        }
        else if (Nome.equals("pesquisa_usuario") == true) {
            npesquisa_usuario = new pesquisa_usuario();
            nObjecto = npesquisa_usuario;
        }
        return nObjecto;


    }

}
