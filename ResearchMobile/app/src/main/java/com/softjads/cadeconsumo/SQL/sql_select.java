package com.softjads.cadeconsumo.SQL;

import com.softjads.cadeconsumo.util.VariavelAPI;

public class sql_select {


	public static String GET_completo = " select id_aluno from resposta where id_pergunta = ? and id_aluno = ? ";
	public static String get_contapergunta = " select max(id_pergunta) from PERGUNTAS ";
	public static String getPersonalizacao_opcao_opcao = " SELECT O.ID_OPCAO, O.OPCAO, O.ID_PERGUNTA, O.ORDENA, O.VALOR  FROM OPCAO O WHERE O.ID_PERGUNTA = ? and O.ID_OPCAO = ? ";
	public static String GET_tem_controle_inicio = " SELECT ID_USUARIO, NM_USUARIO, LONGITUDE, LATITUDE, DATA_INICIO, INICIO  FROM CONTROLE_INICIO WHERE ID_aluno = ?";
	public static String get_todos_alunos = " select distinct id_aluno from resposta ";
	public static String GET_tem_controle_fim= " SELECT * FROM CONTROLE_FIM WHERE ID_aluno = ? ";
	public static String GET_CONTROLE_INICIO = " SELECT ID_ALUNO ID_ALUNO, ID_USUARIO, NM_USUARIO, DATA_INICIO, INICIO, LONGITUDE, LATITUDE, GRAVACAO, NM_PESQUISA, PREVISAO FROM CONTROLE_INICIO C WHERE ID_ALUNO = (SELECT DISTINCT ID_ALUNO FROM resposta R where C.id_aluno = R.id_aluno) ";
	public static String GET_CONTROLE_FIM = " SELECT ID_ALUNO, ID_USUARIO, NM_USUARIO, DATA_FIM, FIM, LONGITUDE, LATITUDE FROM CONTROLE_FIM C WHERE ID_ALUNO = (SELECT DISTINCT ID_ALUNO FROM resposta R where C.id_aluno = R.id_aluno)  ";
	public static String GET_PESQUISA = " SELECT ID_CLIENTE, ID_PESQUISA, DSC_PESQUISA, AUTOMATICO FROM PESQUISA ";
	public static String GET_PESQUISA_usuario = " SELECT P.ID_CLIENTE, P.ID_PESQUISA, P.DSC_PESQUISA, P.AUTOMATICO, P.PREVISAO FROM PESQUISA P, USUARIO U, PESQUISA_USUARIO PU WHERE PU.ID_PESQUISA = P.ID_PESQUISA AND PU.ID_CLIENTE = P.ID_CLIENTE AND PU.ID_USUARIO = U.ID AND U.ID_CLIENTE = P.ID_CLIENTE AND U.NM_USUARIO = ?   ";
	public static String GET_ESCOLA = " SELECT ID_ESCOLA, NOME_ESCOLA FROM ESCOLA ";
	public static String GET_TURMA = " SELECT ID_TURMA, NOME_TURMA FROM TURMA ";
	public static String GET_TURMA_parametro = " SELECT T.ID_TURMA, T.NOME_TURMA FROM TURMA T, ESCOLA E WHERE E.ID_ESCOLA = T.ID_ESCOLA AND E.ID_ESCOLA = ? ";
	public static String GET_USUARIO = " SELECT NM_USUARIO, SENHA, ID FROM USUARIO WHERE NM_USUARIO = ? AND SENHA = ? ";
	public static String GET_USUARIO2 = " SELECT NM_USUARIO, SENHA, ID FROM USUARIO WHERE NM_USUARIO = ? ";
	public static String GET_ALUNO_MAX = " SELECT MAX(ID_ALUNO) FROM ALUNO ";
	public static String GET_ALUNO = " SELECT distinct P.ID_ALUNO, P.NOME, R.ID_ALUNO FROM ALUNO P LEFT OUTER JOIN RESPOSTA R ON P.ID_ALUNO = R.ID_ALUNO WHERE (1 = 1) ";
	public static String GET_ALUNO_completoNOVO = " SELECT distinct A.ID_ALUNO, A.ID_ESCOLA, A.ID_TURMA , A.NOME, A.DT_NASC, A.SEXO FROM ALUNO A, RESPOSTA R WHERE A.ID_ALUNO = R.ID_ALUNO  " ;
	public static String GET_PERGUNTA_CONTA = " SELECT MAX(ID_PERGUNTA) FROM PERGUNTAS ";
	public static String GET_PERGUNTA_CONTA_mim = " SELECT MIN(ID_PERGUNTA) FROM PERGUNTAS ";
	public static String GET_MAX_SALTO = " SELECT MAX(SALTO), ID_PERGUNTA FROM OPCAO ";
	public static String GET_PERGUNTA_QUESTIONARIO_NOVO =  " SELECT P.ID_PERGUNTA, P.DESCRICAO, B.ID_BLOCO, B.DESCBLOCO, B.INFORMACAO, P.NUM_SUBFORMULARIO, P.ID_PERGUNTA_JSON, P.COD_PERGUNTA  " +
			" FROM PERGUNTAS P, BLOCO B " +
			" WHERE B.ID_BLOCO = P.ID_BLOCO ORDER BY  P.ORDENA ; ";
	public static String GET_SALTO =  " SELECT ID_SALTO, SALTO_PARA, PERGUNTA, ITEM_PERGUNTA FROM SALTO ORDER BY SALTO_PARA ";
	public static String GET_OPCAO =
			" SELECT ID_OPCAO, MAXIMO, VALOR, OPCAO, MINIMO, ID_PERGUNTA, SALTO, ORDENA, PERSONALIZACAO " +
	" FROM OPCAO  "+
	" WHERE ID_PERGUNTA = ? ORDER BY ORDENA, ID_OPCAO ";
	public static String GET_OPCAO_OPCAO =
			" SELECT ID_OPCAO, MAXIMO, VALOR, OPCAO, MINIMO, ID_PERGUNTA, SALTO, ORDENA " +
	" FROM OPCAO  "+
	" WHERE ID_PERGUNTA = ? AND ID_OPCAO = ? order by ID_PERGUNTA, ID_OPCAO";
	public static String GET_MAX_OPCAO = " SELECT MAX(ID_OPCAO) FROM OPCAO ";
	public static String GET_ALIMENTOS =
			" SELECT ID, DESCRICAO, ID_ALUNO, CODIGO, ALIMENTO_REFEICAO, QUAL_E_ESSE_ITEM  " +
					" FROM ALIMENTO  "+
					" WHERE ID_ALUNO = ? ";

