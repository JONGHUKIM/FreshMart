CREATE TABLE FRESHMART (

        

        ID number(2,0) CONSTRAINT pk_freshmart_id primary key,

        TYPE_ID number(2,0) CONSTRAINT fk_freshmart_type_id REFERENCES FOOD_CATEGORY(ID),

        FOOD_NAME varchar2(100) CONSTRAINT nn_food_name not null,

        EXPIRATION_DATE date default sysdate,

        STORAGE varchar2(1) CONSTRAINT nn_storage not null,

        FOOD_QUANTITY number(3,0) CONSTRAINT ck_food_quantity check (FOOD_QUANTITY > 0 and FOOD_QUANTITY <= 100),

        IMG varchar2(200)



);



ALTER TABLE FRESHMART ADD CONSTRAINT ck_storage CHECK (STORAGE IN ('T', 'F'));