CREATE TABLE FRESHMART (

ID number(2,0) generated as identity CONSTRAINT pk_freshmart_id primary key,

TYPE_ID number(2,0) CONSTRAINT fk_freshmart_type_id REFERENCES FOOD_CATEGORY(ID),

FOOD_NAME varchar2(100) CONSTRAINT nn_food_name not null,

EXPIRATION_DATE date default sysdate,

STORAGE varchar2(1) CONSTRAINT nn_storage not null,

FOOD_QUANTITY number(3,0) CONSTRAINT ck_food_quantity check (FOOD_QUANTITY > 0 and FOOD_QUANTITY <= 100),

IMG varchar2(200)

);

ALTER TABLE FRESHMART ADD CONSTRAINT ck_storage CHECK (STORAGE IN ('T', 'F'));

ALTER TABLE FRESHMART DROP CONSTRAINT ck_storage;

ALTER TABLE FRESHMART MODIFY STORAGE VARCHAR2(10);

ALTER TABLE FRESHMART ADD CONSTRAINT ck_storage CHECK (STORAGE IN ('냉장실', '냉동실'));

INSERT INTO FRESHMART (TYPE_ID, FOOD_NAME, EXPIRATION_DATE, STORAGE, FOOD_QUANTITY, IMG)
VALUES (6, '사과', TO_DATE('2024-12-31', 'YYYY-MM-DD'), '냉장실', 10, 'images/apple.jpg');

select * from FRESHMART order by EXPIRATION_DATE desc;

select * from FRESHMART order by EXPIRATION_DATE asc;


commit;