create database if not exists FireFly;

use FireFly;

create table if not exists Conta(

Conta_Id int not null auto_increment,
Username varchar(25) not null,
Email varchar (125) not null,
Pass varchar (255),
primary key (Conta_Id)
);

create table if not exists Album(
	Album_Id int not null auto_increment,
    Album_Name varchar(25) not null,
    Album_Year varchar(4) not null,
    primary key(Album_Id),
    Conta_Id int,
    foreign key(Conta_Id) references Conta(Conta_Id)
							On delete cascade
);

create table if not exists Tracks(
	Track_Id int not null auto_increment,
    Track_Name varchar(20) not null,
    Album_Id int,
    Track_Bin varchar(200) not null,
    primary key(Track_Id),
    foreign key(Album_Id) references Album(Album_Id)
							On delete cascade
);