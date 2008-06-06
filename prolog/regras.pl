% As regras n�o consideram algumas situa��es:
% - quebra de pr�-requisito;
% - aproveitamento de disciplinas (aluno transferido de outra universidade).

% ATEN��O
% Predicados marcados com PREDICADO COMPUTADO n�o s�o armazenados
% no banco de dados.

%%%%%%%%%%%%%%% Predicados sobre disciplinas e turmas %%%%%%%%%%%%%%
%
% Disciplina
%   - disciplina(disc, codigo, nome)
%   - semestre_sugerido(disc, numero_semestre)
%   - eh_prerequisito_direto(disc, disc)
%   - eh_prerequisito_trans(disc, disc) -- PREDICADO COMPUTADO
%
% Turma
%   - turma(turma, disc, codigo)
%   - horario(turma, dia, inicio, fim)
%     (o intervalo da aula � fechado em inicio e aberto em fim.)
%
%%%

eh_prerequisito_trans(A, C) :- eh_prerequisito_direto(A, C).
eh_prerequisito_trans(A, C) :- eh_prerequisito_direto(A, B), eh_prerequisito_trans(B, C).

% Define se h� interse��o entre dois intervalos de tempo [A1, A2) e [B1, B2).
choque(A1, A2, B1, B2) :- A2 > B1, A1 < B2.

% Indica se h� choque de hor�rio entre duas turmas.
choque_turma(T1, T2) :- 
	horario(T1, Dia, A1, A2),
	horario(T2, Dia, B1, B2),
	choque(A1, A2, B1, B2).

%%%%%%%%%%%%%%% Predicados sobre alunos %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% pre_matricula_atual(Aluno, Disciplina, Tempo)
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
% foi_aprovado(Aluno, Disciplina, VV)
%   Significa que o aluno foi aprovado na disciplina.
%   VV = valor verdade (veja pode_se_matricular_disc).
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

% O aluno sabe o que est� fazendo.
% Se ele se pre-matriculou em uma disciplina (agora ou no passado), ent�o ele
% pode se matricular nela.
% (a n�o ser que o sistema saiba que n�o pode se matricular)
pode_se_matricular_disc(Aluno, Disciplina, true) :- 
	% not(pode_se_matricular_disc(Aluno, Disciplina, false),
	pre_matricula(Aluno, Disciplina, _).

% Se foi aprovado na disciplina, n�o pode se matricular nela.
pode_se_matricular_disc(Aluno, Disciplina, false) :-
	foi_aprovado(Aluno, Disciplina, true).

% Se ainda n�o foi aprovado na disciplina e j� cumpriu os
% pr�-requisitos, pode se matricular nela.
pode_se_matricular_disc(Aluno, Disciplina, true) :-
	foi_aprovado(Aluno, Disciplina, false), % XXX substituir por false(pode_se_matricular)?
	forall(
		eh_prerequisito_trans(D, Disciplina),
		foi_aprovado(Aluno, D, true)).

% Ningu�m pode se matricular em uma disciplina sem antes cumprir
% seus pr�-requisitos.
pode_se_matricular_disc(Aluno, Disciplina, false) :- 
	foi_aprovado(Aluno, Pre, false),
	eh_prerequisito_trans(Pre, Disciplina).

% Se pode se matricular em uma disciplina, n�o pode se
% matricular em seus "p�s-requisitos".
pode_se_matricular_disc(Aluno, D2, false) :-
	pode_se_matricular_disc(Aluno, D1, true),
	eh_prerequisito_trans(D1, D2).

% Se pode se matricular numa disciplina, j� foi aprovado
% nos pr�-requisitos.
%
% OBS.: Combinando com outra regra, conclui-se que n�o pode
% se matricular nos pr�-requisitos.
foi_aprovado(Aluno, Disciplina, true) :-
	eh_prerequisito_trans(Disciplina, D),
	pode_se_matricular_disc(Aluno, D, true).

% Se n�o foi aprovado no pr�-requisito, n�o foi aprovado na
% disciplina.
foi_aprovado(Aluno, Disciplina, false) :-
	eh_prerequisito_trans(Pre, Disciplina),
	foi_aprovado(Aluno, Pre, false).


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

% Ningu�m pode se matricular em duas turmas com choque de hor�rio.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1), 
	choque_turma(T1, T2).

% Ningu�m pode se matricular em duas turmas da mesma disciplina.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1),
	turma(T1, Disciplina, _),
	turma(T2, Disciplina, _).

% Se n�o est� escolheu a disciplina na pr�-matr�cula, n�o pode se matricular
% nas suas turmas.
pode_se_matricular_turma(Aluno, Turma, false) :-
	not(pre_matricula(Aluno, Disciplina, presente)),
	turma(Turma, Disciplina, _).

% R1
% Se uma pessoa est� matriculada em uma turma, ent�o est� matriculada
% na disciplina correspondente.
matricula_disc(Aluno, Disciplina) :- 
	turma(Turma, Disciplina, _),
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

