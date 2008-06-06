%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Este exemplo é antigo e não está mais sendo mantido. %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

eh_prerequisito(a,ab).
eh_prerequisito(b,ab).
eh_prerequisito(ab,c).

tem_turma(a,  a_01).
tem_turma(b,  b_01).
tem_turma(ab, ab_01).
tem_turma(ab, ab_02).
tem_turma(c,  c_01).
% A classe de turmas é fechada
false(tem_turma(_, _)). 

horario(a_01,  2,  durante(8, 10)).
horario(a_01,  4,  durante(8, 10)).
horario(b_01,  2,  durante(8, 10)).
horario(b_01,  6,  durante(8, 10)).
horario(ab_01, 4,  durante(9, 10)).
horario(ab_02, 4, durante(11, 12)).
horario(c_01,  6, durante(14, 16)).

% Fulano
matricula(fulano,   a_01).
matricula(fulano,   ab_02).

% Beltrano
matricula(beltrano, ab_01).
false(matricula(beltrano, b_01)).

% Sicrano
false(foi_aprovado(sicrano, ab)).
matricula(sicrano, ab).

% TODO: predicado cursou(Pessoa, Disciplina)

