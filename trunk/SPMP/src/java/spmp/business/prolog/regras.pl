% TODO: estudar alguns predicados de prolog:
% - assert, retract
% - forall

% As regras não consideram algumas situações:
% - quebra de pré-requisito;
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

%%%%%%%%% Predicados para viabilizar hipótese do mundo aberto %%%%%%%%%

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
%   próximo período. Na prática, representa que o aluno selecionou a
%   disciplina na tela de pré-matrícula do sistema.
%
% pode_se_matricular(Aluno, Disciplina)
%   Levando em consideração apenas as restrições de pré-requisito, o aluno
%   pode se matricular na disciplina. Não verificamos aqui se há choque
%   de horário.
%
% matricula(Aluno, Turma)
%   Significa que o aluno está matriculado na turma. O conceito de matrícula
%   não é definitivo, isto é, o aluno pode mudar sua matrícula a qualquer
%   momento quando estiver no período de matrícula. Na prática, representa
%   que o aluno selecionou a turma na tela de matrícula do sistema.
%
% foi_aprovado(Aluno, Disciplina)
%   Significa que o aluno foi aprovado na disciplina.
%
%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%% Pré-matrícula %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Nesta etapa o aluno deve selecionar as disciplinas em que tem a intenção
% de se matricular.
%
% A seleção de disciplinas para pré-matrícula deve respeitar os
% pré-requisitos entre as disciplinas.
%
% Assumimos que o aluno só seleciona disciplinas em que pode ser matriculado.
%
% O sistema vai construindo conhecimento sobre o histórico do aluno de
% acordo com suas escolhas.
%
%%%

% O aluno sabe o que está fazendo.
% Se ele selecionou uma disciplina, então ele pode se matricular nela.
% (a não ser que o sistema saiba que não pode se matricular)
pode_se_matricular(Aluno, Disciplina) :- 
	% not(false(pode_se_matricular(Aluno, Disciplina)),
	selecionou(Aluno, Disciplina).

% Se foi aprovado na disciplina, não pode se matricular nela.
false(pode_se_matricular(Aluno, Disciplina)) :-
	foi_aprovado(Aluno, Disciplina).

% Se ainda não foi aprovado na disciplina e já cumpriu os
% pré-requisitos, pode se matricular nela.
pode_se_matricular(Aluno, Disciplina) :-
	false(foi_aprovado(Aluno, Disciplina)), % XXX substituir por false(pode_se_matricular)?
	forall(
		eh_prerequisito_trans(D, Disciplina),
		foi_aprovado(Aluno, D)).

% Ninguém pode se matricular em uma disciplina sem antes cumprir
% seus pré-requisitos.
false(pode_se_matricular(Aluno, Disciplina)) :- 
	false(foi_aprovado(Aluno, Pre)),
	eh_prerequisito_trans(Pre, Disciplina).

% Se pode se matricular em uma disciplina, não pode se
% matricular em seus "pós-requisitos".
false(pode_se_matricular(Aluno, D2)) :-
	pode_se_matricular(Aluno, D1),
	eh_prerequisito_trans(D1, D2).

% Se pode se matricular numa disciplina, já foi aprovado
% nos pré-requisitos.
%
% OBS.: Combinando com outra regra, conclui-se que não pode
% se matricular nos pré-requisitos.
foi_aprovado(Aluno, Disciplina) :-
	eh_prerequisito_trans(Disciplina, D),
	pode_se_matricular(Aluno, D).

false(foi_aprovado(Aluno, Disciplina)) :-
	false(foi_aprovado(Aluno, Pre)),
	eh_prerequisito_trans(Pre, Disciplina).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%% Matrícula %%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Nesta etapa o sistema exibe as turmas das disciplinas selecionadas pelo
% aluno na pré-matrícula, e o aluno pode se matricular em algumas delas.
%
% O sistema identifica choques de horário.
%
%%%

% Ninguém pode se matricular em duas turmas com choque de horário.
false(matricula(Aluno, T2)) :- 
	matricula(Aluno, T1), 
	horario(T1, Dia, Hora1),
	horario(T2, Dia, Hora2),
	choque(Hora1, Hora2).

% Ninguém pode se matricular em duas turmas da mesma disciplina.
false(matricula(Aluno, T2)) :- 
	matricula(Aluno, T1),
	tem_turma(Disciplina, T1),
	tem_turma(Disciplina, T2).

% R1
% Se uma pessoa está matriculada em uma turma, então está matriculada
% na disciplina correspondente.
matricula_disc(Aluno, Disciplina) :- 
	tem_turma(Disciplina, Turma),
	matricula(Aluno, Turma).

% Se não está matriculado na disciplina, não está matriculado nas
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
% O sistema mostra o histórico do aluno, segundo seu conhecimento incompleto.
%%%




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

