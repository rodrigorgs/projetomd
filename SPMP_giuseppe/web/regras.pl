:- dynamic 
	disciplina/3,
	semestre_sugerido/2,
	eh_prerequisito_direto/2,
	turma/3,
	horario/4,
	pre_matricula/3, 
	matricula/2, 
	foi_aprovado/3,
        historico/3,
        aluno/4.

% As regras n�o consideram algumas situa��es:
% - quebra de pr�-requisito;
% - aproveitamento de disciplinas (aluno transferido de outra universidade).

%%%%%%%%%%%%%%%%%%%% Consultas auxiliares %%%%%%%%%%%%%%%%%%%%%%%%%%
get_todas_disciplinas_por_semestre(Semestre, Id, Cod, Nome) :-
	disciplina(Id, Cod, Nome),
	semestre_sugerido(Id, Semestre).

get_todas_disciplinas_por_semestre(0, Id, Cod, Nome) :-
	disciplina(Id, Cod, Nome),
	not(semestre_sugerido(Id, _)).

get_disciplinas(Aluno, Semestre, Id, Cod, Nome) :-
	disciplina(Id, Cod, Nome),
	not(pode_se_matricular_disc(Aluno, Id, false)),
	semestre_sugerido(Id, Semestre).

get_disciplinas(Aluno, 0, Id, Cod, Nome) :-
	disciplina(Id, Cod, Nome),
	not(pode_se_matricular_disc(Aluno, Id, false)),
        not(semestre_sugerido(Id, _)).

get_prerequisitos(Pos, Id, Cod, Nome) :-
    prerequisito(Id, Pos),
    disciplina(Id, Cod, Nome).

% ATENCAO
% Predicados marcados com PREDICADO COMPUTADO n�o s�o armazenados
% no banco de dados.

%%%%%%%%%%%%%%% Predicados sobre disciplinas e turmas %%%%%%%%%%%%%%
%
% Disciplina
%   - disciplina(disc, codigo, nome)
%   - semestre_sugerido(disc, numero_semestre)
%   - prerequisito(disc, disc)
%   - eh_prerequisito_trans(disc, disc) -- PREDICADO COMPUTADO
%
% Turma
%   - turma(disc, turma, codigo)
%   - horario(turma, dia, inicio, fim)
%     (o intervalo da aula � fechado em inicio e aberto em fim.)
%
%%%

eh_prerequisito_trans(A, C) :- prerequisito(A, C).
eh_prerequisito_trans(A, C) :- prerequisito(A, B), eh_prerequisito_trans(B, C).

% Define se h� interse��o entre dois intervalos de tempo [A1, A2) e [B1, B2).
choque(A1, A2, B1, B2) :- A2 > B1, A1 < B2.

% Indica se h� choque de hor�rio entre duas turmas.
choque_turma(T1, T2) :- 
	horario(T1, Dia, A1, A2),
	horario(T2, Dia, B1, B2),
	choque(A1, A2, B1, B2).

%%%%%%%%%%%%%%% Predicados sobre alunos %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% pre_matricula(Aluno, Disciplina, Tempo)
%   Significa que o aluno tem interesse em se matricular na disciplina no
%   pr�ximo per�odo. Na pr�tica, representa que o aluno selecionou a
%   disciplina na tela de pr�-matr�cula do sistema.
%   Tempo pertence a {presente, passado}
%
% pode_se_matricular_disc(Aluno, Disciplina, VV)  PREDICADO COMPUTADO
%   Levando em considera��o apenas as restri��es de pr�-requisito, o aluno
%   pode se matricular na disciplina. N�o verificamos aqui se h� choque
%   de hor�rio.
%   VV = valor verdade, pertencente a {true, false}.
%
%   exemplo de consulta: pode_se_matricular_disc(rodrigo, mat055, X).
%   retorna X = true ou X = false se o sistema souber responder.
%   retorna fail se o sistema n�o souber responder.
%
% pode_se_matricular_turma(Aluno, Turma, VV)  PREDICADO COMPUTADO
%   Levando em considera��o choques de hor�rio e pr�-requisitos entre 
%   disciplinas, indica se o aluno pode se matricular na turma.
%   VV = valor verdade (veja pode_se_matricular_disc).
%
% matricula(Aluno, Turma)
%   Significa que o aluno est� matriculado na turma. O conceito de matr�cula
%   n�o � definitivo, isto �, o aluno pode mudar sua matr�cula a qualquer
%   momento quando estiver no per�odo de matr�cula. Na pr�tica, representa
%   que o aluno pre_matricula a turma na tela de matr�cula do sistema.
%
% foi_aprovado(Aluno, Disciplina, VV) (alguns sao computados)
%   Significa que o aluno foi aprovado na disciplina.
%   VV = valor verdade (veja pode_se_matricular_disc).
%
%   predicados da forma foi_aprovado(_, _, false) n�o s�o armazenados no
%   banco de dados, pois isso pode mudar.
%
%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%% Pr�-matr�cula %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Nesta etapa o aluno deve selecionar as disciplinas em que tem a inten��o
% de se matricular.
%
% A sele��o de disciplinas para pr�-matr�cula deve respeitar os
% pr�-requisitos entre as disciplinas.
%
% Assumimos que o aluno s� seleciona disciplinas em que pode ser matriculado.
%
% O sistema vai construindo conhecimento sobre o hist�rico do aluno de
% acordo com suas escolhas.
%
%%%

