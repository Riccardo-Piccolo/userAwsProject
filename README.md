# userAwsProject
Progetto basilare per la gestione di due endpoint con AWS

# In cosa consiste?
Consiste nella creazione di due funzioni lambda utili per la gestione dei due endpoint _createUser_ e _getUserById_. Andiamo più nello specifico.

## _createUser_
Questo endpoint permette la creazione di un utente nella tabella _users_ tramite il passaggio nella richiesta di tipo **POST** di un file in formato JSON contenente: 
- nome
- cognome
- indirizzo email

Il campo _id_, di tipo numerico, non è richiesto perché esso viene calcolato automaticamente dalla funzione in base agli elementi già presenti, in modo tale da simulare un incremento automatico.
Un possibile sviluppo futuro potrebbe consistere nell'implementare un sistema di validazione degli indirizzi email, oppure nell'inserimento delle località di residenza e/o domicilio tramite una tabella secondaria. 

## _getUserById_
Questo endpoint permette la restituzione di un utente nella tabella _users_ con _id_ uguale a quello parametrizzato nell'url di richiesta (metodo **GET**). 
Esempio: _/getUserById/1_

In caso l'utente cercato non sia presente nella tabella, verrà visualizzato un messaggio di errore.

# Come posso testare ed usufruire di questa API?
Si può usufruire di questa API utilizzando i seguenti link:
- https://gga1miiov1.execute-api.eu-central-1.amazonaws.com/dev/createUser per la funzionalità di creazione utente
- https://gga1miiov1.execute-api.eu-central-1.amazonaws.com/dev/getUserById/{userId} per la funzionalità di ricerca utente tramite id. E' importante che al posto di {userId} venga inserito un id utente numerico.

Per effettuare le richieste in modo corretto, utilizzare un software che permette di effettuare richieste HTTP. Nell'esempio successivo, verrà mostrato l'utilizzo di Postman.

# Esempio di inserimento utente

1. Aprire Postman. In caso esso non sia installato, andare sulla [pagina di installazione](https://www.postman.com/downloads/)
2. Aprire una nuova richiesta premendo il pulsante + indicato dalla freccia ![Immagine Postman 1](/assets/images/immagine-postman-1.jpg)
3. Selezionare il metodo POST dal menu a tendina indicato dalla freccia ![Immagine Postman 2](/assets/images/immagine-postman-2.jpg)
4. Inserire il link della richiesta nella barra accanto al menu a tendina.
5. Spostarsi sulla scheda _Body_.
6. Selezionare l'opzione _raw_.
7. Inserire i campi specificati nel paragrafo [_createUser_](#createUser), come in questo esempio ![Immagine Postman 3](/assets/images/immagine-postman-3.jpg)
8. Premere il pulsante _Send_.
9. Attendere il risultato nella console a fondo pagina.
10. In caso di successo, il risultato è simile a questo ![Immagine Postman 4](/assets/images/immagine-postman-4.jpg)

# Esempio di ricerca utente
1. Seguire i primi 2 passi della lista precedente.
2. Selezionare il metodo GET dal menu a tendina mostrato nel passo 3 del passo precedente.
3. Dopo aver inserito il link nella barra, inserire al posto di _{userId}_ l'id numerico scelto (esempio: 1).
4. Premere il pulsante _Send_.
5. Attendere il risultato nella console a fondo pagina.
6. In caso di successo, il risultato è simile a questo ![Immagine Postman 5](/assets/images/immagine-postman-5.jpg)
7. In caso di insuccesso, il risultato è simile a questo ![Immagine Postman 6](/assets/images/immagine-postman-6.jpg)
