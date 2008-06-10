CREATE DATABASE `spmp` /*!40100 DEFAULT CHARACTER SET latin1 */;

DROP TABLE IF EXISTS `spmp`.`aluno`;
CREATE TABLE  `spmp`.`aluno` (
  `idAluno` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  PRIMARY KEY  (`idAluno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`disciplina`;
CREATE TABLE  `spmp`.`disciplina` (
  `idDisciplina` varchar(6) NOT NULL,
  `codDisciplina` varchar(8) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY  (`idDisciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`turma`;
CREATE TABLE  `spmp`.`turma` (
  `idDisciplina` varchar(6) NOT NULL,
  `idTurma` varchar(12) NOT NULL,
  `codTurma` varchar(8) NOT NULL,
  PRIMARY KEY  (`idTurma`),
  KEY `FK_turma_1` (`idDisciplina`),
  CONSTRAINT `FK_turma_1` FOREIGN KEY (`idDisciplina`) REFERENCES `disciplina` (`idDisciplina`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`horario`;
CREATE TABLE  `spmp`.`horario` (
  `idTurma` varchar(12) NOT NULL,
  `diaSemana` varchar(45) NOT NULL,
  `horaInicio` int(10) unsigned NOT NULL,
  `horaFim` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idTurma`,`diaSemana`,`horaInicio`,`horaFim`),
  CONSTRAINT `FK_horario_1` FOREIGN KEY (`idTurma`) REFERENCES `turma` (`idTurma`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`matricula`;
CREATE TABLE  `spmp`.`matricula` (
  `idAluno` varchar(45) NOT NULL,
  `idTurma` varchar(12) NOT NULL,
  PRIMARY KEY  (`idAluno`,`idTurma`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`pre_matricula`;
CREATE TABLE  `spmp`.`pre_matricula` (
  `idAluno` varchar(45) NOT NULL,
  `idDisciplina` varchar(12) NOT NULL,
  `tempo` varchar(8) NOT NULL,
  PRIMARY KEY  (`idAluno`,`idDisciplina`),
  KEY `FK_pre_matricula_2` (`idDisciplina`),
  CONSTRAINT `FK_pre_matricula_1` FOREIGN KEY (`idAluno`) REFERENCES `aluno` (`idAluno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pre_matricula_2` FOREIGN KEY (`idDisciplina`) REFERENCES `disciplina` (`idDisciplina`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`prerequisito`;
CREATE TABLE  `spmp`.`prerequisito` (
  `idDisciplinaPre` varchar(6) NOT NULL,
  `idDisciplinaPos` varchar(6) NOT NULL,
  PRIMARY KEY  (`idDisciplinaPre`,`idDisciplinaPos`),
  KEY `FK_prerequisito_2` (`idDisciplinaPos`),
  CONSTRAINT `FK_prerequisito_1` FOREIGN KEY (`idDisciplinaPre`) REFERENCES `disciplina` (`idDisciplina`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_prerequisito_2` FOREIGN KEY (`idDisciplinaPos`) REFERENCES `disciplina` (`idDisciplina`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`semestre_sugerido`;
CREATE TABLE  `spmp`.`semestre_sugerido` (
  `idDisciplina` varchar(6) NOT NULL,
  `semestre` varchar(10) NOT NULL,
  PRIMARY KEY  (`idDisciplina`),
  CONSTRAINT `FK_semestre_sugerido_1` FOREIGN KEY (`idDisciplina`) REFERENCES `disciplina` (`idDisciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `spmp`.`historico`;
CREATE TABLE  `spmp`.`historico` (
  `idAluno` varchar(45) NOT NULL,
  `idDisciplina` varchar(6) NOT NULL,
  `aprovado` tinyint(1) NOT NULL,
  PRIMARY KEY  (`idAluno`,`idDisciplina`),
  KEY `FK_historico_2` (`idDisciplina`),
  CONSTRAINT `FK_historico_1` FOREIGN KEY (`idAluno`) REFERENCES `aluno` (`idAluno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_historico_2` FOREIGN KEY (`idDisciplina`) REFERENCES `disciplina` (`idDisciplina`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