% Usa o historico do banco de dados.
foi_aprovado(Aluno, Disciplina, VV) :-
    historico(Aluno, Disciplina, VV).

% Se o aluno se pre-matriculou na disciplina, ent�o ainda n�o foi aprovado nela.
foi_aprovado(Aluno, Disciplina, false) :-
	pre_matricula(Aluno, Disciplina, presente).

% n�o foi aprovado se propaga para frente
foi_aprovado(Aluno, Pos, false) :-
	%eh_prerequisito_trans(Disciplina, Pos),
	prerequisito(Disciplina, Pos),
	foi_aprovado(Aluno, Disciplina, false).

% Se o aluno se pre-matriculou na disciplina, ent�o foi aprovado nos pre-requisitos.
foi_aprovado(Aluno, Pre, true) :-
	prerequisito(Pre, Disciplina),
	pre_matricula(Aluno, Disciplina, presente).

% foi aprovado se propaga pra tras
foi_aprovado(Aluno, Pre, true) :-
	%eh_prerequisito_trans(Pre, Disciplina),
	prerequisito(Pre, Disciplina),
	foi_aprovado(Aluno, Disciplina, true).

% Se j� foi aprovado, n�o pode se matricular
pode_se_matricular_disc(Aluno, Disciplina, false) :-
	foi_aprovado(Aluno, Disciplina, true).

% Se pode se matricular em uma disciplina, n�o pode se
% matricular em seus "p�s-requisitos".
pode_se_matricular_disc(Aluno, Pos, false) :-
	pre_matricula(Aluno, Disc, presente),
	eh_prerequisito_trans(Disc, Pos).

% Se ainda n�o foi aprovado na disciplina e j� cumpriu os
% pr�-requisitos, pode se matricular nela.
pode_se_matricular_disc(Aluno, Disciplina, true) :-
	foi_aprovado(Aluno, Disciplina, false), % XXX substituir por false(pode_se_matricular)?	
	forall(
		eh_prerequisito_trans(Pre, Disciplina),
		foi_aprovado(Aluno, Pre, true)).

pode_se_matricular_disc(Aluno, Disciplina, true) :-
    pre_matricula(Aluno, Disciplina, presente).

get_desconhecido(Aluno, Disciplina) :-
    disciplina(Disciplina, _, _),
    not(matricula_disc(Aluno, Disciplina)),
    not(foi_aprovado(Aluno, Disciplina, true)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%% Matr�cula %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Nesta etapa o sistema exibe as turmas das disciplinas selecionadas pelo
% aluno na pr�-matr�cula, e o aluno pode se matricular em algumas delas.
%
% O sistema identifica choques de hor�rio.
%
%%%

% TODO: substituir pode_se_matricular_turma/3 por nao_pode_se_matricular_turma/2 ?

% predicado para facilitar consulta Java
get_turmas_para_aluno(Aluno, Turma, Disciplina, Cod) :-
	turma(Disciplina, Turma, Cod),
	pre_matricula(Aluno, Disciplina, presente).

% Ningu�m pode se matricular em duas turmas com choque de hor�rio.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1), 
	choque_turma(T1, T2).

% Ningu�m pode se matricular em duas turmas da mesma disciplina.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1),
	turma(Disciplina, T1, _),
	turma(Disciplina, T2, _).

% Se n�o est� escolheu a disciplina na pr�-matr�cula, n�o pode se matricular
% nas suas turmas.
pode_se_matricular_turma(Aluno, Turma, false) :-
	not(pre_matricula(Aluno, Disciplina, presente)),
	turma(Disciplina, Turma, _).

%pode_se_matricular_turma(Aluno, Turma, true) :-
% 	not(pode_se_matricular_turma(Aluno, Turma, _)),
% 	turma(Turma, Disciplina, _),
% 	pre_matricula(Aluno, Disciplina, _).

% R1
% Se uma pessoa est� matriculada em uma turma, ent�o est� matriculada
% na disciplina correspondente.
matricula_disc(Aluno, Disciplina) :- 
	turma(Disciplina, Turma, _),
	matricula(Aluno, Turma).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%% Resultado %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Nesta etapa o aluno informa se foi aprovado nas disciplinas, e com
% isso aumenta o conhecimento do sistema sobre ele.
%
% O sistema mostra o hist�rico do aluno, segundo seu conhecimento incompleto.
%%%




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

