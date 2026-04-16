--  Script MySQL — examen2_soto
--
--  Estrategia ORM: JOINED TABLE  (InheritanceType.JOINED)
--  Una tabla por clase → sin repetición de columnas → normalizado.
--
--  Mapeo clase → tabla:
--  ┌──────────────────────────────────────────────────────────────────────┐
--  │            Clase Java            │  Tabla MySQL       │   Relación   │
--  ├──────────────────────────────────────────────────────────────────────┤
--  │  asegurado                       │  asegurado         │      —       │
--  │  seguro  (abstracta)             │  seguro            │      —       │
--  │  seguroVida extends seguro	   │  seguro_vida       │    1:1 FK    │
--  │  seguroVehiculo extends seguro   │  seguro_vehiculo   │    1:1 FK    │
--  └──────────────────────────────────────────────────────────────────────┘
--
--  Relaciones:
--    seguro.asegurado_id        → asegurado.id         (@ManyToOne  * → 1)
--    seguroVida.numero          → seguro.numero     (@OneToOne   1 → 1)
--    seguroVehiculo.numero      → seguro.numero     (@OneToOne   1 → 1)

-- ================================================================

CREATE DATABASE IF NOT EXISTS examen2_torres
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE examen2_torres;

-- ================================================================
CREATE TABLE IF NOT EXISTS asegurado (

	-- @Id
    id      VARCHAR(50)  NOT NULL  COMMENT '@Id',
    
    -- @Column
    nombre  VARCHAR(100) NOT NULL  COMMENT '@Column',

    PRIMARY KEY (id)

) ENGINE = InnoDB
  COMMENT = '@Entity asegurado';

-- ================================================================

-- TABLA 2 — seguro
-- Clase: seguro (clase base)
-- @Entity  @Inheritance(strategy = InheritanceType.JOINED)
-- ================================================================
  CREATE TABLE IF NOT EXISTS seguro (

	-- @Id - PK compartida con subclases
    numero        VARCHAR(20) NOT NULL COMMENT '@Id — PK compartida con subclases',
    
    -- @Column
    fecha_exp VARCHAR(20) NOT NULL COMMENT  '@Column heredado — formato AAAA/MM/DD',
    
    -- @Column  true= activo | false= vencido
    estado        TINYINT(1)  NOT NULL DEFAULT 1 COMMENT '@Column heredado — 1 activo / 0 vencido',

	-- @ManyToOne @JoinColumn
    -- Cardinalidad: N seguro → 1 asegurado  (diagrama: * → 1)
    asegurado_id VARCHAR(50) NOT NULL  COMMENT '@ManyToOne FK → asegurado.id',

    PRIMARY KEY (numero),

    CONSTRAINT fk_seguro_asegurado
        FOREIGN KEY (asegurado_id)
        REFERENCES asegurado (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT

) ENGINE = InnoDB
  COMMENT = '@Entity seguro';
  
  -- ================================================================
-- TABLA 3 — seguro_Vida
-- Clase: seguroVida extends seguro
-- @Entity  @Table(name = "seguro_vida")
-- @PrimaryKeyJoinColumn(name = "numero")
-- ================================================================
CREATE TABLE IF NOT EXISTS seguro_vida (

    -- @PrimaryKeyJoinColumn
    -- PK de esta tabla = FK @OneToOne → seguro.numero
    numero       VARCHAR(20) NOT NULL
                        COMMENT '@PrimaryKeyJoinColumn — FK 1:1 → seguro.numero',

    -- @Column (campo propio de seguroVida)
    beneficiario  VARCHAR(100) NOT NULL
                        COMMENT '@Column propio seguroVida',

    PRIMARY KEY (numero),

    CONSTRAINT fk_vida_seguro
        FOREIGN KEY (numero)
        REFERENCES seguro (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE

) ENGINE = InnoDB
  COMMENT = '@Entity seguroVida extends seguro';
  
  -- ================================================================
-- TABLA 4 — seguro_Vehiculo
-- Clase: seguroVehiculo extends seguro
-- @Entity  @Table(name = "seguro_Vehiculo")
-- @PrimaryKeyJoinColumn(name = "numero")
-- ================================================================
CREATE TABLE IF NOT EXISTS seguro_vehiculo (

    -- @PrimaryKeyJoinColumn
    numero          VARCHAR(20) NOT NULL
                        COMMENT '@PrimaryKeyJoinColumn — FK 1:1 → seguro.numero',

    -- @Column (campo propio de seguroVehiculo)
		marca  VARCHAR(100) NOT NULL
                        COMMENT '@Column propio segurovehiculo',

    PRIMARY KEY (numero),

    CONSTRAINT fk_vehiculo_seguro
        FOREIGN KEY (numero)
        REFERENCES seguro (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE

) ENGINE = InnoDB
  COMMENT = '@Entity seguroVehiculo extends seguro ';
  
  -- ================================================================
-- DATOS DE PRUEBA
-- ================================================================
SET SQL_SAFE_UPDATES = 0;

DELETE FROM seguro_vida;
DELETE FROM seguro_vehiculo;
DELETE FROM seguro;
DELETE FROM asegurado;


INSERT INTO asegurado (id, nombre) VALUES
('A001', 'Pipe Torres'),
('A002', 'Carlos Ruiz');

INSERT INTO seguro (numero, fecha_exp, estado, asegurado_id) VALUES
('S001', '2026-01-01', 1, 'A001'),
('S002', '2026-02-01', 1, 'A001'),
('S003', '2026-03-01', 0, 'A002');

INSERT INTO seguro_vida (numero, beneficiario) VALUES
('S001', 'Maria Torres');

INSERT INTO seguro_vehiculo (numero, marca) VALUES
('S002', 'Toyota'),
('S003', 'Mazda');

SELECT * FROM asegurado;
SELECT * FROM seguro;
SELECT * FROM seguro_vida;
SELECT * FROM seguro_vehiculo;




  
  