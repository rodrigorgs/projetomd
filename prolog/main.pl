%:- multifile 
%	eh_prerequisito/2,
%	horario/3,
%	turma/3,
%	pre_matricula/3,
%	pode_se_matricular_disc/3,
%	matricula/2.

:- dynamic 
	disciplina/3,
	semestre_sugerido/2,
	eh_prerequisito_direto/2,
	turma/3,
	horario/4,
	pre_matricula/3, 
	matricula/2, 
	foi_aprovado/3.

:- ensure_loaded([disciplinas, turmas, alunos, regras]).

:- pode_se_matricular_disc(rodrigo, fis122, true).
:- foi_aprovado(rodrigo, fis121, true).