	public static String GET_ALIMENTOS_REFEICAO_DESCRICAO =
			" SELECT DISTINCT ALIMENTO_REFEICAO   " +
					" FROM ALIMENTO "+
					" WHERE ID_ALUNO = ?  order by ALIMENTO_REFEICAO_ORDER ";

	public static String GET_ALIMENTOS_REFEICAO =
			" SELECT ID, DESCRICAO, ID_ALUNO, CODIGO, ALIMENTO_REFEICAO, QUAL_E_ESSE_ITEM  " +
					" FROM ALIMENTO  "+
					" WHERE ID_ALUNO = ? and ID = ? ";

	public static String GET_ALIMENTOS_nome =
			" SELECT ID, DESCRICAO, ID_ALUNO " +
					" FROM ALIMENTO  "+
					" WHERE ID = ? ";
	public static String GET_MODO_PREPARACAO =
			" SELECT ID_ALUNO, DESCRICAO " +
					" FROM MODO_PREPARACAO  "+
					" WHERE ID_ALIMENTO = ? ";
	public static String GET_TUDO_ALIMENTO_CHECADO_DOMIC =
			" SELECT  ID_PERGUNTA FROM perguntas where cod_pergunta = '" + VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC2 + "' ";

	public static String GET_TUDO_ALIMENTO_CHECADO_ESC =
			" SELECT  ID_PERGUNTA FROM perguntas where cod_pergunta = '" + VariavelAPI.contant_chave_inicair_anterior_DETALIMESC2 + "'  ";

