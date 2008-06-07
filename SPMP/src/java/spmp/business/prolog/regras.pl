% TODO: estudar alguns predicados de prolog:
% - assert, retract
% - forall

% As regras n�o consideram algumas situa��es:
% - quebra de pr�-requisito;
% - aproveitamento de disciplinas (aluno transferido de outra universidade).

%%%% Predicados %%%
% Disciplina
%   - disciplina(disc, nome)
%   - eh_prerequisito(disc, disc)
%   - eh_prerequisito_trans(disc, disc) -- computado
%   - tem_turma(disc, turma)
%
% Turma
%   - horario(turma, dia, durante(inicio, fim)) -- renomear para tem_horario?
%     (o intervalo "durante" fechado em inicio e aberto em fim.)
%
% Aluno
%   - matricula(aluno, turma) -- renomear para vai_cursar_turma?
%   - matricula_disc(aluno, disc) -- renomear para vai_cursar_disc?
%   - foi_aprovado(aluno, disc)
%   - cursou(aluno, disc)
%

%%%%%%%%% Predicados para viabilizar hip�tese do mundo aberto %%%%%%%%%

prove(P) :- call(P), write('** true'), nl, !.
prove(P) :- false(P), write('** false'), nl, !.
prove(P) :- not(P), not(false(P)), write('** unknown'), nl, !.

is_true(P) :- call(P), !.
is_false(P) :- false(P), !.
is_unknown(P) :- not(P), not(false(P)), !.

%%%%%%%%%%%%% Conceitos Basicos %%%%%%%%%%%%%%%%%%%%%

eh_prerequisito_trans(A, C) :- eh_prerequisito(A, C).
eh_prerequisito_trans(A, C) :- eh_prerequisito(A, B), eh_prerequisito_trans(B, C).

choque(durante(A1, A2), durante(B1, B2)) :- A2 > B1, A1 < B2.

%%%%%%%%%%%%%%% Predicados sobre alunos %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% selecionou(Aluno, Disciplina)
%   Significa que o aluno tem interesse em se matricular na disciplina no
%   pr�ximo per�odo. Na pr�tica, representa que o aluno selecionou a
%   disciplina na tela de pr�-matr�cula do sistema.
%
% pode_se_matricular(Aluno, Disciplina)
%   Levando em considera��o apenas as restri��es de pr�-requisito, o aluno
%   pode se matricular na disciplina. N�o verificamos aqui se h� choque
%   de hor�rio.
%
% matricula(Aluno, Turma)
%   Significa que o aluno est� matriculado na turma. O conceito de matr�cula
%   n�o � definitivo, isto �, o aluno pode mudar sua matr�cula a qualquer
%   momento quando estiver no per�odo de matr�cula. Na pr�tica, representa
%   que o aluno selecionou a turma na tela de matr�cula do sistema.
%
% foi_aprovado(Aluno, Disciplina)
%   Significa que o aluno foi aprovado na disciplina.
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
% Se ele selecionou uma disciplina, ent�o ele pode se matricular nela.
% (a n�o ser que o sistema saiba que n�o pode se matricular)
pode_se_matricular(Aluno, Disciplina) :- 
	% not(false(pode_se_matricular(Aluno, Disciplina)),
	selecionou(Aluno, Disciplina).

% Se foi aprovado na disciplina, n�o pode se matricular nela.
false(pode_se_matricular(Aluno, Disciplina)) :-
	foi_aprovado(Aluno, Disciplina).

% Se ainda n�o foi aprovado na disciplina e j� cumpriu os
% pr�-requisitos, pode se matricular nela.
pode_se_matricular(Aluno, Disciplina) :-
	false(foi_aprovado(Aluno, Disciplina)), % XXX substituir por false(pode_se_matricular)?
	forall(
		eh_prerequisito_trans(D, Disciplina),
		foi_aprovado(Aluno, D)).

% Ningu�m pode se matricular em uma disciplina sem antes cumprir
% seus pr�-requisitos.
false(pode_se_matricular(Aluno, Disciplina)) :- 
	false(foi_aprovado(Aluno, Pre)),
	eh_prerequisito_trans(Pre, Disciplina).

% Se pode se matricular em uma disciplina, n�o pode se
% matricular em seus "p�s-requisitos".
false(pode_se_matricular(Aluno, D2)) :-
	pode_se_matricular(Aluno, D1),
	eh_prerequisito_trans(D1, D2).

% Se pode se matricular numa disciplina, j� foi aprovado
% nos pr�-requisitos.
%
% OBS.: Combinando com outra regra, conclui-se que n�o pode
% se matricular nos pr�-requisitos.
foi_aprovado(Aluno, Disciplina) :-
	eh_prerequisito_trans(Disciplina, D),
	pode_se_matricular(Aluno, D).

false(foi_aprovado(Aluno, Disciplina)) :-
	false(foi_aprovado(Aluno, Pre)),
	eh_prerequisito_trans(Pre, Disciplina).

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

% Ningu�m pode se matricular em duas turmas com choque de hor�rio.
false(matricula(Aluno, T2)) :- 
	matricula(Aluno, T1), 
	horario(T1, Dia, Hora1),
	horario(T2, Dia, Hora2),
	choque(Hora1, Hora2).

% Ningu�m pode se matricular em duas turmas da mesma disciplina.
false(matricula(Aluno, T2)) :- 
	matricula(Aluno, T1),
	tem_turma(Disciplina, T1),
	tem_turma(Disciplina, T2).

% R1
% Se uma pessoa est� matriculada em uma turma, ent�o est� matriculada
% na disciplina correspondente.
matricula_disc(Aluno, Disciplina) :- 
	tem_turma(Disciplina, Turma),
	matricula(Aluno, Turma).

% Se n�o est� matriculado na disciplina, n�o est� matriculado nas
% suas turmas.
false(matricula(Aluno, Turma)) :- 
	false(selecionou(Aluno, Disciplina)),
	tem_turma(Disciplina, Turma).

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

