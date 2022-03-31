create database if not exists FireFly;

use FireFly;

Create table if not exists Conta(
    Conta_Id int not null auto_increment primary key,
	username varchar(25) not null,
    Email varchar(255) not null,
    Pass varchar(255) not null,
    playlists int,
    favoritos int,
    unique(Conta_Id),
    FOREIGN KEY (playlists) REFERENCES Playlist(Playlist_Id),
    FOREIGN KEY (favoritos) REFERENCES Favorites(Favorites_Id)
);

Create table if not exists Playlist(

	Playlist_Id int not null auto_increment primary key,
    P_Name varchar(25) not null,
    P_Music varchar(355),
    unique(Playlist_Id)
);

create table if not exists Favorites(
	Favorites_Id int not null auto_increment primary key,
    F_Music varchar(355),
    unique(Favorites_Id)
);