	public static String GET_TUDO_ALIMENTO_CHECADO_PAPEL =
			" SELECT  ID_PERGUNTA FROM perguntas where cod_pergunta = '" + VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL2 + "'  ";

	public static String GET_ALIMENTO_INSERIDO =
			" SELECT  ID_PERGUNTA FROM perguntas where cod_pergunta =  '"+ VariavelAPI.constant_chave_104 +"'";

	public static String GET_ALIMENTO_REFEICAO =
			" SELECT   distinct ALIMENTO_REFEICAO FROM ALIMENTO ORDER BY ALIMENTO_REFEICAO_ORDER DESC ";

	public static String GET_ADICOES =
			" SELECT ID_ALUNO, DESCRICAO " +
					" FROM ADICOES  "+
					" WHERE ID_ALIMENTO = ? ";

	public static String GET_MEDIDAS_CASEIRAS =
			" SELECT ID_ALUNO, DESCRICAO " +
					" FROM MEDIDAS_CASEIRAS  "+
					" WHERE ID_ALIMENTO = ? ";

	public static String GET_ALIMENTO_TODOS =
			" SELECT ID FROM ALIMENTO ";

	public static String GET_RESPOSTA_3 = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
	" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";
	
	public static String GET_RESPOSTA_1 = " SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA   FROM RESPOSTA R, OPCAO O "+
	" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND (R.ID_OPCAO_PESSOA IS NULL OR R.ID_OPCAO_PESSOA = 0)   ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";


	public static String GET_RESPOSTA_OPCAO_TOTAS = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND R.ID_OPCAO = ? AND R.ID_ALIMENTO = ? ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_TOTAS_REFEICAO = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND R.ID_OPCAO = ? AND R.REFEICAO_DESCRICAO = ? ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	// ALIMENTO
	public static String GET_RESPOSTA_OPCAO_NOME_ALIMENTO = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND O.OPCAO = '" + VariavelAPI.constante_descricao_alimento + "' ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_MEDIDA_CASEIRA = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND O.OPCAO = '" + VariavelAPI.constante_descricao_medida + "' ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_MEDIDA_CASEIRA_2 = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND O.OPCAO = '" + VariavelAPI.constante_descricao_medida_caseira_1 + "' ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_MODO_PREPARACAO = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND O.OPCAO = '" + VariavelAPI.constante_descricao_modo_preparacao + "' ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_QUANTIDADE_MEDIDAS = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND O.OPCAO = '" + VariavelAPI.constante_descricao_quantidade + "' ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";

	public static String GET_RESPOSTA_OPCAO_TOTAS_MAIOR_3 = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO   FROM RESPOSTA R, OPCAO O"+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_PERGUNTA = ? AND R.ID_ALIMENTO = ?  ORDER BY R.ID_PERGUNTA, R.ID_OPCAO ";



	public static String GET_RESPOSTA_VIRTUAL = " SELECT DISTINCT ID_PERGUNTA FROM RESPOSTA WHERE ID_ALUNO = ? ";

	public static String GET_RESPOSTA = " SELECT ID_ALUNO, ID_PERGUNTA, ID_OPCAO, VALOR FROM RESPOSTA order by VALOR desc ";
	
	public static String GET_RESPOSTAOrdenado = " SELECT ID_ALUNO, ID_PERGUNTA, ID_OPCAO, VALOR, ID_OPCAO_PESSOA  FROM RESPOSTA ";
	
	
	public static String GET_CONFIGURACAO = " SELECT ID_CLIENTE, ID_PESQUISA, DSC_PESQUISA, AUTOMATICO, PREVISAO FROM CONFIGURACAO ";

	public static String GET_PERGUNTA_DESCRICAO =
			" SELECT ID_PERGUNTA, DESCRICAO, ID_BLOCO " +
					" FROM PERGUNTAS  " +
					" WHERE ID_PERGUNTA = ? ";

	public static String GET_RESPOSTA_SUBFORMULARIO = " SELECT DISTINCT R.ID_OPCAO_PESSOA FROM RESPOSTA R, PERGUNTAS P WHERE P.ID_PERGUNTA = R.ID_PERGUNTA AND R.ID_ALUNO = ? AND P.NUM_SUBFORMULARIO = ? AND R.ID_OPCAO_PESSOA > 0 order by R.ID_OPCAO_PESSOA ";

