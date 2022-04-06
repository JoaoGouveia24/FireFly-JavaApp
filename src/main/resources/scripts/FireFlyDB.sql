create database if not exists FireFly;

use FireFly;

Create table if not exists Conta(
    Conta_Id int(5) not null auto_increment,
	username varchar(25) not null,
    Email varchar(75) not null,
    Pass varchar(255) not null,
    Avatar_URL varchar(200),
    Favorites_Id int(5),
    primary key(Conta_Id),
    foreign key(Favorites_Id) references Favorites(Favorites_Id)
);

create table if not exists Favorites(
	Favorites_Id int(5) not null auto_increment,
    F_Music varchar(355),
    Track_Id int(5),
    primary key(Favorites_Id),
    foreign key(Track_Id) references Tracks(Track_Id)
);

/* Top to Down*/

create table if not exists Album(
	Album_Id int(5) not null auto_increment,
    Album_Name varchar(25) not null,
    Album_Artist varchar(25) not null,
    Album_Year varchar(4) not null,
    primary key(Album_Id)
);

create table if not exists Tracks(
	Track_Id int(5) not null auto_increment,
    Track_Numbre int(2) not null,
    Track_Name varchar(20) not null,
    Album_Id int(5),
    Track_URL varchar(200) not null,
    primary key(Track_Id),
    foreign key(Album_Id) references Album(Album_Id)
);
