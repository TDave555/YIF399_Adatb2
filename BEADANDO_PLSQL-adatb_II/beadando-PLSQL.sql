--felvitel, torles es modositas procedure-k
CREATE OR REPLACE PROCEDURE FELVITEL(gyarto_az NUMBER, nev VARCHAR2, telephely VARCHAR2) IS
begin
    INSERT INTO gyarto VALUES(gyarto_az, nev, telephely);
end;

DECLARE
    gyarto_az NUMBER(2):=4;
    nev VARCHAR2(30):= 'Huawei Technologies Hungary Kft.';
    telephely VARCHAR2(30):='Budapest, Magyarország';
BEGIN
FELVITEL(gyarto_az, nev, telephely);
END;

CREATE OR REPLACE PROCEDURE MODOSIT(AZ NUMBER, UJ_NEV VARCHAR2) IS
begin
    UPDATE gyarto SET nev = UJ_NEV WHERE gyarto_azonosito = AZ;
end;

DECLARE
    AZ NUMBER(2):=3;
    UJ_NEV VARCHAR2(30):= 'Xiaomi Hungary Kft.';
BEGIN
MODOSIT(AZ, UJ_NEV);
END;

CREATE OR REPLACE PROCEDURE TOROL(AZ NUMBER) IS
begin
    DELETE FROM gyarto WHERE gyarto_azonosito = AZ;
end;

DECLARE
    AZ NUMBER(2):=4;
BEGIN
TOROL(AZ);
END;

--naplozas trigger
CREATE TABLE beadando_log(muvelet VARCHAR2(30), datum date, felhasznalo char(20));

CREATE OR REPLACE TRIGGER gyarto_naplozas before INSERT or UPDATE or DELETE on gyarto for each row
BEGIN
    if UPDATING THEN
        INSERT INTO beadando_log VALUES('modositas', sysdate, user);
    else if INSERTING THEN
        INSERT INTO beadando_log VALUES('felvitel', sysdate, user);
    else if DELETING THEN
        INSERT INTO beadando_log VALUES('torles', sysdate, user);
    end if;
END;


--procedure az adott indointervallumban kiadott telefonok szamlalasara
CREATE OR REPLACE PROCEDURE PIACRA_HELYEZVE(KEZDET DATE, VEGE DATE) IS
begin
    SELECT COUNT(azonosito) AS mobilok_szama FROM Mobiltelefonok WHERE piacra_helyezes_datuma BETWEEN KEZDET AND VEGE;
end;

DECLARE
    KEZDET DATE:=2020-03-01;
    VEGE DATE:= 2022-12-01;
BEGIN
    PIACRA_HELYEZVE(KEZDET, VEGE);
END;
