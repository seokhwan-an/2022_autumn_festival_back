create table if not exists booth
(
    id           bigint         not null auto_increment,
    title        varchar(100)   not null,
    content      varchar(10000) not null,
    booth_type   varchar(255),
    introduction varchar(100)   not null,
    location     varchar(100)   not null,
    notice       varchar(100)   not null,
    start_at     date,
    end_at       date,
    primary key (id)
    );

create table if not exists comment
(
    id                 bigint       not null auto_increment,
    content            varchar(100) not null,
    writer             varchar(10)  not null,
    password           varchar(100)  not null,
    active             boolean,
    booth_id           bigint,
    created_date_time  datetime(6),
    modified_date_time datetime(6),
    primary key (id),
    foreign key (booth_id) references booth (id)
    );

create table if not exists notification
(
    id                 bigint         not null auto_increment,
    title              varchar(100)   not null,
    writer             varchar(10)    not null,
    content            varchar(10000) not null,
    notification_type  varchar(255),
    created_date_time  datetime(6),
    modified_date_time datetime(6),
    primary key (id)
    );

create table if not exists image
(
    id               bigint not null auto_increment,
    origin_file_name varchar(255),
    server_file_name varchar(255),
    stored_file_path varchar(255),
    booth_id         bigint,
    notification_id  bigint,
    primary key (id),
    foreign key (booth_id) references booth (id),
    foreign key (notification_id) references notification (id)
    );

create table if not exists likes
(
    id         bigint not null auto_increment,
    cookie_key varchar(255),
    booth_id   bigint,
    primary key (id),
    foreign key (booth_id) references booth (id)
    );

create table if not exists menu
(
    id       bigint      not null auto_increment,
    name     varchar(15) not null,
    price    integer      not null,
    booth_id bigint,
    primary key (id),
    foreign key (booth_id) references booth (id)
    );
