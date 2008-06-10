:- dynamic 
	disciplina/3,
	semestre_sugerido/2,
	eh_prerequisito_direto/2,
	turma/3,
	horario/4,
	pre_matricula/3, 
	matricula/2, 
	foi_aprovado/3.

% As regras não consideram algumas situações:
% - quebra de pré-requisito;
% - aproveitamento de disciplinas (aluno transferido de outra universidade).

%domain_predicate(X) :-
%	setof(Pred2, predicate_property(Pred2, _), All), !, 
%	setof(Pred1, predicate_property(Pred1, built_in), Builtins), 
%	subtract(All, Builtins, X).

% ATENÇÃO
% Predicados marcados com PREDICADO COMPUTADO não são armazenados
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
%     (o intervalo da aula é fechado em inicio e aberto em fim.)
%
%%%

eh_prerequisito_trans(A, C) :- prerequisito(A, C).
eh_prerequisito_trans(A, C) :- prerequisito(A, B), eh_prerequisito_trans(B, C).

% Define se há interseção entre dois intervalos de tempo [A1, A2) e [B1, B2).
choque(A1, A2, B1, B2) :- A2 > B1, A1 < B2.

% Indica se há choque de horário entre duas turmas.
choque_turma(T1, T2) :- 
	horario(T1, Dia, A1, A2),
	horario(T2, Dia, B1, B2),
	choque(A1, A2, B1, B2).

%%%%%%%%%%%%%%% Predicados sobre alunos %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% pre_matricula(Aluno, Disciplina, Tempo)
%   Significa que o aluno tem interesse em se matricular na disciplina no
%   próximo período. Na prática, representa que o aluno selecionou a
%   disciplina na tela de pré-matrícula do sistema.
%   Tempo pertence a {presente, passado}
%
% pode_se_matricular_disc(Aluno, Disciplina, VV)  PREDICADO COMPUTADO
%   Levando em consideração apenas as restrições de pré-requisito, o aluno
%   pode se matricular na disciplina. Não verificamos aqui se há choque
%   de horário.
%   VV = valor verdade, pertencente a {true, false}.
%
%   exemplo de consulta: pode_se_matricular_disc(rodrigo, mat055, X).
%   retorna X = true ou X = false se o sistema souber responder.
%   retorna fail se o sistema não souber responder.
%
% pode_se_matricular_turma(Aluno, Turma, VV)  PREDICADO COMPUTADO
%   Levando em consideração choques de horário e pré-requisitos entre 
%   disciplinas, indica se o aluno pode se matricular na turma.
%   VV = valor verdade (veja pode_se_matricular_disc).
%
% matricula(Aluno, Turma)
%   Significa que o aluno está matriculado na turma. O conceito de matrícula
%   não é definitivo, isto é, o aluno pode mudar sua matrícula a qualquer
%   momento quando estiver no período de matrícula. Na prática, representa
%   que o aluno pre_matricula a turma na tela de matrícula do sistema.
%
% foi_aprovado(Aluno, Disciplina, VV) (alguns sao computados)
%   Significa que o aluno foi aprovado na disciplina.
%   VV = valor verdade (veja pode_se_matricular_disc).
%
%   predicados da forma foi_aprovado(_, _, false) não são armazenados no
%   banco de dados, pois isso pode mudar.
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

% Se o aluno se pre-matriculou na disciplina, então ainda não foi aprovado nela.
foi_aprovado(Aluno, Disciplina, false) :-
	pre_matricula(Aluno, Disciplina, presente).

% não foi aprovado se propaga para frente
foi_aprovado(Aluno, Pos, false) :-
	%eh_prerequisito_trans(Disciplina, Pos),
	prerequisito(Disciplina, Pos),
	foi_aprovado(Aluno, Disciplina, false).

% Se o aluno se pre-matriculou na disciplina, então foi aprovado nos pre-requisitos.
foi_aprovado(Aluno, Pre, true) :-
	prerequisito(Pre, Disciplina),
	pre_matricula(Aluno, Disciplina, presente).

% foi aprovado se propaga pra tras
foi_aprovado(Aluno, Pre, true) :-
	%eh_prerequisito_trans(Pre, Disciplina),
	prerequisito(Pre, Disciplina),
	foi_aprovado(Aluno, Disciplina, true).

% Se já foi aprovado, não pode se matricular
pode_se_matricular_disc(Aluno, Disciplina, false) :-
	foi_aprovado(Aluno, Disciplina, true).

% Se pode se matricular em uma disciplina, não pode se
% matricular em seus "pós-requisitos".
pode_se_matricular_disc(Aluno, Pos, false) :-
	pre_matricula(Aluno, Disc, presente),
	eh_prerequisito_trans(Disc, Pos).

% Se ainda não foi aprovado na disciplina e já cumpriu os
% pré-requisitos, pode se matricular nela.
pode_se_matricular_disc(Aluno, Disciplina, true) :-
	foi_aprovado(Aluno, Disciplina, false), % XXX substituir por false(pode_se_matricular)?	
	forall(
		eh_prerequisito_trans(Pre, Disciplina),
		foi_aprovado(Aluno, Pre, true)).

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

% TODO: substituir pode_se_matricular_turma/3 por nao_pode_se_matricular_turma/2 ?

% predicado para facilitar consulta Java
turmas_para_aluno(Aluno, Turma) :-
	turma(Disciplina, Turma, _),
	pre_matricula(Aluno, Disciplina, presente).

% Ninguém pode se matricular em duas turmas com choque de horário.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1), 
	choque_turma(T1, T2).

% Ninguém pode se matricular em duas turmas da mesma disciplina.
pode_se_matricular_turma(Aluno, T2, false) :-
	matricula(Aluno, T1),
	turma(Disciplina, T1, _),
	turma(Disciplina, T2, _).

% Se não está escolheu a disciplina na pré-matrícula, não pode se matricular
% nas suas turmas.
pode_se_matricular_turma(Aluno, Turma, false) :-
	not(pre_matricula(Aluno, Disciplina, presente)),
	turma(Disciplina, Turma, _).

%pode_se_matricular_turma(Aluno, Turma, true) :-
% 	not(pode_se_matricular_turma(Aluno, Turma, _)),
% 	turma(Turma, Disciplina, _),
% 	pre_matricula(Aluno, Disciplina, _).

% R1
% Se uma pessoa está matriculada em uma turma, então está matriculada
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
% O sistema mostra o histórico do aluno, segundo seu conhecimento incompleto.
%%%




%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

