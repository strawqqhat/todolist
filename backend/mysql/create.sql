create todolist;

use todolist;

create table `task_info`(
    `id` int(11) not null auto_increment,
    `task_name` varchar(255) not null,
    `description` varchar(255) not null DEFAULT '无',
    `deadline` datetime not null DEFAULT '0000-01-01 00:00:00',
    `finished` int not null DEFAULT -1,
    primary key(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;