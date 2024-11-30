create table recipecommunity (
    liked CHAR(1) DEFAULT 'N' CHECK (liked IN ('Y', 'N')),
    id      number(6, 0) generated as identity,
    title   varchar2(100 char) not null,
    content varchar2(1000 char) not null,
    author  varchar2(100 char) not null,
    created_time    timestamp,
    modified_time   timestamp,
    constraint pk_blogs_id primary key (id)
);

ALTER TABLE recipecommunity
ADD liked CHAR(1) DEFAULT 'N' CHECK (liked IN ('Y', 'N'));

insert into recipecommunity (title, content, author, created_time, modified_time)
values ('11/21', '안녕하세요', '오쌤', systimestamp, systimestamp);

commit;

select * from blogs;
select * from blogs order by id desc;

-- 대/소문자 구분없이 제목에 포함된 문자열로 검색.
select * from blogs
where upper(title) like upper('%hello%')
order by id desc;

-- 대/소문자 구분없이 내용(content)에 포함된 문자열로 검색.
select * from blogs
where upper(content) like upper('%안%')
order by id desc;

-- 대/소문자 구분없이, 제목 또는 내용에 포함된 문자열로 검색.
select * from blogs
where upper(title) like upper('%t%')
    or upper(content) like upper('%t%')
order by id desc;