	public static String GET_RESPOSTA_SUBFORMULARIO_preenchido = " SELECT ID_OPCAO_PESSOA FROM RESPOSTA WHERE ID_ALUNO = ? AND ID_PERGUNTA = ? order by ID_OPCAO_PESSOA ";


	public static String GET_PERGUNTA_CHECK01 = "  SELECT ID_PERGUNTA FROM RESPOSTA WHERE ID_ALUNO =  ?  ";

	/////////
	//QUANDO TIVER MAIS DE UM BOTÃO
	///////
	//public static String GET_PERGUNTA_CHECK03 = "  SELECT ID_PERGUNTA FROM RESPOSTA WHERE ID_ALUNO =  ? AND (ID_PERGUNTA >=  10 AND ID_PERGUNTA <= 125) ";



	public static String GET_RESPOSTA_personalizado = " SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA   FROM RESPOSTA R, OPCAO O "+
			" WHERE R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND (R.ID_PERGUNTA >=   ? AND R.ID_PERGUNTA <= ?) and (R.ID_OPCAO_PESSOA IS NULL OR R.ID_OPCAO_PESSOA = 0) ORDER BY R.ID_PERGUNTA, R.ID_OPCAO  ";


	public static String GET_BUSCA_SUBPERGUNTA = " SELECT R.VALOR, R.ID_PERGUNTA, R.ID_OPCAO, R.ID_OPCAO_PESSOA, O.OPCAO, O.VALOR  FROM RESPOSTA R, OPCAO O, PERGUNTAS P WHERE P.ID_PERGUNTA = R.ID_PERGUNTA AND P.ID_PERGUNTA = P.ID_PERGUNTA AND  R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND P.NUM_SUBFORMULARIO = ? AND R.ID_OPCAO_PESSOA > 0  order by R.ID_OPCAO_PESSOA, R.ID_PERGUNTA, R.ID_OPCAO  ";


	public static String GET_RESPOSTA_subformulario = "SELECT R.ID_ALUNO, R.ID_PERGUNTA, R.ID_OPCAO, O.VALOR AS VALOROPCAO, R.VALOR AS VALORRESPOSTA, O.OPCAO, R.ID_OPCAO_PESSOA   FROM RESPOSTA R, OPCAO O, PERGUNTAS P "+
			" WHERE P.ID_PERGUNTA = R.ID_PERGUNTA AND P.ID_PERGUNTA = O.ID_PERGUNTA AND R.ID_OPCAO = O.ID_OPCAO AND R.ID_ALUNO = ? AND R.ID_OPCAO_PESSOA = ? AND P.NUM_SUBFORMULARIO ORDER BY R.ID_OPCAO_PESSOA, R.ID_PERGUNTA, R.ID_OPCAO  ";

	public static String GET_OBRIGATORIO = " SELECT MINIMO FROM OPCAO WHERE ID_OPCAO = ? ";

    public static String GET_AMBIENTE_CASA = "  SELECT  distinct * FROM resposta r, perguntas p, opcao o where \n" +
			"     p.id_pergunta = o.id_pergunta \n" +
			" and r.id_pergunta = p.id_pergunta \n" +
			" and r.id_pergunta = o.id_pergunta \n" +
			" and r.id_opcao = o.id_opcao\n" +
			" and o.opcao = '" + VariavelAPI.constante_descricao_domiciliar +  "'  AND R.ID_ALUNO = ? ";

	public static String GET_AMBIENTE_ESCOLAR = "  SELECT  distinct * FROM resposta r, perguntas p, opcao o where \n" +
			"     p.id_pergunta = o.id_pergunta \n" +
			" and r.id_pergunta = p.id_pergunta \n" +
			" and r.id_pergunta = o.id_pergunta \n" +
			" and r.id_opcao = o.id_opcao\n" +
			" and o.opcao = '" + VariavelAPI.constante_descricao_escolar +  "'   AND R.ID_ALUNO = ? ";

}