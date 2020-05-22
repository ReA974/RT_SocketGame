RT_SocketGame Projet Réseau Transmission
====

### -> Créer le build

    mkdir build


### -> Compiler le projet

    cd build
	javac -source 1.6 -target 1.6 ../src/*.java -d ./


### -> Lancer le serveur
    
    cd build
    java TPServeur


### -> Lancer les clients
    
    cd build
    java MainClient team positionx positiony



### Deux choix possible pour l'équipe :

    0 -> equipe bleu
    1 -> équipe rouge

### Choix de la position :

    Valeur comprise entre 0 et 9 
