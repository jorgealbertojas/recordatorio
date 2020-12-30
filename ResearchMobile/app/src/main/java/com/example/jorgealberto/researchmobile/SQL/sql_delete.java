package com.example.jorgealberto.researchmobile.SQL;

public class sql_delete {
	
	public static String DEL_TODOS_RESPOSTA_igual = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? ";

	public static String DEL_TODOS_RESPOSTA_ALUNO = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? ";
	
	public static String DEL_TODOS_CONTROLE_INICIO = " DELETE FROM CONTROLE_INICIO WHERE ID_ALUNO = ? ";
	
	public static String DEL_TODOS_CONTROLE_FIM = " DELETE FROM CONTROLE_FIM WHERE ID_ALUNO = ? ";
	
	public static String DEL_TODOS_CONTROLE_INICIO_DATA = " DELETE FROM CONTROLE_INICIO WHERE DATA_INICIO = ? ";
	
	public static String DEL_TODOS_CONTROLE_FIM_DATA = " DELETE FROM CONTROLE_FIM WHERE DATA_FIM = ? ";
	
	public static String DEL_SALTO = "DELETE FROM SALTO WHERE SALTO_PARA = ? ";
	
	public static String DEL_SALTO_PERGUNTA = "DELETE FROM SALTO WHERE PERGUNTA = ? ";
	
	public static String DEL_TODOS_PERGUNTAS = "DELETE FROM PERGUNTAS ";

	public static String DEL_TODOS_OPCAO = "DELETE FROM OPCAO ";

	public static String DEL_ALIMENTO = " DELETE FROM ALIMENTO WHERE ID_ALUNO = ? AND ID = ?   ";


	public static String DEL_ALIMENTO_TUDO = " DELETE FROM ALIMENTO   ";


	public static String DEL_TODOS_RESPOSTA = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ?   ";


	public static String DEL_TODOS_RESPOSTA_SUBPERGUNTA = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO_PESSOA = ?  ";

	public static String DEL_TODOS_RESPOSTA_SUBPERGUNTA_nova = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_OPCAO_PESSOA = ?  ";


	public static String DEL_TODOS_RESPOSTA_ID_OPCAO = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ? ";

	public static String DEL_TODOS_RESPOSTA_ID_OPCAO_nome = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ? AND VALOR = ? ";

	public static String DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ? AND (ID_ALIMENTO = ? OR ID_ALIMENTO ISNULL)";

	public static String DEL_TODOS_RESPOSTA_ID_OPCAO_SUBPERGUNTA = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ? AND ID_OPCAO_PESSOA = ? ";

	public static String DEL_TODOS_RESPOSTA_MAIOR = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND (ID_PERGUNTA > ? AND ID_PERGUNTA > ? AND ID_PERGUNTA < ?) AND ID_OPCAO_PESSOA = 0  ";

	public static String DEL_SALTO_TODOS = " DELETE FROM SALTO ";
	
	public static String DEL_PERGUNTAS_TODOS = " DELETE FROM PERGUNTAS ";

	public static String DEL_OPCAO_TODOS = " DELETE FROM OPCAO ";
	public static String DEL_GRUPOS_ALIMENTOS = " DELETE FROM GRUPOS_ALIMENTOS ";
	public static String DEL_MODO_PREPARACAO = " DELETE FROM MODO_PREPARACAO ";
	public static String DEL_MEDIDAS_CASEIRASS = " DELETE FROM MEDIDAS_CASEIRAS ";
	public static String DEL_ADICOES = " DELETE FROM ADICOES ";

	public static String DEL_RESPOSTA_TODOS = " DELETE FROM RESPOSTA ";

	public static String DEL_BLOCO_TODOS = " DELETE FROM BLOCO ";

	public static String DEL_ALUNO_TODOS = " DELETE FROM ALUNO ";

	public static String DEL_CONTROLE_INICIO = " DELETE FROM CONTROLE_INICIO ";

	public static String DEL_CONTROLE_FIM = " DELETE FROM CONTROLE_FIM ";
	
	public static String DEL_TODOS_RESPOSTA_OPCAO = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ?  ";

	public static String DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA = " DELETE FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? AND ID_OPCAO = ? AND ID_OPCAO_PESSOA = ?  ";

}
